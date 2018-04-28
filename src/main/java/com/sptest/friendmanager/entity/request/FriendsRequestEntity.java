package com.sptest.friendmanager.entity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

import java.util.Collections;
import java.util.List;


@Value
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
@JsonDeserialize(builder = FriendsRequestEntity.FriendsRequestEntityBuilder.class)
public class FriendsRequestEntity {
    private List<String> friends;

    @lombok.Builder
    private FriendsRequestEntity(List<String> friends) {
        this.friends = Collections.unmodifiableList(friends);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class FriendsRequestEntityBuilder {
    }
}
