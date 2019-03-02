package frc.robot.auton.trajectory;

import java.io.IOException;
import java.util.List;
/**
 * Taken from Team319 Talon Trajectory Library
 * 
 * @author Ayush Panda
 */
public abstract class AbstractOttoPathCreator {
    public void generatePaths() throws IOException {
        generateArcFiles(getArcs());
    }

    protected abstract List<OttoPath> getArcs() throws IOException;

    private void generateArcFiles(List<OttoPath> paths) {
        for (OttoPath path : paths) {
            OttoPathGenerator.exportArcToJavaFile(path);
        }
    }
}