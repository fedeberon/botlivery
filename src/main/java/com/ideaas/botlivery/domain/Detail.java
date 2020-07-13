package com.ideaas.botlivery.domain;

import javax.persistence.*;

@Entity
@Table(name = "DETAILS")
public class Detail {

    @Id
    @Column(name = "DET_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "DET_QUANTITY")
    private Long quantity = 1l;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "DET_PROD_ID")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "DET_ORD_CHANNEL_SID")
    private Order order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Detail withProduct(Product product){
        this.product = product;
        return this;
    }

    public Detail withQuantity(Long quantity){
        this.quantity = quantity;
        return this;
    }

    @Override
    public String toString() {
        return " ".concat(quantity.toString()).concat(" ").concat(product.getName()).concat(" ").concat(product.getType().getName());
    }
}
