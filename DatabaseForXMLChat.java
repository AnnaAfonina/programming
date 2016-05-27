package com.afonina;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Text;
import java.beans.Statement;
import java.util.ArrayList;

public class DatabaseForXMLChat {

    private Statement statement;
    private Document mainDoc;
    private ArrayList<String[]> messList = new ArrayList<String[]>();

    public DatabaseForXMLChat ()  {
        mainDoc = buildDoc(messList);
    }

    private Document buildDoc(ArrayList<String[]> messageList) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try{
        builder = dbf.newDocumentBuilder();}
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = builder.newDocument();
        Element messages = document.createElement("messages");
        document.appendChild(messages);

        for (int i = 0; i < messageList.size(); i++) {
            Element chatMessage = document.createElement("chat-message");
            messages.appendChild(chatMessage);
            chatMessage.setAttribute("id",Integer.toString(i));

            Element sender = document.createElement("sender");
            chatMessage.appendChild(sender);
            Text senderText = document.createTextNode(messageList.get(i)[0]);
            sender.appendChild(senderText);

            Element message = document.createElement("message");
            chatMessage.appendChild(message);
            Text messageText = document.createTextNode(messageList.get(i)[1]);
            message.appendChild(messageText);
        }
        return document;
    }

    public void addMessageToDB(String message) {
        messList.add(new String[]{"User", message});
        mainDoc = buildDoc(messList);
    }

    public Document getMainDoc() {
        return mainDoc;
    }
}
