package com.bank;

/**
 *
 * @author Sivaramayya
 */
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
create table BankUsers(First_Name varchar2(30),Middle_Name varchar2(30),Last_Name varchar2(30),,username varchar2(30),password varchar2(30),
* amount Number(9),Email_Id varchar2(30),Mobile_No Number(10));
finally     type     commit        in      cmd
 
 *
 * @author shiva
 */
@WebServlet(name = "Register", urlPatterns = {"/Register"})
public class Register extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
			response.setContentType("text/html");
         getServletContext().getRequestDispatcher("/Header.html").include(request, response);
        String fn = request.getParameter("FN");
        String mn = request.getParameter("MN");
        String ln = request.getParameter("LN");
        String sna = request.getParameter("txtUsr");
        String sp = request.getParameter("txtPwd");
        String sa = request.getParameter("amt");
        int amount = Integer.parseInt(sa);
        String EM = request.getParameter("email");
        String mob = request.getParameter("mobile");
        long CELL = Long.parseLong(mob);
        //out.print(username);

       out.println(CELL);

        try {
            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            Class.forName("oracle.jdbc.driver.OracleDriver");//loading Driver


            Connection con = DriverManager.getConnection(url, "system", "sa123");//getting connection
            String query = "insert into BankUsers values(?,?,?,?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, fn);
            st.setString(2, mn);
            st.setString(3, ln);
            st.setString(4, sna);
            st.setString(5, sp);
            st.setInt(6, amount);
            st.setString(7, EM);
            st.setLong(8, CELL);
            int c = st.executeUpdate();
            if (c>0){
                out.print("Successfully cr@eted Us@er @ccoUNT");
	        out.print("<a href=/BankWeb/Login.html>LOGIN</a>");
                
                
                
               
            }
            else
            {
                out.println("UNABLE TO PROCESS");
             out.println("<a href=/BankWeb/Login.html.html>RegIsTEr nOW....!!! iTS@elf</a>");
            
            }
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }getServletContext().getRequestDispatcher("/Footer.html").include(request, response);
    }
}
