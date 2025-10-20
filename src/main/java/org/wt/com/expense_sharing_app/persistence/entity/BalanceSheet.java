package org.wt.com.expense_sharing_app.persistence.entity;

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
@Table(name = "BALANCE_SHEET")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long balanceSheetId;

    @ManyToOne 
    @JoinColumn(name = "GROUP_ID")
    private Group group;

    @ManyToOne 
    @JoinColumn(name = "USER_ID")
    private User user;

    private Double amount = 0.0;
}
