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
@WebServlet(name = "transfer", urlPatterns = {"/transfer"})
public class transfer extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
         String sa = request.getParameter("amt");
         int amount=Integer.parseInt(sa);
         String sr = request.getParameter("recever");
        PrintWriter out = response.getWriter();
		response.setContentType("text/html");
         getServletContext().getRequestDispatcher("/Header.html").include(request, response);
         //out.print("username");
        
        try {
            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            Class.forName("oracle.jdbc.driver.OracleDriver");//loading Driver
            Connection con = DriverManager.getConnection(url, "system", "sa123");//getting connection
            String query = "update BankUsers set amount=amount-? where username=?";
            System.out.println(query);
            PreparedStatement st = con.prepareStatement(query);
           
            st.setInt(1, amount);
            st.setString(2, username);
              int j = st.executeUpdate();
            
            
             String query1 = "update BankUsers set amount=amount+? where username=?";
            System.out.println(query);
            PreparedStatement st1 = con.prepareStatement(query1);
           
            st1.setInt(1, amount);
            st1.setString(2, sr);
            
           int i = st1.executeUpdate();
           if ((i>0)&&(j>0))
            {  
            out.println("U R transfer succesfully completed" );
			
         //   out.print("<br>");
                
            String query2 = "select * from BankUsers where username=?";
            PreparedStatement st2 = con.prepareStatement(query2);
            st2.setString(1, username);
            
            ResultSet rs = st2.executeQuery();
            if (rs.next()) {
                
                out.println("U R bALANCE  @fter TrAnSfEr IS " + rs.getString("amount"));
		out.println("<a href=/BankWeb/Menu.html>GO TO MENU</a>");
            } 
         //   out.print("<a href="/BankWeb/GetBalance">Balance</a><br>");
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
            
        }
		 getServletContext().getRequestDispatcher("/Footer.html").include(request, response);
        
        
    }
}
