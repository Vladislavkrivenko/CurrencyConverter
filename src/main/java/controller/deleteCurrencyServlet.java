package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CurrencyService;

import java.io.IOException;

@WebServlet("/delete-currency")
public class deleteCurrencyServlet extends HttpServlet {
    CurrencyService service = CurrencyService.getInstance();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        boolean deleted = service.delete(id);
        resp.setStatus(deleted ? HttpServletResponse.SC_NO_CONTENT : HttpServletResponse.SC_NOT_FOUND);
    }
}
