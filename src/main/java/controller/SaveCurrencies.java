package controller;

import com.google.gson.Gson;
import dto.CurrencyDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CurrencyService;
import util.CurrencyValidator;

import java.io.IOException;
import java.sql.SQLException;

import static jakarta.servlet.http.HttpServletResponse.SC_CREATED;

@WebServlet("/save-currency")
public class SaveCurrencies extends HttpServlet {
    private final CurrencyService service = CurrencyService.getInstance();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CurrencyDTO currencyDTO = gson.fromJson(req.getReader(), CurrencyDTO.class);
        try {
            CurrencyValidator.currenciesValidator(currencyDTO);
            service.save(currencyDTO);
            resp.setStatus(SC_CREATED);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
