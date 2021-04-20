package myapp;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;

public class EmailServlet extends HttpServlet {
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("help");
        try{
            sendEmail();
        }
        catch(Exception e)
        {

        }
        response.sendRedirect("/machines.html");
    }

    public static void sendEmail() throws MailjetException, MailjetSocketTimeoutException {
      MailjetClient client;
    MailjetRequest request;
    MailjetResponse response;
    client = new MailjetClient("6b2fbe12d13fc1c476c2862971b17e9d", "3fefdca13732a0b3e27816ee2c2fb4c6", new ClientOptions("v3.1"));
    request = new MailjetRequest(Emailv31.resource)
    .property(Emailv31.MESSAGES, new JSONArray()
    .put(new JSONObject()
    .put(Emailv31.Message.FROM, new JSONObject()
    .put("Email", "nstroupe@hawk.iit.edu")
    .put("Name", "Nat"))
    .put(Emailv31.Message.TO, new JSONArray()
    .put(new JSONObject()
    .put("Email", "nstroupe@hawk.iit.edu")
    .put("Name", "Nat")))
    .put(Emailv31.Message.SUBJECT, "Greetings from Mailjet.")
    .put(Emailv31.Message.TEXTPART, "My first Mailjet email")
    .put(Emailv31.Message.HTMLPART, "<h3>Dear passenger 1, welcome to <a href='https://www.mailjet.com/'>Mailjet</a>!</h3><br />May the delivery force be with you!")
    .put(Emailv31.Message.CUSTOMID, "AppGettingStartedTest")));
    response = client.post(request);
    System.out.println(response.getStatus());
    System.out.println(response.getData());
        
    }
}