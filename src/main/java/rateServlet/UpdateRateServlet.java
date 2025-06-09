package rateServlet;

import com.google.gson.Gson;
import dto.CurrencyRateDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CurrencyRateService;

import java.io.IOException;

@WebServlet("/update-rate")
public class UpdateRateServlet extends HttpServlet {
    CurrencyRateService service = CurrencyRateService.getInstance();
    Gson gson = new Gson();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CurrencyRateDTO rateDTO = gson.fromJson(req.getReader(), CurrencyRateDTO.class);
            boolean update = service.update(rateDTO);
            if (update) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
