package com.lexsav.servlets.filter;

import com.lexsav.dao.UserDAO;
import com.lexsav.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.atomic.AtomicReference;

//Web filter controls every request
@WebFilter("/")
public class Filter implements javax.servlet.Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain filterChain)

            throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;

        //Getting multithread safe reference of DAO object from Servlet Context
        @SuppressWarnings("unchecked")
        final AtomicReference<UserDAO> dao = (AtomicReference<UserDAO>) req.getServletContext().getAttribute("dao");

        final String username = req.getParameter("username");
        final String password = req.getParameter("password");
        final String action = req.getParameter("action");
        final String remember = req.getParameter("remember");

        //To prevent web filter from catching request to resources folder
        final String path = req.getRequestURI().substring(req.getContextPath().length());

        if (path.startsWith("/resources/")) {
            filterChain.doFilter(request, response); // Goes to default servlet.
        }

        //Checking if customer already has the required cookie provided by the app
        //If so, customer may at once proceed to the home page
        final Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {

                //Checking in DAO if some user has the same ID as the name of the cookie
                //Every user has an unique ID and an unique cookie with the same name as user's ID
                //So they should match
                //Value should not be empty
                if (dao.get().checkUserById(cookie.getName()) &&
                    !cookie.getValue().isEmpty())
                {
                    req.setAttribute("username", dao.get().getUsernameById(cookie.getName()));
                    req.getRequestDispatcher("/WEB-INF/view/home.jsp").forward(req, resp);
                }
            }
        }

        if ((username != null) && (password != null) && (action != null)) {

            //Setting username attribute for the home page
            req.setAttribute("username", username);

            //Checking if customer is trying to register a new user
            if (action.equals("registration")) {

                //Create a new user only if the provided username does not exist in DAO
                if (!dao.get().checkUserByUsername(username)) {

                    final String id = Integer.toString(dao.get().getSize() + 1);
                    final User user = new User(id, username, password);

                    dao.get().add(user);

                    //Checking if customer selected "Remember me" option
                    //If so, create a cookie for the given user and add it to the HTTP Servlet response
                    if (remember != null && remember.equals("on")) {
                        Cookie cookie = getRememberMeCookie(id, username);
                        resp.addCookie(cookie);
                    }

                    req.getRequestDispatcher("/WEB-INF/view/home.jsp").forward(req, resp);

                } else {
                    //If username already exists, forward customer to the login page
                    //and notify the that username has already been taken
                    req.getRequestDispatcher("/WEB-INF/view/login-form.jsp?registration").forward(req, resp);
                }
            //Checking if customer is trying to login
            } else if (action.equals("login")) {

                //Proceed to the home page only if username and password are correct
                if (dao.get().checkUserByUsernameAndPassword(username, password)) {

                    String id = dao.get().getIdByUsername(username);

                    //Checking if customer selected "Remember me" option
                    //If so, create a cookie for the given user and add it to the HTTP Servlet response
                    if (remember != null && remember.equals("on")) {
                        Cookie cookie = getRememberMeCookie(id, username);
                        resp.addCookie(cookie);
                    }

                    req.getRequestDispatcher("/WEB-INF/view/home.jsp").forward(req, resp);

                } else {
                    //If username and/or password are not found in DAO, forward customer
                    //to the login page and notify that username and/or password are invalid
                    req.getRequestDispatcher("/WEB-INF/view/login-form.jsp?login").forward(req, resp);
                }
            }

        } else {
            //Forward to the login page, if customer doesn't have the required cookie
            //and none of the options (login, registration) are selected
            req.getRequestDispatcher("/WEB-INF/view/login-form.jsp").forward(req, resp);
        }
    }

    @Override
    public void destroy() {
    }

    //Turns username + some random string to md5 hash
    private String md5Hash(String username) throws NoSuchAlgorithmException {

        String entryData = username + "md5HashCookie";
        MessageDigest m = MessageDigest.getInstance("MD5");

        byte[] data = entryData.getBytes();
        m.update(data,0,data.length);
        BigInteger i = new BigInteger(1,m.digest());

        return String.format("%1$032X", i);
    }

    //Generates cookie for the "Remember me" function
    //Name is ID of the user, value is md5 hash from md5Hash() method
    private Cookie getRememberMeCookie(String id, String username) {

        String md5CookieValue = null;

        try {
            md5CookieValue = md5Hash(username);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        final Cookie cookie = new Cookie(id, md5CookieValue);
        cookie.setMaxAge(60 * 60 * 24 * 15);

        return cookie;
    }

}