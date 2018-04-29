package com.sptest.friendmanager.controller;

import com.sptest.friendmanager.entity.response.FriendListResponseEntity;
import com.sptest.friendmanager.entity.response.RecipientsListResponseEntity;
import com.sptest.friendmanager.util.FriendManagerUtils;
import com.sptest.friendmanager.entity.request.EmailRequestEntity;
import com.sptest.friendmanager.entity.request.FollowOrBlockRequestEntity;
import com.sptest.friendmanager.entity.request.FriendsRequestEntity;
import com.sptest.friendmanager.entity.request.RecipientsRequestEntity;
import com.sptest.friendmanager.entity.response.GeneralResponseEntity;
import com.sptest.friendmanager.service.RelationshipService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RestController
public class FriendsController {
    private final RelationshipService relationshipService;

    @Autowired
    public FriendsController(RelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/friends", produces = "application/json")
    @ApiOperation(value = "Create a friend connection between two email addresses.", response = GeneralResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully create the friend connection"),
            @ApiResponse(code = 400, message = "Bad Request: Illegal Arguments e.g. invalid emails, duplicated emails."),
            @ApiResponse(code = 500, message = "Server error")
    }
    )
    public ResponseEntity<?> addFriends(@RequestBody FriendsRequestEntity friendsRequestEntity) {
        List<String> friends = friendsRequestEntity.getFriends();
        GeneralResponseEntity.GeneralResponseEntityBuilder resultBuilder = GeneralResponseEntity.builder();
        if (!FriendManagerUtils.isEmailListValid(friends, resultBuilder)) {
            return ResponseEntity.badRequest().body(resultBuilder.build());
        }

        return FriendManagerUtils.tryToReturn(() -> relationshipService.addFriends(friends.get(0), friends.get(1)));
    }


    @RequestMapping(method = RequestMethod.POST, path = "/friends", produces = "application/json")
    @ApiOperation(value = "Retrieve the friends list for an email address.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the friends list",
                    response = FriendListResponseEntity.class),
            @ApiResponse(code = 400, message = "Bad Request: Illegal Arguments e.g. invalid email",
                    response = GeneralResponseEntity.class),
            @ApiResponse(code = 500, message = "Server error",
                    response = GeneralResponseEntity.class)
    }
    )
    public ResponseEntity<?> getFriends(@RequestBody EmailRequestEntity emailRequestEntity) {
        String email = emailRequestEntity.getEmail();

        GeneralResponseEntity.GeneralResponseEntityBuilder resultBuilder = GeneralResponseEntity.builder();
        if (!FriendManagerUtils.isEmailValid(email, resultBuilder)) {
            return ResponseEntity.badRequest().body(resultBuilder.build());
        }

        return FriendManagerUtils.tryToReturn(() -> relationshipService.getFriends(email));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/common-friends", produces = "application/json")
    @ApiOperation(value = "Retrieve the common friends list between two email addresses")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the common friends list",
                    response = FriendListResponseEntity.class),
            @ApiResponse(code = 400, message = "Bad Request: Illegal Arguments e.g. invalid email",
                    response = GeneralResponseEntity.class),
            @ApiResponse(code = 500, message = "Server error",
                    response = GeneralResponseEntity.class)
    }
    )
    public ResponseEntity<?> getCommonFriends(@RequestBody FriendsRequestEntity friendsRequestEntity) {
        List<String> friends = friendsRequestEntity.getFriends();

        GeneralResponseEntity.GeneralResponseEntityBuilder resultBuilder = GeneralResponseEntity.builder();
        if (!FriendManagerUtils.isEmailListValid(friends, resultBuilder)) {
            return ResponseEntity.badRequest().body(resultBuilder.build());
        }

        return FriendManagerUtils.tryToReturn(() -> relationshipService.getCommonFriends(friends.get(0), friends.get(1)));
    }


    @RequestMapping(method = RequestMethod.PUT, path = "/followers", produces = "application/json")
    @ApiOperation(value = "Subscribe to updates from an email address", response = GeneralResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully subscribed to the email address"),
            @ApiResponse(code = 400, message = "Bad Request: Illegal Arguments e.g. invalid emails, duplicated emails."),
            @ApiResponse(code = 500, message = "Server error")
    }
    )
    public ResponseEntity<?> addFollowers(@RequestBody FollowOrBlockRequestEntity followOrBlockRequestEntity) {
        String requestor = followOrBlockRequestEntity.getRequestor();
        String target = followOrBlockRequestEntity.getTarget();

        GeneralResponseEntity.GeneralResponseEntityBuilder resultBuilder = GeneralResponseEntity.builder();
        if (!FriendManagerUtils.isEmailValid(requestor, resultBuilder) || !FriendManagerUtils.isEmailValid(target, resultBuilder)) {
            return ResponseEntity.badRequest().body(resultBuilder.build());
        }

        return FriendManagerUtils.tryToReturn(() -> relationshipService.follow(requestor, target));
    }


    @RequestMapping(method = RequestMethod.PUT, path = "/blockers", produces = "application/json")
    @ApiOperation(value = "Block updates from an email address", response = GeneralResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully blocked the email address"),
            @ApiResponse(code = 400, message = "Bad Request: Illegal Arguments e.g. invalid emails, duplicated emails."),
            @ApiResponse(code = 500, message = "Server error")
    }
    )
    public ResponseEntity<?> addBlockers(@RequestBody FollowOrBlockRequestEntity followOrBlockRequestEntity) {
        String requestor = followOrBlockRequestEntity.getRequestor();
        String target = followOrBlockRequestEntity.getTarget();

        GeneralResponseEntity.GeneralResponseEntityBuilder resultBuilder = GeneralResponseEntity.builder();
        if (!FriendManagerUtils.isEmailValid(requestor, resultBuilder) || !FriendManagerUtils.isEmailValid(target, resultBuilder)) {
            return ResponseEntity.badRequest().body(resultBuilder.build());
        }

        return FriendManagerUtils.tryToReturn(() -> relationshipService.block(requestor, target));
    }


    @RequestMapping(method = RequestMethod.POST, path = "/recipients", produces = "application/json")
    @ApiOperation(value = "Retrieve all email addresses that can receive updates from an email address")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all email addresses",
                    response = RecipientsListResponseEntity.class),
            @ApiResponse(code = 400, message = "Bad Request: Illegal Arguments e.g. invalid email",
                    response = GeneralResponseEntity.class),
            @ApiResponse(code = 500, message = "Server error",
                    response = GeneralResponseEntity.class)
    }
    )
    public ResponseEntity<?> getRecipients(@RequestBody RecipientsRequestEntity recipientsRequestEntity) {
        String sender = recipientsRequestEntity.getSender();
        String text = recipientsRequestEntity.getText();

        GeneralResponseEntity.GeneralResponseEntityBuilder resultBuilder = GeneralResponseEntity.builder();
        if (!FriendManagerUtils.isEmailValid(sender, resultBuilder)) {
            return ResponseEntity.badRequest().body(resultBuilder.build());
        }
        if (text == null || text.isEmpty()) {
            return ResponseEntity.badRequest().body(resultBuilder.errorMessage("Illegal Arguments: null or empty text"));
        }

        return FriendManagerUtils.tryToReturn(() -> relationshipService.getRecipients(sender, text));
    }
}
