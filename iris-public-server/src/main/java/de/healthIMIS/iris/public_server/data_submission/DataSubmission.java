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
package de.healthIMIS.iris.public_server.data_submission;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;

import de.healthIMIS.iris.public_server.core.Aggregate;
import de.healthIMIS.iris.public_server.core.Feature;
import de.healthIMIS.iris.public_server.core.Id;
import de.healthIMIS.iris.public_server.data_request.DataRequest.DataRequestIdentifier;
import de.healthIMIS.iris.public_server.department.Department.DepartmentIdentifier;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * A data submission from an app of a citizen or event/location operator to the health department.
 * 
 * @author Jens Kutzsche
 */
@Entity
@Table(name = "data_submission")
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@Getter
@Setter(AccessLevel.PACKAGE)
public class DataSubmission extends Aggregate<DataSubmission, DataSubmission.DataSubmissionIdentifier> {

	private DataRequestIdentifier requestId;
	private DepartmentIdentifier departmentId;

	private String secret;
	private String keyReferenz;
	private @Lob String encryptedData;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Feature feature;

	public DataSubmission(
		DataRequestIdentifier requestId,
		DepartmentIdentifier departmentId,
		String secret,
		String keyReferenz,
		String encryptedData,
		Feature feature) {

		super();

		this.id = DataSubmissionIdentifier.random();
		this.requestId = requestId;
		this.departmentId = departmentId;
		this.secret = secret;
		this.keyReferenz = keyReferenz;
		this.encryptedData = encryptedData;
		this.feature = feature;
	}

	@Embeddable
	@EqualsAndHashCode
	@RequiredArgsConstructor(staticName = "of")
	@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
	public static class DataSubmissionIdentifier implements Id, Serializable {

		private static final long serialVersionUID = -8254677010830428881L;

		final UUID submissionId;

		static DataSubmissionIdentifier random() {
			return DataSubmissionIdentifier.of(UUID.randomUUID());
		}

		@Override
		public String toString() {
			return submissionId.toString();
		}
	}
}
