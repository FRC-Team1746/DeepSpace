import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

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
        lift.setHaveBall(true);
        assertArrayEquals("Test if Otto Off", new double[]{0, 0}, lift.update(), 1e-8);
        assertEquals("Testing indicator off", 0, lift.indicator, 1e-8);
        lift.setDriver_Se_Button(true);
        assertArrayEquals("Test if Otto On", new double[]{800, Constants.ballPosition1}, lift.update(), 1e-8);
        assertEquals("Testing indicator on", 1, lift.indicator, 1e-8);
        lift.setDriver_Se_Button(false);
        lift.driver_Se_Button();
        lift.setDriver_Se_Button(true);
        assertArrayEquals("Test if Otto Off", new double[]{0, 0}, lift.update(), 1e-8);
        assertEquals("Testing indicator off", 0, lift.indicator, 1e-8);
    }

    @Test
    public void resetEncoders() {
        lift.setLiftDown(true);
        assertArrayEquals("Test NoButton resetEncoders", new double[]{-1, 0}, lift.update(), 1e-8);
    }
}