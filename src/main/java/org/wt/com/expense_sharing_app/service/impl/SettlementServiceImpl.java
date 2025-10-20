package org.wt.com.expense_sharing_app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wt.com.expense_sharing_app.DTO.BalanceDTO;
import org.wt.com.expense_sharing_app.DTO.SettlementDTO;
import org.wt.com.expense_sharing_app.persistence.entity.BalanceSheet;
import org.wt.com.expense_sharing_app.persistence.entity.Group;
import org.wt.com.expense_sharing_app.persistence.entity.Settlement;
import org.wt.com.expense_sharing_app.persistence.entity.User;
import org.wt.com.expense_sharing_app.persistence.repository.BalanceSheetRepository;
import org.wt.com.expense_sharing_app.persistence.repository.GroupRepository;
import org.wt.com.expense_sharing_app.persistence.repository.SettlementRepository;
import org.wt.com.expense_sharing_app.persistence.repository.UserRepository;
import org.wt.com.expense_sharing_app.service.SettlementService;
import org.wt.com.expense_sharing_app.util.CommonUtil;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SettlementServiceImpl implements SettlementService { 

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BalanceSheetRepository balanceSheetRepository;

    @Autowired
    private SettlementRepository settlementRepository;

    @Autowired
    private CommonUtil commonUtil;

    public Settlement settleUpBalance(SettlementDTO settlementDTO) {
        
       Group group = groupRepository.findByGroupId(settlementDTO.getGroupId())
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));

        User payerUsr = userRepository.findById(settlementDTO.getPayerId())
                .orElseThrow(() -> new EntityNotFoundException("Payer not found"));

        User receiverUsr = userRepository.findById(settlementDTO.getReceiverId())
                .orElseThrow(() -> new EntityNotFoundException("Receiver not found"));

      commonUtil.isMemberOfGroup(payerUsr, group);
      commonUtil.isMemberOfGroup(receiverUsr, group);

      //Update Balance Sheets
      BalanceSheet payerBalanceSheet = balanceSheetRepository.findByGroupAndUser(group, payerUsr)
                .orElseGet(() -> new BalanceSheet(null, group, payerUsr, null));

        payerBalanceSheet.setAmount(payerBalanceSheet.getAmount() + settlementDTO.getAmount());
        balanceSheetRepository.save(payerBalanceSheet);

        BalanceSheet receiverBalanceSheet = balanceSheetRepository.findByGroupAndUser(group, receiverUsr)
                .orElseGet(() -> new BalanceSheet(null, group, receiverUsr, null));

        receiverBalanceSheet.setAmount(receiverBalanceSheet.getAmount() - settlementDTO.getAmount());
        balanceSheetRepository.save(receiverBalanceSheet);


        Settlement settlement = new Settlement();
        settlement.setAmount(settlementDTO.getAmount());
        settlement.setGroup(group);
        settlement.setFromUser(payerUsr);
        settlement.setToUser(receiverUsr);
        settlement.setNote(settlementDTO.getNote());

        log.info("Settlement recorded: Payer {} paid Receiver {} amount {} in Group {}", 
                payerUsr.getUserId(), receiverUsr.getUserId(), settlementDTO.getAmount(), group.getGroupId());
                
        return settlementRepository.save(settlement);
    
    }

    @Override
    public BalanceDTO getGroupBalances(Long groupId) {
        Group group = groupRepository.findByGroupId(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));
        
        BalanceSheet balanceSheet = balanceSheetRepository.findByGroup(group).orElseGet(() -> new BalanceSheet());
        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setGroupName(group.getGroupName());
        balanceDTO.setAmount(balanceSheet.getAmount());
        
        return balanceDTO;
    }

    @Override
    public BalanceDTO getUserBalances(Long userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        BalanceSheet balanceSheet = balanceSheetRepository.findByUser(user).orElseGet(() -> new BalanceSheet());
        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setUserName(user.getName());
        balanceDTO.setAmount(balanceSheet.getAmount()); 

        return balanceDTO;
    }

}
