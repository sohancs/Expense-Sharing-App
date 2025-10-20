package org.wt.com.expense_sharing_app.service;

import java.util.List;

import org.wt.com.expense_sharing_app.dto.ExpenseDTO;
import org.wt.com.expense_sharing_app.persistence.entity.Expense;

public interface ExpenseService {

    Expense addExpense(ExpenseDTO expenseDTO);

    List<Expense> getGroupExpenses(Long groupId);
}
