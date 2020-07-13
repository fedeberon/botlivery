package com.ideaas.botlivery.controller;

import com.ideaas.botlivery.domain.Action;
import com.ideaas.botlivery.enums.TypeJsonCollect;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.ideaas.botlivery.util.JsonUtil.checkResponse;
import static com.ideaas.botlivery.util.JsonUtil.getAnswer;

@RestController
@RequestMapping("twilio")
public class ConfirmController {

    @Value("${api.url}")
    private String apiUrl;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "checkYesNoOrders")
    public @ResponseBody
    String checkYesNoOrders(HttpServletRequest request){
        String register = request.getParameter("Memory");
        JSONObject jsonRegister = new JSONObject(register);
        if(checkResponse(jsonRegister, TypeJsonCollect.COLLECT_CONFIRM_ORDER)){
            String answer = getAnswer(jsonRegister, TypeJsonCollect.COLLECT_CONFIRM_ORDER, "appt_confirm_order");
            if(answer.toLowerCase().equals("no")){
                return new Action().withRedirect(apiUrl.concat("/twilio/pay")).toJson().toString();
            }
            else {
                String say = "Perfecto! ya estoy listo para recibir tu pedido";
                return new Action().withSay(say).toJson().toString();
            }
        }
        return new Action().withRedirect("task://greeting").toJson().toString();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "checkYesNoDrinks")
    public @ResponseBody String checkYesNoDrinks(HttpServletRequest request){
        String register = request.getParameter("Memory");
        JSONObject jsonRegister = new JSONObject(register);
        if(checkResponse(jsonRegister, TypeJsonCollect.COLLECT_DRINK)){
            String answer = getAnswer(jsonRegister, TypeJsonCollect.COLLECT_DRINK, "appt_confirm_drinks");
            if(answer.toLowerCase().equals("no")){
                return new Action().withRedirect("task://pay").toJson().toString();
            }
            else return new Action().withRedirect("task://drinks").toJson().toString();
        }
        return new Action().withSay("No entendi bien, comencemos nuevamente.").withRedirect("task://greeting").toJson().toString();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "checkYesNoAddress")
    public @ResponseBody String checkYesNoAddress(HttpServletRequest request){
        String register = request.getParameter("Memory");
        JSONObject jsonRegister = new JSONObject(register);

        if(checkResponse(jsonRegister, TypeJsonCollect.COLLECT_USER_DATA)){
            String answer = getAnswer(jsonRegister, TypeJsonCollect.COLLECT_USER_DATA, "appt_confirm_address");
            if(answer.toLowerCase().equals("no")){
                return new Action().withSay("Ok, vamos a modificarlo !").withRedirect(apiUrl.concat("/twilio/user/address/edit")).toJson().toString();
            }
            else {
                String say  = "Perfecto! ya estoy listo para recibir tu pedido";
                String say1 = "Tenemos {RestauranteMenuCategorias} o Ver Menu Aqui ->{RestauranteMenuURL}.";
                String say2 = "Si ya sabes que pedir, indicanos La Cantidad y el plato que mas te guste, (ej: quiero 6 empanadas de carne).";
                return new Action().withSay(say).withSay(say1).withSay(say2).toJson().toString();
            }
        }

        return new Action().withRedirect("task://greeting").toJson().toString();
    }
}
