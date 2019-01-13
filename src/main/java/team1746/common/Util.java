package team1746.common;

public class Util {

	private static int ENCODER_TICKS_PER_REVOLUTION = 8192;
	private static int WHEEL_DIAMETER = 6;

	public static double getEncoderTicksFromInches(double inches) {         
		return getEncoderTicksFromInches(WHEEL_DIAMETER, inches);
	}

	private static double getEncoderTicksFromInches(int wheel_diameter, double inches)	{

		double inchesPerRevolution = Math.PI * wheel_diameter;

		double revolutions = inches / inchesPerRevolution;
		double ticks = revolutions * ENCODER_TICKS_PER_REVOLUTION;

		return ticks;
	}
	

	public static double getInchesFromEncoderTicks(double encoder_ticks) {
		return getInchesFromEncoderTicks(WHEEL_DIAMETER, encoder_ticks);
	}
	
	private static double getInchesFromEncoderTicks(int wheel_diameter, double encoder_ticks) {
		
		double inchesPerRevolution = Math.PI * wheel_diameter;
		double revolutions = encoder_ticks*ENCODER_TICKS_PER_REVOLUTION;
		
		double inches = (revolutions)/(inchesPerRevolution);

		return inches;
	}
}
