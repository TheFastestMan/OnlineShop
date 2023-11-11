package ru.railshop.onlineshop.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.railshop.onlineshop.dto.ProductDto;
import ru.railshop.onlineshop.service.ProductService;
import ru.railshop.onlineshop.util.JspHelper;

import java.io.IOException;

@WebServlet("/admin/addProducts")
public class AddProductsServlet extends HttpServlet {

    private final ProductService productService = ProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher(JspHelper.getJspFormat("addProducts")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("productName");
        String description = req.getParameter("description");
        Integer quantity = Integer.valueOf(req.getParameter("quantity"));
        Double price = Double.valueOf(req.getParameter("price"));

        ProductDto productDto = ProductDto.builder()
                .productName(name)
                .description(description)
                .quantity(quantity)
                .price(price)
                .build();

        try {
            productService.addProduct(productDto);
            resp.sendRedirect(req.getContextPath() + "/user");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}

