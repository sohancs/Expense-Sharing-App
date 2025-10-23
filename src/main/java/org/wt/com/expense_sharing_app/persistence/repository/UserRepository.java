package org.wt.com.expense_sharing_app.persistence.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.wt.com.expense_sharing_app.persistence.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(Long userId);

    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);

    Optional<User> findByGoogleId(String googleId);

}
