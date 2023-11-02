package ru.railshop.onlineshop.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.railshop.onlineshop.dto.UserDto;
import ru.railshop.onlineshop.service.UserService;
import ru.railshop.onlineshop.util.JspHelper;


import java.io.IOException;
import java.util.Optional;

@Slf4j
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Entering LoginServlet's doGet method");
        req.getRequestDispatcher(JspHelper.getJspFormat("login")).forward(req, resp);
        log.info("Exiting LoginServlet's doGet method");
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Entering LoginServlet's doPost method");

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Optional<UserDto> userOptional = userService.login(email, password);
        log.info("hi00000");
        if (email != null && password != null && userOptional.isPresent()) {
            UserDto user = userOptional.get();
            log.info("hi10101");

            req.getSession().setAttribute("user", user);
            resp.sendRedirect("/user");
            log.info("hi20202");
        } else {
            resp.sendRedirect("/login?error&email=" + email);
            log.info("hi30303");
        }

        log.info("Exiting LoginServlet's doPost method");
        log.info("hi40404");
    }
}