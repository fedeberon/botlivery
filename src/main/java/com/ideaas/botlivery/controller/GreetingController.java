package com.ideaas.botlivery.controller;

import com.ideaas.botlivery.domain.*;
import com.ideaas.botlivery.enums.CategoryEnum;
import com.ideaas.botlivery.enums.TypeJsonCollect;
import com.ideaas.botlivery.enums.TypeResponse;
import com.ideaas.botlivery.service.interfaces.TypeOfProductService;
import com.ideaas.botlivery.service.interfaces.UserService;
import com.ideaas.botlivery.util.JsonUtil;
import com.ideaas.botlivery.util.Util;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.ideaas.botlivery.util.JsonUtil.checkResponse;
import static com.ideaas.botlivery.util.JsonUtil.getAnswer;


@RestController
@RequestMapping(path = "twilio", produces = MediaType.APPLICATION_JSON_VALUE)
public class GreetingController {

    @Value("${api.url}")
    private String apiUrl;
    private TypeOfProductService typeOfProductService;
    private UserService userService;

    @Autowired
    public GreetingController(TypeOfProductService typeOfProductService, UserService userService) {
        this.typeOfProductService = typeOfProductService;
        this.userService = userService;
    }

    // Find your Account Sid and Token at twilio.com/user/account
    public static final String ACCOUNT_SID = "AC4fa08a712365062c4562ae6f9c0b4c04";
    public static final String AUTH_TOKEN = "c95ce393dbea848396507d486d09cc70";
    public static final String TWILIO_NUMBER = "+15555555555";



    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "greeting")
    public @ResponseBody String greeting(HttpServletRequest request) {
        String userIdentifier = request.getParameter("UserIdentifier");
        User user = userService.get(userIdentifier);

        if(Objects.isNull(user)){
            Question q1 = new Question("Como es tu nombre ?", "appt_first_name", TypeResponse.FIRST_NAME);
            Question q2 = new Question("Como es tu apellido ?", "appt_last_name", TypeResponse.LAST_NAME);
            Question q3 = new Question("Cual es tu dirección ?", "appt_address");
            Collect collect = new Collect(TypeJsonCollect.COLLECT_USER_DATA.getDescription()).withQuestion(q1).withQuestion(q2).withQuestion(q3).onComplete(apiUrl.concat("/twilio/user"));

            return new Action().withSay("Hola, comencemos por tus datos").withCollect(collect).withListen(true).toJson().toString();
        }

        String greeting = Util.getGreeting().concat(user.getFirstName());
        String say = greeting.concat(", soy el Bot de {RestauranteName}, Antes de Avanzar, me confirmas si tu pedido va a ").concat(user.getAddress()).concat("?");
        Question q1 = new Question(say, "appt_confirm_address", TypeResponse.YES_NO);
        Collect collect = new Collect(TypeJsonCollect.COLLECT_USER_DATA.getDescription()).withQuestion(q1).onComplete(apiUrl.concat("/twilio/checkYesNoAddress"));

        return new Action().withCollect(collect).withListen(true).toJson().toString();
    }

}