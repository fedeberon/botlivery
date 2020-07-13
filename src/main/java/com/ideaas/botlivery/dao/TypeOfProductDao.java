package com.ideaas.botlivery.dao;

import com.ideaas.botlivery.domain.TypeOfProduct;
import com.ideaas.botlivery.enums.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeOfProductDao extends JpaRepository<TypeOfProduct, Long> {

    List<TypeOfProduct> findAllByCategory(CategoryEnum category);

}
