package frc.auton.follower;

import frc.auton.follower.SrxMotionProfile;

public class SrxTrajectory {
    public boolean flipped;
    public boolean highGear;
    public SrxMotionProfile rightProfile;
    public SrxMotionProfile centerProfile;
    public SrxMotionProfile leftProfile;

    public SrxTrajectory() {

    }

    public SrxTrajectory(SrxMotionProfile left, SrxMotionProfile center, SrxMotionProfile right) {
        this.leftProfile = left;
        this.centerProfile = center;
        this.rightProfile = right;
    }
}