package dao;

import dao.daoUtil.ConnectionManager;
import entity.CurrencyRate;
import exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyRateDAO {
    private static final CurrencyRateDAO INSTANCE = new CurrencyRateDAO();


    private static final String DELETE_CURRENCY_RATE = """
            DELETE FROM currencyRate
            WHERE id = ?
            """;
    private static final String SAVE_CURRENCY_RATE = """
            INSERT INTO currencyRate (id, from_Currency, to_Currency, rate)
            VALUES((?, ?, ?,?)
            """;

    private static final String UPDATE_CURRENCY_RATE = """
            UPDATE currencyRate
            SET id,
            from_Currency,
            to_Currency,
            rate
               WHERE id = ? 
               """;
    private static final String FIND_CURRENCY_BY_ID_RATE = """
            SELECT id,
            from_Currency,
            to_Currency,
            rate
            FROM currencyRate
            WHERE id = ?
            """;
    private static final String FIND_ALL_CURRENCY_RATE = """
            SELECT id, 
            from_Currency,
            to_Currency,
            rate
            FROM currencyRate
            """;

    private CurrencyRateDAO() {
    }

    public void save(CurrencyRate currencyRate) throws SQLException {
        try (var con = ConnectionManager.getConnect();
             var preparedStatement = con.prepareStatement(SAVE_CURRENCY_RATE)) {
            preparedStatement.setInt(1, currencyRate.id());
            preparedStatement.setString(2, currencyRate.fromCurrency());
            preparedStatement.setString(3, currencyRate.toCurrency());
            preparedStatement.setString(4, currencyRate.rate().toPlainString());
            preparedStatement.executeUpdate();
            System.out.println("Currency saved successfully!");
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public boolean delete(int id) {
        try (var connection = ConnectionManager.getConnect();
             var preparedStatement = connection.prepareStatement(DELETE_CURRENCY_RATE)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public boolean update(CurrencyRate currencyRate) {
        try (var connection = ConnectionManager.getConnect();
             var preparedStatement = connection.prepareStatement(UPDATE_CURRENCY_RATE)) {
            preparedStatement.setInt(1, currencyRate.id());
            preparedStatement.setString(1, currencyRate.fromCurrency());
            preparedStatement.setString(2, currencyRate.toCurrency());
            preparedStatement.setString(3, currencyRate.rate().toPlainString());
            int rowsUpdate = preparedStatement.executeUpdate();
            return rowsUpdate > 0;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Optional<CurrencyRate> findById(int id) {
        try (var connection = ConnectionManager.getConnect();
             var preparedStatement = connection.prepareStatement(FIND_CURRENCY_BY_ID_RATE)) {
            preparedStatement.setInt(1, id);

            var resultSet = preparedStatement.executeQuery();
            CurrencyRate currencyRate = null;
            if (resultSet.next()) {
                currencyRate = buildCurrencyRate(resultSet);
            }
            return Optional.ofNullable(currencyRate);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public List<CurrencyRate> findAll() {
        try (var connection = ConnectionManager.getConnect();) {
            var preparedStatement = connection.prepareStatement(FIND_ALL_CURRENCY_RATE);
            var resultSet = preparedStatement.executeQuery();
            List<CurrencyRate> currencyRates = new ArrayList<>();
            while (resultSet.next()) {
                currencyRates.add(buildCurrencyRate(resultSet));
            }
            return currencyRates;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private CurrencyRate buildCurrencyRate(ResultSet resultSet) throws SQLException {
        return new CurrencyRate(
                resultSet.getInt("id"),
                resultSet.getString("from_Currency"),
                resultSet.getString("to_Currency"),
                resultSet.getBigDecimal("rate")
        );
    }

    public static CurrencyRateDAO getInstance() {
        return INSTANCE;
    }
}
