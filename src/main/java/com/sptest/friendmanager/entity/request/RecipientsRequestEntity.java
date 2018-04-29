package com.sptest.friendmanager.entity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;


@Value
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
@JsonDeserialize(builder = RecipientsRequestEntity.RecipientsRequestEntityBuilder.class)
public class RecipientsRequestEntity {

    @ApiModelProperty(notes = "Sender email address, put valid email address",
            example = "andy@example.com")
    private final String sender;

    @ApiModelProperty(notes = "The update sent by sender",
            example = "Hi john@example.com")
    private final String text;

    @lombok.Builder
    private RecipientsRequestEntity(String sender, String text) {
        this.sender = sender;
        this.text = text;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class RecipientsRequestEntityBuilder {
    }
}
