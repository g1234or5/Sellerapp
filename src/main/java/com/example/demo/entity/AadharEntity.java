package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
@Table(name="aadhar_details")
public class AadharEntity {
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	@javax.persistence.Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="aadhar_id")
	private Long Id;
	@Column(name="aadhar_no")
	private String aadharNumber;
	@Column(name="name")
	private String name;
	@Column(name="mobileNumber")
	private String mobileNumber;
	@Column(name="front_page")
	private String frontPage;
	public String getAadharNumber() {
		return aadharNumber;
	}
	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String  getFrontPage() {
		return frontPage;
	}
	public void setFrontPage(String frontPage) {
		this.frontPage = frontPage;
	}
	

}
