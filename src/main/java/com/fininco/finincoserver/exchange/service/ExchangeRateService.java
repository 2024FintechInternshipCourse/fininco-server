package com.fininco.finincoserver.exchange.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fininco.finincoserver.exchange.dto.ExchangeRateDto;
import com.fininco.finincoserver.exchange.dto.ExchangeRateResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExchangeRateService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static String data_code = "AP01"; // 일반환율

    @Value("${api.exchange_authkey}")
    public String exchange_authkey; 
    
    public List<ExchangeRateResponseDto> getExchangeRate(String searchdate) throws IOException {
        
    	// API 요청 URL 
        String url = String.format(
            "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey=%s&searchdate=%s&data=%s",
            exchange_authkey, searchdate, data_code
        );
        try {   	
            // RestTemplate API 호출
            String jsonResponse = restTemplate.getForObject(url, String.class);
            
            // JSON 문자열을 -> DTO 리스트로
            List<ExchangeRateResponseDto> allRates = objectMapper.readValue(jsonResponse, new TypeReference<List<ExchangeRateResponseDto>>() {});

            // 달러(USD)와 엔화(JPY)만 필터링
            return allRates.stream()
                    .filter(rate -> "USD".equals(rate.getCur_unit()) || "JPY".equals(rate.getCur_unit()))
                    .collect(Collectors.toList());
            
            //return objectMapper.readValue(jsonResponse, new TypeReference<List<ExchangeRateResponseDto>>() {});
        } catch (RestClientException e) {
            // 예외 처리
            e.printStackTrace();
            throw new IOException("Error occurred while fetching exchange rates", e);
        }
    
    }    
}


