package org.firstinspires.ftc.teamcode.autonomous;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.util.*;
import java.util.function.BinaryOperator;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.RobotMain358;

@Autonomous
public class blue_carousel extends RobotMain358 {

    private boolean done = false;
    int FINAL_POSITION;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void runOpMode() throws InterruptedException {

        CHASSIS_INITIALIZE();

        // detect position during init
        while (!opModeIsActive()){

            telemetry.addData("ds front left: ", dsFrontLeft.getDistance(DistanceUnit.INCH));
            telemetry.addData("ds front right: ", dsFrontRight.getDistance(DistanceUnit.INCH));
            telemetry.addData("ds left: ", dsLeft.getDistance(DistanceUnit.INCH));
            telemetry.addData("ds right: ", dsFrontRight.getDistance(DistanceUnit.INCH));

            // store detected positions into a list
            List<Integer> detected = new ArrayList<>();
            // store 20 values into a list
            for (int i = 0; i < 20; i++) {
                detected.add(DETECT_POSITION_BLUE());
                sleep(10);
            }

            // find the most common value in the list
            FINAL_POSITION = detected.stream().
                    reduce(BinaryOperator.maxBy((o1,o2) -> Collections.frequency(detected, o1) -
                            Collections.frequency(detected, o2))).orElse(3);
            // clear the list to keep the detection running
            detected.clear();

            // add telemetry for the detected position
            telemetry.addData("Position Detected", FINAL_POSITION);
            telemetry.update();
        }

        waitForStart();
        while (opModeIsActive() && !done) {

            forward(15, 0.5);
            turn(-90, 0.5);
            forward(-23, 0.5);
            strafe(8,0.5);
            strafe(2.5, 0.3);

            carousel("blue");

            strafe(-36, 0.5);
            forward(-10,0.8);
            forward(31, 0.5);
            turn(-90, 0.5);

            slideAuto();

            blackBox.setPosition(0.086); sleep(1300);
            blackBox.setPosition(0.439); sleep(500);
            blackBox.setPosition(0.086); sleep(1300);
            blackBox.setPosition(0.439); sleep(500);

            FINAL_POSITION = 0;
            slideAuto();

            strafe(40, 0.5);
            strafe(5, 0.3);
            forward(15, 0.5);

            done = true;
        }
    }

    public void slideAuto(){
        /**
         * LEVEL 1 = 600 - 50
         * LEVEL 2 = 1200 - 50
         * MAX / LEVEL 3 = 1800 - 50
         * */

        telemetry.addData("slide", "yay!");
        telemetry.update();

        // set target position based on sensed position
        if (FINAL_POSITION == 0){
            slideMotor.setTargetPosition(50);
        }else if (FINAL_POSITION == 1) {
            strafeRightAuto(10, 0.3, 9);
            slideMotor.setTargetPosition(550);
        } else if (FINAL_POSITION == 2) {
            strafeRightAuto(10, 0.3, 8);
            slideMotor.setTargetPosition(1150);
        } else if (FINAL_POSITION == 3) {
            strafeRightAuto(10, 0.3, 7);
            slideMotor.setTargetPosition(1750);
        }

        // set power and mode
        slideMotor.setPower(1);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (slideMotor.isBusy()){}

    }
}