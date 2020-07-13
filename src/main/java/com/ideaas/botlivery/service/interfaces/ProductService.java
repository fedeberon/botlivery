package com.ideaas.botlivery.service.interfaces;

import com.ideaas.botlivery.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll() throws Exception;

    Product get(Long id);

    Product getByNameAndType(String name, String type);
}
