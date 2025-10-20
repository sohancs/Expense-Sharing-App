package org.wt.com.expense_sharing_app.persistence.repository;

import org.wt.com.expense_sharing_app.persistence.entity.Group;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findByGroupId(Long groupId);

    
}
