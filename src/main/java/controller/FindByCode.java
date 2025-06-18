package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CurrencyDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CurrencyService;
import util.CurrencyValidator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@WebServlet("/find_by_code")
public class FindByCode extends HttpServlet {
    private final CurrencyService service = CurrencyService.getInstance();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try {
            String code = req.getParameter("code");
            if (code == null || code.isBlank()) {
                throw new IllegalArgumentException("Missing or empty 'code' parameter");
            }
            CurrencyValidator.validateCurrencyCode(code);
            Optional<CurrencyDTO> currencyDTO = service.findByCode(code.toUpperCase());

            if (currencyDTO.isPresent()) {
                resp.setStatus(HttpServletResponse.SC_OK);
                String json = gson.toJson(currencyDTO.get());
                try (var printWriter = resp.getWriter()) {
                    printWriter.write(json);
                }
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
