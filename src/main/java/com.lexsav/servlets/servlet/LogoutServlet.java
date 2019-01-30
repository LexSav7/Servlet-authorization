package com.lexsav.servlets.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

//Invoked when user wants to logout from the app
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        final HttpSession session = req.getSession(false);
        final Cookie[] cookies = req.getCookies();

        //Deleting all cookies the current user has
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
            }
        }

        //Invalidating the session
        if (session != null) {
            session.invalidate();
        }

        //Redirecting to the login page
        resp.sendRedirect("/?logout");
    }

}