package model;

import java.math.BigDecimal;

public class Currency {
    private String code;
    private String name;
    private String symbol;
    private BigDecimal rateToUSD;

    public Currency(String code, String name, String symbol, BigDecimal rateToUSD) {
        this.code = code;
        this.name = name;
        this.symbol = symbol;
        this.rateToUSD = rateToUSD;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getRateToUSD() {
        return rateToUSD;
    }

    public void setRateToUSD(BigDecimal rateToUSD) {
        this.rateToUSD = rateToUSD;
    }
}
