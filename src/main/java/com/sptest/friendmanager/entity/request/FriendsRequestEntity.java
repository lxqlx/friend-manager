package com.sptest.friendmanager.entity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Collections;
import java.util.List;


@Value
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
@JsonDeserialize(builder = FriendsRequestEntity.FriendsRequestEntityBuilder.class)
public class FriendsRequestEntity {
    @ApiModelProperty(notes = "Put exactly 2 different valid email addresses")
    private List<String> friends;

    @lombok.Builder
    private FriendsRequestEntity(List<String> friends) {
        this.friends = Collections.unmodifiableList(friends);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class FriendsRequestEntityBuilder {
    }
}
