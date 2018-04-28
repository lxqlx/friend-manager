package com.sptest.friendmanager.db.repository;

import com.sptest.friendmanager.db.model.FriendRelationshipDto;
import com.sptest.friendmanager.db.model.RelationshipKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface FriendRepository extends JpaRepository<FriendRelationshipDto, RelationshipKey> {
    List<FriendRelationshipDto> findByRelationshipKeyRequestEmail(String email);
}
