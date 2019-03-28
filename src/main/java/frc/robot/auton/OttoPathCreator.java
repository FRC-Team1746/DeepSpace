package frc.robot.auton;

import static java.util.Arrays.asList;

import org.apache.commons.csv.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

import frc.robot.auton.team254.lib.trajectory.WaypointSequence.Waypoint;
import frc.robot.auton.trajectory.AbstractOttoPathCreator;
import frc.robot.auton.trajectory.OttoPath;
import frc.robot.auton.trajectory.SrxTranslatorConfig;


public class OttoPathCreator extends AbstractOttoPathCreator {
    private SrxTranslatorConfig config = new SrxTranslatorConfig();
    private String HOME_FOLDER = "C:\\Users\\ayush\\Desktop\\FRC\\Paths2019\\output\\output";

    public OttoPathCreator() {
        config.max_acc = 10; // Acceleration In FPS
        config.max_vel = 20; // In FPS
        config.wheelDiaInch = 4;
        config.scaleFactor = 0.1;
        config.encoderTicksPerRev = 1143;
        config.robotLength = 34;
        config.robotWidth = 34;
        config.highGear = false;
    }

    public static void main(String[] args) throws IOException{
        new OttoPathCreator().generatePaths();
    }
  
    @Override
    protected List<OttoPath> getArcs() throws IOException {
        List<OttoPath> paths = new ArrayList<OttoPath>();
        paths.addAll(getConfigArcs());
        paths.addAll(generateCsvArcs());
        return paths;
    }   

   
    private List<OttoPath> generateCsvArcs() throws IOException {
        List<OttoPath> paths = new ArrayList<OttoPath>();

        List<File> filesInFolder = Files.walk(Paths.get(HOME_FOLDER))
        .filter(Files::isRegularFile)
        .map(Path::toFile)
        .collect(Collectors.toList());

        for(File file : filesInFolder) {
            OttoPath CsvPath = new OttoPath(config, file.getName());
            FileReader in = new FileReader(file);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            for(CSVRecord record : records) {
                String x = record.get(1);
                String y = record.get(2);
                String velocity = record.get(4);
                String heading = record.get(7);
                CsvPath.addWaypoint(Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(heading),
                Double.parseDouble(velocity), Double.parseDouble(velocity));
            }
            paths.add(CsvPath);
        }
        return paths;
    }

     /**
     * Configuation Arcs relating to Distance, Turning, and Speed
     * DistanceScaling- Robot will run 3 feet, adjust scaling to get exact distance
     * TurnScaling- Robot will run 3 feet, then turn and go 3 feet to the left, adjusting heading loop to get exact angle
     * SpeedScaling- Robot will run 3 feet and 3 feet to the left at 3 FPS. Run another 3 feet forward and 3 feet to the left.
     * End 6 feet away from starting position facing opposite direction  
     * @return List of Configuration Arcs
     */
        
    private List<OttoPath> getConfigArcs() {
        OttoPath distanceScaling = new OttoPath(config, "DistanceScaling");
        distanceScaling.addWaypoint(new Waypoint(2, 13.5, 0, 0, 0));
        distanceScaling.addWaypointRelative(5, 0, 0, 0, 3);
        

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