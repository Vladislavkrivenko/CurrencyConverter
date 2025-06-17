package controller;

import com.google.gson.Gson;
import dto.CurrencyRateDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CurrencyRateService;
import util.CurrencyValidator;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/save-rate")
public class SaveRateServlet extends HttpServlet {
    private final CurrencyRateService service = CurrencyRateService.getInstance();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CurrencyRateDTO currencyRateDTO = gson.fromJson(req.getReader(), CurrencyRateDTO.class);
        try {
            CurrencyValidator.rateValidator(currencyRateDTO);
            service.save(currencyRateDTO);
            resp.setStatus(201);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
