package frc.robot.constants;

public class Constants {
	public static final double liftEncoderPerInch = 100; //Practice Bot: 120
	public static final double liftBottomFromFloor = 4.375; // Inches
	public static final double liftEncoderPosition0 = liftEncoderPerInch/2;
	public static final double hatchPosition2 = liftEncoderPerInch * ((33-liftBottomFromFloor)/3);//28 inches 788 Ticks (w/out dividing by 3 - 2835 Ticks)
	public static final double hatchPosition3 = liftEncoderPerInch * ((60-liftBottomFromFloor)/3);//56 inches 2065 Ticks (w/out dividing by 3 - 6195 Ticks)
	public static final double ballPosition1 = liftEncoderPerInch * ((24-liftBottomFromFloor)/3); // 22 inches 705 Ticks (w/out dividing by 3 - 2115 Ticks)
	public static final double ballPositionCargo = liftEncoderPerInch * ((36-liftBottomFromFloor)/3);//36 Inches 1265 Ticks (w/out dividing by 3 - 3795 Ticks)
	public static final double ballPosition2 = liftEncoderPerInch * ((53-liftBottomFromFloor)/3);//49 inches 1785 Ticks (w/out dividing by 3 - 5355 Ticks)
	public static final double ballPosition3 = liftEncoderPerInch * ((70-liftBottomFromFloor)/3);//77 inches 2905 Ticks (w/out dividing by 3 - 8715 Ticks)
	public static final double liftAutonStartPosition = 0;//Starting Position 
	public static final double liftEncoderTolerance = liftEncoderPerInch/3;
	
	/**
	 * Which PID slot to pull gains from. Starting 2018, you can choose from
	 * 0,1,2 or 3. Only the first two (0,1) are visible in web-based
	 * configuration.
	 */
	public static final int kSlotIdx = 0;

	/*
	 * Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops. For
	 * now we just want the primary one.
	 */
	public static final int kPIDLoopIdx = 0;

	/*
	 * set to zero to skip waiting for confirmation, set to nonzero to wait and
	 * report to DS if action fails.
	 */ 
	public static final int kTimeoutMs = 10;
}
