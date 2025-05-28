package controller;

import com.google.gson.Gson;
import dto.CurrencyRequestDTO;
import dto.CurrencyResultDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CurrencyService;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

@WebServlet("/convert")
public class Controller extends HttpServlet {
    private final CurrencyService service = new CurrencyService();
    Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp);
    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String from = req.getParameter("from");
        String to = req.getParameter("to");
        BigDecimal amount = new BigDecimal(req.getParameter("amount"));

        CurrencyRequestDTO requestDTO = new CurrencyRequestDTO(from, to, amount);
        CurrencyResultDTO resultDTO = service.convert(requestDTO);

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.write(gson.toJson(resultDTO));
    }
}
