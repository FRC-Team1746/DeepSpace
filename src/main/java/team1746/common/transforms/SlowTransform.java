package team1746.common.transforms;

public class SlowTransform implements ITransform{
	
	private double DAMPEN_VALUE = 0.65;

	@Override
	public double transform(double input) {
		
		return input * DAMPEN_VALUE;
	}

}
