package myapp;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignIn extends HttpServlet {
  
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Checks if user is logged in. If not, redirects user to log in. If yes, checks
     * if user has a nickname If user has no nickname, redirects uder to set
     * nickname Also allows user to loggout if they are logged in and have a
     * nickname set
     */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html");

    UserService userService = UserServiceFactory.getUserService();
    if (userService.isUserLoggedIn()){
      String userEmail = userService.getCurrentUser().getEmail();
      String urlToRedirectToAfterUserLogsOut = "/index.html";
      String logoutUrl = userService.createLogoutURL(urlToRedirectToAfterUserLogsOut);
      response.getWriter().println(
          "<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Open+Sans:300,400\"><link rel=\"stylesheet\" href=\"font-awesome-4.5.0/css/font-awesome.min.css\"><link rel=\"stylesheet\" href=\"css/bootstrap.min.css\"><link rel=\"stylesheet\" href=\"css/templatemo-style.css\">");
      response.getWriter().println(
          "<div class=\"navbar\"><div class=\"logo\"><a href=\"uploadMachine.html\"><img src=\"https://static.thenounproject.com/png/1754024-200.png\" width=\"130\"/></a></div><nav><ul><li><a href=\"index.html\">Home</a></li><li><a href=\"resources.html\">Resources</a></li><li><a href=\"/signin\">Sign In</a></li></ul></nav></div>");
      response.getWriter().println("<h1>Hello " + userEmail + "!</h1>");
      response.getWriter().println("<h2>Logout <a href=\"" + logoutUrl + "\">here</a>.</h2>");
      response.getWriter().println("</div></body>");
      response.getWriter().println("</body>");
    } else {
      String urlToRedirectToAfterUserLogsIn = "/index.html";
      String loginUrl = userService.createLoginURL(urlToRedirectToAfterUserLogsIn);
      response.getWriter().println(
          "<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Open+Sans:300,400\"><link rel=\"stylesheet\" href=\"font-awesome-4.5.0/css/font-awesome.min.css\"><link rel=\"stylesheet\" href=\"css/bootstrap.min.css\"><link rel=\"stylesheet\" href=\"css/templatemo-style.css\">");
      response.getWriter().println(
          "<div class=\"navbar\"><div class=\"logo\"><a href=\"uploadMachine.html\"><img src=\"https://static.thenounproject.com/png/1754024-200.png\" width=\"130\"/></a></div><nav><ul><li><a href=\"index.html\">Home</a></li><li><a href=\"resources.html\">Resources</a></li><li><a href=\"/signin\">Sign In</a></li></ul></nav></div>");
      response.getWriter().println(
          "<br><h1 class=\"tm-wrapper-center\">Hello! Click below to sign in!</h1><br>");
      response.getWriter().println("<h1 class=\"tm-wrapper-center\"><a href=\"" + loginUrl + "\">Login</a><h1>");
    }

  }
}