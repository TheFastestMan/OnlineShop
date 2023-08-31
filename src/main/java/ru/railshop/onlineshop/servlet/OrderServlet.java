package ru.railshop.onlineshop.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.railshop.onlineshop.service.OrderService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {

    private final OrderService orderService = OrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try (var writer = resp.getWriter()) {
            writer.write("<h1> List of orders </h1>");
            writer.write("<ul>");
            orderService.findAllOrder().stream().forEach(orderDto ->
                    writer.write("""
                            <li>
                            %s-%s 
                            </li>
                            """.formatted(orderDto.id(), orderDto.orderDateAndStatus())));
            writer.write("</ul>");

        }
    }
}
