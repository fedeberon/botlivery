package com.ideaas.botlivery.service;

import com.ideaas.botlivery.dao.ProductDao;
import com.ideaas.botlivery.domain.Product;
import com.ideaas.botlivery.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductDao dao;

    @Autowired
    public ProductServiceImpl(ProductDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Product> findAll() {
        return dao.findAll();
    }

    @Override
    public Product get(Long id){
        return dao.getOne(id);
    }

    @Override
    public Product getByNameAndType(String name, String type){
        List<Product> products = dao.getByName(name);
        return products.stream()
                        .filter(x -> x.getType().getName().equals(type))
                        .findFirst()
                        .orElse(null);

    }

}
