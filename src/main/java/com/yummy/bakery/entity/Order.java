package com.yummy.bakery.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "orders", uniqueConstraints = {@UniqueConstraint(columnNames = { "user_id", "order_id" }) })
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private long id;

	@NotNull
	@Column(name = "status")
	private String status;

	@NotNull
	@Column(name = "user_id")
	private String userId;

	@OneToMany(cascade = CascadeType.ALL, targetEntity=OrderDetails.class, orphanRemoval=true)
	@JoinColumn(name = "order_id")
	private Set<OrderDetails> orderDetails;

	@Transient
	private User user;

	//@Temporal(TemporalType.TIMESTAMP)
	//@Column(name="time_stamp", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	//private Date date;

	private Date created;
	private Date updated;

	public Order() {

	}

	@PrePersist
	protected void onCreate() {
		created = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		updated = new Date();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Set<OrderDetails> getOrderDetails() {
		return orderDetails;
	}


	public void setOrderDetails(Set<OrderDetails> orderDetails) {
		this.orderDetails = orderDetails;
	}

	/*public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
*/
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
}
