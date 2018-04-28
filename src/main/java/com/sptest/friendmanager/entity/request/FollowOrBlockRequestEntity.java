package com.sptest.friendmanager.entity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;


@Value
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
@JsonDeserialize(builder = FollowOrBlockRequestEntity.Builder.class)
public class FollowOrBlockRequestEntity {
    private final String requestor;
    private final String target;

    @lombok.Builder(builderClassName = "Builder")
    private FollowOrBlockRequestEntity(String requestor, String target) {
        this.requestor = requestor;
        this.target = target;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
