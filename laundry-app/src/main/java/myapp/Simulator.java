package myapp;
import java.util.ArrayList;


public class Simulator{

  private ArrayList<Machine> washers;
  private ArrayList<Machine> dryers;
  private ArrayList<String> locations;
  private final int machineNum = 5;

 public Simulator() {
    washers = new ArrayList<>();
    dryers = new ArrayList<>();
    locations = new ArrayList<>();
    setLocations();
    setMachines();
    calculateRemaining();

  }

  public void start() {
    breakdownMachines();
    scheduleMachines();
  }

  public void setLocations() {
    locations.add("Guns");
    locations.add("MSV");
    locations.add("SSV");
    locations.add("Carmen");

  }

  //adds <machineNum> amount of washers and <machineNum> amount if dryer to each location
  public void setMachines() {
    for (int i=0; i < locations.size(); i++) {
      for(int j=0; j< machineNum; j++){
        washers.add(new Machine("washer", locations.get(i)));
        dryers.add(new Machine("dryer", locations.get(i)));
      }
    }
  }

  //determine random nubmer num of machines to start
  public void scheduleMachines() {
    String[] timePeriod = {"rush", "non-rush"};
    String randomTime = timePeriod[(int)(Math.random() * 2)];
    int washerNum, dryerNum;

    if(randomTime.equals("rush")) {
      washerNum = (int)(washers.size()*0.5);
      dryerNum = (int)(dryers.size()*0.5);
    }
    else { //non-rush
      washerNum = (int)(washers.size()*0.3);
      dryerNum = (int)(dryers.size()*0.3);
    }
      
    scheduleRandom(washerNum, dryerNum);
 
  }

  //choose machines randomly by their id 
  public void scheduleRandom(int washingNum, int dryerNum) {
    int randomMachine;
    for (int i=0; i < washingNum; i++) {
      randomMachine = (int)(Math.random() * (washers.size()-1))+1;
      Machine m = washers.get(randomMachine);
      while(m.getStatus().equals("busy")) {
        randomMachine = (int)(Math.random() * (washers.size()-1))+1;
        m = washers.get(randomMachine);
      }
      //start the washing machine
      int randomTime = (int)(Math.random() * 40);
      m.setStatus("busy");
      m.setEnd(randomTime);
    }

    for (int i=0; i < dryerNum; i++) {
      randomMachine = (int)(Math.random() * (dryers.size()-1))+1;
      Machine m = dryers.get(randomMachine);
      while(m.getStatus().equals("busy")) {
        randomMachine = (int)(Math.random() * (dryers.size()-1))+1;
        m = dryers.get(randomMachine);
      }
      //start the dryer
      int randomTime = (int)(Math.random() * 40) + 5;
      m.setStatus("busy");
      m.setEnd(randomTime);
    }
  }

  //choose random machines by id and set status=offline
  public void breakdownMachines(){
    int washerNum = (int)(washers.size()*0.1);
    int dryerNum = (int)(dryers.size()*0.1);
    int randomMachine;

    for (int i=0; i < washerNum; i++) {
      randomMachine = (int)(Math.random() * (washers.size()-1))+1;
      Machine m = washers.get(randomMachine);
      while(m.getStatus().equals("offline")) {
        randomMachine = (int)(Math.random() * (washers.size()-1))+1;
        m = washers.get(randomMachine);
      }
      m.setStatus("offline");
    }

    for (int i=0; i < dryerNum; i++) {
      randomMachine = (int)(Math.random() * (dryers.size()-1))+1;
      Machine m = dryers.get(randomMachine);
      while(m.getStatus().equals("offline")) {
        randomMachine = (int)(Math.random() * (dryers.size()-1))+1;
        m = dryers.get(randomMachine);
      }
      m.setStatus("offline");
    }
  }

  //calculates time remaining for each machine
  public void calculateRemaining() {
    for(int i=0; i < washers.size(); i++){
      if(washers.get(i).getStatus().equals("busy")) {
        washers.get(i).setTimeRemaining();
      }
    }
    for(int i=0; i<dryers.size(); i++){
      if(dryers.get(i).getStatus().equals("busy")) {
        dryers.get(i).setTimeRemaining();
      }
    }

  }

  //returns all machines 
  public ArrayList<Machine> getMachines() {
    calculateRemaining();
    ArrayList<Machine> allMachine = new ArrayList<>();
    allMachine.addAll(washers);
    allMachine.addAll(dryers);
    return allMachine;

  }

public ArrayList<String> getLocationList() {
    return locations;
}

//returns number of available machines in all locations
public int[] numAvailable(String location) {
    Machine mc;
    int dCount = 0, wCount = 0;
    int[] count = {0,0};
    calculateRemaining();

    for(int i=0; i < washers.size(); i++){
      mc = washers.get(i);
      if (mc.getLocation().equals(location)) {
        if(mc.getStatus().equals("idle")){
          wCount++;
        }
     }
    }

    for(int i = 0; i < dryers.size(); i++){
      mc = dryers.get(i);
      if (mc.getLocation().equals(location)) {
          if(mc.getStatus().equals("idle")){
            dCount++;
          }
      }
    }
    count[0] = wCount;
    count[1] = dCount;
    return count.clone();
  }


}
