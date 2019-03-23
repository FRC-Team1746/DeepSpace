package frc.robot.auton;

import com.ctre.phoenix.sensors.PigeonIMU;

import frc.robot.subsystems.TeleopDrive;

public class driveStraight {
    private States currentState;
    private double m_speed;
    private int m_delayCounter;
    private TeleopDrive m_driveTrain;
    private double m_initialHeading;

    public enum States {
        INIT,
        DRIVESTRAIGHTINIT,
        DRIVESTRAIGHT,
        STOP,
        IDLE
    }

    public driveStraight(TeleopDrive driveTrain) {
        this.m_driveTrain = driveTrain;

        currentState = States.IDLE;
    }

    public void init() {
        currentState = States.INIT;
    }

    public void auton() {
        switch(currentState) {
            case INIT:
                m_driveTrain.resetEncoders();
                m_initialHeading = m_driveTrain.getAngle();
                m_speed = .25;
                currentState = States.DRIVESTRAIGHTINIT;
                break;
            case DRIVESTRAIGHTINIT:
                break;
            
        }
    }
}