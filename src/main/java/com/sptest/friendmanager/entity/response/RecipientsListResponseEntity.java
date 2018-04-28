package com.sptest.friendmanager.entity.response;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Collections;
import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
@JsonDeserialize(builder = RecipientsListResponseEntity.Builder.class)
public class RecipientsListResponseEntity extends GeneralResponseEntity {
    private final List<String> recipients;

    @lombok.Builder(builderClassName = "Builder")
    private RecipientsListResponseEntity(boolean success, List<String> recipients) {
        super(success);
        this.recipients = recipients == null ?  Collections.emptyList() : Collections.unmodifiableList(recipients);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder{
    }
}
