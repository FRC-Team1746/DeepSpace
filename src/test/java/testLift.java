import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Before;
import org.junit.Test;

import frc.robot.constants.Constants;

public class testLift {
    private liftModule lift;

    @Before
    public void setup() {
        lift = new liftModule();
    }

    @Test
    public void createdLift() {
        assertNotNull(lift);
    }

    @Test
    public void automationToggle() {
        lift.setDriver_Se_Button(true);
        lift.setHaveBall(true);
        assertArrayEquals("Test if Otto On", new double[]{800, Constants.ballPosition1}, lift.update(), 1e-8);
        lift.setDriver_Se_Button(false);
        double[] test = lift.update();
        lift.setDriver_Se_Button(true);
        assertArrayEquals("Test if Otto Off", new double[]{0, 0}, lift.update(), 1e-8);
    }

    @Test
    public void resetEncoders() {
        lift.setLiftDown(true);
        assertArrayEquals("Test NoButton resetEncoders", new double[]{-1, 0}, lift.update(), 1e-8);
    }
}