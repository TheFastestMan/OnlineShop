package ru.railshop.onlineshop.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.hibernate.Session;
import ru.railshop.onlineshop.dto.UserDto;
import ru.railshop.onlineshop.service.UserService;
import ru.railshop.onlineshop.util.JspHelper;

import java.util.List;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        List<UserDto> allUsers = userService.findAllUser();
        req.setAttribute("users", allUsers);
        req.getRequestDispatcher(JspHelper.getJspFormat("adminPanel")).forward(req, resp);
    }
}
