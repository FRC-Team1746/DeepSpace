package frc.robot.auton.follower;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

public interface FollowsArc {
    public TalonSRX getLeft();
    public TalonSRX getRight();
    public double getDistance();
    public PigeonIMU getGyro();
    public double getAngle();
}