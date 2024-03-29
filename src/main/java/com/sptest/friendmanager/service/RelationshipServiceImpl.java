package com.sptest.friendmanager.service;

import com.sptest.friendmanager.util.FriendManagerUtils;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
public class RelationshipServiceImpl implements RelationshipService {

    private FriendRepository friendRepository;

    private FollowerRepository followerRepository;

    private BlockerRepository blockerRepository;

    @Autowired
    public RelationshipServiceImpl(FriendRepository friendRepository,
                                   FollowerRepository followerRepository,
                                   BlockerRepository blockerRepository) {
        this.friendRepository = friendRepository;
        this.followerRepository = followerRepository;
        this.blockerRepository = blockerRepository;
    }


    @Override
    public GeneralResponseEntity addFriends(@NonNull String email1, @NonNull String email2) {
        if (blockerRepository.existsById(new RelationshipKey(email1, email2))) {
            return FriendManagerUtils.failureResponseWithErrorMessage(email1 + " is blocking " + email2);
        }

        if (blockerRepository.existsById(new RelationshipKey(email2, email1))) {
            return FriendManagerUtils.failureResponseWithErrorMessage(email2 + " is blocking " + email1);
        }

        friendRepository.save(new FriendRelationshipDto(email1, email2));
        friendRepository.save(new FriendRelationshipDto(email2, email1));
        return FriendManagerUtils.successResponse();
    }

    @Override
    public FriendListResponseEntity getFriends(@NonNull String email) {
        return FriendManagerUtils.toFriendsListResponseEntity(friendRepository.findByRelationshipKeyRequestEmail(email));
    }

    @Override
    public FriendListResponseEntity getCommonFriends(@NonNull String email1, @NonNull String email2) {
        return FriendManagerUtils.toCommonFriendsListResponseEntity(friendRepository.findByRelationshipKeyRequestEmail(email1),
                friendRepository.findByRelationshipKeyRequestEmail(email2));
    }

    @Override
    public GeneralResponseEntity follow(@NonNull String requestor, @NonNull String target) {
        RelationshipKey key = new RelationshipKey(requestor, target);
        if (blockerRepository.existsById(key)) {
            blockerRepository.deleteById(key);
        }
        followerRepository.save(new FollowerRelationshipDto(requestor, target));
        return FriendManagerUtils.successResponse();
    }

    @Override
    public GeneralResponseEntity block(@NonNull String requestor, @NonNull String target) {
        blockerRepository.save(new BlockerRelationshipDto(requestor, target));
        return FriendManagerUtils.successResponse();
    }

    @Override
    public RecipientsListResponseEntity getRecipients(@NonNull String sender, @NonNull String text) {
        Set<String> friends = friendRepository.findByRelationshipKeyRequestEmail(sender).stream()
                .map(FriendRelationshipDto::getRelationshipKey)
                .map(RelationshipKey::getTargetEmail)
                .collect(Collectors.toSet());
        List<String> followers = followerRepository.findByRelationshipKeyTargetEmail(sender).stream()
                .map(FollowerRelationshipDto::getRelationshipKey).map(RelationshipKey::getRequestEmail).collect(Collectors.toList());
        List<String> blockers = blockerRepository.findByRelationshipKeyTargetEmail(sender).stream()
                .map(BlockerRelationshipDto::getRelationshipKey).map(RelationshipKey::getRequestEmail).collect(Collectors.toList());

        friends.addAll(followers);
        friends.addAll(FriendManagerUtils.extractEmails(text));
        friends.remove(sender);
        friends.removeAll(blockers);

        return new RecipientsListResponseEntity(true, new ArrayList<>(friends));
    }

}
