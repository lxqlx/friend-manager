package com.sptest.friendmanager.entity.response;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Collections;
import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
public class RecipientsListResponseEntity extends GeneralResponseEntity {
    @ApiModelProperty(notes = "List of recipients email addresses")
    private final List<String> recipients;

    public RecipientsListResponseEntity(boolean success, List<String> recipients) {
        super(success);
        this.recipients = recipients == null ?  Collections.emptyList() : Collections.unmodifiableList(recipients);
    }
}
