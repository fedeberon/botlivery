package com.ideaas.botlivery.controller;

import com.ideaas.botlivery.domain.*;
import com.ideaas.botlivery.enums.TypeJsonCollect;
import com.ideaas.botlivery.service.interfaces.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.ideaas.botlivery.util.JsonUtil.getAnswer;

@RestController
@RequestMapping("twilio")
public class UserController {

    @Value("${api.url}")
    private String apiUrl;
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "user")
    public @ResponseBody
    String persistNewUser(HttpServletRequest request){
        User user = newUser(request);
        userService.save(user);
        return new Action().withSay("Gracias ".concat(user.getFirstName()).concat("! Ya estas en nuestro registro !")).withRedirect("task://menu").toJson().toString();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("user/address/edit")
    public String editAddress(){
        Question q1 = new Question("Indicanos tu dirección", "appt_address");
        Collect collect = new Collect(TypeJsonCollect.COLLECT_ADDRESS.getDescription()).withQuestion(q1).onComplete(apiUrl.concat("/twilio/user/address"));
        return new Action().withCollect(collect).toJson().toString();
    }

    @PostMapping("user/address")
    public String saveAddress(HttpServletRequest request){
        String register = request.getParameter("Memory");
        String userIdentifier = request.getParameter("UserIdentifier");
        User user = userService.get(userIdentifier);
        JSONObject jsonRegister = new JSONObject(register);
        String address = getAnswer(jsonRegister,TypeJsonCollect.COLLECT_ADDRESS, "appt_address");
        user.setAddress(address);
        userService.save(user);
        String say = "Ya estoy listo para recibir tu pedido. Tenemos {RestauranteMenuCategorias} o Ver Menu Aqui ->{RestauranteMenuURL}. Si ya sabes que pedir, indicanos La Cantidad y el plato que mas te guste, (ej: 6 empanadas de carne).";
        return new Action().withSay("Genial! Ya agendamos tu dirección.").withSay(say).toJson().toString();
    }


    private static User newUser(HttpServletRequest request){
        String userIdentifier = request.getParameter("UserIdentifier");
        String register = request.getParameter("Memory");
        JSONObject jsonRegister = new JSONObject(register);
        String firstName = getAnswer(jsonRegister, TypeJsonCollect.COLLECT_USER_DATA, "appt_first_name");
        String lastName = getAnswer(jsonRegister, TypeJsonCollect.COLLECT_USER_DATA,  "appt_last_name");
        String address = getAnswer(jsonRegister, TypeJsonCollect.COLLECT_USER_DATA,  "appt_address");

        return new User(userIdentifier, firstName, lastName, address);
    }

}
