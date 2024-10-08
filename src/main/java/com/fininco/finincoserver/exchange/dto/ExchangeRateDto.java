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
	private BigDecimal getCurrency;
	private BigDecimal sellCurrency;
	private BigDecimal exchangeRate;

	public LocalDate getBaseDate() {
		return baseDate;
	}

	public void setBaseDate(LocalDate baseDate) {
		this.baseDate = baseDate;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getGetCurrency() {
		return getCurrency;
	}

	public void setGetCurrency(BigDecimal getCurrency) {
		this.getCurrency = getCurrency;
	}

	public BigDecimal getSellCurrency() {
		return sellCurrency;
	}

	public void setSellCurrency(BigDecimal sellCurrency) {
		this.sellCurrency = sellCurrency;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
}
