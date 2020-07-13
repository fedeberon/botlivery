package com.ideaas.botlivery.domain;

import com.ideaas.botlivery.enums.CategoryEnum;

import javax.persistence.*;

@Entity
@Table(name = "TYPES_OF_PRODUCTS")
public class TypeOfProduct {

    @Id
    @Column(name = "TOP_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "TOP_NAME")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "TOR_CATEGORY")
    private CategoryEnum category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public TypeOfProduct withName(String name){
        this.name = name;
        return this;
    }
}
