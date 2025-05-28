package dto;

import java.math.BigDecimal;

public record CurrencyRequestDTO(String fromCurrency, String toCurrency, BigDecimal amount) {
}
