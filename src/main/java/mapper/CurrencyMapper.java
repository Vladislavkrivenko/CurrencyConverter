package mapper;

import dto.CurrencyDTO;
import entity.Currencies;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CurrencyMapper {
    CurrencyMapper CURRENCY_MAPPER = Mappers.getMapper(CurrencyMapper.class);

    CurrencyDTO toDto(Currencies currencies);
    Currencies toEntity(CurrencyDTO currencyDTO);
}
