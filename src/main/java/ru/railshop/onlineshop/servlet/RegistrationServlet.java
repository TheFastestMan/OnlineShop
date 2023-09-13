package ru.railshop.onlineshop.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.railshop.onlineshop.dto.CreateUserDto;
import ru.railshop.onlineshop.service.UserService;
import ru.railshop.onlineshop.util.JspHelper;

import java.io.IOException;
import java.util.List;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", List.of("ADMIN", "USER"));
        req.setAttribute("genders", List.of("MALE", "FEMALE"));
        req.getRequestDispatcher(JspHelper.getJspFormat("registration")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String role = req.getParameter("role");
        String gender = req.getParameter("gender");

        CreateUserDto createUserDto = new CreateUserDto(name, password, email, role, gender);

        userService.create(createUserDto);

    }
}
