package com.mytests.springdataaggregate.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Person {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private String surName;

	private String main_address;

	private LocalDateTime birthday;

	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
	@OrderColumn(name = "order_id")
	private List<Phone> phones = new ArrayList<>();

	@ElementCollection
	@MapKeyEnumerated(EnumType.STRING)
	private Map<AddressType, String> addresses = new HashMap<>();

	@Version
	private int version;

	public String[] getNations() {
		return nations;
	}

	public void setNations(String[] nations) {
		this.nations = nations;
	}

	private String[] nations;

	public Person() {}

	public Person(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String nickName) {
		this.surName = nickName;
	}

	public String getMain_address() {
		return main_address;
	}

	public void setMain_address(String address) {
		this.main_address = address;
	}

	public LocalDateTime getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDateTime createdOn) {
		this.birthday = createdOn;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public Map<AddressType, String> getAddresses() {
		return addresses;
	}

	public void addPhone(Phone phone) {
		phones.add( phone );
		phone.setPerson( this );
	}

	@Override
	public String toString() {
		return "Person{" +
			   "name='" + name + '\'' +
			   ", surName='" + surName + '\'' +
			   ", main address='" + main_address + '\'' +
			   ", birthday=" + birthday +
			   ", phones=" + phones +
			   ", addresses=" + addresses +
			   ", version=" + version +
			   ", nations=" + nations +

			   '}';
	}
}