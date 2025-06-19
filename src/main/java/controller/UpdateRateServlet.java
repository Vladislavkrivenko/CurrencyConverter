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

@WebServlet("/update-amount")
public class UpdateRateServlet extends HttpServlet {
    private final CurrencyRateService service = CurrencyRateService.getInstance();
    private final Gson gson = new Gson();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CurrencyRateDTO rateDTO = gson.fromJson(req.getReader(), CurrencyRateDTO.class);
            CurrencyValidator.rateValidator(rateDTO);
            boolean update = service.update(rateDTO);
            if (update) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
