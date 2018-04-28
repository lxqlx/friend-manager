package com.sptest.friendmanager.entity.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
@JsonDeserialize(builder = GeneralResponseEntity.Builder.class)
public class GeneralResponseEntity {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private final boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String errorMessage;

    protected GeneralResponseEntity(boolean success) {
        this.success = success;
        this.errorMessage = null;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @lombok.Builder(builderClassName = "Builder")
    private GeneralResponseEntity(boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder{
    }
}
