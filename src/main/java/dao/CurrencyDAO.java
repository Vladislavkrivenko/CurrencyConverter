package dao;

import service.CurrencyRate;

import java.math.BigDecimal;

public class CurrencyDAO {

    public CurrencyRate findRate(String from, String to, BigDecimal amount) {
        String key = from + " " + to;
        return new CurrencyRate(from, to, amount);
    }
}
