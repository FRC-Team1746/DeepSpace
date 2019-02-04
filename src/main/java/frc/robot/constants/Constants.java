package frc.robot.constants;

public class Constants {
	public static final double liftEncoderPerInch = 914;
	public static final double liftBottomFromFloor = 6;
	public static final double liftEncoderPosition0 = -6666;
	public static final double hatchPosition1 = liftEncoderPerInch * (31-liftBottomFromFloor);//29 Inches 21022 Ticks
	public static final double hatchPosition2 = liftEncoderPerInch * (76-liftBottomFromFloor);//5 feet 49356 Ticks
	public static final double hatchPosition3 = liftEncoderPerInch * (73-liftBottomFromFloor);//6 feet 60324 Ticks
	public static final double ballPosition1 = liftEncoderPerInch * (31-liftBottomFromFloor);//29 Inches 21022 Ticks
	public static final double ballPosition2 = liftEncoderPerInch * (76-liftBottomFromFloor);//5 feet 49356 Ticks
	public static final double ballPosition3 = liftEncoderPerInch * (73-liftBottomFromFloor);//6 feet 60324 Ticks
	public static final double liftAutonStartPosition = 25246;//Starting Position 
	public static final double liftEncoderTolerance = liftEncoderPerInch/2;
	public static final double climbingBarHeight = 81;           //In Inches ;

	//Retractor
	public static final double retZeroDeg = 858; // In 5 Volts Out Of 1023  Units 
	public static final double retFourtyFiveDeg = retZeroDeg - 115;
	public static final double retNinetyDeg = retZeroDeg - 245; 
	public static final double retCrashDeg = retZeroDeg - 445; //Starting Position
	public static final int retSpeed = 50;
	
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
