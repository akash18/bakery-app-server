package com.yummy.bakery.entity;

public class UserDetails {

	private String fullName;
	private String addr1;
	private String addr2;
	private long phoneNumber;
	private String city;
	private String postalCode;

	public UserDetails(){

	}

	public UserDetails(String fullName, String addr1, String addr2, long phoneNumber, String city, String postalCode) {
		this.fullName = fullName;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.phoneNumber = phoneNumber;
		this.city = city;
		this.postalCode = postalCode;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

}
