package com.mytests.springdataaggregate.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Entity
public class Phone {

	int price;
	@Id
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	private Person person;

	@Column(name = "phone_number")
	private String number;

	@Enumerated(EnumType.STRING)
	@Column(name = "phone_type")
	private PhoneType type;

	@OneToMany(mappedBy = "phone", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Call> calls = new ArrayList<>(  );

	@OneToMany(mappedBy = "phone")
	@MapKey(name = "time")
	private Map<LocalDateTime, Call> callLog = new HashMap<>();

	@ElementCollection
	private List<LocalDateTime> repairTimestamps = new ArrayList<>(  );

	public Phone() {}

	public Phone(String number) {
		this.number = number;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public PhoneType getType() {
		return type;
	}

	public void setType(PhoneType type) {
		this.type = type;
	}

	public List<Call> getCalls() {
		return calls;
	}

	public Map<LocalDateTime, Call> getCallLog() {
		return callLog;
	}

	public List<LocalDateTime> getRepairTimestamps() {
		return repairTimestamps;
	}

	public void addCall(Call call) {
		calls.add( call );
		callLog.put( call.getTime(), call );
		call.setPhone( this );
	}

	@Override
	public String toString() {
		return "Phone{" +
			   "number='" + number + '\'' +
			   ", person=" + person.getName()+" "+person.getSurName() +
			   ", type=" + type +
			   ", price=" + price +
			   '}';
	}
}