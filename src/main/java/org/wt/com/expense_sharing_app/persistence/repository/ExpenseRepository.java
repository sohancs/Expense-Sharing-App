package org.wt.com.expense_sharing_app.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.wt.com.expense_sharing_app.persistence.entity.Expense;
import org.wt.com.expense_sharing_app.persistence.entity.Group;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByGroup(Group group);

}
