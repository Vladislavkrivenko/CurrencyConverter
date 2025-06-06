package dto;

import java.math.BigDecimal;

public record CurrencyRateDTO(String fromCurrency, String toCurrency, BigDecimal rate) {
}
