package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
public class AUTONOMOUS_PARK extends RobotMain358{

    private boolean done = false;

    public void runOpMode() throws InterruptedException {

        CHASSIS_INITIALIZE();

        waitForStart();
        while (opModeIsActive()) {

            blackBox.setPosition(0.086);
            sleep(500);

            telemetry.addData("hello", blackBox.getPosition());
            telemetry.update();





        }
    }
}