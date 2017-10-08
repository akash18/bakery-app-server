package com.yummy.bakery.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "images")
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private long id;

    @NotNull
    @Column(name = "image_url")
    private String imagePath;

    //@OneToOne
    //@JoinColumn(name="product_id")
    //@JsonBackReference
    @OneToOne(mappedBy = "productImage")
    @JsonBackReference
    private Product product;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="time_stamp", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date date;

    public Image() {

    }

  /*  public Image(String imagePath, Product product, Date date) {
        super();
        this.imagePath = imagePath;
        this.product = product;
        this.date = date;
    }*/

   /* public Image(String imagePath, Product product) {
        super();
        this.imagePath = imagePath;
        this.product = product;
    }*/

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    //@JsonProperty("image_url")
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}