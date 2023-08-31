package ru.railshop.onlineshop.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.railshop.onlineshop.entity.User;
import ru.railshop.onlineshop.exception.DaoException;
import ru.railshop.onlineshop.service.UserService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        Long userId = Long.valueOf(req.getParameter("userId"));
        try (var writer = resp.getWriter()) {
            writer.write("<h1>User:</h1>");
            writer.write("<ul>");

            userService.findUserDyId(userId).stream().forEach(userDto ->
                    writer.write("""
                            <li>
                            <a>%s</a>
                            </li>
                            """.formatted(userDto.description())));

            writer.write("</ul>");
        } catch (DaoException e) {
            throw new DaoException(e);
        }
    }
}

