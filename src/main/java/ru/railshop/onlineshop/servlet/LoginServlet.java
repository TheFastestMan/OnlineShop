package ru.railshop.onlineshop.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import ru.railshop.onlineshop.dto.UserDto;
import ru.railshop.onlineshop.service.UserService;
import ru.railshop.onlineshop.util.JspHelper;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getJspFormat("login")).forward(req, resp);
    }

    @Override
    @SneakyThrows
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
//        userService.login(req.getParameter("email"), req.getParameter("password"))
//                .ifPresentOrElse(userDto -> onLoginSuccess(userDto, req, resp),
//                        () -> onLoginFail(req, resp));
//    }
//
//    @SneakyThrows
//    private void onLoginFail(HttpServletRequest req, HttpServletResponse resp) {
//        resp.sendRedirect("/login?error&email=" + req.getParameter("email"));
//    }
//
//    @SneakyThrows
//    private void onLoginSuccess(CreateUserDto userDto, HttpServletRequest req, HttpServletResponse resp) {
//        req.getSession().setAttribute("user", userDto);
//        resp.sendRedirect("/user");
//    }
//}

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Optional<UserDto> userOptional = userService.login(login, password);

        if (login != null && password != null && userOptional.isPresent()) {
            UserDto user = userOptional.get();
            req.getSession().setAttribute("user", user);
            resp.sendRedirect("/user");
        } else {
            resp.sendRedirect("/login?error&email=" + req.getParameter("email"));
        }
    }
}
