package frc.robot;

// import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.lang.Math;
// import edu.wpi.first.wpilibj.VictorSP;
// import edu.wpi.first.wpilibj.Encoder;
// import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
// import edu.wpi.first.wpilibj.DigitalOutput;
import com.ctre.phoenix.motorcontrol.ControlMode;
// import java.util.concurrent.TimeUnit;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
// import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.*;

public class Lift {

	ElectricalConstants m_eConstants;
	Controls m_controls;
	Constants Constants;

	private VictorSPX liftLeft;
	private WPI_TalonSRX liftRight;
	private double liftPosition;
	private boolean moving;

	private DigitalInput liftBottom;
	private DigitalInput liftTop;
	private boolean m_UpDpadPrevious;
	private boolean m_DownDpadPrevious;

	StringBuilder _sb = new StringBuilder();

	public Lift(Controls controls) {
		m_controls = controls;
		m_eConstants = new ElectricalConstants();
		Constants = new Constants();
		liftLeft = new VictorSPX(m_eConstants.ELEVATOR_LEFT);
		liftRight = new WPI_TalonSRX(m_eConstants.ELEVATOR_RIGHT);
		liftBottom = new DigitalInput(m_eConstants.LIFT_BOTTOM);
		liftTop = new DigitalInput(m_eConstants.LIFT_TOP);
		liftLeft.follow(liftRight);

		liftPosition = 13;
		moving = false;

		/* first choose the sensor */
		liftRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx,
				Constants.kTimeoutMs);
		liftRight.setSensorPhase(false);
		liftRight.setInverted(false);
		/* Set relevant frame periods to be at least as fast as periodic rate */
		liftRight.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
		liftRight.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);
		/* set the peak and nominal outputs */
		liftRight.configNominalOutputForward(0, Constants.kTimeoutMs);
		liftRight.configNominalOutputReverse(0, Constants.kTimeoutMs);
		liftRight.configPeakOutputForward(1, Constants.kTimeoutMs);
		liftRight.configPeakOutputReverse(-1, Constants.kTimeoutMs);
		/* set closed loop gains in slot0 - see documentation */
		liftRight.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
		liftRight.config_kF(0, .146, Constants.kTimeoutMs);
		liftRight.config_kP(0, 2.5, Constants.kTimeoutMs);
		liftRight.config_kI(0, 0, Constants.kTimeoutMs);
		liftRight.config_kD(0, 25, Constants.kTimeoutMs);
		liftLeft.config_kF(0, .146, Constants.kTimeoutMs);
		liftLeft.config_kP(0, 2.5, Constants.kTimeoutMs);
		liftLeft.config_kI(0, 0, Constants.kTimeoutMs);
		liftLeft.config_kD(0, 25, Constants.kTimeoutMs);
		/* set acceleration and vcruise velocity - see documentation */
		liftRight.configMotionCruiseVelocity(500, Constants.kTimeoutMs);
		liftRight.configMotionAcceleration(5000, Constants.kTimeoutMs);
		/* zero the sensor */
		liftRight.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
		liftRight.configOpenloopRamp(0, 0);
		liftRight.configClosedloopRamp(0, 0);

		// liftBottom = new DigitalInput(m_eConstants.LIFT_BOTTOM);
		// m_liftTestLED = new DigitalOutput(m_eConstants.LIFT_LED);

		m_UpDpadPrevious = false;
		m_DownDpadPrevious = false;

		resetEncoder();
	}

	public void resetEncoder() {
		// m_liftEncoder.reset();
		liftRight.setSelectedSensorPosition(0, 0, 10);
	}

	public void setEncoderToStart() {
		liftRight.setSelectedSensorPosition((int) Constants.liftAutonStartPosition, 0, 10);
		liftPosition = Constants.liftAutonStartPosition;
	}

	public void setRampRate(double rate) {
		liftRight.configOpenloopRamp(rate, 5);
		liftLeft.configOpenloopRamp(rate, 5);

	}

	public void setBrakeMode(boolean brake) {
		if (brake) {
			liftRight.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
			liftLeft.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		}
	}

	public void setCoast(boolean coast) {
		if (coast) {
			liftRight.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
			liftLeft.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
		}
	}

	public void update() {

		if ((liftBottom.get()) && !m_controls.driver_A_Button() && !m_controls.driver_X_Button()
				&& !m_controls.driver_B_Button() && !m_controls.driver_Y_Button()
				&& !(m_controls.driver_YR_Axis() > .15 || m_controls.driver_YR_Axis() < -.15)) {
			liftRight.set(0);
		} else if(liftTop.get()){
			liftRight.set(0);
		} else{
			if (m_controls.driver_YR_Axis() > .15 || m_controls.driver_YR_Axis() < -.15) {
				liftRight.configMotionCruiseVelocity((int) (6500 + (Math.abs(m_controls.driver_YR_Axis() * 1500))),
						Constants.kTimeoutMs);
				liftPosition = getLiftPosition() - m_controls.driver_YR_Axis() * 2.5 * Constants.liftEncoderPerInch;
			} else {
				if (m_controls.driver_Y_Button()) {
					liftRight.configMotionCruiseVelocity(9000, Constants.kTimeoutMs);
					liftPosition = Constants.liftEncoderPosition4;
					System.out.println("Y Pressed");
				} else if (m_controls.driver_B_Button()) {
					liftRight.configMotionCruiseVelocity(9000, Constants.kTimeoutMs);
					liftPosition = Constants.liftEncoderPosition2;
					System.out.println("B Pressed");
				} else if (m_controls.driver_X_Button()) {
					liftRight.configMotionCruiseVelocity(9000, Constants.kTimeoutMs);
					liftPosition = Constants.liftEncoderPosition1;
					System.out.println("X Pressed");
				} else if (m_controls.driver_A_Button()) {
					if (liftBottom.get()) {
						liftRight.configMotionCruiseVelocity(0, Constants.kTimeoutMs);
					} else {
						liftRight.configMotionCruiseVelocity(7000, Constants.kTimeoutMs);
						liftPosition = Constants.liftEncoderPosition0;
					}
					System.out.println("A Pressed");
				}

			}

			liftRight.set(ControlMode.MotionMagic, liftPosition);
		}

		if ((liftBottom.get())) {
			resetEncoder();
			liftPosition = 0;
		}

		System.out.println(liftRight.getSelectedSensorVelocity(0));

	}

	public boolean updatePosition(int position) {
		if (!moving) {
			if (position == 3) {
				liftRight.configMotionCruiseVelocity(6000, Constants.kTimeoutMs);
				liftPosition = Constants.liftEncoderPosition3;
			} else if (position == 1) {
				liftRight.configMotionCruiseVelocity(6000, Constants.kTimeoutMs);
				liftPosition = Constants.liftEncoderPosition1;
			} else if (position == 4) {
				liftRight.configMotionCruiseVelocity(6000, Constants.kTimeoutMs);
				liftPosition = Constants.liftEncoderPosition4;
			} else if (position == 0) {
				if (liftBottom.get() || liftTop.get()) {
					liftRight.configMotionCruiseVelocity(0, Constants.kTimeoutMs);
				} else {
					liftRight.configMotionCruiseVelocity(4000, Constants.kTimeoutMs);
					liftPosition = Constants.liftEncoderPosition0;
				}
			}
			moving = true;
			return false;
		} else {
			liftRight.set(ControlMode.MotionMagic, liftPosition);
		}
		if (Math.abs(getLiftPosition() - liftPosition) <= Constants.liftEncoderTolerance) {
			moving = false;
			return true;
		} else {
			return false;
		}
	}

	public double getLiftPosition() {
		return liftRight.getSelectedSensorPosition(Constants.kPIDLoopIdx);
	}

	public void updateSmartDashboard() {
		SmartDashboard.putNumber("Lift position", getLiftPosition());
		SmartDashboard.putNumber("Intended", liftPosition);
		SmartDashboard.putBoolean("Bottom Value: ", liftBottom.get());
		SmartDashboard.putBoolean("Bottom Sensor: ", (liftBottom.get()));
	}

}