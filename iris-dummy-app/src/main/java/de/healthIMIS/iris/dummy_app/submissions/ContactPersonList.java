package de.healthIMIS.iris.dummy_app.submissions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A collection of contact persons who had contact with the queried person during the queried time. This data must be encrypted with the key
 * of health department from DataRequest.keyOfHealthDepartment!
 */

public class ContactPersonList {

	@JsonProperty("contactPersons")
	private List<ContactPerson> contactPersons = new ArrayList<ContactPerson>();

	@JsonProperty("dataProvider")
	private ContactPersonListDataProvider dataProvider = null;

	@JsonProperty("startDate")
	private LocalDate startDate = null;

	@JsonProperty("endDate")
	private LocalDate endDate = null;

	public ContactPersonList contactPersons(List<ContactPerson> contactPersons) {
		this.contactPersons = contactPersons;
		return this;
	}

	public ContactPersonList addContactPersonsItem(ContactPerson contactPersonsItem) {
		this.contactPersons.add(contactPersonsItem);
		return this;
	}

	/**
	 * Get contactPersons
	 * 
	 * @return contactPersons
	 **/
	public List<ContactPerson> getContactPersons() {
		return contactPersons;
	}

	public void setContactPersons(List<ContactPerson> contactPersons) {
		this.contactPersons = contactPersons;
	}

	public ContactPersonList dataProvider(ContactPersonListDataProvider dataProvider) {
		this.dataProvider = dataProvider;
		return this;
	}

	/**
	 * Get dataProvider
	 * 
	 * @return dataProvider
	 **/

	public ContactPersonListDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(ContactPersonListDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public ContactPersonList startDate(LocalDate startDate) {
		this.startDate = startDate;
		return this;
	}

	/**
	 * Start date of contacts for this list.
	 * 
	 * @return startDate
	 **/
	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public ContactPersonList endDate(LocalDate endDate) {
		this.endDate = endDate;
		return this;
	}

	/**
	 * End date of contacts for this list.
	 * 
	 * @return endDate
	 **/
	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ContactPersonList contactPersonList = (ContactPersonList) o;
		return Objects.equals(this.contactPersons, contactPersonList.contactPersons)
			&& Objects.equals(this.dataProvider, contactPersonList.dataProvider)
			&& Objects.equals(this.startDate, contactPersonList.startDate)
			&& Objects.equals(this.endDate, contactPersonList.endDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(contactPersons, dataProvider, startDate, endDate);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ContactPersonList {\n");

		sb.append("    contactPersons: ").append(toIndentedString(contactPersons)).append("\n");
		sb.append("    dataProvider: ").append(toIndentedString(dataProvider)).append("\n");
		sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
		sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
