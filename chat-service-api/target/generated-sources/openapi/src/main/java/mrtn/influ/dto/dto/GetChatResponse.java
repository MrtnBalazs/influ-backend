package mrtn.influ.dto.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * GetChatResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-12-19T14:18:12.532393300+01:00[Europe/Budapest]")
public class GetChatResponse {

  @Valid
  private List<@Valid Chat> chatList = new ArrayList<>();

  public GetChatResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public GetChatResponse(List<@Valid Chat> chatList) {
    this.chatList = chatList;
  }

  public GetChatResponse chatList(List<@Valid Chat> chatList) {
    this.chatList = chatList;
    return this;
  }

  public GetChatResponse addChatListItem(Chat chatListItem) {
    if (this.chatList == null) {
      this.chatList = new ArrayList<>();
    }
    this.chatList.add(chatListItem);
    return this;
  }

  /**
   * Get chatList
   * @return chatList
  */
  @NotNull @Valid 
  @Schema(name = "chatList", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("chatList")
  public List<@Valid Chat> getChatList() {
    return chatList;
  }

  public void setChatList(List<@Valid Chat> chatList) {
    this.chatList = chatList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetChatResponse getChatResponse = (GetChatResponse) o;
    return Objects.equals(this.chatList, getChatResponse.chatList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(chatList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetChatResponse {\n");
    sb.append("    chatList: ").append(toIndentedString(chatList)).append("\n");
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

