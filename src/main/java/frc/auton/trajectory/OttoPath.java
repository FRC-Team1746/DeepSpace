package frc.auton.trajectory;

import frc.auton.team254.lib.trajectory.WaypointSequence;
import frc.auton.team254.lib.trajectory.WaypointSequence.Waypoint;

public class OttoPath {
    private SrxTranslatorConfig config;
    private WaypointSequence waypointSequence;

    public OttoPath(SrxTranslatorConfig config) {
        this(config, config.name);
    }

    public OttoPath(SrxTranslatorConfig config, String name) {
        this(config, name, false);
    }

    public OttoPath(SrxTranslatorConfig config, String name, boolean driveBackwards) {
        this.config = new SrxTranslatorConfig(config);
        this.config.name = name;
        this.waypointSequence = new WaypointSequence(10);
        this.config.direction = driveBackwards ? -1 : 1;
    }

    public OttoPath(OttoPath path) {
        config = path.config;
        waypointSequence = path.waypointSequence;
    }

    public void setWaypointSequence(WaypointSequence wps) {
        waypointSequence = wps;
    }

    public WaypointSequence getWaypointSequence() {
        return waypointSequence;
    }

    public void addWaypoint(Waypoint wp) {
        this.waypointSequence.addWaypoint(wp);
    }

    public void addWaypointRadians(double x, double y, double theta_rad, double endVelocity, double maxVelocity) {
        this.waypointSequence.addWaypoint(new Waypoint(x, y, theta_rad, endVelocity, maxVelocity));
    }

    public void addWaypoint(double x, double y, double theta_deg, double endVelocity, double maxVelocity) {
        this.waypointSequence.addWaypoint(new Waypoint(x, y, Math.toRadians(theta_deg), endVelocity, maxVelocity));
    }

    public void addWaypoint(double x, double y, double theta_deg) {
        if (waypointSequence.getNumWaypoints() > 0) {
            getLastWaypoint().endVelocity = config.max_vel;
        }
        this.waypointSequence.addWaypoint(new Waypoint(x, y, Math.toRadians(theta_deg), 0, config.max_vel));
    }

    public void addWaypointRelative(double x, double y, double theta_deg) {
        if (waypointSequence.getNumWaypoints() > 1) {
            getLastWaypoint().endVelocity = config.max_vel;
        }
        this.waypointSequence.addWaypoint(new Waypoint(x, y, Math.toRadians(theta_deg), 0, config.max_vel));
    }

    public void addWaypointRelative(double x, double y, double theta_deg, double endVelocity, double maxVelocity) {
        Waypoint lastWaypoint = getLastWaypoint();
        Waypoint newWaypoint = new Waypoint(lastWaypoint.x + x, lastWaypoint.y + y, lastWaypoint.theta + Math.toRadians(theta_deg), endVelocity, maxVelocity);
        this.waypointSequence.addWaypoint(newWaypoint);
    }

    public Waypoint getLastWaypoint() {
        Waypoint lastWaypoint = this.waypointSequence.getWaypoint(this.waypointSequence.getNumWaypoints() - 1);
        return lastWaypoint;
    }

    public void setConfig(SrxTranslatorConfig c) {
        this.config = c;
    }

    public SrxTranslatorConfig getConfig() {
        return this.config;
    }
}
