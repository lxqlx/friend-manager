package com.sptest.friendmanager.entity.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Collections;
import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
public class FriendListResponseEntity extends GeneralResponseEntity {
    @ApiModelProperty(notes = "List of email addresses")
    private final List<String> friends;

    @ApiModelProperty(notes = "Size of the list")
    private final int count;

    public FriendListResponseEntity(boolean success, List<String> friends) {
        super(success);
        this.friends = friends == null ? Collections.emptyList() : Collections.unmodifiableList(friends);
        count = friends.size();
    }
}
