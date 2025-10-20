package org.wt.com.expense_sharing_app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.wt.com.expense_sharing_app.DTO.GroupDTO;
import org.wt.com.expense_sharing_app.persistence.entity.Group;
import org.wt.com.expense_sharing_app.persistence.entity.User;
import org.wt.com.expense_sharing_app.persistence.repository.GroupRepository;
import org.wt.com.expense_sharing_app.persistence.repository.UserRepository;
import org.wt.com.expense_sharing_app.service.GroupService;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    public Group createGroup(GroupDTO groupDTO) {
        Group group = objectMapper.convertValue(groupDTO, Group.class);

        group.setGroupName(groupDTO.getGrpName());
        group.setDescription(groupDTO.getGrpDescription());

        if(!CollectionUtils.isEmpty(groupDTO.getMemberIds())) {
            List<User> members = new ArrayList<>();

            groupDTO.getMemberIds().forEach(memberId -> {
                User user = userRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + memberId));
                members.add(user);
            });

            group.setMembers(members); 
        }

        return groupRepository.save(group);
    }    

    public Group getGroupById(Long groupId) {
        return groupRepository.findById(groupId).orElse(null);
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

}
