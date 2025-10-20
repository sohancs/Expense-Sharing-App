package org.wt.com.expense_sharing_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wt.com.expense_sharing_app.DTO.APIResponseDTO;
import org.wt.com.expense_sharing_app.DTO.SettlementDTO;
import org.wt.com.expense_sharing_app.persistence.entity.Settlement;
import org.wt.com.expense_sharing_app.service.SettlementService;

@RestController
@RequestMapping("/api/settlements")
public class SettlementController {

    private final SettlementService settlementService;

    public SettlementController(SettlementService settlementService) {
        this.settlementService = settlementService;
    }


    @PostMapping("/settleUpBalance")
    public ResponseEntity<?> settleUpBalance(@RequestBody SettlementDTO settlementDTO) {
        Settlement settlement = settlementService.settleUpBalance(settlementDTO);
         return ResponseEntity.ok(APIResponseDTO.builder()
            .errorCode(null)
            .errorMessage(null)
            .data("Settlement between payer ID " + settlementDTO.getPayerId() +
                  " and receiver ID " + settlementDTO.getReceiverId() + " of amount " + settlementDTO.getAmount() + " completed successfully.")
            .build());
       
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<?> getGroupBalances(@PathVariable Long groupId) {
         return ResponseEntity.ok(APIResponseDTO.builder()
            .errorCode(null)
            .errorMessage(null)
            .data(settlementService.getGroupBalances(groupId))
            .build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?>  getUserBalances(@PathVariable Long userId) {
        return ResponseEntity.ok(APIResponseDTO.builder()
            .errorCode(null)
            .errorMessage(null)
            .data(settlementService.getUserBalances(userId))
            .build());
    }
}
