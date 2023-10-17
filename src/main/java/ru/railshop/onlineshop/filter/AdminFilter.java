    package ru.railshop.onlineshop.filter;

    import jakarta.servlet.*;
    import jakarta.servlet.annotation.WebFilter;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import lombok.extern.slf4j.Slf4j;
    import ru.railshop.onlineshop.dto.UserDto;
    import ru.railshop.onlineshop.entity.Role;

    import java.io.IOException;

    @Slf4j
    @WebFilter("/admin/*")
    public class AdminFilter implements Filter {
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {
            HttpServletRequest req = (HttpServletRequest) request;
            UserDto user = (UserDto) req.getSession().getAttribute("user");

            if (user == null || !user.role().equals(Role.ADMIN)) {
                HttpServletResponse resp = (HttpServletResponse) response;
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                return;
            }

            log.info("User with role {} and name {} has access to the admin page", user.role(), user.username());

            chain.doFilter(request, response);
        }
    }
