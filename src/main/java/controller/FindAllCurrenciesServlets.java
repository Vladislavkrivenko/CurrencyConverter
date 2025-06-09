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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/find-all")
public class FindAllCurrenciesServlets extends HttpServlet {
    private final CurrencyService service = CurrencyService.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        List<CurrencyDTO> currencies = service.findAll();
        String json = gson.toJson(currencies);
        try (var printWriter = resp.getWriter()) {
            printWriter.write(json);
        }
    }
}
