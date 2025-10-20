package org.wt.com.expense_sharing_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wt.com.expense_sharing_app.DTO.APIResponseDTO;
import org.wt.com.expense_sharing_app.DTO.GroupDTO;
import org.wt.com.expense_sharing_app.service.GroupService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping(value = "/createGroup")
    public ResponseEntity<?> createGroup(@Valid @RequestBody GroupDTO groupDTO) {
        return ResponseEntity.ok(APIResponseDTO.builder()
            .errorCode(null)
            .errorMessage(null)
            .data("New group created with name : " + groupService.createGroup(groupDTO).getGroupName())
            .build());
    }

    @GetMapping(value = "/allGroups")
    public ResponseEntity<?> getAllGroups() {
        return ResponseEntity.ok(APIResponseDTO.builder()
            .errorCode(null)
            .errorMessage(null)
            .data(groupService.getAllGroups())
            .build());
    }
}
