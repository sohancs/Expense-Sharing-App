package org.wt.com.expense_sharing_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wt.com.expense_sharing_app.DTO.APIResponseDTO;
import org.wt.com.expense_sharing_app.DTO.ExpenseDTO;
import org.wt.com.expense_sharing_app.service.ExpenseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping(value = "/addExpense")
    public ResponseEntity<?> addExpense(@Valid @RequestBody ExpenseDTO expenseDTO) {
         return ResponseEntity.ok(APIResponseDTO.builder()
            .errorCode(null)
            .errorMessage(null)
            .data(expenseService.addExpense(expenseDTO))
            .build());
    }

   @PostMapping(value = "/groupExpenses")
    public ResponseEntity<?> getGroupExpenses(Long groupId) {
         return ResponseEntity.ok(APIResponseDTO.builder()
            .errorCode(null)
            .errorMessage(null)
            .data(expenseService.getGroupExpenses(groupId))
            .build());
    }

}
