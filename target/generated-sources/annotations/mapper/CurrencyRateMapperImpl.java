package mapper;

import dto.CurrencyRateDTO;
import entity.CurrencyRate;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-28T11:31:17+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
public class CurrencyRateMapperImpl implements CurrencyRateMapper {

    @Override
    public CurrencyRateDTO toRateDto(CurrencyRate currencyRate) {
        if ( currencyRate == null ) {
            return null;
        }

        BigDecimal amount = null;
        String fromCurrency = null;
        String toCurrency = null;

        amount = currencyRate.rate();
        fromCurrency = currencyRate.fromCurrency();
        toCurrency = currencyRate.toCurrency();

        CurrencyRateDTO currencyRateDTO = new CurrencyRateDTO( fromCurrency, toCurrency, amount );

        return currencyRateDTO;
    }

    @Override
    public CurrencyRate toEntity(CurrencyRateDTO currencyRateDTO) {
        if ( currencyRateDTO == null ) {
            return null;
        }

        BigDecimal rate = null;
        String fromCurrency = null;
        String toCurrency = null;

        rate = currencyRateDTO.amount();
        fromCurrency = currencyRateDTO.fromCurrency();
        toCurrency = currencyRateDTO.toCurrency();

        int id = 0;

        CurrencyRate currencyRate = new CurrencyRate( id, fromCurrency, toCurrency, rate );

        return currencyRate;
    }
}
