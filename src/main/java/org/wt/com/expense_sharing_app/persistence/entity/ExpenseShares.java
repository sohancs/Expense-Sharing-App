package org.wt.com.expense_sharing_app.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "EXPENSE_SHARES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseShares {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXPENSE_SHARE_ID")
    private Long expenseShareId;

    @ManyToOne
    @JoinColumn(name = "EXPENSE_ID")
    private Expense expense;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "SHARE_AMOUNT", nullable = false)
    private Double shareAmount;
}
