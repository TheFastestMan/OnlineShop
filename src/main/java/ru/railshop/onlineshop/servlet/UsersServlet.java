package ru.railshop.onlineshop.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.railshop.onlineshop.exception.DaoException;
import ru.railshop.onlineshop.service.UserService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try (var writer = resp.getWriter()) {
            writer.write("<h1>List of Users:</h1>");
            writer.write("<ul>");

            userService.findAllUser().stream().forEach(userDto ->
                    writer.write("""
                            <li>
                            <a href ='/user?userId=%d'>%s</a>
                            </li>
                            """.formatted(userDto.id(), userDto.description())));

            writer.write("</ul>");
        } catch (DaoException e) {
            throw new DaoException(e);
        }
    }
}

