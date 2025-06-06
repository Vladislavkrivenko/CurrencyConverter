package service;

import dao.CurrenciesDAO;
import dto.CurrencyDTO;
import entity.Currencies;
import mapper.CurrencyMapper;

import java.sql.SQLException;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class CurrencyService {
    private static final CurrencyService INSTANCE = new CurrencyService();
    private final CurrenciesDAO dao = CurrenciesDAO.getInstance();

    private CurrencyService() {
    }

    public List<CurrencyDTO> findAll() {
        return dao.findAll().stream().map(CurrencyMapper.CURRENCY_MAPPER::toDto)
                .collect(toList());

    }

    public void save(CurrencyDTO currencyDTO) throws SQLException {
        Currencies currencies = CurrencyMapper.CURRENCY_MAPPER.toEntity(currencyDTO);
        dao.save(currencies);
    }

    public boolean delete(int id) {
        return dao.delete(id);
    }

    public boolean update(CurrencyDTO currencyDTO) {
        Currencies currencies = CurrencyMapper.CURRENCY_MAPPER.toEntity(currencyDTO);
        return dao.update(currencies);
    }

    public static CurrencyService getInstance() {
        return INSTANCE;
    }
}
