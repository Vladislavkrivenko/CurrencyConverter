package entity;

import java.math.BigDecimal;

public record CurrencyRate(int id, String fromCurrency, String toCurrency, BigDecimal rate) {
}
