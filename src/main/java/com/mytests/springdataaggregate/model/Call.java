package com.mytests.springdataaggregate.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "phone_call")
public class Call {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private Phone phone;

	@Column(name = "call_time")
	private LocalDateTime time;

	private int duration;

	public Phone getPhone() {
		return phone;
	}

	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime timestamp) {
		this.time = timestamp;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}