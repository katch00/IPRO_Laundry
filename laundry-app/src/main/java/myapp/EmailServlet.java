package myapp;

import java.io.IOException;

// import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;

import org.json.JSONArray;
import org.json.JSONObject;

// @WebServlet("/email")
public class EmailServlet extends HttpServlet {

    @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    String name = request.getParameter("contact_name");
    String email = request.getParameter("contact_email");
    String subject = request.getParameter("contact_subject");
    String message = request.getParameter("contact_message");
    String type = request.getParameter("report-typen");
    String location = request.getParameter("contact_location");
    String machine = request.getParameter("contact_machinee");
    
    
    sendEmail(name,email,subject,message,type,location,machine);
    response.sendRedirect("/confirm.html");

  }

  public void sendEmail(String name, String email, String subject, String message, String type, String location, String machine) {
    System.out.println(type);
    if(name == null || name.length() == 0) {name = "<em>(empty)</em>";}
    if(email == null || email.length() == 0) {email = "<em>(empty)</em>";}
    if(location == null || location.length() == 0) {location = "<em>(empty)</em>";}
    if(machine == null || machine.length() == 0) {machine = "<em>(empty)</em>";}

    if(type.equals("Developers")) {
        System.out.println("Sending an email to the developers!");
        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;
        client = new MailjetClient("6b2fbe12d13fc1c476c2862971b17e9d", "3fefdca13732a0b3e27816ee2c2fb4c6", new ClientOptions("v3.1"));
        request = new MailjetRequest(Emailv31.resource)
        .property(Emailv31.MESSAGES, new JSONArray()
            .put(new JSONObject()
                .put(Emailv31.Message.FROM, new JSONObject()
                .   put("Email", "jgonzalez13@hawk.iit.edu")
                    .put("Name", "Juan"))
                .put(Emailv31.Message.TO, new JSONArray()
                    .put(new JSONObject()
                        .put("Email", "jgonzalez13@hawk.iit.edu")
                        .put("Name", "Juan"))
                    .put(new JSONObject()
                        .put("Email", "aozturk2@hawk.iit.edu")
                        .put("Name", "Asude"))
                    .put(new JSONObject()
                        .put("Email", "jruzek@hawk.iit.edu")
                        .put("Name", "Julia"))
                    .put(new JSONObject()
                        .put("Email", "nstroupe@hawk.iit.edu")
                        .put("Name", "Natalia"))
                    )
                .put(Emailv31.Message.SUBJECT, "[User Report to Developers]: " + subject)
                .put(Emailv31.Message.TEXTPART, "User Report")
                .put(Emailv31.Message.HTMLPART, "<h3>This is an automatically generated email from the IIT Laundry App.</h3><br/><strong>Name:</strong>" + name + "<br/><br/><strong>Email:</strong>" + email + "<br/><br/><strong>Message:</strong>" + message + "<br/><br/>")
                .put(Emailv31.Message.CUSTOMID, "AppGettingStartedTest")
            )
        );
        try {
            response = client.post(request);
            System.out.println(response.getStatus());
            System.out.println(response.getData());
        } catch (MailjetException | MailjetSocketTimeoutException e) {e.printStackTrace();}
    } else {//Maitenance
        System.out.println("Sending an email to IIT Maitenance!");
        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;
        client = new MailjetClient("6b2fbe12d13fc1c476c2862971b17e9d", "3fefdca13732a0b3e27816ee2c2fb4c6", new ClientOptions("v3.1"));
        request = new MailjetRequest(Emailv31.resource)
        .property(Emailv31.MESSAGES, new JSONArray()
            .put(new JSONObject()
                .put(Emailv31.Message.FROM, new JSONObject()
                .   put("Email", "jgonzalez13@hawk.iit.edu")
                    .put("Name", "Juan"))
                .put(Emailv31.Message.TO, new JSONArray()
                    .put(new JSONObject()
                        .put("Email", "jgonzalez13@hawk.iit.edu")
                        .put("Name", "Juan")))
                .put(Emailv31.Message.SUBJECT, "[User Report to IIT Maitenance]: " + subject)
                .put(Emailv31.Message.TEXTPART, "User Report")
                .put(Emailv31.Message.HTMLPART, "<h3>This is an automatically generated email from the IIT Laundry App.</h3><br/><strong>Name:</strong>" + name + "<br/><br/><strong>Email:</strong>" + email + "<br/><br/><strong>Location:</strong>" + location + "<br/><br/><strong>Machine Number:</strong>" + machine + "<br/><br/><strong>Message:</strong>" + message + "<br/><br/>")
                .put(Emailv31.Message.CUSTOMID, "AppGettingStartedTest")
            )
        );
        try {
            response = client.post(request);
            System.out.println(response.getStatus());
            System.out.println(response.getData());
        } catch (MailjetException | MailjetSocketTimeoutException e) {e.printStackTrace();}
    }
    
  }

  
}