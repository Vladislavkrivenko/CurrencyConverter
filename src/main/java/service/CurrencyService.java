package service;

import dao.CurrenciesDAO;
import dto.CurrencyDTO;
import entity.Currencies;
import mapper.CurrencyMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class CurrencyService implements CurrenciesService<CurrencyDTO> {
    private static final CurrencyService INSTANCE = new CurrencyService();
    private final CurrenciesDAO dao = CurrenciesDAO.getInstance();

    private CurrencyService() {
    }

    @Override
    public List<CurrencyDTO> findAll() {
        return dao.findAll().stream().map(CurrencyMapper.CURRENCY_MAPPER::toDto)
                .collect(toList());

    }

    @Override
    public void save(CurrencyDTO currencyDTO) {
        Currencies currencies = CurrencyMapper.CURRENCY_MAPPER.toEntity(currencyDTO);
        try {
            dao.save(currencies);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int id) {
        return dao.delete(id);
    }

    @Override
    public boolean update(CurrencyDTO currencyDTO) {
        Currencies currencies = CurrencyMapper.CURRENCY_MAPPER.toEntity(currencyDTO);
        return dao.update(currencies);
    }

    public Optional<CurrencyDTO> findByCode(String code) {
        return dao.findByCode(code).map(CurrencyMapper.CURRENCY_MAPPER::toDto);
    }

    public static CurrencyService getInstance() {
        return INSTANCE;
    }
}
