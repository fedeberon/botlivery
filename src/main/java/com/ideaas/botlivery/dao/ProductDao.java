package com.ideaas.botlivery.dao;

import com.ideaas.botlivery.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Long> {

    List<Product> getByName(String name);

}
