package com.sptest.friendmanager.service;

import com.sptest.friendmanager.Util.DtoToEntityUtils;
import com.sptest.friendmanager.db.model.BlockerRelationshipDto;
import com.sptest.friendmanager.db.model.FollowerRelationshipDto;
import com.sptest.friendmanager.db.model.FriendRelationshipDto;
import com.sptest.friendmanager.db.model.RelationshipKey;
import com.sptest.friendmanager.db.repository.BlockerRepository;
import com.sptest.friendmanager.db.repository.FollowerRepository;
import com.sptest.friendmanager.db.repository.FriendRepository;
import com.sptest.friendmanager.entity.response.FriendListResponseEntity;
import com.sptest.friendmanager.entity.response.GeneralResponseEntity;
import com.sptest.friendmanager.entity.response.RecipientsListResponseEntity;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
public class RelationshipServiceImpl implements RelationshipService {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private FollowerRepository followerRepository;

    @Autowired
    private BlockerRepository blockerRepository;


    @Override
    public GeneralResponseEntity addFriends(@NonNull String email1, @NonNull String email2) {
        if (blockerRepository.existsById(new RelationshipKey(email1, email2))) {
            return DtoToEntityUtils.failureResponseWithErrorMessage(email1 + " is blocking " + email2);
        }

        if (blockerRepository.existsById(new RelationshipKey(email2, email1))) {
            return DtoToEntityUtils.failureResponseWithErrorMessage(email2 + " is blocking " + email1);
        }

        friendRepository.save(new FriendRelationshipDto(email1, email2));
        friendRepository.save(new FriendRelationshipDto(email2, email1));
        return DtoToEntityUtils.successResponse();
    }

    @Override
    public FriendListResponseEntity getFriends(@NonNull String email) {
        return DtoToEntityUtils.toFriendsListResponseEntity(friendRepository.findByRelationshipKeyRequestEmail(email));
    }

    @Override
    public FriendListResponseEntity getCommonFriends(@NonNull String email1, @NonNull String email2) {
        return DtoToEntityUtils.toCommonFriendsListResponseEntity(friendRepository.findByRelationshipKeyRequestEmail(email1),
                friendRepository.findByRelationshipKeyRequestEmail(email2));
    }

    @Override
    public GeneralResponseEntity follow(@NonNull String requestor, @NonNull String target) {
        blockerRepository.delete(new BlockerRelationshipDto(requestor, target));
        followerRepository.save(new FollowerRelationshipDto(requestor, target));
        return DtoToEntityUtils.successResponse();
    }

    @Override
    public GeneralResponseEntity block(@NonNull String requestor, @NonNull String target) {
        blockerRepository.save(new BlockerRelationshipDto(requestor, target));
        return DtoToEntityUtils.successResponse();
    }

    @Override
    public RecipientsListResponseEntity getRecipients(@NonNull String sender, @NonNull String text) {
        List<String> friends = friendRepository.findByRelationshipKeyRequestEmail(sender).stream()
                .map(FriendRelationshipDto::getRelationshipKey)
                .map(RelationshipKey::getTargetEmail)
                .collect(Collectors.toList());

        List<String> followers = followerRepository.findByRelationshipKeyRequestEmail(sender).stream()
                .map(FollowerRelationshipDto::getRelationshipKey)
                .map(RelationshipKey::getTargetEmail)
                .collect(Collectors.toList());

        List<String> blockers = blockerRepository.findByRelationshipKeyRequestEmail(sender).stream()
                .map(BlockerRelationshipDto::getRelationshipKey)
                .map(RelationshipKey::getTargetEmail)
                .collect(Collectors.toList());

        Set<String> result = new HashSet<>(friends);
        result.addAll(followers);
        result.addAll(DtoToEntityUtils.extractEmails(text));
        result.removeAll(blockers);

        return RecipientsListResponseEntity.builder().success(true).recipients(new ArrayList<>(result)).build();
    }

}
