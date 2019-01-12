package frc.auton.follower;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public interface FollowsArc {
    public TalonSRX getLeft();
    public TalonSRX getRight();
    public double getDistance();
}