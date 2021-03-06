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

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

// @WebServlet("/machines")
public class Machines extends HttpServlet {
   
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    private Simulator simulator;
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(req.getParameter("quick") != null) {
            
            ArrayList<String> locationList = new ArrayList<>();
            locationList.addAll(simulator.getLocationList());
            ArrayList<Location> locationObj = new ArrayList<>();
        
            for(int i =0; i <locationList.size();i++) {
                String loc = locationList.get(i);
                int[] count = simulator.numAvailable(loc);
                locationObj.add(new Location(loc, count[0], count[1]));
            }
            
            String json = new Gson().toJson(locationObj);
            resp.setContentType("application/json;");
            resp.getWriter().println(json);

        }
        else { 


        String requestLocation = getParameter(req, "locationList", "MSV");
        FilterPredicate fp = new FilterPredicate("location", FilterOperator.EQUAL, requestLocation);

        Query query = new Query("machine").setFilter(fp);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	      
        PreparedQuery results = datastore.prepare(query);
	
	    ArrayList<Machine> machines = new ArrayList<>();
        for (Entity entity : results.asIterable()) {
            long id = (long) entity.getProperty("id");
            String name = (String) entity.getProperty("name");
	        String type = (String) entity.getProperty("type");
            String location = (String) entity.getProperty("location");
	        String status = (String) entity.getProperty("status");
	        LocalDateTime end =  LocalDateTime.parse((String)entity.getProperty("end"));
            String testing = type + " " + id + "'s status is currently " + status;
	        Machine comment = new Machine(id, name, type, location, status, end, testing);
	        machines.add(comment);
        }

        Collections.sort(machines);
       	String json = new Gson().toJson(machines);

        resp.setContentType("application/json;");
        resp.getWriter().println(json);
    }

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
       	
	    //clear the datastore, delete all machines
        Query query = new Query("machine");
        PreparedQuery toDelete = datastore.prepare(query);
        for (Entity entity : toDelete.asIterable()) {
            datastore.delete(entity.getKey());
        }
	
        //start the simulator
	    simulator = new Simulator();
	    simulator.start(); 
	    ArrayList<Machine> allMachines = new ArrayList<>();
	    allMachines.addAll(simulator.getMachines());

        //up1oad all simulator machines to datastore
        for(int i=0; i < allMachines.size(); i++) {
            Machine m = allMachines.get(i);
            Entity mchnEntity = new Entity("machine");
            mchnEntity.setProperty("id", m.getID());
            mchnEntity.setProperty("name", m.getName());
            mchnEntity.setProperty("type", m.getType());
            mchnEntity.setProperty("location", m.getLocation());
	        mchnEntity.setProperty("status", m.getStatus());
            mchnEntity.setProperty("end", m.getEnd().format(formatter));
            datastore.put(mchnEntity);
        }

	    response.sendRedirect("/machines.html");
    }

    private String getParameter(HttpServletRequest request, String name, String defaultValue) {
        String value = request.getParameter(name);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

   public class Location {
        private String location;
        private int washerAvail, dryerAvail;
        public Location(String loc, int washer, int dryer) {
            location = loc;
            washerAvail = washer;
            dryerAvail = dryer;
        }
    }
}

