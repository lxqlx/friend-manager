package com.sptest.friendmanager.entity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;


@Value
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
@JsonDeserialize(builder = FollowOrBlockRequestEntity.FollowOrBlockRequestEntityBuilder.class)
public class FollowOrBlockRequestEntity {

    @ApiModelProperty(notes = "Requester Email Address, put a valid email different from target address")
    private final String requestor;

    @ApiModelProperty(notes = "Target Email Address, put a valid email different from requester address")
    private final String target;

    @lombok.Builder
    private FollowOrBlockRequestEntity(String requestor, String target) {
        this.requestor = requestor;
        this.target = target;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class FollowOrBlockRequestEntityBuilder {
    }
}
