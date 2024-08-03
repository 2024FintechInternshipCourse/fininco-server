/*
 * 
 * 환율 Entity
 * 
 * 환율 식별번호, 환율 기준시간, 환율 국가, 환율 가격
 * 
 */




package com.fininco.finincoserver.exchange.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Entity
@Getter
@NoArgsConstructor
@Table(name = "ExchangeRate")
public class ExchangeRate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "dateTime")
	private LocalDateTime dateTime;
	
	@Column(name = "country")
    private String country;
	

	@Column(name = "price")
	private long price;
	

	public Long getId() {
		return id;
	}


	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public String getCountry() {
		return country;
	}


	public long getPrice() {
		return price;
	}


	public ExchangeRate(Long id, LocalDateTime dateTime, String country, long price) {
		super();
		this.id = id;
		this.dateTime = dateTime;
		this.country = country;
		this.price = price;
	}


}
