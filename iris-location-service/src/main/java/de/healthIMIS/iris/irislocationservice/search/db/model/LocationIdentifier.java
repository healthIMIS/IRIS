package de.healthIMIS.iris.irislocationservice.search.db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LocationIdentifier implements Serializable {

	private static final long serialVersionUID = -1165343189152677433L;

	private String providerId;

	private String locationId;

}
