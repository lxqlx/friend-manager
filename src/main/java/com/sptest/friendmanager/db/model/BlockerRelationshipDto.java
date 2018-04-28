package com.sptest.friendmanager.db.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "blocker_relationship")
public class BlockerRelationshipDto extends RelationshipDto {
    public BlockerRelationshipDto(String email1, String email2) {
        super(email1, email2);
    }
}
