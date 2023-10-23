package ru.railshop.onlineshop.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.railshop.onlineshop.dto.UserDto;
import ru.railshop.onlineshop.entity.Gender;
import ru.railshop.onlineshop.entity.Role;
import ru.railshop.onlineshop.exception.ValidationException;
import ru.railshop.onlineshop.service.UserService;
import ru.railshop.onlineshop.util.JspHelper;
import ru.railshop.onlineshop.validator.CreateUserValidator;
import ru.railshop.onlineshop.validator.ValidateResult;

import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();
    private final CreateUserValidator userValidator = CreateUserValidator.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", Role.values());
        req.setAttribute("genders", Gender.values());
        req.getRequestDispatcher(JspHelper.getJspFormat("registration")).forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        Role role = Role.valueOf(req.getParameter("role"));
        Gender gender = Gender.valueOf(req.getParameter("gender"));

        UserDto userDto = UserDto.builder()
                .username(name)
                .email(email)
                .password(password)
                .role(Role.valueOf(String.valueOf(role)))
                .gender(Gender.valueOf(String.valueOf(gender)))
                .build();

        ValidateResult validationResult = userValidator.isValid(userDto);

        if (!validationResult.isValid()) {
            req.setAttribute("errors", validationResult.getErrors());
            doGet(req, resp);
            return;
        }

        try {
            userService.create(userDto);
            resp.sendRedirect("login");
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }
    }
}
