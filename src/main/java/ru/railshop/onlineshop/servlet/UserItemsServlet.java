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
import java.util.List;

@WebServlet("/userItems")
public class UserItemsServlet extends HttpServlet {

    private final ProductService productService = ProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long userId = Long.parseLong(req.getParameter("userId"));
            List<ProductDto> productDTOs = productService.getProductsByUserId(userId);

            req.setAttribute("userItems", productDTOs);
            req.getRequestDispatcher(JspHelper.getJspFormat("userItems")).forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to retrieve user items.");
        }
    }
}
