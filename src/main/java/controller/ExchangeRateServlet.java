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
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {
    private final CurrencyRateService service = CurrencyRateService.getInstance();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try {
            String pathInfo = req.getPathInfo();
            if (pathInfo == null || pathInfo.length() != 7) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            String pair = pathInfo.substring(1).toUpperCase();
            String from = pair.substring(0, 3);
            String to = pair.substring(3);

            Optional<CurrencyRateDTO> rateDTO = service.findByPair(from, to);
            if (rateDTO.isPresent()) {
                resp.setStatus(HttpServletResponse.SC_OK);
                String json = gson.toJson(rateDTO.get());

                try (var printWriter = resp.getWriter()) {
                    printWriter.write(json);
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("PATCH".equalsIgnoreCase(req.getMethod())) {
            doPatch(req, resp);
        } else {
            super.service(req, resp);
        }
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try {
            String pathInfo = req.getPathInfo();
            if (pathInfo == null || pathInfo.length() != 7) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String pair = pathInfo.substring(1).toUpperCase();
            String from = pair.substring(0, 3);
            String to = pair.substring(3);

            String rateStr = req.getParameter("rate");
            if (rateStr == null || rateStr.isBlank()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            BigDecimal rate;
            try {
                rate = new BigDecimal(rateStr);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            boolean updated = service.update(from, to, rate);
            if (updated) {
                var updatedRateOpt = service.findByPair(from, to);
                if (updatedRateOpt.isPresent()) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    String json = gson.toJson(updatedRateOpt.get());
                    resp.getWriter().write(json);
                } else {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}