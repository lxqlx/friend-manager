package com.sptest.friendmanager.db.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RelationshipKey implements Serializable {

    @Column(name = "requestor_email")
    @Email
    @NotNull
    private String requestEmail;

    @Column(name = "target_email")
    @Email
    @NotNull
    private String targetEmail;

    public RelationshipKey() {
    }

    public RelationshipKey(String requestEmail, String targetEmail) {
        this.requestEmail = requestEmail;
        this.targetEmail = targetEmail;
    }

    public String getRequestEmail() {
        return requestEmail;
    }

    public String getTargetEmail() {
        return targetEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RelationshipKey)) return false;
        RelationshipKey that = (RelationshipKey) o;
        return Objects.equals(getRequestEmail(), that.getRequestEmail()) &&
                Objects.equals(getTargetEmail(), that.getTargetEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRequestEmail(), getTargetEmail());
    }
}
