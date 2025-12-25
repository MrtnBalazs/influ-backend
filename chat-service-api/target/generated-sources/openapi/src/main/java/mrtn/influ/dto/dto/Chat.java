package mrtn.influ.dto.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * Chat
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-12-19T14:18:12.532393300+01:00[Europe/Budapest]")
public class Chat {

  private Integer id;

  private String sourceId;

  private String destinationId;

  private String text;

  public Chat() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Chat(Integer id, String sourceId, String destinationId, String text) {
    this.id = id;
    this.sourceId = sourceId;
    this.destinationId = destinationId;
    this.text = text;
  }

  public Chat id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @NotNull 
  @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Chat sourceId(String sourceId) {
    this.sourceId = sourceId;
    return this;
  }

  /**
   * Get sourceId
   * @return sourceId
  */
  @NotNull 
  @Schema(name = "sourceId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("sourceId")
  public String getSourceId() {
    return sourceId;
  }

  public void setSourceId(String sourceId) {
    this.sourceId = sourceId;
  }

  public Chat destinationId(String destinationId) {
    this.destinationId = destinationId;
    return this;
  }

  /**
   * Get destinationId
   * @return destinationId
  */
  @NotNull 
  @Schema(name = "destinationId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("destinationId")
  public String getDestinationId() {
    return destinationId;
  }

  public void setDestinationId(String destinationId) {
    this.destinationId = destinationId;
  }

  public Chat text(String text) {
    this.text = text;
    return this;
  }

  /**
   * Get text
   * @return text
  */
  @NotNull 
  @Schema(name = "text", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("text")
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Chat chat = (Chat) o;
    return Objects.equals(this.id, chat.id) &&
        Objects.equals(this.sourceId, chat.sourceId) &&
        Objects.equals(this.destinationId, chat.destinationId) &&
        Objects.equals(this.text, chat.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, sourceId, destinationId, text);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Chat {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    sourceId: ").append(toIndentedString(sourceId)).append("\n");
    sb.append("    destinationId: ").append(toIndentedString(destinationId)).append("\n");
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
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

