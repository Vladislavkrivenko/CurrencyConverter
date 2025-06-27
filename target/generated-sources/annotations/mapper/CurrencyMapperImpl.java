package mapper;

import dto.CurrencyDTO;
import entity.Currencies;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-27T15:08:32+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
public class CurrencyMapperImpl implements CurrencyMapper {

    @Override
    public CurrencyDTO toDto(Currencies currencies) {
        if ( currencies == null ) {
            return null;
        }

        String name = null;
        String code = null;
        String sign = null;

        name = currencies.fullName();
        code = currencies.code();
        sign = currencies.sign();

        CurrencyDTO currencyDTO = new CurrencyDTO( name, code, sign );

        return currencyDTO;
    }

    @Override
    public Currencies toEntity(CurrencyDTO currencyDTO) {
        if ( currencyDTO == null ) {
            return null;
        }

        String fullName = null;
        String code = null;
        String sign = null;

        fullName = currencyDTO.name();
        code = currencyDTO.code();
        sign = currencyDTO.sign();

        int id = 0;

        Currencies currencies = new Currencies( id, fullName, code, sign );

        return currencies;
    }
}
