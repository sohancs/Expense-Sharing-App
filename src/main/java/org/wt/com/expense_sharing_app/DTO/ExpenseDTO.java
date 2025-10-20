package org.wt.com.expense_sharing_app.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExpenseDTO {
    private Long groupId;

    @NotNull(message = "Payer ID cannot be null")
    private Long paidById;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotNull(message = "Total amount cannot be null")
    private Double totalAmount;

    @NotNull(message = "isEqualShare cannot be null")
    private Boolean isEqualShare;

    private List<UserShareDto> shares;

    @Data
    public static class UserShareDto {
        private Long userId;
        private Double shareAmount;
    }
}
