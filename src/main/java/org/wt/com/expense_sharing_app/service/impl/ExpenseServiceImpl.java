package org.wt.com.expense_sharing_app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wt.com.expense_sharing_app.dto.ExpenseDTO;
import org.wt.com.expense_sharing_app.persistence.entity.BalanceSheet;
import org.wt.com.expense_sharing_app.persistence.entity.Expense;
import org.wt.com.expense_sharing_app.persistence.entity.ExpenseShares;
import org.wt.com.expense_sharing_app.persistence.entity.Group;
import org.wt.com.expense_sharing_app.persistence.entity.User;
import org.wt.com.expense_sharing_app.persistence.repository.BalanceSheetRepository;
import org.wt.com.expense_sharing_app.persistence.repository.ExpenseRepository;
import org.wt.com.expense_sharing_app.persistence.repository.GroupRepository;
import org.wt.com.expense_sharing_app.persistence.repository.UserRepository;
import org.wt.com.expense_sharing_app.service.ExpenseService;
import org.wt.com.expense_sharing_app.util.CommonUtil;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BalanceSheetRepository balanceSheetRepository;

    @Autowired
    private CommonUtil commonUtil;

    @Transactional
    public Expense addExpense(ExpenseDTO expenseDTO) {
        Group group = groupRepository.findByGroupId(expenseDTO.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found"));

        User paidByUsr = userRepository.findByUserId(expenseDTO.getPaidById())
                .orElseThrow(() -> new RuntimeException("User not found"));

        commonUtil.isMemberOfGroup(paidByUsr, group);

        Expense expense = new Expense();
        List<BalanceSheet> balanceSheetList = new ArrayList<>();

        expense.setDescription(expenseDTO.getDescription());
        expense.setTotalAmount(expenseDTO.getTotalAmount());
        expense.setGroup(group);
        expense.setPaidBy(paidByUsr);

        List<ExpenseShares> expenseSharesList = new ArrayList<>();

        if (expenseDTO.getIsEqualShare()) {
            Double equalShareAmount = expenseDTO.getTotalAmount() / group.getMembers().size();

            group.getMembers().forEach(member -> {
                ExpenseShares expenseShare = new ExpenseShares();
                expenseShare.setUser(member);
                expenseShare.setShareAmount(equalShareAmount);

                expenseSharesList.add(expenseShare);

                //Update balance sheet for each member  
                BalanceSheet balanceSheet = balanceSheetRepository
                        .findByGroupAndUser(group, member)
                        .orElse(new BalanceSheet(null, group, member, 0.0));

                if (paidByUsr.getUserId() == balanceSheet.getUser().getUserId()) {
                    balanceSheet.setAmount(balanceSheet.getAmount() + (expenseDTO.getTotalAmount() - equalShareAmount));
                } else {
                    balanceSheet.setAmount(balanceSheet.getAmount() - equalShareAmount);
                }

                balanceSheetList.add(balanceSheet);
            });

            expense.setExpenseShares(expenseSharesList);
        } else {
            Double sum = expenseDTO.getShares().stream().mapToDouble(share -> share.getShareAmount()).sum();
            if(sum != expenseDTO.getTotalAmount()) {
                throw new RuntimeException("Sum of shares does not equal total amount");
            }
            
            expenseDTO.getShares().forEach(shareDto -> {
                User shareUser = userRepository.findById(shareDto.getUserId())
                        .orElseThrow(() -> new EntityNotFoundException("User not found for share"));

                ExpenseShares expenseShare = new ExpenseShares();
                expenseShare.setShareAmount(shareDto.getShareAmount());
                expenseShare.setUser(shareUser);
                expenseShare.setExpense(expense);

                expenseSharesList.add(expenseShare);

                //Update balance sheet for each member  
                BalanceSheet balanceSheet = balanceSheetRepository
                        .findByGroupAndUser(group, shareUser)
                        .orElse(new BalanceSheet(null, group, shareUser, 0.0));

                if (paidByUsr.getUserId() == balanceSheet.getUser().getUserId()) {
                    balanceSheet.setAmount(balanceSheet.getAmount() + (expenseDTO.getTotalAmount() - expenseShare.getShareAmount()));
                } else {
                    balanceSheet.setAmount(balanceSheet.getAmount() - expenseShare.getShareAmount());
                }

                balanceSheetList.add(balanceSheet);
            });

            expense.setExpenseShares(expenseSharesList);
        }

        balanceSheetRepository.saveAll(balanceSheetList);

        return expenseRepository.save(expense);

    }

    public List<Expense> getGroupExpenses(Long groupId) {
        Group group = groupRepository.findByGroupId(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));

        return expenseRepository.findByGroup(group);
    }

}
