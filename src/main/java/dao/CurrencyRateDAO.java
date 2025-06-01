package dao;

import dao.daoUtil.ConnectionManager;
import dto.CurrencyRequestDTO;
import service.CurrencyRate;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CurrencyRateDAO {
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
//        CurrencyRequestDTO currencyRequestDTO = new CurrencyRequestDTO("GBP", "UAH", new BigDecimal("55.5"));
//        CurrencyDTO currencyDTO = new CurrencyDTO("US Dollar", "USD", "$");
        CurrencyDAO currencyDAO = new CurrencyDAO();
//        currencyDAO.saveCurrency(currencyDTO);
    }

    public void saveCurrencyRate(CurrencyRequestDTO requestDTO) throws SQLException {
        String sql = "INSERT INTO currencyRate (from_Currency, to_Currency, rate) VALUES (?, ?, ?)";
        try (var con = ConnectionManager.getConnect();
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {

            preparedStatement.setString(1, requestDTO.fromCurrency());
            preparedStatement.setString(2, requestDTO.toCurrency());
            preparedStatement.setString(3, requestDTO.rate().toPlainString());
            preparedStatement.executeUpdate();
            System.out.println("Currency saved successfully!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public CurrencyRate findRate(String from, String to, BigDecimal rate) {
        return new CurrencyRate(from, to, rate);
    }
}
