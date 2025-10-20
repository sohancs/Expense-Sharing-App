package org.wt.com.expense_sharing_app.persistence.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Table(name = "SETTLEMENT")
@Data
public class Settlement {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SETTLEMENT_ID")
    private Long settlementId;

    @ManyToOne
    @JoinColumn(name = "FROM_USER_ID") 
    private User fromUser;

    @ManyToOne 
    @JoinColumn(name = "TO_USER_ID") 
    private User toUser;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID") 
    private Group group;

    @Column(name = "AMOUNT", nullable = false)
    private Double amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SETTLEMENT_DATE_TIME", nullable = false)
    private LocalDateTime settlementDateTime = LocalDateTime.now();

    @Column(name = "NOTE")
    private String note;
}
