package com.fininco.finincoserver.exchange.dto;

/*
 * 
 * 어플리케이션 내부에서 사용할 DTO
 * 
 */

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExchangeRateDto {
	
	private LocalDate baseDate;
	private String currencyCode;
	private String getCurrency;
	private String sellCurrency;
	private BigDecimal exchangeRate;

	public LocalDate getBaseDate() {
		return baseDate;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public String getGetCurrency() {
		return getCurrency;
	}

	public String getSellCurrency() {
		return sellCurrency;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setBaseDate(LocalDate baseDate) {
		this.baseDate = baseDate;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public void setGetCurrency(String getCurrency) {
		this.getCurrency = getCurrency;
	}

	public void setSellCurrency(String sellCurrency) {
		this.sellCurrency = sellCurrency;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
}
