package dao;

import dao.daoUtil.ConnectionManager;
import dto.CurrencyDTO;
import exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyDAO {
    private static final String DELETE_CURRENCY = """
            DELETE FROM currency
            WHERE id = ?
            """;
    private static final String SAVE_CURRENCY = """
            INSERT INTO currency (id, full_name, code, sign,)
            VALUES((?, ?, ?, ?)
            """;

    private static final String UPDATE_CURRENCY = """
            UPDATE currency
            SET id = ?,
                full_name = ?,
                code = ?,
                sign = ?
               WHERE id = ? 
               """;
    private static final String FIND_CURRENCY_BY_ID = """
            SELECT id, 
                   full_name,
                   code, 
                   sign
            FROM currency
            WHERE id = ?
            """;
    private static final String FIND_ALL_CURRENCY = """
            SELECT * 
            FROM currency
            WHERE id = ?
            """;

    public void save(CurrencyDTO currencyDTO) throws SQLException {
        try (var con = ConnectionManager.getConnect();
             var preparedStatement = con.prepareStatement(SAVE_CURRENCY)) {
            preparedStatement.setInt(1, currencyDTO.id());
            preparedStatement.setString(2, currencyDTO.fullName());
            preparedStatement.setString(3, currencyDTO.code());
            preparedStatement.setString(4, currencyDTO.sign());
            preparedStatement.executeUpdate();
            System.out.println("Currency saved successfully!");
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public void delete(int id) {
        try (var connection = ConnectionManager.getConnect();
             var preparedStatement = connection.prepareStatement(DELETE_CURRENCY)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public void update(CurrencyDTO currencyDTO) {
        try (var connection = ConnectionManager.getConnect();
             var preparedStatement = connection.prepareStatement(UPDATE_CURRENCY)) {
            preparedStatement.setInt(1, currencyDTO.id());
            preparedStatement.setString(1, currencyDTO.fullName());
            preparedStatement.setString(2, currencyDTO.code());
            preparedStatement.setString(3, currencyDTO.sign());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Optional<CurrencyDTO> findById(int id) {
        try (var connection = ConnectionManager.getConnect();
             var preparedStatement = connection.prepareStatement(FIND_CURRENCY_BY_ID)) {
            preparedStatement.setInt(1, id);

            var resultSet = preparedStatement.executeQuery();
            CurrencyDTO currencyDTO = null;
            if (resultSet.next()) {
                currencyDTO = buildDto(resultSet);
            }
            return Optional.ofNullable(currencyDTO);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public List<CurrencyDTO> findAll() {
        try (var connection = ConnectionManager.getConnect();) {
            var preparedStatement = connection.prepareStatement(FIND_ALL_CURRENCY);
            var resultSet = preparedStatement.executeQuery();
            List<CurrencyDTO> dtos = new ArrayList<>();
            while (resultSet.next()) {
                dtos.add(buildDto(resultSet));
            }
            return dtos;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private CurrencyDTO buildDto(ResultSet resultSet) throws SQLException {
        return new CurrencyDTO(
                resultSet.getInt("id"),
                resultSet.getString("full_name"),
                resultSet.getString("code"),
                resultSet.getString("sign")
        );
    }
}
