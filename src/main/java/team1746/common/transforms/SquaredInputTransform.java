package team1746.common.transforms;

public class SquaredInputTransform implements ITransform {

	/**
	 * Returns square of input while maintaining sign
	 */
	@Override
	public double transform(double input) {
		return Math.copySign(input*input, input);
	}

}
