package com.sptest.friendmanager.db.repository;

import com.sptest.friendmanager.db.model.FollowerRelationshipDto;
import com.sptest.friendmanager.db.model.RelationshipKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface FollowerRepository extends JpaRepository<FollowerRelationshipDto, RelationshipKey> {
    List<FollowerRelationshipDto> findByRelationshipKeyRequestEmail(String requestEmail);
}