package com.afonina;

import org.w3c.dom.Document;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;


public class Chat extends HttpServlet {
    private DatabaseForXMLChat myDB = new DatabaseForXMLChat();
    private ArrayList<Document> messList = new ArrayList<Document>();
    private Document mainDoc = myDB.getMainDoc();

   /* public void refreshHTML(HttpServletRequest request,
                            HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\" />");

        String title = "ServletChatWithHistory";
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
    }*/

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException
    {
        response.setContentType("text/xml; charset=utf-8");
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            mainDoc = myDB.getMainDoc();
            DOMSource source = new DOMSource(mainDoc);
            StreamResult result = new StreamResult(response.getWriter());
            Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*refreshHTML(request,response);
        PrintWriter out = response.getWriter();
        String filePath = "C:\\Users\\st032334\\IdeaProjects\\Chat\\src\\main\\java\\com\\afonina\\database.txt";
        InputStream is = new FileInputStream(new File(filePath));
        Reader r = new InputStreamReader(is, "utf-8");
        BufferedReader br = new BufferedReader(r);

            while (true) {
                String s = br.readLine();
                if(s!= null) {
                    out.println(s);
                    out.println("<br>");
                }
                else{
                    break;
                }
            }
        is.close();*/

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
        /*String mewMess = request.getParameter("testText");
        String filePath = "C:\\Users\\st032334\\IdeaProjects\\Chat\\src\\main\\java\\com\\afonina\\database.txt";
        if(mewMess!=null){
            FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.newLine();
            bw.write(mewMess);
            bw.close();
            fw.close();
        }
        doGet(request, response);*/
        if (request != null){
            String mewMess = request.getReader().readLine();
            myDB.addMessageToDB(mewMess);
            mainDoc = myDB.getMainDoc();
            doGet(request, response);
        }
    }
}
