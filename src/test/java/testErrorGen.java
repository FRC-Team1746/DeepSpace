import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.Supplier;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import frc.robot.tools.ErrorGen;

public class testErrorGen {
    private static ErrorGen testGenA;
    private static ErrorGen testGenB;
    private static ErrorGen testGenC;
    Supplier<Double> inputSource = () -> 1.0;
    Supplier<Double> targetSource = () -> 0.5;
    Supplier<Double> randomSource = () -> Math.random();

    
    @Before
    public void setup() {
        testGenA = new ErrorGen(inputSource, targetSource, "src\\test\\resources\\testGenA.csv");
        testGenB = new ErrorGen(inputSource, targetSource, "src\\test\\resources\\testGenB.csv");
        testGenC = new ErrorGen(inputSource, randomSource, "src\\test\\resources\\testGenC.csv");
    }

    public static byte[] getFileChecksum(MessageDigest digest, File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);

        byte[] byteArray = new byte[1024];
        int bytesCount = 0;

        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        };

        fis.close();

        byte[] bytes = digest.digest();

       return bytes;
    }

    @Test
    public void testCreation() {
        try {
            assertNotNull(testGenA);
            testGenA.run();
        } catch (IOException e) {
            fail("IOException Occured");
            e.printStackTrace();
        }
    }

    @Test
    public void csvAccuracy() {
        try {
            testGenA.run();
            testGenB.run();
            File csvA = new File("src\\test\\resources\\testGenA.csv");
            File csvB = new File("src\\test\\resources\\testGenB.csv");
            MessageDigest md5Digest = MessageDigest.getInstance("MD5");
            assertArrayEquals(getFileChecksum(md5Digest, csvA), getFileChecksum(md5Digest, csvB));
        } catch (IOException e) {
            fail("IOException Occured");
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            fail("No Algorithim Exception Occured");
            e.printStackTrace();
        }
    }

    @Test
    public void randomCsvData() {
        try {
            assertNotNull(testGenC);
            testGenC.run();
        } catch (IOException e) {
            fail("IOException Occured");
            e.printStackTrace();
        }
    }

    @After
    public void confirmation() {

    }

}