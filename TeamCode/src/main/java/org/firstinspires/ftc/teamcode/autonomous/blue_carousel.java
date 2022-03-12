package org.firstinspires.ftc.teamcode.autonomous;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.util.*;
import java.util.function.BinaryOperator;

import org.firstinspires.ftc.teamcode.RobotMain358;

@Autonomous
public class blue_carousel extends RobotMain358 {

    private boolean done = false;
    int FINAL_POSITION;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void runOpMode() throws InterruptedException {

        CHASSIS_INITIALIZE();

        while (!opModeIsActive()){
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

            FINAL_POSITION = DETECT_POSITION_BLUE();
        }

        waitForStart();
        while (opModeIsActive() && !done) {

            forward(15, 0.5);
            turn(-90, 0.5);
            forward(-20, 0.5);
            strafe(-5,0.5);


            done = true;
        }
    }

    public void slideAuto(){
        /**
         * LEVEL 1 = 600 - 50
         * LEVEL 2 = 1200 - 50
         * MAX / LEVEL 3 = 1800 - 50
         * */

        // set target position based on sensed position
//        if (position == 0) {
        slideMotor.setTargetPosition(50);
//        } else if (position == 1) {
//            one = true;
        strafe(-4,0.5);
        slideMotor.setTargetPosition(550);
//        } else if (position == 2) {
//            strafe(-5,0.5);
        slideMotor.setTargetPosition(1150);
//        } else if (position == 3) {
//            strafe(-5,0.5);
        slideMotor.setTargetPosition(1750);
//        }

        // set power and mode
        slideMotor.setPower(0.7);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (slideMotor.isBusy()){}

    }
}