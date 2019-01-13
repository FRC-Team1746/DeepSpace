package team1746.common.transforms;

public class PreDefinedPowerTransform implements ITransform {
	
	private double PREDEFINED_POWER =  0.1;

	@Override
	public double transform(double input) {
		return PREDEFINED_POWER;
	}

}
