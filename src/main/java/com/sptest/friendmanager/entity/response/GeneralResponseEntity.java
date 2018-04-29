package com.sptest.friendmanager.entity.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
public class GeneralResponseEntity {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private final boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String errorMessage;

    GeneralResponseEntity(boolean success) {
        this.success = success;
        this.errorMessage = null;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @lombok.Builder
    private GeneralResponseEntity(boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }
}
