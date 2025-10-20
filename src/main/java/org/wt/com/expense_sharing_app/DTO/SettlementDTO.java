package org.wt.com.expense_sharing_app.dto;

import lombok.Data;

@Data
public class SettlementDTO {
    private Long groupId;
    private Long payerId;
    private Long receiverId;
    private Double amount;
    private String note;
}
