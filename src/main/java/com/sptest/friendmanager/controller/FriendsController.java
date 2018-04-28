package com.sptest.friendmanager.controller;

import com.sptest.friendmanager.Util.FriendManagerUtils;
import com.sptest.friendmanager.entity.request.EmailRequestEntity;
import com.sptest.friendmanager.entity.request.FollowOrBlockRequestEntity;
import com.sptest.friendmanager.entity.request.FriendsRequestEntity;
import com.sptest.friendmanager.entity.request.RecipientsRequestEntity;
import com.sptest.friendmanager.entity.response.GeneralResponseEntity;
import com.sptest.friendmanager.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;

@RestController
public class FriendsController {
    @Autowired
    RelationshipService relationshipService;

    @RequestMapping(method = RequestMethod.PUT, path = "/friends")
    public ResponseEntity<GeneralResponseEntity> addFriends(@RequestBody FriendsRequestEntity friendsRequestEntity) {
        List<String> friends = friendsRequestEntity.getFriends();
        if (friends.size() != 2) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(FriendManagerUtils.failureResponseWithErrorMessage("Illegal Number of Emails"));
        }

        GeneralResponseEntity generalResponseEntity = FriendManagerUtils.validateEmailList(friends);
        if (!generalResponseEntity.isSuccess()) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(generalResponseEntity);
        }

        return ResponseEntity.ok(relationshipService.addFriends(friends.get(0), friends.get(1)));
    }


    @RequestMapping(method = RequestMethod.GET, path = "/friends")
    public ResponseEntity<?> getFriends(@RequestBody EmailRequestEntity emailRequestEntity) {
        String email = emailRequestEntity.getEmail();

        GeneralResponseEntity generalResponseEntity = FriendManagerUtils.validateEmailList(Arrays.asList(email));
        if (!generalResponseEntity.isSuccess()) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(generalResponseEntity);
        }

        return ResponseEntity.ok(relationshipService.getFriends(email));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/common-friends")
    public ResponseEntity<?> getFriends(@RequestBody FriendsRequestEntity friendsRequestEntity) {
        List<String> friends = friendsRequestEntity.getFriends();
        if (friends.size() != 2) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(FriendManagerUtils.failureResponseWithErrorMessage("Illegal Number of Emails"));
        }

        GeneralResponseEntity generalResponseEntity = FriendManagerUtils.validateEmailList(friends);
        if (!generalResponseEntity.isSuccess()) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(generalResponseEntity);
        }

        return ResponseEntity.ok(relationshipService.getCommonFriends(friends.get(0), friends.get(1)));
    }


    @RequestMapping(method = RequestMethod.PUT, path = "/followers")
    public ResponseEntity<?> addFollowers(@RequestBody FollowOrBlockRequestEntity followOrBlockRequestEntity) {
        String requestor = followOrBlockRequestEntity.getRequestor();
        String target = followOrBlockRequestEntity.getTarget();
        GeneralResponseEntity generalResponseEntity = FriendManagerUtils.validateEmailList(Arrays.asList(requestor, target));
        if (!generalResponseEntity.isSuccess()) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(generalResponseEntity);
        }

        return ResponseEntity.ok(relationshipService.follow(requestor, target));
    }


    @RequestMapping(method = RequestMethod.PUT, path = "/blockers")
    public ResponseEntity<?> addBlockers(@RequestBody FollowOrBlockRequestEntity followOrBlockRequestEntity) {
        String requestor = followOrBlockRequestEntity.getRequestor();
        String target = followOrBlockRequestEntity.getTarget();
        GeneralResponseEntity generalResponseEntity = FriendManagerUtils.validateEmailList(Arrays.asList(requestor, target));
        if (!generalResponseEntity.isSuccess()) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(generalResponseEntity);
        }

        return ResponseEntity.ok(relationshipService.block(requestor, target));
    }


    @RequestMapping(method = RequestMethod.GET, path = "/recipients")
    public ResponseEntity<?> getRecipients(@RequestBody RecipientsRequestEntity recipientsRequestEntity) {
        String sender = recipientsRequestEntity.getSender();
        String text = recipientsRequestEntity.getText();
        GeneralResponseEntity generalResponseEntity = FriendManagerUtils.validateEmailList(Arrays.asList(sender));
        if (!generalResponseEntity.isSuccess()) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(generalResponseEntity);
        }

        return ResponseEntity.ok(relationshipService.getRecipients(sender, text));
    }




}
