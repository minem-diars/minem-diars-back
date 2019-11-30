package com.minem.diars.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minem.diars.app.model.entity.TicketPurchaseEntity;
import com.minem.diars.app.util.constants.MinemConstants;

@Repository(MinemConstants.TICKET_PURCHASE_REPOSITORY)
public interface TicketPurchaseRepository extends JpaRepository<TicketPurchaseEntity, Integer> {

}
