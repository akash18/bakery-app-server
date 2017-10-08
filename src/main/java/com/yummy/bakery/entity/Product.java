package com.yummy.bakery.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@SequenceGenerator(name = "PRODUCTS_SEQ", sequenceName = "SEQUENCE_PRODUCTS")
	@Column(name = "product_id")
	private long id;

	@NotNull
	@Column(name = "name")
	private String productName;

	@NotNull
	@Column(name = "type")
	private String productType;

	@NotNull
	@Column(name = "description")
	private String productDescription;

	@NotNull
	@Column(name = "price")
	private Double productPrice;

	@NotNull
	@Column(name = "quantity")
	private Integer productQuantity;

	@NotNull
	@Column(name = "is_active", columnDefinition = "boolean default true")
	private boolean isActive = true;

	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "product", targetEntity=Image.class)
	private Set<Image> images;*/
//	@OneToOne(
//			fetch = FetchType.EAGER,
//			mappedBy = "product",
//			cascade = CascadeType.ALL
//	)

	//@OneToOne(mappedBy="product", cascade = CascadeType.ALL)
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id")
	//@JsonBackReference
	private Image productImage;

    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private Set<OrderDetails> orderDetails;*/

	public Image getProductImage() {
		return productImage;
	}

	public void setProductImage(Image productImage) {
		this.productImage = productImage;
	}

	/*@Temporal(TemporalType.TIMESTAMP)
	@Column(name="time_stamp", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Date date;*/

	private Date created;
	private Date updated;

	@PrePersist
	protected void onCreate() {
		created = new Date();
		//isActive = true;
	}

	@PreUpdate
	protected void onUpdate() {
		updated = new Date();
	}

	public Product() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}

/*	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}*/


	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public static void updateAllFields(Product oldProduct, Product newProduct){
		newProduct.setActive(oldProduct.isActive());
		newProduct.setProductDescription(oldProduct.getProductDescription());
		newProduct.setProductName(oldProduct.getProductName());
		newProduct.setProductPrice(oldProduct.getProductPrice());
		newProduct.setProductQuantity(oldProduct.getProductQuantity());
		newProduct.setProductType(oldProduct.getProductType());
		newProduct.setProductImage(oldProduct.getProductImage());
	}

}