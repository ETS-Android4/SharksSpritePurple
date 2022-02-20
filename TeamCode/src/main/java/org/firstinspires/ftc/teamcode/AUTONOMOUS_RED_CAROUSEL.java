package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

@Autonomous
public class AUTONOMOUS_RED_CAROUSEL extends RobotMain358{

    private boolean done = false;
    public int position;

    public void runOpMode() throws InterruptedException {

        CHASSIS_INITIALIZE();

        waitForStart();
        while (opModeIsActive() && !done) {
            position = 3;

            forward(14,1);
            turn(-90,0.5);

            dsAuto();

            forward(-12,1);
            turn(90,0.5);
            forward(24, 1);

            slideAuto();

            blackBox.setPosition(0.086); sleep(1300);
            blackBox.setPosition(0.439); sleep(500);
            blackBox.setPosition(0.086); sleep(1300);
            blackBox.setPosition(0.439); sleep(500);

            position = 0;
            slideAuto();

            strafe(48,1);
            forward(-26,1);
            forward(-4,0.5);
            strafe(-10,0.7);
            turn(-45, 0.5);
            strafe(8,0.7);
            strafe(2,.5);

            carousel("red");

            strafe(-8,0.7);
            turn(45,0.5);
            strafe(20,1);
            forward(17,1);

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
            strafe(-1,0.5);
            slideMotor.setTargetPosition(550);
        } else if (position == 2) {
            strafe(-2,0.5);
            slideMotor.setTargetPosition(1150);
        } else if (position == 3) {
            strafe(-2,0.5);
            slideMotor.setTargetPosition(1750);
        }

        // set power and mode
        slideMotor.setPower(0.7);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (slideMotor.isBusy()){}

    }

    public void dsAuto() {
        // drive to the first detection position
        forward(8,0.3);
        // wait a second for accuracy
        sleep(500);

        telemetry.addData("range", String.format(Locale.US, "%.01f in", dsFront.getDistance(DistanceUnit.INCH)));
        telemetry.update();

        // if we successfully detect the marker
        if (dsFront.getDistance(DistanceUnit.INCH) < 10) {
            // tell the program to put the cube at the first level
            position = 1;
        }

        // drive to the second detection position
        forward(-9.5,0.3);
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