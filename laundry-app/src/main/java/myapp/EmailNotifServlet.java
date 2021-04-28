package myapp;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;

public class EmailNotifServlet extends HttpServlet {


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userEmail = getParameter(request, "email", "");
        String message = "Your machine is now available! Thank you for using IIT Laundry.";
        try
        {
            sendEmail(message, userEmail);
        }
        catch(Exception e)
        {
            //reponse for error
        }
        response.sendRedirect("/confirmNotif.html");
    }

    public static void sendEmail(String message, String userEmail) throws MailjetException, MailjetSocketTimeoutException {
      MailjetClient client;
    MailjetRequest request;
    MailjetResponse response;
    client = new MailjetClient("6b2fbe12d13fc1c476c2862971b17e9d", "3fefdca13732a0b3e27816ee2c2fb4c6", new ClientOptions("v3.1"));
    request = new MailjetRequest(Emailv31.resource)
    .property(Emailv31.MESSAGES, new JSONArray()
    .put(new JSONObject()
    .put(Emailv31.Message.FROM, new JSONObject()
    .put("Email", "nstroupe@hawk.iit.edu")
    .put("Name", "IIT Laundry"))
    .put(Emailv31.Message.TO, new JSONArray()
    .put(new JSONObject()
    .put("Email", userEmail)
    .put("Name", "Student"))) //get name?
    .put(Emailv31.Message.SUBJECT, "Machine now Available")
    .put(Emailv31.Message.TEXTPART, "")
    .put(Emailv31.Message.HTMLPART, message)
    .put(Emailv31.Message.CUSTOMID, "AppGettingStartedTest")));
    response = client.post(request);
    System.out.println(response.getStatus());
    System.out.println(response.getData());
        
    }

    private String getParameter(HttpServletRequest request, String name, String defaultValue) {
        String value = request.getParameter(name);
        if (value == null) 
        {
            return defaultValue;
        }
        return value;
    }
}