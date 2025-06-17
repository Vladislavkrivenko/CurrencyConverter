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

@WebServlet("/update-rate")
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
                resp.setStatus(200);
            } else {
                resp.setStatus(404);
            }
        } catch (IllegalArgumentException e) {
            resp.setStatus(400);
        } catch (Exception e) {
            resp.setStatus(500);
        }
    }
}
