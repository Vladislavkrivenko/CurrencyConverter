package dto;

import java.math.BigDecimal;

public record CurrencyDTO(String sourceCurrencyCode, String targetCurrencyCode, BigDecimal amountToTransfer,
                          BigDecimal transferredAmount) {
}
