package com.sptest.friendmanager.entity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;


@Value
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
@JsonDeserialize(builder = RecipientsRequestEntity.RecipientsRequestEntityBuilder.class)
public class RecipientsRequestEntity {
    private final String sender;
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
