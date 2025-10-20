package org.wt.com.expense_sharing_app.service;

import java.util.List;

import org.wt.com.expense_sharing_app.persistence.entity.Group;

public interface GroupService {
    public Group createGroup(org.wt.com.expense_sharing_app.DTO.GroupDTO groupDTO);
    public Group getGroupById(Long groupId);
    public List<Group> getAllGroups();
}
