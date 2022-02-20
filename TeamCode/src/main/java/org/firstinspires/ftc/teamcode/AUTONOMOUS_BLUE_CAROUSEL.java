package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

@Autonomous
public class AUTONOMOUS_BLUE_CAROUSEL extends RobotMain358{

    private boolean done = false;
    public int position;
    public boolean one = false;

    public void runOpMode() throws InterruptedException {

        CHASSIS_INITIALIZE();

        waitForStart();
        while (opModeIsActive() && !done) {
            position = 1;

            forward(14,0.5);
            turn(-90,0.5);

            dsAuto();

            strafe(-33,0.5);
            strafe(8,0.5);
            turn(-90,0.5);

            slideAuto();

            blackBox.setPosition(0.086); sleep(1300);
            blackBox.setPosition(0.439); sleep(500);
            blackBox.setPosition(0.086); sleep(1300);
            blackBox.setPosition(0.439); sleep(500);

            position = 0;
            slideAuto();

            strafe(5,0.5);
            forward(-5,0.5);
            turn(90,0.5);

            forward(-30,0.5);
            forward(-5,0.5);

            if (one) {
                strafe(41,0.5);
            } else {
                strafe(39,0.5);
            }


            carousel("blue");

            strafe(-19,1);
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
        if (position == 0) {
            slideMotor.setTargetPosition(50);
        } else if (position == 1) {
            one = true;
            strafe(-5,0.5);
            slideMotor.setTargetPosition(550);
        } else if (position == 2) {
            strafe(-7,0.5);
            slideMotor.setTargetPosition(1150);
        } else if (position == 3) {
            strafe(-7,0.5);
            slideMotor.setTargetPosition(1750);
        }

        // set power and mode
        slideMotor.setPower(0.7);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (slideMotor.isBusy()){}

    }

    public void dsAuto() {

        // wait a second for accuracy
        sleep(500);

        telemetry.addData("range", String.format(Locale.US, "%.01f in", dsFront.getDistance(DistanceUnit.INCH)));
        telemetry.update();

        // if we successfully detect the marker
        if (dsFront.getDistance(DistanceUnit.INCH) < 10) {
            // tell the program to put the cube at the first level
            position = 3;
        }

        // drive to the second detection position
        forward(10,0.3);
        // wait a second for accuracy
        sleep(500);

        telemetry.addData("range", String.format(Locale.US, "%.01f in", dsFront.getDistance(DistanceUnit.INCH)));
        telemetry.update();

        // if we successfully detect the marker
        if (dsFront.getDistance(DistanceUnit.INCH) < 10) {
            // tell the program to put the cube at the second level
            position = 2;
        }

        // if the marker is not at either position 1 or 2, then it must be at 3,
        // which is preset to 3, so we don't have to change it again.

        telemetry.addData("position", position);
        telemetry.update();
    }
}