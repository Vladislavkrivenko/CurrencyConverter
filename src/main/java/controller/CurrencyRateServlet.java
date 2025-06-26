package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CurrencyRateDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CurrencyRateService;
import util.CurrencyValidator;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/exchangeRates")
public class CurrencyRateServlet extends HttpServlet {
    private final CurrencyRateService service = CurrencyRateService.getInstance();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try {
            List<CurrencyRateDTO> rateDTOS = service.findAll();
            String json = gson.toJson(rateDTOS);
            resp.setStatus(HttpServletResponse.SC_OK);

            try (var printWriter = resp.getWriter()) {
                printWriter.write(json);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try {

            String fromCurrency = req.getParameter("baseCurrencyCode");
            String toCurrency = req.getParameter("targetCurrencyCode");
            String amountStr = req.getParameter("rate");

            if (fromCurrency == null || fromCurrency.isBlank()
                || toCurrency == null || toCurrency.isBlank()
                || amountStr == null || amountStr.isBlank()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            BigDecimal amount;
            try {
                amount = new BigDecimal(amountStr);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            CurrencyRateDTO dto = new CurrencyRateDTO(fromCurrency.toUpperCase(), toCurrency.toUpperCase(), amount);
            CurrencyValidator.rateValidator(dto);

            service.save(dto);
            String json = gson.toJson(dto);
            try (PrintWriter writer = resp.getWriter()) {
                writer.write(json);
            }
            resp.setStatus(HttpServletResponse.SC_CREATED);

        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

