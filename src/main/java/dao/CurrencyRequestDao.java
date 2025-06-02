package dao;

import dao.daoUtil.ConnectionManager;
import dto.CurrencyRequestDTO;
import exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyRequestDao {
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
            WHERE id = ?
            """;

    public void save(CurrencyRequestDTO requestDTO) throws SQLException {
        try (var con = ConnectionManager.getConnect();
             var preparedStatement = con.prepareStatement(SAVE_CURRENCY_RATE)) {
            preparedStatement.setInt(1, requestDTO.id());
            preparedStatement.setString(2, requestDTO.fromCurrency());
            preparedStatement.setString(3, requestDTO.toCurrency());
            preparedStatement.setString(4, requestDTO.rate().toPlainString());
            preparedStatement.executeUpdate();
            System.out.println("Currency saved successfully!");
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public void delete(int id) {
        try (var connection = ConnectionManager.getConnect();
             var preparedStatement = connection.prepareStatement(DELETE_CURRENCY_RATE)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public void update(CurrencyRequestDTO requestDTO) {
        try (var connection = ConnectionManager.getConnect();
             var preparedStatement = connection.prepareStatement(UPDATE_CURRENCY_RATE)) {
            preparedStatement.setInt(1, requestDTO.id());
            preparedStatement.setString(1, requestDTO.fromCurrency());
            preparedStatement.setString(2, requestDTO.toCurrency());
            preparedStatement.setString(3, requestDTO.rate().toPlainString());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Optional<CurrencyRequestDTO> findById(int id) {
        try (var connection = ConnectionManager.getConnect();
             var preparedStatement = connection.prepareStatement(FIND_CURRENCY_BY_ID_RATE)) {
            preparedStatement.setInt(1, id);

            var resultSet = preparedStatement.executeQuery();
            CurrencyRequestDTO currencyRequestDTO = null;
            if (resultSet.next()) {
                currencyRequestDTO = buildDto(resultSet);
            }
            return Optional.ofNullable(currencyRequestDTO);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public List<CurrencyRequestDTO> findAll() {
        try (var connection = ConnectionManager.getConnect();) {
            var preparedStatement = connection.prepareStatement(FIND_ALL_CURRENCY_RATE);
            var resultSet = preparedStatement.executeQuery();
            List<CurrencyRequestDTO> requestDTOS = new ArrayList<>();
            while (resultSet.next()) {
                requestDTOS.add(buildDto(resultSet));
            }
            return requestDTOS;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private CurrencyRequestDTO buildDto(ResultSet resultSet) throws SQLException {
        return new CurrencyRequestDTO(
                resultSet.getInt("id"),
                resultSet.getString("from_Currency"),
                resultSet.getString("to_Currency"),
                resultSet.getBigDecimal("rate")
        );
    }
}
