package com.afonina;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class Chat1 extends HttpServlet {
    private ArrayList<String> messlist = new ArrayList<String>();


    public void refreshHTML(HttpServletRequest request,
                            HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\" />");

        String title = "PrimServletChat";
        out.println("<title>" + title + "</title>");
        out.println("</head>");
        out.println("<body bgcolor=\"white\">");

        out.println("<h3>" + title + "</h3>");

        out.println("<P>");
        out.print("<form id = \"form\" action=\"/Chat/message\"");
        out.println("method=POST>");
        out.println("<input type=text size=20 name=testText>");
        out.println("<br>");
        out.println("<input type=submit>");
        out.println("</form>");

        out.println("</body>");
        out.println("</html>");
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException
    {
       /* messlist.add("ghsdgo");
        messlist.add("jhdsgfgh");
        messlist.add("rjhhtfgdghj");*/
        refreshHTML(request,response);
        PrintWriter out = response.getWriter();
        if(messlist!= null){
            for (String s : messlist ) {

                if(s!= "") {
                    out.println(s);
                    out.println("<br>");
                }
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
        String mewMess = request.getParameter("testText");
        if(mewMess!=null){
            messlist.add(mewMess);
        }
        doGet(request, response);
    }
}
