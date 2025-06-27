package mapper;

import dto.CurrencyDTO;
import entity.Currencies;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CurrencyMapper {
    CurrencyMapper CURRENCY_MAPPER = Mappers.getMapper(CurrencyMapper.class);

    @Mapping(source = "fullName", target = "name")
    CurrencyDTO toDto(Currencies currencies);

    @Mapping(source = "name", target = "fullName")
    Currencies toEntity(CurrencyDTO currencyDTO);
}
