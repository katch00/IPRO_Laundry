package myapp;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//@WebServlet("/machines")
public class DemoServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("{ \"name\": \"World\" }");
    }

    @Override
        public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String machineName = getParameter(request, "machine-name-input", "");
        String location = getParameter(request, "machine-location-input", "MSV");
        String machineType = getParameter(request, "machine-type-input", "washer");
        String status = getParameter(request, "machine-status-input", "online");
        long timestamp = System.currentTimeMillis();
    

        Entity mchnEntity = new Entity("machine");
        mchnEntity.setProperty("name", machineName);
        mchnEntity.setProperty("location", location);
        mchnEntity.setProperty("type", machineType);
        mchnEntity.setProperty("status", status);
        mchnEntity.setProperty("timestamp", timestamp);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(mchnEntity);

        response.sendRedirect("/index.html");
    }
  
    private String getParameter(HttpServletRequest request, String name, String defaultValue) {
        String value = request.getParameter(name);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }
}
