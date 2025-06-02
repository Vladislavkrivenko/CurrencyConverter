package service;

import dao.CurrencyDAO;
import dto.CurrencyRequestDTO;
import dto.CurrencyResultDTO;

import java.math.BigDecimal;

public class CurrencyService {
    private final CurrencyDAO dao = new CurrencyDAO();

    public CurrencyResultDTO convert(CurrencyRequestDTO dto) {
        CurrencyRate rate = dao.findById(dto.fromCurrency(), dto.toCurrency(), dto.rate());
        BigDecimal result = dto.rate().multiply(rate.rate());
        return new CurrencyResultDTO(result);
    }
}
