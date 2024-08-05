package com.fininco.finincoserver.exchange.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fininco.finincoserver.exchange.dto.ExchangeRateDto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * 환율 Entity
 * 
 * 아이디, 날짜, 통화코드, 송금 받을때, 송금 보낼때, 매매기준율
 * 
 */


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ExchangeRate")
public class ExchangeRate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "base_date")
	private LocalDate basedate;
	
	@Column(name = "currency_code")
    private String currencyCode;
	
	@Column(name = "get_currency")
    private String getCurrency;
	
	@Column(name = "sell_currency")
    private String sellCurrency;
	
	@Column(name = "exchange_rate")
	private BigDecimal exchangeRate;
	
	

	public Long getId() {
		return id;
	}



	public LocalDate getBasedate() {
		return basedate;
	}



	public String getCurrencyCode() {
		return currencyCode;
	}



	public String getGetCurrency() {
		return getCurrency;
	}



	public String getSellCurreny() {
		return sellCurrency;
	}



	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}



	public ExchangeRate(Long id, LocalDate basedate, String currencyCode, String getCurrency, String sellCurrency,
			BigDecimal exchangeRate) {
		super();
		this.id = id;
		this.basedate = basedate;
		this.currencyCode = currencyCode;
		this.getCurrency = getCurrency;
		this.sellCurrency = sellCurrency;
		this.exchangeRate = exchangeRate;
	}


}
