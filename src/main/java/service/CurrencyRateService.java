package service;

import dao.CurrencyRateDAO;
import dto.CurrencyRateDTO;
import dto.CurrencyResultDTO;
import entity.CurrencyRate;
import mapper.CurrencyRateMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class CurrencyRateService implements CurrenciesService<CurrencyRateDTO> {
    private static final CurrencyRateService INSTANCE = new CurrencyRateService();
    private final CurrencyRateDAO rateDAO = CurrencyRateDAO.getInstance();

    private CurrencyRateService() {
    }

    @Override
    public List<CurrencyRateDTO> findAll() {
        return rateDAO.findAll().stream().map(CurrencyRateMapper.CURRENCY_RATE_MAPPER::toRateDto)
                .collect(toList());
    }

    @Override
    public void save(CurrencyRateDTO currencyRateDTO) throws SQLException {
        CurrencyRate rate = CurrencyRateMapper.CURRENCY_RATE_MAPPER.toEntity(currencyRateDTO);
        rateDAO.save(rate);
    }

    @Override
    public boolean delete(int id) {
        return rateDAO.delete(id);
    }

    @Override
    public boolean update(CurrencyRateDTO currencyRateDTO) {
        CurrencyRate rate = CurrencyRateMapper.CURRENCY_RATE_MAPPER.toEntity(currencyRateDTO);
        return rateDAO.update(rate);
    }

    public Optional<CurrencyRateDTO> findByPair(String from, String to) {
        return rateDAO.findCurrencyPair(from, to)
                .map(CurrencyRateMapper.CURRENCY_RATE_MAPPER::toRateDto);
    }

    public static CurrencyRateService getInstance() {
        return INSTANCE;
    }

    public CurrencyResultDTO convert(CurrencyRateDTO rateDTO) {
        CurrencyRate currencyRate = rateDAO.findCurrencyPair(rateDTO.fromCurrency(), rateDTO.toCurrency())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Course not found for pair: " + rateDTO.fromCurrency() + "-" + rateDTO.toCurrency()));

        BigDecimal result = rateDTO.amount()
                .multiply(currencyRate.rate()).setScale(4, RoundingMode.HALF_UP);
        return new CurrencyResultDTO(result);
    }
}
