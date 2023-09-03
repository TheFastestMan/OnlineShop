package ru.railshop.onlineshop.util;

import lombok.experimental.UtilityClass;

import javax.print.StreamPrintService;

@UtilityClass
public class JspHelper {
    private static final String JSP_FORMAT = "/WEB-INF/jsp/%s.jsp";

    public static String getJspFormat(String jsp) {
        return JSP_FORMAT.formatted(jsp);
    }
}
