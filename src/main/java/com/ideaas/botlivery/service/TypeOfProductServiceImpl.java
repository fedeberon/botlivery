package com.ideaas.botlivery.service;

import com.ideaas.botlivery.dao.TypeOfProductDao;
import com.ideaas.botlivery.domain.TypeOfProduct;
import com.ideaas.botlivery.enums.CategoryEnum;
import com.ideaas.botlivery.service.interfaces.TypeOfProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeOfProductServiceImpl implements TypeOfProductService {

    private TypeOfProductDao dao;

    @Autowired
    public TypeOfProductServiceImpl(TypeOfProductDao dao) {
        this.dao = dao;
    }

    public List<TypeOfProduct> findAll(){
        return dao.findAll();
    }

    @Override
    public List<TypeOfProduct> findAllByCategory(CategoryEnum category){
        return dao.findAllByCategory(category);
    }


}
