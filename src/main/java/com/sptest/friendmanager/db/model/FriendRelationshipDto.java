package com.sptest.friendmanager.db.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "friend_relationship")
@NoArgsConstructor
@Data
public class FriendRelationshipDto {
    @EmbeddedId
    RelationshipKey relationshipKey;

    public FriendRelationshipDto(String email1, String email2) {
        this.relationshipKey = new RelationshipKey(email1, email2);
    }
}
