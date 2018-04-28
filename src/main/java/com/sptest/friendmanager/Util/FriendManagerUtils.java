package com.sptest.friendmanager.Util;

import com.sptest.friendmanager.db.model.FriendRelationshipDto;
import com.sptest.friendmanager.db.model.RelationshipKey;
import com.sptest.friendmanager.entity.response.FriendListResponseEntity;
import com.sptest.friendmanager.entity.response.GeneralResponseEntity;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FriendManagerUtils {
    private static Pattern SIMPLE_EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9-_.]+@[a-zA-Z0-9-_.]+");

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
                return FriendManagerUtils.failureResponseWithErrorMessage("Illegal Arguments: duplicated email.");
            }
            if (email == null || email.isEmpty()) {
                return FriendManagerUtils.failureResponseWithErrorMessage("Illegal Arguments: empty email");
            }
            if (EmailValidator.getInstance().isValid(email)) {
                return FriendManagerUtils.failureResponseWithErrorMessage("Illegal Arguments: invalid  email");
            }

            emailsSet.add(email);
        }
        return FriendManagerUtils.successResponse();
    }

    public static List<String> extractEmails(String text) {
        Set<String> emailSet = new HashSet<>();
        Matcher matcher = SIMPLE_EMAIL_PATTERN.matcher(text);
        EmailValidator validator = EmailValidator.getInstance();
        String currentEmail;
        while(matcher.find()) {
            currentEmail = matcher.group();
            if (validator.isValid(currentEmail)) {
                emailSet.add(matcher.group());
            }
        }
        return new ArrayList<>(emailSet);
    }
}
