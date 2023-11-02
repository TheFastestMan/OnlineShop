package ru.railshop.onlineshop.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.railshop.onlineshop.dto.ProductDto;
import ru.railshop.onlineshop.service.ProductService;
import ru.railshop.onlineshop.util.JspHelper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@WebServlet("/products")
public class ProductsListServlet extends HttpServlet {
    private final ProductService productService = ProductService.getInstance();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        log.info("Entering ProductsListServlet's doGet method");

        try {
            List<ProductDto> products = productService.getAllProducts();
            System.out.println("Number of products retrieved: " + products.size());
            req.setAttribute("pr", products);
            req.getRequestDispatcher(JspHelper.getJspFormat("products")).forward(req, resp);
        } catch (Exception e) {
            log.error("Error processing the request", e);
            // You can redirect to an error page or take other actions here.
            // For simplicity, we're just forwarding to a general error page:
            req.getRequestDispatcher(JspHelper.getJspFormat("error")).forward(req, resp);
        }

        log.info("Exiting ProductsListServlet's doGet method");
    }
}

