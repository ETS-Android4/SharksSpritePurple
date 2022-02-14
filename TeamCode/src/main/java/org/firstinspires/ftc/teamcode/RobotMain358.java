package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.lang.*;

public abstract class RobotMain358 extends LinearOpMode {
    protected DcMotor lf;
    protected DcMotor lb;
    protected DcMotor rf;
    protected DcMotor rb;
    protected DcMotor slideMotor;
    protected DcMotor crMotor;
    protected DcMotor intakeMotor;
    protected Servo blackBox;
    protected CRServo hello;

    protected DistanceSensor dsFront;
    protected DistanceSensor dsFreight;

    public double driveFactor = 0.7; //for TeleOp
    public final double slidePower = 0.1;
    public long lastTime = System.currentTimeMillis();
    public int timeElapsed = 1000; // this is in milliseconds

    final double DRIVE_FACTOR = 30 * (10/9.2) * (10/10.2);
    final double TURN_FACTOR = 5 * (90.0/95);

    ElapsedTime runtime = new ElapsedTime();    // Use to determine when end game is starting.
    final double HALF_TIME = 60.0;              // Wait this many seconds before rumble-alert for half-time.
    final double END_GAME = 90.0;               // Wait this many seconds before rumble-alert for end-time.

    public static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";
    public static final String[] LABELS = {"Ball", "Cube"};
    public static final String VUFORIA_KEY = "AbfVBDz/////AAABmYjPxLVfc06Kki/omu9b26Vk1TfvZO7giwjiWUu3cBC4GLD957469zF341ecaqFEoca1E35mbaSrBC/Hn5UZgPxpIjYNTOLRBJi72mUr9HO+mMAwuq9Qrs3MQ9E0OOTPolRHSiuorwRU/eTDNksoKVhNdtilPnWFktTaLS2dX6M8MiL3IXxUBxItTd+lbDuKLLVwPDO12DSWR1kOc11jKOnFBgfYUFrDLAq9X6yW74XQlOm26vE/mr/EJ3uO6y5QWysl9oQFGgoDioxqfRuCXQ2oy4BafcHVkwsMoJwAFeIP7zOVukmpIB7NzgZRQ8xy1+EfQTg75ojzmZplPf+wKWd4ypO4XJs3nGAk1kM+/thh";
    public VuforiaLocalizer vuforia;
    public TFObjectDetector tfod;


    public void CHASSIS_INITIALIZE() throws InterruptedException{
        lf = hardwareMap.dcMotor.get("lf");
        lb = hardwareMap.dcMotor.get("lb");
        rf = hardwareMap.dcMotor.get("rf");
        rb = hardwareMap.dcMotor.get("rb");

        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor. ZeroPowerBehavior.BRAKE);

        rf.setDirection(DcMotor.Direction.REVERSE);
        rb.setDirection(DcMotor.Direction.REVERSE);

