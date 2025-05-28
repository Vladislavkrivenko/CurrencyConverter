package service;

import java.math.BigDecimal;

public record CurrencyRate(String from, String to, BigDecimal rate) {
}
