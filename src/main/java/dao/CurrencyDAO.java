package dao;

import dao.daoUtil.ConnectionManager;
import dto.CurrencyDTO;
import dto.CurrencyRequestDTO;
import service.CurrencyRate;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static dao.daoUtil.ConnectionManager.openConnection;

public class CurrencyDAO {
    public static void main(String[] args) throws SQLException {
//        String sql = "CREATE TABLE IF NOT EXISTS currencyRate (\n"
//                     + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
//                     + "    from_Currency TEXT NOT NULL,\n"
//                     + "    to_Currency TEXT NOT NULL,\n"
//                     + "    rate TEXT NOT NULL\n"
//                     + ");";
//        try (Connection connection = openConnection()) {
//            var statement = connection.createStatement();
//            System.out.println(connection.getTransactionIsolation());
//            var executeResult = statement.execute(sql);
//            System.out.println(executeResult + "create");
//        }
        CurrencyRequestDTO currencyRequestDTO = new CurrencyRequestDTO("RUB", "UAH", new BigDecimal("0.3"));
        CurrencyDAO currencyDAO = new CurrencyDAO();
        currencyDAO.saveCurrencyRate(currencyRequestDTO);
    }


    public CurrencyRate findRate(String from, String to, BigDecimal rate) {
        return new CurrencyRate(from, to, rate);
    }

    public CurrencyDTO getAllCurrencies(String fullName, String code, String sign, BigDecimal rate) {
        String str = """
                SELECT full_name,
                code,
                sign,
                FROM currencies
                """;
        return new CurrencyDTO(fullName, code, sign);
    }

    public void saveCurrency(CurrencyDTO currencyDTO) throws SQLException {
        String sql = "INSERT INTO currency (full_name, code, sign) VALUES (?, ?, ?)";
        try (var con = ConnectionManager.openConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {

            preparedStatement.setString(1, currencyDTO.fullName());
            preparedStatement.setString(2, currencyDTO.code());
            preparedStatement.setString(3, currencyDTO.sign());
            preparedStatement.executeUpdate();
            System.out.println("Currency saved successfully!");
        } catch (SQLException e) {
            throw new RuntimeException("Error saving currencies", e);
        }

    }

    public void saveCurrencyRate(CurrencyRequestDTO requestDTO) throws SQLException {
        String sql = "INSERT INTO currencyRate (from_Currency, to_Currency, rate) VALUES (?, ?, ?)";
        try (var con = ConnectionManager.openConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {

            preparedStatement.setString(1, requestDTO.fromCurrency());
            preparedStatement.setString(2, requestDTO.toCurrency());
            preparedStatement.setString(3, requestDTO.rate().toPlainString());
            preparedStatement.executeUpdate();
            System.out.println("Currency saved successfully!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            openConnection().close();
        }

    }
}
