package frc.auton.trajectory;

import java.util.List;
/**
 * Taken from Team319 Talon Trajectory Library
 * 
 * @author Ayush Panda
 */
public abstract class AbstractOttoPathCreator {
    public void generatePaths() {
        generateArcFiles(getArcs());
    }

    protected abstract List<OttoPath> getArcs();

    private void generateArcFiles(List<OttoPath> paths) {
        for (OttoPath path : paths) {
            OttoPathGenerator.exportArcToJavaFile(path);
        }
    }
}