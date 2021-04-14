package laundrySimulator;

import java.util.ArrayList;

public class Simulator{

  private ArrayList<Machine> washingMachines;
  private ArrayList<Machine> dryers;
  private ArrayList<String> locations;
  private final int machineNum = 5;

  public Simulator() {
    washingMachines = new ArrayList<>();
    dryers = new ArrayList<>();
    locations = new ArrayList<>();
    setLocations();
    setMachines();

  }

  public void start() {
    breakdownMachines();
    scheduleMachines();
  }

  public void setLocations() {
    locations.add("MSV Lewis");
    locations.add("MSV Grad");
    locations.add("MSV East");
    locations.add("MSV South");
    locations.add("MSV North");
    locations.add("MSV Fowler");
  }

  public void setMachines() { //adds <machineNum> number of washing machines&dryers for each location
    for (int i=0; i < locations.size(); i++) {
      for(int j=0; j< machineNum; j++){
        washingMachines.add(new Machine("washing", locations.get(i)));
        dryers.add(new Machine("dryer", locations.get(i)));
      }
    }
  }

  public void scheduleMachines() { //schedule the machines randomly
    String[] timePeriod = {"rush", "non-rush"};
    String randomTime = timePeriod[(int)(Math.random() * 2)];

    if(randomTime.equals("rush")) {
      int washingNum = (int)(washingMachines.size()*0.5);
      int dryerNum = (int)(dryers.size()*0.5);
      scheduleRandom(washingNum, dryerNum);
    }
    else { //non-rush
      int washingNum = (int)(washingMachines.size()*0.3);
      int dryerNum = (int)(dryers.size()*0.3);
      scheduleRandom(washingNum, dryerNum);
    }
  }

  public void scheduleRandom(int washingNum, int dryerNum) {
    int randomMachine;
    for (int i=0; i < washingNum; i++) {
      randomMachine = (int)(Math.random() * (washingMachines.size()-1))+1;
      Machine m = washingMachines.get(randomMachine);
      while(m.getInUse() || !m.getIsFunctioning()) {
        randomMachine = (int)(Math.random() * (washingMachines.size()-1))+1;
        m = washingMachines.get(randomMachine);
      }
      //start the washing machine
      int randomTime = (int)(Math.random() * 40);
      m.setInUse(true);
      m.setStart();
      m.setEnd(randomTime);
    }
    for (int i=0; i < dryerNum; i++) {
      randomMachine = (int)(Math.random() * (dryers.size()-1))+1;
      Machine m = dryers.get(randomMachine);
      while(m.getInUse() || !m.getIsFunctioning()) {
        randomMachine = (int)(Math.random() * (dryers.size()-1))+1;
        m = dryers.get(randomMachine);
      }
      //start the dryer
      int randomTime = (int)(Math.random() * 40) + 5;
      m.setStart();
      m.setEnd(randomTime);
    }
  }

  public void breakdownMachines(){
    int washingNum = (int)(washingMachines.size()*0.1);
    int dryerNum = (int)(dryers.size()*0.1);
    int randomMachine;

    for (int i=0; i < washingNum; i++) {
      randomMachine = (int)(Math.random() * (dryers.size()-1))+1;
      Machine m = washingMachines.get(randomMachine);
      while(!m.getInUse() && !m.getIsFunctioning()) {
        randomMachine = (int)(Math.random() * (dryers.size()-1))+1;
        m = washingMachines.get(randomMachine);
      }
      m.setIsFunctioning(false);
    }

    for (int i=0; i < dryerNum; i++) {
      randomMachine = (int)(Math.random() * (dryers.size()-1))+1;
      Machine m = dryers.get(randomMachine);
      while(!m.getInUse() && !m.getIsFunctioning()) {
        randomMachine = (int)(Math.random() * (dryers.size()-1))+1;
        m = dryers.get(randomMachine);
      }
      m.setIsFunctioning(false);
    }
}

  public String allMachines() {
    String report ="All washing machines and dryers on campus\n";
    calculateRemaining();
    for(int i=0; i < washingMachines.size(); i++){
      report += "\n" + washingMachines.get(i).getReportAll();
    }
    for(int i=0; i<dryers.size(); i++){
      report += "\n" + dryers.get(i).getReportAll();
    }
    return report;

  }

  public String allMachinesByLoc(String location) {
    String report ="All washing machines and dryers in: " + location + "\n";
    calculateRemaining();
    for(int i=0; i < washingMachines.size(); i++){
      if (washingMachines.get(i).getLocation().equals(location))
        report += "\n" + washingMachines.get(i).getReportLoc();
    }
    for(int i=0; i<dryers.size(); i++){
      if (dryers.get(i).getLocation().equals(location))
        report += "\n" + dryers.get(i).getReportLoc();
    }
    return report;
  }

  public String numAvailable() {
    String report = "Number of available machines in all locations: \n";
    Machine mc;
    calculateRemaining();
    for(int i=0; i < locations.size(); i++){
      String loc = locations.get(i);
      int dCount = 0, lCount = 0;

      for(int j=0; j < washingMachines.size(); j++){
        mc = washingMachines.get(j);
        if (mc.getLocation().equals(loc)) {
           if(!mc.getInUse() && mc.getIsFunctioning())
              lCount++;
        }
      }
      for(int j=0; j < dryers.size(); j++){
        mc = dryers.get(j);
        if (mc.getLocation().equals(loc)) {
           if(!mc.getInUse() && mc.getIsFunctioning())
              dCount++;
        }
      }
      report += loc + " Washing Machines: " + lCount + " Dryers: " + dCount + "\n";
    }
    return report;
  }

  public String numAvailableByLoc(String location){
    String report = "Number of available machines in: " + location +"\n";
    Machine mc;
    int dCount = 0, lCount = 0;
    calculateRemaining();

    for(int i=0; i < washingMachines.size(); i++){
      mc = washingMachines.get(i);
      if (mc.getLocation().equals(location)) {
        if(!mc.getInUse() && mc.getIsFunctioning())
          lCount++;
        }
    }

    for(int i=0; i < dryers.size(); i++){
      mc = dryers.get(i);
      if (mc.getLocation().equals(location)) {
          if(!mc.getInUse() && mc.getIsFunctioning())
            dCount++;
      }
    }
     return report += "Washing Machines: " + lCount + " Dryers: " + dCount + "\n";
  }

  public void calculateRemaining() {

    for(int i=0; i < washingMachines.size(); i++){
      if(washingMachines.get(i).getInUse()) {
        washingMachines.get(i).setElapsedTime();
      }
    }
    for(int i=0; i<dryers.size(); i++){
      if(dryers.get(i).getInUse()) {
        dryers.get(i).setElapsedTime();
      }
    }

  }
}