        slideMotor = hardwareMap.dcMotor.get("slideMotor");
        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideMotor.setDirection(DcMotor.Direction.REVERSE);
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        crMotor = hardwareMap.dcMotor.get("crMotor");
        crMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intakeMotor = hardwareMap.dcMotor.get("intakeMotor");
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        blackBox = hardwareMap.servo.get("blackBox");
        blackBox.setPosition(0.467);

//        dsFront = hardwareMap.get(DistanceSensor.class, "dsFront");
//        dsFreight = hardwareMap.get(DistanceSensor.class, "dsFreight");
    }

    // TeleOp Switch Drive
    public double switchDriveUp(double df){
        if (df == 0.3) {
            return 0.7;
        }
        else if (df == 0.7){
            return 1;
        }
        else if (df == 1){
            return 1;
        }
        return df;
    }

    public double switchDriveDown(double df){
        if (df == 0.3) {
            return 0.3;
        }
        else if (df == 0.7){
            return 0.3;
        }
        else if (df == 1){
            return 0.7;
        }
        return df;
    }

    public void forward (int inch, double power){
        int ticks = (int) (inch * DRIVE_FACTOR);

        //Reset Encoders
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Set Target Position
        lf.setTargetPosition(lf.getCurrentPosition() - ticks);
        lb.setTargetPosition(lb.getCurrentPosition() - ticks);
        rf.setTargetPosition(rf.getCurrentPosition() - ticks);
        rb.setTargetPosition(rb.getCurrentPosition() - ticks);

        //Set Drive Power
        lf.setPower(power);
        lb.setPower(power);
        rf.setPower(power);
        rb.setPower(power);

        //Set to RUN_TO_POSITION mode
        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (lf.isBusy() && lb.isBusy() && rf.isBusy() && rb.isBusy()){
            telemetry.addData("lf", -lf.getCurrentPosition());
            telemetry.addData("rf", -rf.getCurrentPosition());
            telemetry.addData("lb", -lb.getCurrentPosition());
            telemetry.addData("rb", -rb.getCurrentPosition());
            telemetry.update();
            //Wait Until Target Position is Reached
        }
        sleep(500);
    }

    public void turn (int degree, double power){
        int ticks = (int) (degree * TURN_FACTOR);

        //Reset Encoders
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Set Target Position
        lf.setTargetPosition(lf.getCurrentPosition() - ticks);
        lb.setTargetPosition(lb.getCurrentPosition() - ticks);
        rf.setTargetPosition(rf.getCurrentPosition() + ticks);
        rb.setTargetPosition(rb.getCurrentPosition() + ticks);

        //Set Drive Power
        lf.setPower(power);
        lb.setPower(power);
        rf.setPower(power);
        rb.setPower(power);

        //Set to RUN_TO_POSITION mode
        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (lf.isBusy() && lb.isBusy() && rf.isBusy() && rb.isBusy()){
            telemetry.addData("lf", -lf.getCurrentPosition());
            telemetry.addData("rf", -rf.getCurrentPosition());
            telemetry.addData("lb", -lb.getCurrentPosition());
            telemetry.addData("rb", -rb.getCurrentPosition());
            telemetry.update();
            //Wait Until Target Position is Reached
        }
        sleep(500);
    }

    public void strafe (int degree, double power){
        int ticks = (int) (degree * TURN_FACTOR);

        //Reset Encoders
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Set Target Position
        lf.setTargetPosition(lf.getCurrentPosition() + ticks);
        lb.setTargetPosition(lb.getCurrentPosition() - ticks);
        rf.setTargetPosition(rf.getCurrentPosition() - ticks);
        rb.setTargetPosition(rb.getCurrentPosition() + ticks);

        //Set Drive Power
        lf.setPower(power);
        lb.setPower(power);
        rf.setPower(power);
        rb.setPower(power);

        //Set to RUN_TO_POSITION mode
        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (lf.isBusy() && lb.isBusy() && rf.isBusy() && rb.isBusy()){
            telemetry.addData("lf", -lf.getCurrentPosition());
            telemetry.addData("rf", -rf.getCurrentPosition());
            telemetry.addData("lb", -lb.getCurrentPosition());
            telemetry.addData("rb", -rb.getCurrentPosition());
            telemetry.update();
            //Wait Until Target Position is Reached
        }
        sleep(500);
    }

    public void carousel(String state){

        //Reset Encoders
        crMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Set Target Position
        crMotor.setTargetPosition(crMotor.getCurrentPosition() + 3000);

        //Set Drive Power
        if (state == "red") {
            crMotor.setPower(0.3);
        } else if (state == "blue") {
            crMotor.setPower(-0.3);
        }

        //Set to RUN_TO_POSITION mode
        crMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (crMotor.isBusy()){
        }
    }

    public void slideAuto(int position){
        /**
         * LEVEL 1 = 500
         * LEVEL 2 = 1000
         * MAX / LEVEL 3 = 1700
         * */
        //Set Target Position
        slideMotor.setTargetPosition(slideMotor.getCurrentPosition() + position);

        //Set Drive Power
        if (position > slideMotor.getCurrentPosition()) {
            slideMotor.setPower(0.5);
        } else if (position < slideMotor.getCurrentPosition()){
            slideMotor.setPower(-0.3);
        }


        //Set to RUN_TO_POSITION mode
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (slideMotor.isBusy()){
        }
    }

//    public void initVuforia() throws ExceptionInInitializerError {
//
//        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
//        parameters.vuforiaLicenseKey = VUFORIA_KEY;
//        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");
//        vuforia = ClassFactory.getInstance().createVuforia(parameters);
//    }
//
//    public void initTfod() throws ExceptionInInitializerError {
//        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
//                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
//        tfodParameters.minResultConfidence = 0.85f;
//        tfodParameters.isModelTensorFlow2 = true;
//        tfodParameters.inputSize = 320;
//        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
//        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
//    }
}