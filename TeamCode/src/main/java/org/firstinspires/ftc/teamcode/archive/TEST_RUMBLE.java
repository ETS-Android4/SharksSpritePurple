package org.firstinspires.ftc.teamcode.archive;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

@Disabled
@TeleOp
public class TEST_RUMBLE extends LinearOpMode {

    private boolean pressed = false;

    public void runOpMode() throws InterruptedException {

        waitForStart();
        while (opModeIsActive()) {

            if (pressed) {
                // Right Bumper is pressed, so send left and right "trigger" values to left and right rumble motors.
                gamepad1.rumble(gamepad1.left_trigger, gamepad1.right_trigger, Gamepad.RUMBLE_DURATION_CONTINUOUS);

                // Show what is being sent to rumbles
                telemetry.addData(">", "Squeeze triggers to control rumbles");
                telemetry.addData("> : Rumble", "Left: %.0f%%   Right: %.0f%%", gamepad1.left_trigger * 100, gamepad1.right_trigger * 100);

                if (gamepad1.left_bumper) {
                    gamepad1.stopRumble();
                    pressed = false;
                }
            } else {
                if (gamepad1.right_bumper){
                    pressed = true;
                }

                //  Prompt for manual rumble action
                telemetry.addData(">", "Hold Left-Bumper to test Manual Rumble");
                telemetry.addData(">", "Press A (Cross) for three blips");
                telemetry.addData(">", "Squeeze right trigger slowly for 1 blip");
            }
        }
    }
}