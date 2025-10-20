package org.wt.com.expense_sharing_app.DTO;

import lombok.Data;

@Data
public class BalanceDTO {

    private String userName;
    private String groupName;
    private Double amount;
}
