package org.wt.com.expense_sharing_app.util;

import org.springframework.stereotype.Component;
import org.wt.com.expense_sharing_app.persistence.entity.Group;
import org.wt.com.expense_sharing_app.persistence.entity.User;

@Component
public class CommonUtil {

    public User isMemberOfGroup(User user, Group group) {
        return group.getMembers().stream()
                .filter(member -> member.getUserId().equals(user.getUserId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User is not a member of the group"));
    }

}
