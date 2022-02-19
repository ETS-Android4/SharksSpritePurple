package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

@Autonomous
public class AUTONOMOUS_RED_CAROUSEL extends RobotMain358{

    private boolean done = false;

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

            carousel("red");
            strafe(-8,0.7);
            turn(45,0.5);
            strafe(20,1);
            forward(17,1);

            done = true;
        }
    }
}