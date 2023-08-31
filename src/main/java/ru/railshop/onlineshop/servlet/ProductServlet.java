package ru.railshop.onlineshop.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.railshop.onlineshop.service.ProductService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    private final ProductService productService = ProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try (var writer = resp.getWriter()) {
            writer.write("<h1>List of products</h1>");
            writer.write("<ul>");
            productService.findAllProduct().stream().forEach(productDto ->
                    writer.write("""
                            <li>
                            %s
                            </li>
                            """.formatted(productDto.description())));
            writer.write("</ul>");
        }
    }
}
