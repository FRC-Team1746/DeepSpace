package frc.auton.trajectory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import frc.auton.team254.lib.trajectory.Path;
import frc.auton.team254.lib.trajectory.PathGenerator;
import frc.auton.team254.lib.trajectory.Trajectory;
import frc.auton.team254.lib.trajectory.WaypointSequence;


public class OttoPathGenerator extends PathGenerator {
    public static Path makeCheezyPath(OttoPath path) {
        Path p = new Path();

        p = PathGenerator.makePath(path.getWaypointSequence(), path.getConfig(), path.getConfig().wheelbaseWidthFeet, path.getConfig().name);

        if (!isDirectionValid(path)) {
            p.offsetHeading(-Math.PI);
        }

        if (path.getConfig().direction == -1) {
            p = reversePath(p);
        }

        return p;
    }

    private static boolean isDirectionValid(OttoPath path) {
        WaypointSequence wps = path.getWaypointSequence();
        if (wps.getWaypoint(wps.getNumWaypoints() - 1).x >= wps.getWaypoint(0).x) {
            return true;
        } else {
            return false;
        }
    }

    private static Path reversePath(Path p) {
        Trajectory oldCenter = p.getTrajectory();
        oldCenter.scale(-1);

        return new Path(p.getName(), oldCenter);
    }

    public static void exportArcToJavaFile(String relativeDirectoryName, OttoPath path) {
        SrxTrajectoryExporter exporter = new SrxTrajectoryExporter(relativeDirectoryName);
        Path chezyPath = makePath(path.getWaypointSequence(), path.getConfig(), path.getConfig().wheelbaseWidthFeet, path.getConfig().name);
        SrxTranslator srxt = new SrxTranslator();
        SrxTrajectory combined = srxt.getSrxMotionProfileFromChezyPath(chezyPath, path.getConfig());
        if(!exporter.exportSrxArcAsJavaFile(combined, path.getConfig(), path.getWaypointSequence())) {
            System.err.println("SOMETHING WENT WRONG!!!!, PATH COULD NOT BE WRITTEN");
        } else {
            System.out.println("Export succesful");
        }
    }

    public static void exportArcToJavaFile(OttoPath path) {
        exportArcToJavaFile("src\\main\\java\\frc\\auton\\arcs", path);
    }

    public static void exportRotationToJavaFile(String relativeDirectoryName, OttoRotation bobRotation) {
		SrxTrajectoryExporter exporter = new SrxTrajectoryExporter(relativeDirectoryName);

		SrxTrajectory rotation = new SrxTrajectory();
		bobRotation.getConfig().name = bobRotation.getName();
		int numPoints = (int) (bobRotation.getSeconds() / bobRotation.getConfig().dt);
		double degreesPerPoint = bobRotation.getDegrees() / (numPoints - 1);
		double[][] points = new double[numPoints][4];
		for (int i = 0; i < numPoints - 1; i++) {
			points[i][0] = 0;
			points[i][1] = 0;
			points[i][2] = bobRotation.getConfig().dt;
			points[i][3] = degreesPerPoint * i;
		}

		points[numPoints - 1][0] = 0;
		points[numPoints - 1][1] = 0;
		points[numPoints - 1][2] = bobRotation.getConfig().dt;
		points[numPoints - 1][3] = bobRotation.getDegrees();

		rotation.arcProfile = new SrxMotionProfile(points.length, points);

		if (!exporter.exportSrxArcAsJavaFile(rotation, bobRotation.getConfig(), new WaypointSequence(0))) {
			System.err.println("A path could not be written!!!!");
			System.exit(1);
		}
	}
	public static void copyFilesToRelativeDirectory(String fromDirectoryRelative, String toDirectoryRelative) {
		int fileCount = 0;
		File fromDirectory = new File(fromDirectoryRelative);
		File toDirectory = new File(toDirectoryRelative);

		for (File source : fromDirectory.listFiles()) {
			try {
				fileCount++;
				File dest = new File(toDirectory + "\\" + source.getName());
				copyFileUsingStream(source, dest);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Copied " + fileCount + " files to " + toDirectoryRelative);
	}

	private static void copyFileUsingStream(File source, File dest) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(source);
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} finally {
			is.close();
			os.close();
		}
	}

    
}