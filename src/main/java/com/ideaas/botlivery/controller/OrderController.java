package com.ideaas.botlivery.controller;

import com.ideaas.botlivery.domain.*;
import com.ideaas.botlivery.enums.CategoryEnum;
import com.ideaas.botlivery.enums.TypeJsonCollect;
import com.ideaas.botlivery.enums.TypeResponse;
import com.ideaas.botlivery.service.interfaces.OrderService;
import com.ideaas.botlivery.service.interfaces.ProductService;
import com.ideaas.botlivery.service.interfaces.TypeOfProductService;
import com.ideaas.botlivery.util.JsonUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.ideaas.botlivery.util.JsonUtil.*;

@RestController
@RequestMapping("twilio")
public class OrderController {

    @Value("${api.url}")
    private String apiUrl;
    private TypeOfProductService typeOfProductService;
    private OrderService orderService;
    private ProductService productService;


    @Autowired
    public OrderController(TypeOfProductService typeOfProductService, OrderService orderService, ProductService productService) {
        this.typeOfProductService = typeOfProductService;
        this.orderService = orderService;
        this.productService = productService;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "menu")
    public @ResponseBody
    String menu() throws Exception {
        List<TypeOfProduct> types = typeOfProductService.findAllByCategory(CategoryEnum.FOOD);
        AllowValue allowValue = new AllowValue();
        StringBuilder values = new StringBuilder();
        types.forEach(type -> {
            allowValue.addValue(type.getName());
            values.append(type.getName()).append(", ");
        });
        Validate validate = new Validate(allowValue);
        validate.withMessage("No comprendo la opcion elegida" + values);
        Question q1 = new Question("Comencemos con la comida...", "type_of_food").withValidate(validate);

        Question q2 = new Question("Cuantas", "units");
        Collect collect = new Collect(TypeJsonCollect.COLLECT_FOOD.getDescription()).withQuestion(q1).withQuestion(q2).onComplete(apiUrl.concat("/twilio/addDrinks"));
        return new Action().withSay("Que queres agregar a tu orden ?").withCollect(collect).withListen(true).toJson().toString();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "drinks")
    public @ResponseBody String drinks() {
        List<TypeOfProduct> types = typeOfProductService.findAllByCategory(CategoryEnum.DRINK);
        AllowValue allowValue = new AllowValue();
        StringBuilder values = new StringBuilder();
        types.forEach(type -> {
            allowValue.addValue(type.getName());
            values.append(type.getName()).append(", ");
        });
        Validate validate = new Validate(allowValue);
        validate.withMessage("No comprendo la opcion elegida, puede elegir estas opciones : " + values);
        Question q1 = new Question("Que bebidas te gustaria sumar ?", "type_drink").withValidate(validate);
        Collect collect = new Collect(TypeJsonCollect.COLLECT_DRINK.getDescription()).withQuestion(q1).onComplete(apiUrl.concat("/twilio/confirmOrder"));

        return new Action().withCollect(collect).withListen(true).toJson().toString();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "confirmOrder")
    public @ResponseBody String confirmOrder(HttpServletRequest request) {
        String register = statusOrder(request);
        AllowValue allowValue = new AllowValue().addValue("Si","SI","si").addValue("No","no","NO");
        Validate validate = new Validate(allowValue);
        validate.withMessage("Por favor, responda SI o NO.");
        Question q1 = new Question("Deseas agregar algo mas ?", "appt_confirm_order", TypeResponse.YES_NO).withValidate(validate);
        Collect collect = new Collect(TypeJsonCollect.COLLECT_CONFIRM_ORDER.getDescription()).withQuestion(q1).onComplete(apiUrl.concat("/twilio/checkYesNoOrders"));

        return new Action().withSay(register).withCollect(collect).withListen(true).toJson().toString();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "addDrinks")
    public @ResponseBody String addDrinks(HttpServletRequest request) {
        Question q1 = new Question("Deseas agregar bebidas ?", "appt_confirm_drinks", TypeResponse.YES_NO);
        Collect collect = new Collect(TypeJsonCollect.COLLECT_DRINK.getDescription()).withQuestion(q1).onComplete(apiUrl.concat("/twilio/checkYesNoDrinks"));

        return new Action().withCollect(collect).toJson().toString();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "order")
    public @ResponseBody String order(HttpServletRequest request) {
        AllowValue allowValue = new AllowValue().addValue("Si","SI","si").addValue("No","no","NO");
        Validate validate = new Validate(allowValue);
        validate.withMessage("Por favor, responda SI o NO");
        Question q1 = new Question("Deseas agregar algo mas ?", "appt_confirm_order", TypeResponse.YES_NO).withValidate(validate);
        Collect collect = new Collect(TypeJsonCollect.COLLECT_CONFIRM_ORDER.getDescription()).withQuestion(q1).onComplete(apiUrl.concat("/twilio/checkYesNoOrders"));
        String nameOfProduct = request.getParameter("Field_Product_Value");
        String typeOfProduct = request.getParameter("Field_Type_Value");
        String quantity = request.getParameter("Field_Quantity_Value");

        Long units = Objects.nonNull(quantity) ? Long.parseLong(quantity) : 1l;
        Product productToOrder = productService.getByNameAndType(nameOfProduct, typeOfProduct);

        if(Objects.isNull(productToOrder)) {
            return new Action().withSay("No encontramos esta variedad.").withCollect(collect).withListen(true).toJson().toString();
        }

        String register = request.getParameter("Memory");
        JSONObject jsonRegister = new JSONObject(register);

        Order order = orderService.getValidOrder(getChannelSid(jsonRegister));
        List<Detail> details = new ArrayList(){{
            add(new Detail().withProduct(productToOrder).withQuantity(units));
        }};
        order.setDetails(details);
        orderService.save(order);

        return new Action().withCollect(collect).toJson().toString();
    }

    private String statusOrder(HttpServletRequest request){
        String register = request.getParameter("Memory");
        JSONObject jsonRegister = new JSONObject(register);
        List<Detail> details = new ArrayList<>();

        JSONObject answerFood = JsonUtil.getAnswer(jsonRegister, TypeJsonCollect.COLLECT_FOOD.getDescription());
        if(checkResponse(jsonRegister, TypeJsonCollect.COLLECT_FOOD)){
            Product food = parse(jsonRegister , TypeJsonCollect.COLLECT_FOOD, TypeJsonCollect.FOOD);
            JSONObject jsonAnswerNumberPortions = answerFood.getJSONObject("units");
            String units = jsonAnswerNumberPortions.getString("answer");
            details.add(new Detail().withProduct(food).withQuantity(Long.parseLong(units)));
        }

        if(checkResponse(jsonRegister, TypeJsonCollect.COLLECT_DRINK)){
            Product drink = parse(jsonRegister, TypeJsonCollect.COLLECT_DRINK, TypeJsonCollect.DRINK);
            details.add(new Detail().withProduct(drink));
        }

        Order order = orderService.getValidOrder(getChannelSid(jsonRegister));
        order.setDetails(details);
        orderService.save(order);

        StringBuilder resul = new StringBuilder("Su pedido hasta el momento es: ");
        order.getDetails().forEach(resul::append);

        return resul.toString();
    }

    private Product parse(JSONObject json, TypeJsonCollect collect, TypeJsonCollect type){
        String food = getAnswer(json, collect, type.getDescription());
        return new Product().withName(food);
    }
}
