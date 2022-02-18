package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
public class AUTONOMOUS_RED_CAROUSEL extends RobotMain358{

    private boolean done = false;

    public void runOpMode() throws InterruptedException {

        CHASSIS_INITIALIZE();

        waitForStart();
        while (opModeIsActive() && !done) {

            forward(14,0.3);
            turn(-90,0.2);

            forward(8,0.3);
            sleep(1000);
            forward(-9.5,0.3);
            sleep(1000);
            forward(-9.5,0.3);
            sleep(1000);

            forward(-1.6,0.2);
            turn(87,0.2);
            forward(25, 0.3);
            strafe(-1.5,0.3);

            slideAuto(2);

            blackBox.setPosition(0.086);
            sleep(1000);
            blackBox.setPosition(0.439);
            sleep(500);
            blackBox.setPosition(0.086);
            sleep(1000);
            blackBox.setPosition(0.439);
            sleep(500);blackBox.setPosition(0.086);
            sleep(1000);
            blackBox.setPosition(0.439);
            sleep(500);

            done = true;
        }
    }
}