package frc.auton;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.ArrayList;

import frc.auton.team254.lib.trajectory.WaypointSequence.Waypoint;
import frc.auton.trajectory.AbstractOttoPathCreator;
import frc.auton.trajectory.OttoPath;
import frc.auton.trajectory.SrxTranslatorConfig;

public class OttoPathCreator extends AbstractOttoPathCreator {
    private SrxTranslatorConfig config = new SrxTranslatorConfig();

    public OttoPathCreator() {
        config.max_acc = 1; // Acceleration In FPS
        config.max_vel = 6; // In FPS
        config.wheelDiaInch = 6;
        config.scaleFactor = 1.0;
        config.encoderTicksPerRev = 4096;
        config.robotLength = 34;
        config.robotWidth = 34;
        config.highGear = false;
    }

    public static void main(String[] args) {
        new OttoPathCreator().generatePaths();
    }
  
    @Override
    protected List<OttoPath> getArcs() {
        List<OttoPath> paths = new ArrayList<OttoPath>();
        paths.addAll(getConfigArcs());
        return paths;
    }   

    private List<OttoPath> getConfigArcs() {
        OttoPath distanceScaling = new OttoPath(config, "DistanceScaling");
        distanceScaling.addWaypoint(new Waypoint(2, 13.5, 0, 0, 0));
        distanceScaling.addWaypointRelative(3, 0, 0, 0, 3);

        OttoPath turnScaling = new OttoPath(config, "TurnScaling");
        turnScaling.addWaypoint(new Waypoint(2, 13.5, 0, 0, 0));
        turnScaling.addWaypointRelative(3, 3, 89.99, 0, 3);

        OttoPath speedScaling = new OttoPath(config, "SpeedScaling");
        speedScaling.addWaypoint(new Waypoint(2, 13.5, 0, 0, 0));
        speedScaling.addWaypointRelative(3, 3, 89.99, 1, 3);
        speedScaling.addWaypointRelative(-3, 3, 89.99, 0, 1);

        return asList(distanceScaling, turnScaling, speedScaling);
    }



    
}