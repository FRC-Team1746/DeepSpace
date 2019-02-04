package frc.robot.auton.follower;

public class Constants {
    /**
    * How many sensor units per rotation.
    * Using CTRE Magnetic Encoder.
    * @link https://github.com/CrossTheRoadElec/Phoenix-Documentation#what-are-the-units-of-my-sensor
    */
    public static class Gains {
        public final double kP;
        public final double kI;
        public final double kD;
        public final double kF;
        public final double kIzone;
        public final double kPeakOutput;
        public Gains(double _kP, double _kI, double _kD, double _kF, double _kIzone, double _kPeakOutput){
            kP = _kP;
            kI = _kI;
            kD = _kD;
            kF = _kF;
            kIzone = _kIzone;
            kPeakOutput = _kPeakOutput;
        }
    }
    public final static int kSensorUnitsPerRot = 4096;
    /**
    * Motor neutral dead-band, set to the minimum 0.1%.
    */
    public final static double kNeutralDeadband = 0.001;
    /**
    * Pigeon will reports 8192 units per 360 deg (1 rotation)
    * If using encoder-derived (left plus/minus right) heading, find this emperically.
    */
    public final static double kTurnUnitsPerDeg = 8192.0 / 360.0;
    /**
    * PID Gains may have to be adjusted based on the responsiveness of control loop
    * kP kI kD kF Iz PeakOut */
    public final static Gains kGains_MotProf = new Gains(0.4, 0.0, 0.0, 1023.0/6800.0, 400, 0.5); /* measured 6800 velocity units at full motor output */
    public final static int kPrimaryPIDSlot = 0; // any slot [0,3]
    public final static int kAuxPIDSlot = 1; // any slot [0,3]
}
    