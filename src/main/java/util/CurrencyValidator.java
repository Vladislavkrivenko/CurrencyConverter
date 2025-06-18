package util;

import dto.CurrencyDTO;
import dto.CurrencyRateDTO;

import java.math.BigDecimal;

public class CurrencyValidator {

    public static void currenciesValidator(CurrencyDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("CurrencyDTO must not be null");
        }

        String fullName = dto.fullName();
        String code = dto.code();
        String sign = dto.sign();

        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name must not be empty");
        }
        if (fullName.length() < 2) {
            throw new IllegalArgumentException("Full name must be at least 2 characters");
        }

        if (code == null || !code.matches("^[A-Z]{3}$")) {
            throw new IllegalArgumentException("Currency code must be 3 uppercase letters");
        }
        if (sign == null || sign.trim().isEmpty()) {
            throw new IllegalArgumentException("Currency sign must not be empty");
        }
        if (sign.length() > 3) {
            throw new IllegalArgumentException("Currency sign must be at most 3 characters");
        }
    }

    public static void rateValidator(CurrencyRateDTO rateDTO) {
        if (rateDTO == null) {
            throw new IllegalArgumentException("CurrencyRateDTO must not be null");
        }
        String fromCurrency = rateDTO.fromCurrency();
        String toCurrency = rateDTO.toCurrency();
        BigDecimal rate = rateDTO.rate();

        if (fromCurrency == null || !fromCurrency.matches("^[A-Z]{3}$")) {
            throw new IllegalArgumentException("From currency code must be 3 uppercase letters");
        }
        if (toCurrency == null || !toCurrency.matches("^[A-Z]{3}$")) {
            throw new IllegalArgumentException("To currency code must be 3 uppercase letters");
        }
        if (rate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Rate must be greater than zero");
        }
    }

    public static void validateCurrencyCode(String code) {
        if (!code.matches("^[A-Z]{3}$")) {
            throw new IllegalArgumentException("Currency code must be 3 uppercase letters");
        }
    }
}
