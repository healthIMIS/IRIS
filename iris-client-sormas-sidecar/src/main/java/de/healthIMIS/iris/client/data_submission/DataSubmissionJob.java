/*******************************************************************************
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************/
package de.healthIMIS.iris.client.data_submission;

import de.healthIMIS.iris.client.core.IrisClientProperties;
import de.healthIMIS.iris.client.core.IrisProperties;
import de.healthIMIS.iris.client.core.sync.SyncTimes;
import de.healthIMIS.iris.client.core.sync.SyncTimesRepository;
import de.healthIMIS.iris.client.data_request.DataRequestManagement;
import de.healthIMIS.iris.client.data_submission.service.DataSubmissionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.KeyStore;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Jens Kutzsche
 */
@Component
@Slf4j
@Profile("!inttest")
@RequiredArgsConstructor
class DataSubmissionJob {

	private final @NonNull SyncTimesRepository syncTimes;
	private final @NonNull DataRequestManagement dataRequests;
	private final @Qualifier("iris-rest") @NonNull RestTemplate rest;
	private final @NonNull IrisClientProperties clientProperties;
	private final @NonNull IrisProperties properties;
	private final @NonNull ObjectMapper mapper;
	private final @NonNull KeyStore keyStore;
	private final @NonNull ModelMapper modelMapper;
	private final @NonNull DataSubmissionRepository submissions;
	private final @NonNull DataSubmissionService dataSubmissionService;

	private long errorCounter = 0;

	@Scheduled(fixedDelay = 15000)
	void run() {

		log.trace("Submission job - start");

		var lastSync = syncTimes.findById(SyncTimes.DataTypes.Submissions).map(SyncTimes::getLastSync)
				.orElse(Instant.ofEpochSecond(0));

		try {

			log.trace("Submission job - GET to server is sent");
			var response = rest.getForEntity("https://{address}:{port}/hd/data-submissions?departmentId={depId}&from={from}",
					DataSubmissionDto[].class, properties.getServerAddress().getHostName(), properties.getServerPort(),
					clientProperties.getClientId(), lastSync);

			handleDtos(response.getBody());

			saveLastSync(response);

			log.debug("Submission job - GET to public server sent: {}",
					Arrays.stream(response.getBody()).map(it -> it.getRequestId().toString()).collect(Collectors.joining(", ")));

			dataSubmissionService.deleteDataSubmissionFromServer(Arrays.asList(response.getBody().clone()));

			errorCounter = 0;

		} catch (RestClientException e) {

			errorCounter++;

			e.printStackTrace();

			if (errorCounter == 1 || errorCounter % 20 == 0) {
				if (log.isTraceEnabled()) {
					log.warn(
							String.format("Submission job - can't submit data submissions from server for %s tries!", errorCounter),
							e);
				} else {
					log.warn("Submission job - can't submit data submissions from server for {} tries!", errorCounter);
				}
			}
		}
	}

	private void handleDtos(DataSubmissionDto[] dtos) {
		Arrays.stream(dtos).map(this::mapToStrategie).forEach(DataSubmissionProcessor::process);
	}

	private DataSubmissionProcessor<?> mapToStrategie(DataSubmissionDto it) {

		var request = dataRequests.findById(it.getRequestId()).get();

		switch (it.getFeature()) {
			case Contacts_Events:
				return new ContactsEventsSubmissionProcessor(it, request, keyStore, mapper);
			// return new ContactsEventsSubmissionProcessor(it, request, keyStore, mapper, sormasTaskApi, sormasPersonApi,
			// sormasContactApi, sormasEventApi, sormasParticipantApi);
			case Guests:
				return new GuestsSubmissionProcessor(it, request, keyStore, mapper, modelMapper, submissions, dataRequests);
			// return new GuestsSubmissionProcessor(it, request, keyStore, mapper, sormasTaskApi, sormasParticipantApi,
			// sormasPersonApi);
			default:
				return null;
		}
	}

	private void saveLastSync(ResponseEntity<DataSubmissionDto[]> response) {

		var lastModified = response.getHeaders().getLastModified();

		var lastSync = Instant.ofEpochMilli(lastModified);

		syncTimes.save(SyncTimes.of(SyncTimes.DataTypes.Submissions, lastSync));
	}
}
