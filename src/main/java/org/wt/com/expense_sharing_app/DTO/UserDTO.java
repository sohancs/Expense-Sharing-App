package org.wt.com.expense_sharing_app.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;
    private String email;
    private String phoneNumber;
}

