package com.sptest.friendmanager.util;

import com.sptest.friendmanager.db.model.FriendRelationshipDto;
import com.sptest.friendmanager.db.model.RelationshipKey;
import com.sptest.friendmanager.entity.response.FriendListResponseEntity;
import com.sptest.friendmanager.entity.response.GeneralResponseEntity;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.function.Supplier;
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
        return new FriendListResponseEntity(true, friendsList);
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
        return new FriendListResponseEntity(true, result);

    }

    public static boolean isEmailListValid(List<String> emails, GeneralResponseEntity.GeneralResponseEntityBuilder resultBuilder) {
        if (emails.size() != 2) {
            resultBuilder.success(false).errorMessage("Illegal Arguments: Expecting 2 emails in the list.");
            return false;
        }
        Set<String> emailsSet = new HashSet<>();
        for (String email : emails) {
            if (emailsSet.contains(email)) {
                resultBuilder.success(false).errorMessage("Illegal Arguments: Duplicated email.");
                return false;
            }

            if (!isEmailValid(email, resultBuilder)) {
                return false;
            }

            emailsSet.add(email);
        }
        resultBuilder.success(true);
        return true;
    }

    public static boolean isEmailValid(String email, GeneralResponseEntity.GeneralResponseEntityBuilder resultBuilder) {
        if (email == null || email.isEmpty()) {
            resultBuilder.success(false).errorMessage("Illegal Arguments: empty email");
            return false;
        }
        if (!EmailValidator.getInstance().isValid(email)) {
            resultBuilder.success(false).errorMessage("Illegal Arguments: invalid  email");
            return false;
        }
        resultBuilder.success(true);
        return true;
    }

    public static ResponseEntity<?> tryToReturn(Supplier<?> supplier) {
        try {
            return ResponseEntity.ok(supplier.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(failureResponseWithErrorMessage(e.getMessage()));
        }
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
