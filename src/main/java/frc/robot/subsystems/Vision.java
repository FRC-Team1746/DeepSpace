package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.networktables.*;

public class Vision {

  double validTarget;  // Whether the limelight has any valid targets (0 or 1)
  double xOffset;      // Horiszontal Offset from crosshair to target (-27 degrees to 27 degrees)
  double yOffset;      // Vertical Offset from crosshair to target (-20.5 degrees to 20.5 degrees)
  double targetArea;   // target area (0% of image to 100%)
  double skew;         // skew or rotation (-90 degrees to 0 degrees)
  double pipeLatency;  // the pipeline's latency contribution (ms) add at least 11 ms for image capture latency
  double tshort;       // Sidelength of shortest side of the fitted bounding box (pixels)
  double tlong;        // Sidelength of longest side of the fitted bounding box (pixels)
  double thor;         // Horizontal sidelength of the rough bounding box (0 - 320 pixels)
  double tvert;        // Vertical sidelength of the rough bounding box (0 - 320 pixels)
  double getpipe;      // True active pipeline index of the camera (0 .. 9)
  double camtran;      // Results of a 3D position solution, 6 numbers: Translation (x,y,y) Rotation(pitch,yaw,roll)
      //these are used for generating drive and steer from vision
  double LimelightDriveCommand;
  double LimelightDriveMax = 0.3;
  double LimelightSteerCommand;
  double Drive_K = 0.1;   // this needs to be tuned to robot
  double Steer_K = 0.005;   // this needs to be tuned to robot
  double DesiredTargetArea = 23;  // this needs to be tuned to robot

  SerialPort jevois;
  
  // int baudRate = 9600;
  int baudRate = 115200;
  int dataBits = 8;

  // public Vision(){
  //   jevois = new SerialPort(baudRate, SerialPort.Port.kUSB1);

  // }

  // public void track(){
  //   jevois.enableTermination();
  //   System.out.println(jevois.readString());
  //   // System.out.println("Tracking...");
  //   if (jevois.getBytesReceived() > 0) {
  //     System.out.println("There are: " + jevois.getBytesReceived() + " availible to read");   
  //   } else {
  //     System.out.println("Number of bytes: " + jevois.getBytesReceived());
  //   }
  // }
  
  public double GenerateDrive() {
    if (!isTargetValid()){
      LimelightDriveCommand = 0.0;
    } else if ((Math.sqrt(DesiredTargetArea) - Math.sqrt(getTargetArea())) * Drive_K > LimelightDriveMax){
      LimelightDriveCommand = LimelightDriveMax;
    } else {
      LimelightDriveCommand = (Math.sqrt(DesiredTargetArea) - Math.sqrt(getTargetArea())) * Drive_K;
    }
    return LimelightDriveCommand;
  }

  public double GenerateSteer() {
    if (!isTargetValid()) {
      LimelightSteerCommand = 0.0;
    } else {
      LimelightSteerCommand = getXOffset() * Steer_K;
    }
    return LimelightSteerCommand;
  }

  public boolean fetchUpdate() {
    try {
      validTarget = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
      xOffset = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
      yOffset = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
      targetArea = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
      skew = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ts").getDouble(0);
      pipeLatency = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tl").getDouble(0);
      tshort = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tshort").getDouble(0);
      tlong = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tlong").getDouble(0);
      thor = NetworkTableInstance.getDefault().getTable("limelight").getEntry("thor").getDouble(0);
      tvert = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tvert").getDouble(0);
      getpipe = NetworkTableInstance.getDefault().getTable("limelight").getEntry("getpipe").getDouble(0);
      camtran = NetworkTableInstance.getDefault().getTable("limelight").getEntry("camtran").getDouble(0);
    } catch(Exception ex) {
      return false;
    }
    return true;
  }


  public boolean isTargetValid() { 
    if (validTarget > 0.5) {
      return true;
    } else {
      return false;
    }
  }
  
  public double getXOffset() {
    return xOffset;
  }

  public double getYOffset() {
    return yOffset;
  }

  public double getTargetArea() {
    return targetArea;
  }

  public double getSkew() {
    return skew;
  }

  public double getPipeLatency() {
    return pipeLatency;
  }

  public double getTShort() {
    return tshort;
  }

  public double getTLong() {
    return tlong;
  }

  public double getThor() {
    return thor;
  }

  public double getTVert() {
    return tvert;
  }

  public double getPipe() {
    return getpipe;
  }

  public double getCamTran() {
    return camtran;
  }

  public void setLedMode(int ledMode) { // 0-use the LED mode set in the current pipeline   1-force off   2-force blink  3-force on
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(ledMode);
  }

  public void setCamMode(int camMode) { // 0-Vision processor   1-DriverCamera
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(camMode);
  }

  public void setPipeLine(int pipeLine) { // 0 through 9 as pipeline
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(pipeLine);
  }

    // (stream modes) 0 - Standard: Side-by-side streams if a webcam is attached to Limelight
    // 1 - Pip Main: the secondary camara stream is placed in the lower-right corner of the primary camera stream
    // 2 - PiP Secondary: The primary camera stream is placed in the lower-right corner of the secondary camera stream
  public void setStream(int streamMode) { 
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("stream").setNumber(streamMode);
  }

  public void takeSnapshot(int snapshootingOnOrOf) { // 0-off    1-two snapshots per second
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("snapshot").setNumber(snapshootingOnOrOf);
  }



  public String toString() {
    String str = "Vision [validTarget= " + validTarget + "(" + isTargetValid() + "), xOffset= " + xOffset + ", yOffset= " + yOffset + 
      ", targetArea= " + targetArea + ", skew= " + skew + ", pipeLatency= " + pipeLatency + ", tshort= " + tshort + ", tlong= " + tlong + 
      ", thor= " + thor + ", tvert= " + tvert + ", getpipe= " + getpipe + ", camtran= " + camtran + " ]";
    return str;
  }

}
