package com.sptest.friendmanager.Util;

import com.sptest.friendmanager.db.model.FriendRelationshipDto;
import com.sptest.friendmanager.db.model.RelationshipKey;
import com.sptest.friendmanager.entity.response.FriendListResponseEntity;
import com.sptest.friendmanager.entity.response.GeneralResponseEntity;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;

import java.util.*;
import java.util.stream.Collectors;

public class DtoToEntityUtils {
    public static FriendListResponseEntity toFriendsListResponseEntity(List<FriendRelationshipDto> dtoList) {
        List<String> friendsList = dtoList
                .stream()
                .map(FriendRelationshipDto::getRelationshipKey)
                .map(RelationshipKey::getTargetEmail)
                .collect(Collectors.toList());
        return FriendListResponseEntity.builder().success(true).friends(friendsList).build();
    }

    public static GeneralResponseEntity failureResponseWithErrorMessage(String message) {
        return GeneralResponseEntity.builder().success(false).errorMessage(message).build();
    }

    public static GeneralResponseEntity successResponse() {
        return GeneralResponseEntity.builder().success(true).build();
    }

    public static FriendListResponseEntity toCommonFriendsListResponseEntity(List<FriendRelationshipDto> dtoList1
            , List<FriendRelationshipDto> dtoList2) {
        Set<String> set = dtoList1.stream()
                .map(FriendRelationshipDto::getRelationshipKey)
                .map(RelationshipKey::getTargetEmail)
                .collect(Collectors.toSet());
        List<String> result = dtoList2.stream()
                .map(FriendRelationshipDto::getRelationshipKey)
                .map(RelationshipKey::getTargetEmail)
                .filter(set::contains).collect(Collectors.toList());
        return FriendListResponseEntity.builder().success(true).friends(result).build();

    }

    public static GeneralResponseEntity validateArguments(String... emails) {
        Set<String> emailsSet = new HashSet<>();
        for (String email : emails) {
            if (emailsSet.contains(email)) {
                return DtoToEntityUtils.failureResponseWithErrorMessage("Illegal Arguments: duplicated user id.");
            }
            if (email == null || email.isEmpty()) {
                return DtoToEntityUtils.failureResponseWithErrorMessage("Illegal Arguments: empty user id");
            }
            emailsSet.add(email);
        }
        return DtoToEntityUtils.successResponse();
    }

    public static List<String> extractEmails(String text) {
        return null;
    }
}
