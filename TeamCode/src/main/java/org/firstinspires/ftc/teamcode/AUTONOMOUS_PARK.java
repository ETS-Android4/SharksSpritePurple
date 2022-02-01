package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
public class AUTONOMOUS_PARK extends RobotMain358{

    private boolean done = false;

    public void runOpMode() throws InterruptedException {

        CHASSIS_INITIALIZE();

        waitForStart();
        while (opModeIsActive() && !done) {
            slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

//            slide(500);
            forward(70, 0.5);               //dash!!!

            done = true;
        }
    }
}