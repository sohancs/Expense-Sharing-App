package org.wt.com.expense_sharing_app.DTO;

import lombok.Data;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class GroupDTO {

    @NotBlank(message = "Group name is mandatory")
    private String grpName;
    private String grpDescription;

    @NotNull(message = "Member IDs cannot be null")
    private List<Long> memberIds;
}