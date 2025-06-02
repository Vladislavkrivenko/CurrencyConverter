package dto;

import java.math.BigDecimal;

public record CurrencyRequestDTO(int id, String fromCurrency, String toCurrency, BigDecimal rate) {
}
