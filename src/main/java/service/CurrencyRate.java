package service;

import java.math.BigDecimal;

public record CurrencyRate(String from_currency, String to_currency, BigDecimal rate) {
}
