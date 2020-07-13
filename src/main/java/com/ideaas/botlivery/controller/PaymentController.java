package com.ideaas.botlivery.controller;

import com.ideaas.botlivery.domain.Action;
import com.ideaas.botlivery.domain.Order;
import com.ideaas.botlivery.service.interfaces.OrderService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static com.ideaas.botlivery.util.JsonUtil.getChannelSid;

@RestController
@RequestMapping("twilio")
public class PaymentController {

    private OrderService orderService;

    @Autowired
    public PaymentController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "pay")
    public @ResponseBody
    String pay(HttpServletRequest request) {
        String register = request.getParameter("Memory");
        JSONObject json = new JSONObject(register);
        String channelSid = getChannelSid(json);
        Order order = orderService.getValidOrder(channelSid);
        StringBuilder resul = new StringBuilder("Su pedido: %0a");
        order.getDetails().forEach(detail -> {
            resul.append(detail).append("%0a");
            Double parcial = detail.getProduct().getPrice() * detail.getQuantity();
            Long total = Objects.nonNull(order.getTotal()) ? order.getTotal() : 0l;
            order.setTotal(total+parcial.longValue());
        });

        return new Action().withSay(resul.toString()).withSay("El total de tu compra es de $".concat(order.getTotal().toString()).concat(" -> www.mercadopago.com.ar.")).withSay("Gracias.").toJson().toString();
    }

}
