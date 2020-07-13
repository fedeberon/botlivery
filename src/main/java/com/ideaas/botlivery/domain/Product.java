package com.ideaas.botlivery.domain;

import org.json.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCTS")
public class Product {

    @Id
    @Column(name = "PRO_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "PRO_NAME")
    private String name;
    @Column(name = "PRO_PRICE")
    private Double price;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "PRO_TOP_ID", nullable = false)
    private TypeOfProduct type;


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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public TypeOfProduct getType() {
        return type;
    }

    public void setType(TypeOfProduct type) {
        this.type = type;
    }

    private String convertFromJson(JSONObject object){
        return "";
    }

    public Product withName(String name) {
        this.name = name;
        return this;
    }

    public Product withType(String type){
        this.type = new TypeOfProduct().withName(type);
        return this;
    }

}
