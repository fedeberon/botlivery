package com.ideaas.botlivery.service.interfaces;

import com.ideaas.botlivery.domain.Order;

public interface OrderService {
    Order save(Order order);

    Order getValidOrder(String channelSid);
}
