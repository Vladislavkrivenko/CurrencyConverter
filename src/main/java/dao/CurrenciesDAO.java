package dao;

import dao.daoUtil.ConnectionManager;
import entity.Currencies;
import exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrenciesDAO {
    private static final CurrenciesDAO INSTANCE = new CurrenciesDAO();

    private static final String DELETE_CURRENCY = """
            DELETE FROM currency
            WHERE id = ?
            """;
    private static final String SAVE_CURRENCY = """
            INSERT INTO currency (id, full_name, code, sign)
            VALUES(?, ?, ?, ?)
            """;

    private static final String UPDATE_CURRENCY = """
            UPDATE currency
            SET full_name = ?,
                code = ?,
                sign = ?
               WHERE id = ? 
               """;
    private static final String FIND_CURRENCY_BY_CODE = """
             SELECT * 
             FROM currency 
             WHERE code = ?;
            """;
    private static final String FIND_ALL_CURRENCY = """
            SELECT id,
                   full_name,
                   code,
                   sign
            FROM currency
            """;

    private CurrenciesDAO() {
    }


    public void save(Currencies currencies) throws SQLException {
        try (var con = ConnectionManager.getConnect();
             var preparedStatement = con.prepareStatement(SAVE_CURRENCY)) {
            preparedStatement.setInt(1, currencies.id());
            preparedStatement.setString(2, currencies.fullName());
            preparedStatement.setString(3, currencies.code());
            preparedStatement.setString(4, currencies.sign());
            preparedStatement.executeUpdate();
            System.out.println("Currency saved successfully!");
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public boolean delete(int id) {
        try (var connection = ConnectionManager.getConnect();
             var preparedStatement = connection.prepareStatement(DELETE_CURRENCY)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public boolean update(Currencies currencies) {
        try (var connection = ConnectionManager.getConnect();
             var preparedStatement = connection.prepareStatement(UPDATE_CURRENCY)) {
            preparedStatement.setString(1, currencies.fullName());
            preparedStatement.setString(2, currencies.code());
            preparedStatement.setString(3, currencies.sign());
            preparedStatement.setInt(4, currencies.id());
            int rowsUpdate = preparedStatement.executeUpdate();
            return rowsUpdate > 0;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Optional<Currencies> findByCode(String code) {
        try (var connection = ConnectionManager.getConnect();
             var preparedStatement = connection.prepareStatement(FIND_CURRENCY_BY_CODE)) {
            preparedStatement.setString(1, code);

            var resultSet = preparedStatement.executeQuery();
            Currencies currencies = null;

            if (resultSet.next()) {
                currencies = buildCurrencies(resultSet);
            }
            return Optional.ofNullable(currencies);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public List<Currencies> findAll() {
        try (var connection = ConnectionManager.getConnect();) {
            var preparedStatement = connection.prepareStatement(FIND_ALL_CURRENCY);
            var resultSet = preparedStatement.executeQuery();
            List<Currencies> currencies = new ArrayList<>();
            while (resultSet.next()) {
                currencies.add(buildCurrencies(resultSet));
            }
            return currencies;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private Currencies buildCurrencies(ResultSet resultSet) throws SQLException {
        return new Currencies(
                resultSet.getInt("id"),
                resultSet.getString("full_name"),
                resultSet.getString("code"),
                resultSet.getString("sign")
        );
    }

    public static CurrenciesDAO getInstance() {
        return INSTANCE;
    }
}
