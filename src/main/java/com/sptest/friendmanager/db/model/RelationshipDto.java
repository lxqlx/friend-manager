package com.sptest.friendmanager.db.model;

import javax.management.relation.Relation;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
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
