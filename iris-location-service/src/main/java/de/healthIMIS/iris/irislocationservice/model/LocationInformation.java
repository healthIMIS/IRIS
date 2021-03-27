package de.healthIMIS.iris.irislocationservice.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.LocationContact;
import io.swagger.model.LocationContext;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * LocationInformation
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-27T12:26:11.318Z[GMT]")


public class LocationInformation   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("publicKey")
  private String publicKey = null;

  @JsonProperty("contact")
  private LocationContact contact = null;

  @JsonProperty("contexts")
  @Valid
  private List<LocationContext> contexts = null;

  public LocationInformation id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Interne ID (beim Provider)
   * @return id
   **/
  @Schema(example = "5eddd61036d39a0ff8b11fdb", required = true, description = "Interne ID (beim Provider)")
      @NotNull

    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public LocationInformation name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Name des Standorts
   * @return name
   **/
  @Schema(example = "Restaurant Alberts", required = true, description = "Name des Standorts")
      @NotNull

    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocationInformation publicKey(String publicKey) {
    this.publicKey = publicKey;
    return this;
  }

  /**
   * Öffentlicher Schlüssel, ggf. für Nachrichtenaustausch
   * @return publicKey
   **/
  @Schema(description = "Öffentlicher Schlüssel, ggf. für Nachrichtenaustausch")
  
    public String getPublicKey() {
    return publicKey;
  }

  public void setPublicKey(String publicKey) {
    this.publicKey = publicKey;
  }

  public LocationInformation contact(LocationContact contact) {
    this.contact = contact;
    return this;
  }

  /**
   * Get contact
   * @return contact
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public LocationContact getContact() {
    return contact;
  }

  public void setContact(LocationContact contact) {
    this.contact = contact;
  }

  public LocationInformation contexts(List<LocationContext> contexts) {
    this.contexts = contexts;
    return this;
  }

  public LocationInformation addContextsItem(LocationContext contextsItem) {
    if (this.contexts == null) {
      this.contexts = new ArrayList<LocationContext>();
    }
    this.contexts.add(contextsItem);
    return this;
  }

  /**
   * Get contexts
   * @return contexts
   **/
  @Schema(description = "")
      @Valid
    public List<LocationContext> getContexts() {
    return contexts;
  }

  public void setContexts(List<LocationContext> contexts) {
    this.contexts = contexts;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LocationInformation locationInformation = (LocationInformation) o;
    return Objects.equals(this.id, locationInformation.id) &&
        Objects.equals(this.name, locationInformation.name) &&
        Objects.equals(this.publicKey, locationInformation.publicKey) &&
        Objects.equals(this.contact, locationInformation.contact) &&
        Objects.equals(this.contexts, locationInformation.contexts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, publicKey, contact, contexts);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LocationInformation {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    publicKey: ").append(toIndentedString(publicKey)).append("\n");
    sb.append("    contact: ").append(toIndentedString(contact)).append("\n");
    sb.append("    contexts: ").append(toIndentedString(contexts)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
