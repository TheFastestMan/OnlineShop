package ru.railshop.onlineshop.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ru.railshop.onlineshop.dto.ProductDto;
import ru.railshop.onlineshop.dto.UserDto;
import ru.railshop.onlineshop.service.CartService;
import ru.railshop.onlineshop.service.ProductService;

import java.io.IOException;

@Slf4j
@WebServlet("/addToCart")
public class AddToCartServlet extends HttpServlet {
    private final ProductService productService = ProductService.getInstance();
    private final CartService cartService = CartService.getInstance();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Entering AddToCartServlet's doPost method");

        Long productId = Long.parseLong(req.getParameter("productId"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        try {
            UserDto userDto = (UserDto) req.getSession().getAttribute("user");
            ProductDto productDto = productService.getProductById(productId);

            if (userDto != null && productDto != null && productDto.getQuantity() >= quantity) {
                productService.reduceQuantityByOne(productId, quantity);
                cartService.addProductToCart(userDto, productDto, quantity);

                log.debug("Product ID received: " + productId + " with quantity: " + quantity);

                req.setAttribute("addToCartSuccess", true);
            } else {
                req.setAttribute("addToCartError", true);
            }
        } catch (Exception e) {
            log.error("Error adding the product to the cart", e);
            req.setAttribute("addToCartError", true);
        }
        resp.sendRedirect(req.getContextPath() + "/products");
    }
}