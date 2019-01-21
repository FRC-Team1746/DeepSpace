package frc.auton.follower;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.ElectricalConstants;;

public class AutonDriveTrain {
    private ElectricalConstants eConstants;
    public TalonSRX rightTalon;
    private VictorSPX rightFollowerA;
	private VictorSPX rightFollowerB;
	public TalonSRX leftTalon;
	private VictorSPX leftFollowerA;
    private VictorSPX leftFollowerB;
    
    public AutonDriveTrain() {
        eConstants = new ElectricalConstants();

        rightTalon = new TalonSRX(eConstants.MOTOR_DRIVE_RIGHT_MASTER);
        rightFollowerA = new VictorSPX(eConstants.MOTOR_DRIVE_RIGHT_FOLLOWER_A);
        rightFollowerB = new VictorSPX(eConstants.MOTOR_DRIVE_RIGHT_FOLLOWER_B);
        leftTalon = new TalonSRX(eConstants.MOTOR_DRIVE_LEFT_MASTER);
        leftFollowerA = new VictorSPX(eConstants.MOTOR_DRIVE_LEFT_FOLLOWER_A);
        leftFollowerB = new VictorSPX(eConstants.MOTOR_DRIVE_LEFT_FOLLOWER_B);

        rightFollowerA.follow(rightTalon);
        rightFollowerB.follow(rightTalon);
        leftFollowerA.follow(leftTalon);
        leftFollowerB.follow(leftTalon);

    }

    public double getDistance() {
        return 0;
    }

    public TalonSRX getRightTalon() {
        return rightTalon;
    }

    public TalonSRX getLeftTalon() {
        return leftTalon;
    }
}