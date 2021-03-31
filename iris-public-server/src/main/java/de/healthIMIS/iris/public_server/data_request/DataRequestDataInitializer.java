/*******************************************************************************
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************/
package de.healthIMIS.iris.public_server.data_request;

import static java.time.temporal.ChronoUnit.*;

import de.healthIMIS.iris.public_server.core.DataInitializer;
import de.healthIMIS.iris.public_server.core.Feature;
import de.healthIMIS.iris.public_server.data_request.DataRequest.DataRequestIdentifier;
import de.healthIMIS.iris.public_server.data_request.DataRequest.Status;
import de.healthIMIS.iris.public_server.department.DepartmentDataInitializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataRequestDataInitializer implements DataInitializer {

	public static final DataRequestIdentifier REQ_ID_1 = DataRequestIdentifier
			.of(UUID.fromString("790b9a69-17f8-4ba7-a8ae-2f7bf34e0b80"));
	static final DataRequestIdentifier REQ_ID_2 = DataRequestIdentifier
			.of(UUID.fromString("2707fd28-9b4f-4140-b80e-d56d9aad831f"));
	static final DataRequestIdentifier REQ_ID_3 = DataRequestIdentifier
			.of(UUID.fromString("3907e730-af89-4944-8e75-fbe6ba60c904"));

	private final DataRequestRepository requests;

	/*
	 * (non-Javadoc)
	 * @see quarano.core.DataInitializer#initialize()
	 */
	@Override
	public void initialize() {

		log.debug("Test data: creating data requests …");

		var list = new ArrayList<DataRequest>();

		list.add(new DataRequest(REQ_ID_1, DepartmentDataInitializer.DEPARTMENT_ID_1, Instant.now().minus(2, DAYS), null,
				null, EnumSet.of(Feature.Contacts_Events), Status.DATA_REQUESTED));

		list.add(new DataRequest(REQ_ID_2, DepartmentDataInitializer.DEPARTMENT_ID_1, Instant.now().minus(4, DAYS),
				Instant.now().minus(2, DAYS), null, EnumSet.of(Feature.Contacts_Events), Status.DATA_REQUESTED));

		list.add(new DataRequest(REQ_ID_3, DepartmentDataInitializer.DEPARTMENT_ID_2, Instant.now().minus(4, DAYS),
				Instant.now().minus(2, DAYS), null, EnumSet.of(Feature.Contacts_Events), Status.DATA_REQUESTED));

		requests.saveAll(list);
	}
}
