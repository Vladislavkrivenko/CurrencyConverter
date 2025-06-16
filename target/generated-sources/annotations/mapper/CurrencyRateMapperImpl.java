package mapper;

import dto.CurrencyRateDTO;
import entity.CurrencyRate;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-12T16:58:44+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
public class CurrencyRateMapperImpl implements CurrencyRateMapper {

    @Override
    public CurrencyRateDTO toRateDto(CurrencyRate currencyRate) {
        if ( currencyRate == null ) {
            return null;
        }

        String fromCurrency = null;
        String toCurrency = null;
        BigDecimal rate = null;

        fromCurrency = currencyRate.fromCurrency();
        toCurrency = currencyRate.toCurrency();
        rate = currencyRate.rate();

        CurrencyRateDTO currencyRateDTO = new CurrencyRateDTO( fromCurrency, toCurrency, rate );

        return currencyRateDTO;
    }

    @Override
    public CurrencyRate toEntity(CurrencyRateDTO currencyRateDTO) {
        if ( currencyRateDTO == null ) {
            return null;
        }

        String fromCurrency = null;
        String toCurrency = null;
        BigDecimal rate = null;

        fromCurrency = currencyRateDTO.fromCurrency();
        toCurrency = currencyRateDTO.toCurrency();
        rate = currencyRateDTO.rate();

        int id = 0;

        CurrencyRate currencyRate = new CurrencyRate( id, fromCurrency, toCurrency, rate );

        return currencyRate;
    }
}
