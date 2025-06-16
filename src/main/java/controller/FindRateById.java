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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@WebServlet("/find_by_id")
public class FindRateById extends HttpServlet {
    private final CurrencyRateService service = CurrencyRateService.getInstance();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Optional<CurrencyRateDTO> rateDTO = service.findById(id);

            if (rateDTO.isPresent()) {
                String json = gson.toJson(rateDTO.get());

                try (var printWriter = resp.getWriter()) {
                    printWriter.write(json);
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
