package myapp;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
public class EmailServlet {
  /*public static void main(String[] args) throws MailjetException, MailjetSocketTimeoutException {
    MailjetClient client;
    MailjetRequest request;
    MailjetResponse response;
    client = new MailjetClient(System.getenv("6b2fbe12d13fc1c476c2862971b17e9d"), System.getenv("449c161d24dd70e1a730682fb86593e2"), new ClientOptions("v3.1"));
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
  }*/
}