package mapper;

import dto.CurrencyRateDTO;
import entity.CurrencyRate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CurrencyRateMapper {
    CurrencyRateMapper CURRENCY_RATE_MAPPER = Mappers.getMapper(CurrencyRateMapper.class);

    CurrencyRateDTO toRateDto(CurrencyRate currencyRate);

    CurrencyRate toEntity(CurrencyRateDTO currencyRateDTO);
}
