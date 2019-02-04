package frc.robot.auton.trajectory;

import frc.robot.auton.team254.lib.trajectory.WaypointSequence;
import frc.robot.auton.team254.lib.util.ChezyMath;

public class OttoWaypointSequence extends WaypointSequence {

    public static class OttoWaypoint extends Waypoint {

        public OttoWaypoint(double x, double y, double theta, double endVelocity, double maxVelocity) {
            super(x, y, theta, endVelocity, maxVelocity);
        }

        public OttoWaypoint(OttoWaypoint point) {
            super(point);
        }

        public double x;
        public double y;
        public double theta;
    }
    OttoWaypoint[] _waypoints;
    int num_waypoints;

    public OttoWaypointSequence(int max_size) {
        super(max_size);
    }

    public void addWaypoint(OttoWaypoint w) {
        if (num_waypoints < _waypoints.length) {
            _waypoints[num_waypoints] = w;
            num_waypoints++;
        }
    }

    public int getNumWaypoints() {
        return num_waypoints;
    }

    public OttoWaypoint getWaypoint(int index) {
        if (index >= 0 || index < getNumWaypoints()) {
            return _waypoints[index];
        } else {
            return null;
        }
    }

    public OttoWaypointSequence invertY() {
        OttoWaypointSequence inverted = new OttoWaypointSequence(10);
        inverted.num_waypoints = num_waypoints;
        for (int i=0; i < num_waypoints; i++) {
            inverted._waypoints[i] = _waypoints[i];
            inverted._waypoints[i].y *= -1;
            inverted._waypoints[i].theta = ChezyMath.boundAngle0to2PiRadians(2 * Math.PI - inverted._waypoints[i].theta);
        }
        return inverted;
    }
}