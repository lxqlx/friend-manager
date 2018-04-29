package com.sptest.friendmanager.entity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
@JsonDeserialize(builder = EmailRequestEntity.EmailRequestEntityBuilder.class)
public class EmailRequestEntity {
    @ApiModelProperty(notes = "Email address you want to retrieve friends list for")
    private final String email;

    @lombok.Builder
    private EmailRequestEntity(String email) {
        this.email = email;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class EmailRequestEntityBuilder {
    }
}
