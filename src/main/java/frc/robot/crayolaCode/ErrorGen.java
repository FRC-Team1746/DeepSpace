package frc.robot.crayolaCode;

import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Supplier;
import java.io.*;
import java.nio.file.Paths;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVFormat;
/**
 * Error Generation/Collector for PID Error Analysis 
 * @author Ayush Panda (TechNoLog1c)
 */

public class ErrorGen {
    private String path;
    private Supplier<Double> inputSource;
    private Supplier<Double> targetSource;
    private int N = 100;

    public ErrorGen(Supplier<Double> inputSource, Supplier<Double> targetSource, String path) {
        this.path = path;
        this.inputSource = inputSource;
        this.targetSource = targetSource;
    }

    public void run() throws IOException{
        try (
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(path));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("T", "Error"));
        ) {
            for(int i=0; i<=N; i++) {
                String error = String.valueOf(targetSource.get()-inputSource.get());
                csvPrinter.printRecord(String.valueOf(i), error);
            }
            csvPrinter.flush(); 
        } 
    }
}