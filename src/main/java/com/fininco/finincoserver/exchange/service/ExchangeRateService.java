package com.fininco.finincoserver.exchange.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fininco.finincoserver.exchange.dto.ExchangeRateDto;
import com.fininco.finincoserver.exchange.dto.ExchangeRateResponseDto;
import com.fininco.finincoserver.exchange.entity.ExchangeRate;
import com.fininco.finincoserver.exchange.repository.ExchangeRateRepository;
import com.fininco.finincoserver.global.utils.DateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExchangeRateService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;


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

            // 응답 결과를 로그로 출력
            System.out.println("API Response: " + jsonResponse);

            // jsonResponse가 null인 경우 예외 처리
            if (jsonResponse == null) {
                throw new IOException("Received null response from API");
            }


            // JSON 문자열을 -> DTO 리스트로
            List<ExchangeRateResponseDto> allRates = objectMapper.readValue(jsonResponse, new TypeReference<List<ExchangeRateResponseDto>>() {});

           // 달러(USD)와 엔화(JPY(100)) 필터링
            return allRates.stream()
                    .filter(rate -> "USD".equals(rate.getCur_unit()) || "JPY(100)".equals(rate.getCur_unit()))
                    .collect(Collectors.toList());

        } catch (RestClientException e) {
            // 예외 처리
            e.printStackTrace();
            throw new IOException("Error occurred while fetching exchange rates", e);
        }

    }

    @Scheduled(fixedRate = 30000) // 5분 간격으로 실행 -> 30000
    public void fetchAndSaveExchangeRates() { // DB에 저장
        try {
            // 오늘 날짜를 가장 최근의 영업일로 변환
            LocalDate today = DateUtils.getLastBusinessDay(LocalDate.now());

            // 오늘의 환율 데이터를 가져옴
            List<ExchangeRateResponseDto> rates = getExchangeRate(today.toString().replace("-", ""));

            // DTO를 엔티티로 변환
            List<ExchangeRate> exchangeRates = rates.stream()
                    .map(rate -> ExchangeRate.builder()
                            .basedate(LocalDate.now())
                            .currencyCode(rate.getCur_unit())
                            .getCurrency(convertToBigDecimal(rate.getTtb()))
                            .sellCurrency(convertToBigDecimal(rate.getTts()))
                            .exchangeRate(convertToBigDecimal(rate.getDeal_bas_r()))
                            .build())
                    .collect(Collectors.toList());

            // DB에 저장
            exchangeRateRepository.saveAll(exchangeRates);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BigDecimal convertToBigDecimal(String value) {
        if (value == null || value.isEmpty()) {
            return BigDecimal.ZERO;
        }
        try {
            // 문자열에서 ','를 제거하고 BigDecimal로 변환
            return new BigDecimal(value.replace(",", ""));
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

}