package org.wt.com.expense_sharing_app.service;

import org.wt.com.expense_sharing_app.DTO.BalanceDTO;
import org.wt.com.expense_sharing_app.DTO.SettlementDTO;
import org.wt.com.expense_sharing_app.persistence.entity.Settlement;

public interface SettlementService {

    Settlement settleUpBalance(SettlementDTO settlementDTO);

    BalanceDTO getGroupBalances(Long groupId);

    BalanceDTO getUserBalances(Long userId);
}
