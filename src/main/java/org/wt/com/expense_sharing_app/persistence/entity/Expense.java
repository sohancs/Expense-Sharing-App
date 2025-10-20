package org.wt.com.expense_sharing_app.persistence.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Table(name = "EXPENSE")
@Data
public class Expense {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXPENSE_ID")
    private Long expenseId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "TOTAL_AMOUNT", nullable = false)
    private Double totalAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPENSE_DATE_TIME", nullable = false)
    private LocalDateTime expenseDateTime = LocalDateTime.now();

    @ManyToOne 
    @JoinColumn(name = "GROUP_ID")
    private Group group;

    @ManyToOne 
    @JoinColumn(name = "USER_ID")
    private User paidBy;

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExpenseShares> expenseShares = new ArrayList<>();
}
