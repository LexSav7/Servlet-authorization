package com.lexsav.servlets;

import com.lexsav.dao.UserDAO;
import com.lexsav.model.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.atomic.AtomicReference;

@WebListener
public class ContextListener implements ServletContextListener {

    //Using operation memory instead of JDBC
    private AtomicReference<UserDAO> dao;

    //Initializing atomic DAO reference and adding it to the servlet context
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        dao = new AtomicReference<>(new UserDAO());

        final ServletContext servletContext =
                servletContextEvent.getServletContext();

        servletContext.setAttribute("dao", dao);
    }

    //Deleting DAO reference when app is shut down
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        dao = null;
    }
}