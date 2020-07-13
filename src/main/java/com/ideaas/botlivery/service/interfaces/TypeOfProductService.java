package com.ideaas.botlivery.service.interfaces;

import com.ideaas.botlivery.domain.TypeOfProduct;
import com.ideaas.botlivery.enums.CategoryEnum;

import java.util.List;

public interface TypeOfProductService {
    List<TypeOfProduct> findAll();

    List<TypeOfProduct> findAllByCategory(CategoryEnum category);
}
