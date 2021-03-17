package myapp;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
//import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.gson.Gson;

//import com.google.appengine.api.datastore.Query.SortDirection;
import java.io.IOException;
//import java.io.PrintWriter;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
//import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//@WebServlet("/machines")
public class Machines extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String requestLocation = getParameter(req, "locationList", "MSV");
        FilterPredicate fp = new FilterPredicate("location", FilterOperator.EQUAL, requestLocation);

        Query query = new Query("machine").setFilter(fp);
   
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);

        List<Machine> machines = new ArrayList<>();
        for (Entity entity : results.asIterable()) {
            long id = entity.getKey().getId();
            String name = (String) entity.getProperty("name");
            long timestamp = (long) entity.getProperty("timestamp");
            String location = (String) entity.getProperty("location");
            String status = (String) entity.getProperty("status");
            String type = (String) entity.getProperty("type");
            
            String testing = type + " " + name + "'s status is currently " + status;
      
            Machine comment = new Machine(id, name, timestamp, location, status, type, testing);
            machines.add(comment);
        }

        String json = new Gson().toJson(machines);
   

        resp.setContentType("application/json;");
        resp.getWriter().println(json); 
        
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
