package com.fci.itdl.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.fci.itdl.model.Store;


@SuppressWarnings("serial")

@WebServlet(name = "ITDLServletSignup", urlPatterns = {"/ITDLServletSignup"})
public class ITDLServletSignup extends HttpServlet {
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		resp.setContentType("text/html;charset=UTF-8");
		String responseOut = "";
        PrintWriter out = resp.getWriter();
        try{

            String storeEmail = req.getParameter("email");
            Store store = Store.getStore(storeEmail);
    		if (store == null) 
    		{
    			responseOut = "<font size=\"4\" color=\"#000000\" face=\"Lucida Calligraphy\"><b>Click and drag the marker.</b></font><br>";
                out.println(responseOut);
    		}
        }
        catch(Exception e){
        	
        }
	}
}
