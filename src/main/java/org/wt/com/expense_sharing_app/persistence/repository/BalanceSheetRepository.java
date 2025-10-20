package org.wt.com.expense_sharing_app.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.wt.com.expense_sharing_app.persistence.entity.BalanceSheet;
import org.wt.com.expense_sharing_app.persistence.entity.Group;
import org.wt.com.expense_sharing_app.persistence.entity.User;

@Repository
public interface BalanceSheetRepository extends JpaRepository<BalanceSheet, Long>{

    Optional<BalanceSheet> findByGroupAndUser(Group group, User member);

    Optional<BalanceSheet> findByGroup(Group group);

    Optional<BalanceSheet> findByUser(User user);

}
