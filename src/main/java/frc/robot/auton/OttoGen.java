package frc.robot.auton;

import java.util.function.Function;

/**
 * Generating/Executing PID with Neural Networks
 * @author TechNoLog1c (Ayush Panda)
 * 
 * @see MAKE SPECIFY INPUT AND TARGET SOURCE IN CODE Nvm, Function objects exist LMAO
 */
public class OttoGen {
    private Function inputSource;
    private Function targetSource;
    private double targetValue;
    private boolean returnPID;

    public OttoGen(Function inputSource, Function targetSource, double targetValue, boolean returnPID) {
        this.inputSource = inputSource;
        this.targetSource = targetSource;
        this.targetValue = targetValue;
        this.returnPID = returnPID;

        double[][] W1 = new double[2][3]; // [Input Neurons], [PID Values] might wanna jack np for this
        double[][] B1 = new double[1][3]; // [# of Bias values], [PID Values]

        double[][] W2 = new double[3][1]; // [PID Values], [System Input]
    }

    public void train() {
        for(int i=0; i < 4000; i++) {

            if(i % 100 == 0) {

            }
        }

    }

    public void run() {

    }

    private double activate(String type) {
        switch(type) {
            case "P":
            break;

            case "I":
            break;

            case "D":
            break;

            default:
                System.out.println("ACTIVATION NOT SPECIFIED");
            break;
        }
        return 0.1;
    } 
}