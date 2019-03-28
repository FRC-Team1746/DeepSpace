package frc.robot.auton.tests;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.function.Supplier;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import frc.robot.auton.crayolaCode.ErrorGen;

public class testErrorGen {
    private ErrorGen testGenA;
    private ErrorGen testGenB;
    Supplier<Double> inputSource = () -> Math.random();
    Supplier<Double> targetSource = () -> 0.5;

    
    @Before
    public void setup() {
        testGenA = new ErrorGen(inputSource, targetSource, "src\\main\\deploy\\testGenA.csv");
        testGenB = new ErrorGen(inputSource, targetSource, "src\\main\\deploy\\testGenB.csv");
    }

    @Test
    public void testCreation() {
        try {
            assertNotNull(testGenA);
            testGenA.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void assertTargetSource() {

    }

    @After
    public void confirmation() {

    }

}