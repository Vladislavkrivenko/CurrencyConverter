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

@WebServlet("/update-currency")
public class UpdateCurrencyServlet extends HttpServlet {

    private final CurrencyService service = CurrencyService.getInstance();
    private final Gson gson = new Gson();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            CurrencyDTO currencyDTO = gson.fromJson(req.getReader(), CurrencyDTO.class);
            CurrencyValidator.currenciesValidator(currencyDTO);
            boolean updated = service.update(currencyDTO);
            if (updated) {
                resp.setStatus(200);
            } else {
                resp.setStatus(404);
            }
        } catch (IllegalArgumentException e) {
            resp.setStatus(400);
        }catch (Exception e){
            resp.setStatus(500);
        }
    }
}
