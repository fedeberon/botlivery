package com.ideaas.botlivery.service;

import com.ideaas.botlivery.dao.OrderDao;
import com.ideaas.botlivery.domain.Order;
import com.ideaas.botlivery.service.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDao dao;

    @Autowired
    public OrderServiceImpl(OrderDao dao) {
        this.dao = dao;
    }

    @Override
    public Order save(Order order){
        order.getDetails().forEach(detail -> detail.setOrder(order));
        return dao.save(order);
    }

    @Override
    public Order getValidOrder(String channelSid){
        Order order = dao.getByChannelSid(channelSid);
        if(Objects.nonNull(order)) return order;

        return new Order().withChannelSid(channelSid);
    }

}
