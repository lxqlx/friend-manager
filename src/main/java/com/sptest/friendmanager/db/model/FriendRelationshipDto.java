package com.sptest.friendmanager.db.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "friend_relationship")
public class FriendRelationshipDto extends RelationshipDto {
    public FriendRelationshipDto(String email1, String email2) {
        super(email1, email2);
    }
}
