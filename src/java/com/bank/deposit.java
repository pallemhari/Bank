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
@WebServlet(name = "deposit", urlPatterns = {"/deposit"})
public class deposit extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
      
        String sa = request.getParameter("amt");
         int amount=Integer.parseInt(sa);
        PrintWriter out = response.getWriter();
			response.setContentType("text/html");
         getServletContext().getRequestDispatcher("/Header.html").include(request, response);
         //out.print("username");
        
        try {
            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            Class.forName("oracle.jdbc.driver.OracleDriver");//loading Driver
            Connection con = DriverManager.getConnection(url, "system", "sa123");//getting connection
            String query = "update BankUsers set amount=amount+? where username=?";
            System.out.println(query);
            PreparedStatement st = con.prepareStatement(query);
           
            st.setInt(1, amount);
            st.setString(2, username);
            
           int i = st.executeUpdate();
           if (i>0)
            {  
            //out.print("U R bALANCE after deposit " );
              
           
            
            
            String query1 = "select * from BankUsers where username=?";
            PreparedStatement st1 = con.prepareStatement(query1);
            st1.setString(1, username);
            
            ResultSet rs = st1.executeQuery();
            if (rs.next()) {
                
                out.println("U R bALANCE  @fter dep0sit IS " + rs.getString("amount"));
	        out.print("<a href=/BankWeb/Menu.html>...RETURN TO MENU...</a>");
            } 
        }
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        getServletContext().getRequestDispatcher("/Footer.html").include(request, response);
        
    }
}
//update BankUsers set amount=amount+? where username=?