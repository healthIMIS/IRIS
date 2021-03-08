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

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import de.healthIMIS.iris.public_server.core.Aggregate;
import de.healthIMIS.iris.public_server.core.Feature;
import de.healthIMIS.iris.public_server.core.Id;
import de.healthIMIS.iris.public_server.department.Department.DepartmentIdentifier;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * A data request from the health department to a citizen or event/location operator.
 * 
 * @author Jens Kutzsche
 */
@Entity
@Table(name = "data_request")
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@Getter
@Setter(AccessLevel.PACKAGE)
public class DataRequest extends Aggregate<DataRequest, DataRequest.DataRequestIdentifier> {

	private DepartmentIdentifier departmentId;

	private String checkCodeName;
	private String checkCodeDayOfBirth;
	private String checkCodeRandom;

	private Instant requestStart;
	private Instant requestEnd;

	@Enumerated(EnumType.STRING)
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "data_request_feature", joinColumns = @JoinColumn(name = "request_id"))
	@Column(name = "feature", nullable = false)
	private Set<Feature> features;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status = Status.Open;

	public DataRequest(
		DataRequestIdentifier id,
		DepartmentIdentifier departmentId,
		String checkCodeName,
		String checkCodeDayOfBirth,
		String checkCodeRandom,
		Instant requestStart,
		Instant requestEnd,
		Set<Feature> features,
		Status status) {

		super();

		this.id = id;
		this.departmentId = departmentId;
		this.checkCodeName = checkCodeName;
		this.checkCodeDayOfBirth = checkCodeDayOfBirth;
		this.checkCodeRandom = checkCodeRandom;
		this.requestStart = requestStart;
		this.requestEnd = requestEnd;
		this.features = features;
		this.status = status;
	}

	@Embeddable
	@EqualsAndHashCode
	@RequiredArgsConstructor(staticName = "of")
	@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
	public static class DataRequestIdentifier implements Id, Serializable {

		private static final long serialVersionUID = -8254677010830428881L;

		final UUID requestId;

		/**
		 * for JSON deserialization
		 */
		public static DataRequestIdentifier of(String uuid) {
			return of(UUID.fromString(uuid));
		}

		@Override
		public String toString() {
			return requestId.toString();
		}
	}

	public enum Status {
		Open
	}
}
