import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Machine {

  private int machineID;
  private String machineType;
  private String location;

  private boolean isFunctioning;
  private boolean inUse;
  public static int objectNum = 1;

  private LocalDateTime start;
  private LocalDateTime end;
  private long elapsedTime;

  public Machine() {
    setMachineID(objectNum);
    setMachineType("unknown");
    setLocation("unknown");
    setIsFunctioning(false);
    setInUse(false);
    objectNum++;
  }
  public Machine( String type, String loc) {
    setMachineID(objectNum);
    setMachineType(type);
    setLocation(loc);
    setIsFunctioning(true);
    setInUse(false);
    objectNum++;
  }

  public int getMachineID() { return machineID; }
  public String getMachineType() { return machineType; }
  public String getLocation() { return location; }
  public boolean getIsFunctioning() { return isFunctioning; }
  public boolean getInUse() { return inUse; }
  public LocalDateTime getStart(){ return start; }
  public LocalDateTime getEnd(){ return end; }
  public long getElapsedTime() { return elapsedTime; }

  public void setMachineID(int id) { machineID = id; }
  public void setMachineType (String type) { machineType = type; }
  public void setLocation (String loc) { location = loc; }
  public void setIsFunctioning(boolean b) { isFunctioning = b; }
  public void setInUse(boolean b) { inUse = b;}

  public void setStart() {
    start = LocalDateTime.now();
  }
  public void setEnd(int minute) {
    end = start.plusMinutes(minute);
  }
  public void setElapsedTime(){
    LocalDateTime time = LocalDateTime.now();
    elapsedTime=  Duration.between(time,end).toMillis();
    if( elapsedTime < 0) {
      elapsedTime = 0;
      inUse = false;
    }

  }

  public String getReportAll() {
    return  "ID: " + machineID + " Type: " + machineType + " Location: " + location
            + " is Functioning: " + isFunctioning + " in Use: " + inUse
            + " Time Remaining: " + (elapsedTime/60000) + " min";
  }

  public String getReportLoc() {
    return  "ID: " + machineID + " Type: " + machineType
            + " is Functioning: " + isFunctioning + " in Use: " + inUse
            + " Time Remaining: " + (elapsedTime/60000) + " min";
  }

  public String toString() {
    return "ID: " + machineID + " Type: " + machineType + " Location: " + location
          + " is Functioning: " + isFunctioning + " in Use: "
          + inUse + " Start: " + start + " End: " + end;
  }


}
