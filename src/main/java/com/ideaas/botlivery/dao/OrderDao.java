package com.ideaas.botlivery.dao;

import com.ideaas.botlivery.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao extends JpaRepository<Order, Long> {

    Order getByChannelSid(String channelSid);

}
