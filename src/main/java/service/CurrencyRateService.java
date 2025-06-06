package service;

import dao.CurrencyRateDAO;
import dto.CurrencyRateDTO;
import mapper.CurrencyRateMapper;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class CurrencyRateService {
    private static final CurrencyRateService INSTANCE = new CurrencyRateService();
    private final CurrencyRateDAO rateDAO = CurrencyRateDAO.getInstance();

    private CurrencyRateService() {
    }

    public List<CurrencyRateDTO> findAll() {
        return rateDAO.findAll().stream().map(CurrencyRateMapper.CURRENCY_RATE_MAPPER::toRateDto)
                .collect(toList());
    }

    public static CurrencyRateService getInstance() {
        return INSTANCE;
    }

}
