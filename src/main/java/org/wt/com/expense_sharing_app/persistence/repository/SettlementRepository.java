package org.wt.com.expense_sharing_app.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wt.com.expense_sharing_app.persistence.entity.Settlement;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {

}
