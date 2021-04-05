package myapp;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class Machine implements Comparable{

  private long id;
  private String name;
  private String type;
  private String location;
  private String status;

  private LocalDateTime end;
  private long timeRemaining;
  private String testing;

  public static long objectNum = 1;

  
  //creating new machine, called by Simulator
  public Machine( String type, String loc) {
    setID(objectNum);
    setName(String.valueOf(id));
    setType(type);
    setLocation(loc);
    setStatus("idle");
    setEnd(LocalDateTime.of(0,1,1,0,0,0));
    setTimeRemaining(0);
    setTesting("");
    objectNum++;
  }

  //copying a machine for records, called by Machines 
  public Machine(long i, String n, String typ, String loc, String stat,  LocalDateTime endTime,  String test ){
    setID(i);
    setName(n);
    setType(typ);
    setLocation(loc);
    setStatus(stat);
    setEnd(endTime);
    setTimeRemaining();
    setTesting(test);
  }

  public long  getID() { return id; }
  public String getName() { return name;}
  public String getType() { return type; }
  public String getLocation() { return location; }
  public String getStatus() { return status;}
  public LocalDateTime getEnd(){ return end; }
  public long getTimeRemaining() { return timeRemaining; }
  public String getTesting() { return testing;}

  public void setID(long i) { id  = i; }
  public void setName(String n) { name = n;}
  public void setType (String t) { type = t; }
  public void setLocation (String l) { location = l; }
  public void setStatus (String s) { status = s; }
  public void setTesting(String t) { testing = t;}
  public void setTimeRemaining(long t) { timeRemaining = t; }
  public void setEnd(LocalDateTime t) { end = t; } //called from Machines
  public void setEnd(int minute) { //called from Simulator
    LocalDateTime start = LocalDateTime.now();
    end = start.plusMinutes(minute);
  }
 
  public void setTimeRemaining(){
    LocalDateTime time = LocalDateTime.now();
    timeRemaining=  Duration.between(time,end).toMillis()/60000;
    if( timeRemaining < 1 && status.equals("busy")) {
      timeRemaining = 0;
      setStatus("idle");
      setEnd(LocalDateTime.of(0,1,1,0,0,0));
    }

  }

  public String toString() {
    return "ID: " + id + " Name: " + name + " Type: " + type + " Location: " + location
          + " Status: " + status + " End: " + end  + " Time Remaining: " + timeRemaining ;
  }

  @Override //for sorting the list
    public int compareTo(Object o) {
        long idCompare = ((Machine)o).getID();
        return this.id.intValue() - idCompare.intValue();
    }

}

