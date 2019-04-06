import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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
    public void automationRumble() {
      lift.setDriver_Se_Button(true);
      lift.setRumbleTimer(0);
      double[] test = lift.update();
      assertTrue("Test if Rumble is On", lift.rumble==true);
      lift.setDriver_Se_Button(false);
      lift.driver_Se_Button();
      lift.setDriver_Se_Button(true);
      lift.setRumbleTimer(31);
      double[] test1 = lift.update();
      assertTrue("Test if Rumble is Off", lift.rumble==false);  
    }

    @Test
    public void automationLED() {
        lift.setHaveBall(false);
        lift.setAutoOn(true);
        lift.setTimer(0);
        double[] test = lift.update();
        assertTrue("Test if LED is On", lift.ledIndicator==true);
        lift.setTimer(51);
        double[] test1 = lift.update();
        assertTrue("Test if LED is Off", lift.ledIndicator==false);
    }

    @Test
    public void resetEncoders() {
        lift.setLiftDown(true);
        assertArrayEquals("Test NoButton resetEncoders", new double[]{-1, 0}, lift.update(), 1e-8);
    }
}