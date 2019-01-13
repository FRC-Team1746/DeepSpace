package frc.robot.commands.autons;

import java.util.HashMap;


import edu.wpi.first.wpilibj.command.CommandGroup;
import team1746.motion_profiling.PathFollower;

/**
 *
 */
public class MiddleStartRightSwitchMP extends CommandGroup {

    public MiddleStartRightSwitchMP(HashMap<String, PathFollower> forwardsPathFollowers) {
    			
    	addSequential(forwardsPathFollowers.get("centerRight"));
    	

    	
    }
}
