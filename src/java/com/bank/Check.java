/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author user
 */
@WebServlet(name = "Check", urlPatterns = {"/Check"})
public class Check extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String su = request.getParameter("txtUsr");
        String sp = request.getParameter("txtPwd");
        PrintWriter out=response.getWriter();
        response.setContentType("text/html");
        getServletContext().getRequestDispatcher("/Header.html").include(request, response);
       
        try {
            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            Class.forName("oracle.jdbc.driver.OracleDriver");//loading Driver
            Connection con = DriverManager.getConnection(url, "system", "sa123");//getting connection
            String query = "select * from BankUsers where username=? and password=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, su);
            st.setString(2, sp);
           ResultSet rs=st.executeQuery();
           if(rs.next())
           {
               HttpSession session=request.getSession();
               session.setAttribute("username", su);
               response.sendRedirect("/BankWeb/Menu.html");
           }
           else
           {
               out.println("U R NOT Authenticated ");
               out.println("<a href=/BankWeb/Login.html>PLe@se SiGn Up</a>");
           }

        } catch (Exception e) {
            e.printStackTrace();

        }
        getServletContext().getRequestDispatcher("/Footer.html").include(request, response);  

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
