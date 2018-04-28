package com.sptest.friendmanager.service;

import com.sptest.friendmanager.entity.response.FriendListResponseEntity;
import com.sptest.friendmanager.entity.response.GeneralResponseEntity;
import com.sptest.friendmanager.entity.response.RecipientsListResponseEntity;

import java.util.List;

public interface RelationshipService {

    GeneralResponseEntity addFriends(String email1, String email2);

    FriendListResponseEntity getFriends(String email);

    FriendListResponseEntity getCommonFriends(String email1, String email2);

    GeneralResponseEntity follow(String requestor, String target);

    GeneralResponseEntity block(String requestor, String target);

    RecipientsListResponseEntity getRecipients(String sender, String text);
}
