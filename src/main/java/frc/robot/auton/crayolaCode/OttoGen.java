package frc.robot.auton.crayolaCode;

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
    private int N = 4000;
    private boolean returnPID;
    private double[][] W1;
    private double[][] W2;

    public OttoGen(Function inputSource, Function targetSource, double targetValue, boolean returnPID) {
        this.inputSource = inputSource;
        this.targetSource = targetSource;
        this.targetValue = targetValue;
        this.returnPID = returnPID;

        W1 = np.random(2, 3); // [Input Neurons], [PID Values] might wanna jack np for this
        W2 = np.random(3, 1); // [PID Values], [System Input]
    }

    public void train() {
        for(int i=0; i < N; i++) {
            double[][] input = new double[1][2];
            double[][] u1 = np.dot(input, W1);
            double[][] output = np.dot(u1, W2);

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

    public static void main(String[] args) {

    }
}