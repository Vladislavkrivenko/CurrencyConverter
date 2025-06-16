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
import java.util.List;

@WebServlet("/all-rate")
public class FindAllRateServlet extends HttpServlet {
    private final CurrencyRateService service = CurrencyRateService.getInstance();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        List<CurrencyRateDTO> requestDTOS = service.findAll();
        String json = gson.toJson(requestDTOS);
        try (var printWriter = resp.getWriter()) {
            printWriter.write(json);
        }

    }
}

