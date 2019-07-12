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
 * @author shiva
 */
@WebServlet(name = "GetBalance", urlPatterns = {"/GetBalance"})
public class GetBalance extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String su = (String) session.getAttribute("username");
        PrintWriter out = response.getWriter();
			response.setContentType("text/html");
         getServletContext().getRequestDispatcher("/Header.html").include(request, response);
         //out.print("username");
        try {
            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            Class.forName("oracle.jdbc.driver.OracleDriver");//loading Driver
            Connection con = DriverManager.getConnection(url, "system", "sa123");//getting connection
            String query = "select * from BankUsers where username=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, su);
            
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                
                out.println("U R bALANCE IS " + rs.getString("amount"));
	        out.print("<a href=/BankWeb/Menu.html>MENU</a>");
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
            
        }
         getServletContext().getRequestDispatcher("/Footer.html").include(request, response);
        
    }
}
