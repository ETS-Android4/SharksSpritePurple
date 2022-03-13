package org.firstinspires.ftc.teamcode.autonomous;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import java.util.*;
import java.util.function.BinaryOperator;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.RobotMain358;

@Autonomous
public class blue_warehouse extends RobotMain358 {

    private boolean done = false;
    int FINAL_POSITION;
    double counter = 0;
    int holder;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void runOpMode() throws InterruptedException {

        CHASSIS_INITIALIZE();

        while (!opModeIsActive()){

            telemetry.addData("ds front left: ", dsFrontLeft.getDistance(DistanceUnit.INCH));
            telemetry.addData("ds front right: ", dsFrontRight.getDistance(DistanceUnit.INCH));
            telemetry.addData("ds left: ", dsLeft.getDistance(DistanceUnit.INCH));
            telemetry.addData("ds right: ", dsFrontRight.getDistance(DistanceUnit.INCH));

            List<Integer> detected = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                detected.add(DETECT_POSITION_BLUE());
                sleep(10);
            }

            FINAL_POSITION = detected.stream().
                    reduce(BinaryOperator.maxBy((o1,o2) -> Collections.frequency(detected, o1) -
                            Collections.frequency(detected, o2))).orElse(3);
            detected.clear();

            telemetry.addData("Position Detected", FINAL_POSITION);
            telemetry.update();
            holder = FINAL_POSITION;
        }

        waitForStart();
        while (opModeIsActive() && !done) {

            telemetry.addData("ds front left: ", dsFrontLeft.getDistance(DistanceUnit.INCH));
            telemetry.addData("ds front right: ", dsFrontRight.getDistance(DistanceUnit.INCH));
            telemetry.addData("ds left: ", dsLeft.getDistance(DistanceUnit.INCH));
            telemetry.addData("ds right: ", dsFrontRight.getDistance(DistanceUnit.INCH));

            if (FINAL_POSITION == 1) {strafe(8, 0.5);}

            forward(39, 0.7);
            sleep(500);
            slideAuto();

            blackBox.setPosition(0.086); sleep(1300);
            blackBox.setPosition(0.439); sleep(500);

            FINAL_POSITION = 0;
            slideAuto();
            strafe(2, 0.7);

            forward(-34, 1);
            turn(-90, 0.5);
            strafeLeftAuto(200, 0.5, 2);
            sleep(500);

            if (holder == 1) {
                forward(28, 0.7);
            } else {
                forward(33, 0.7);
            }


            counter = forwardUntilIntakeAuto(2, 0.2, counter);
            counter = forwardUntilIntakeAuto(2, 0.2, counter);
            counter = forwardUntilIntakeAuto(2, 0.2, counter);
            counter = forwardUntilIntakeAuto(2, 0.2, counter);
            counter = forwardUntilIntakeAuto(2, 0.2, counter);
            counter = forwardUntilIntakeAuto(2, 0.2, counter);
            counter = forwardUntilIntakeAuto(2, 0.2, counter);
            intakeMotor.setPower(-0.6);
            sleep(500);

            forward(-3, 0.5);
            strafeLeftAuto(200, 0.5, 2);
            forward(-(37+counter), 0.7);
            intakeMotor.setPower(0);
            strafeLeftAuto(200, 0.5, 2);
            sleep(500);
            strafe(-30, 0.5);

            FINAL_POSITION = 3;
            slideAuto();

            blackBox.setPosition(0.086); sleep(1300);
            blackBox.setPosition(0.439); sleep(500);

            FINAL_POSITION = 0;
            slideAuto();

            strafe(40, 1);
            forward(65, 1);

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
            strafeRightAuto(10, 0.3, 8.5);
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