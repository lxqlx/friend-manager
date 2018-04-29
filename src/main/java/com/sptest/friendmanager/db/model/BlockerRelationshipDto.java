package com.sptest.friendmanager.db.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "blocker_relationship")
@NoArgsConstructor
@Data
public class BlockerRelationshipDto {
    @EmbeddedId
    RelationshipKey relationshipKey;

    public BlockerRelationshipDto(String email1, String email2) {
        this.relationshipKey = new RelationshipKey(email1, email2);
    }
}