package com.sptest.friendmanager.db.model;

import javax.management.relation.Relation;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public abstract class RelationshipDto {
    @EmbeddedId
    RelationshipKey relationshipKey;

    public RelationshipDto() {}

    public RelationshipDto(String email1, String email2) {
        this.relationshipKey = new RelationshipKey(email1, email2);
    }

    public RelationshipDto(RelationshipKey key) {
        this.relationshipKey = key;
    }

    public RelationshipKey getRelationshipKey() {
        return relationshipKey;
    }

    public void setRelationshipKey(RelationshipKey key) {
        this.relationshipKey = key;
    }
}
