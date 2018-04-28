package com.sptest.friendmanager.db.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "follower_relationship")
public class FollowerRelationshipDto extends RelationshipDto {
    public FollowerRelationshipDto(String email1, String email2) {
        super(email1, email2);
    }
}
