package frc.auton.trajectory;

import frc.auton.team254.lib.trajectory.TrajectoryGenerator;

public class SrxTranslatorConfig extends TrajectoryGenerator.Config {
    public String name = "config";
    public double wheelbaseWidthFeet;
    public double wheelDiaInch;
    public int encoderTicksPerRev;
    public double scaleFactor;
    public int direction = 1;
    public double robotWidth;
    public double robotLength;
    public boolean highGear;

    public SrxTranslatorConfig() {
        this.dt = 0.01;
    }

    public SrxTranslatorConfig(SrxTranslatorConfig config) {
        this.name = config.name;
        this.wheelbaseWidthFeet = config.wheelbaseWidthFeet;
        this.wheelDiaInch = config.wheelDiaInch;
        this.max_acc = config.max_acc;
        this.max_jerk = config.max_jerk;
        this.max_vel = config.max_vel;
        this.highGear = config.highGear;
        this.encoderTicksPerRev = config.encoderTicksPerRev;
        this.scaleFactor = config.scaleFactor;
        this.direction = config.direction;
        this.robotLength = config.robotLength;
        this.robotWidth = config.robotWidth;
        this.dt = config.dt;
        
    }
}