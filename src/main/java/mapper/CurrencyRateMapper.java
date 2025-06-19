package mapper;

import dto.CurrencyRateDTO;
import entity.CurrencyRate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CurrencyRateMapper {
    CurrencyRateMapper CURRENCY_RATE_MAPPER = Mappers.getMapper(CurrencyRateMapper.class);

    @Mapping(source = "rate", target = "amount")
    CurrencyRateDTO toRateDto(CurrencyRate currencyRate);

    @Mapping(source = "amount", target = "rate")
    CurrencyRate toEntity(CurrencyRateDTO currencyRateDTO);
}
