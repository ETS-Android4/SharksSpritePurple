package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

@TeleOp
public class Mec_TeleOp358 extends RobotMain358{

    public void runOpMode() throws InterruptedException {

//        initVuforia();
//        initTfod();
//
//        if (tfod != null) {
//            tfod.activate();
//            tfod.setZoom(1, 16.0/9.0);
//        }

        CHASSIS_INITIALIZE();

        waitForStart();
        while(opModeIsActive()){

            runtime.reset();    // Start game timer.

///////////////////////////// Drive Train ////////////////////////////////////////////////
                                                                                        //
            // define slow drive                                                        //
            if (gamepad1.right_bumper){                                                 //
                if (System.currentTimeMillis() - lastTime > timeElapsed){               //
                    driveFactor = switchDriveUp(driveFactor);                           //
                    lastTime = System.currentTimeMillis();                              //
                }                                                                       //
            }                                                                           //
                                                                                        //
            // define slow drive                                                        //
            if (gamepad1.left_bumper){                                                  //
                if (System.currentTimeMillis() - lastTime > timeElapsed){               //
                    driveFactor = switchDriveDown(driveFactor);                         //
                    lastTime = System.currentTimeMillis();                              //
                }                                                                       //
            }                                                                           //
                                                                                        //
            double y = gamepad1.left_stick_y;                                           //
            double x = -gamepad1.left_stick_x;                                          //
            double rx = -gamepad1.right_stick_x;                                        //
                                                                                        //
            double denom = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);       //
            double lfPower = (y + x + rx) / denom;                                      //
            double lbPower = (y - x + rx) / denom;                                      //
            double rfPower = (y - x - rx) / denom;                                      //
            double rbPower = (y + x - rx) / denom;                                      //
                                                                                        //
            lf.setPower(lfPower * driveFactor);                                         //
            lb.setPower(lbPower * driveFactor);                                         //
            rf.setPower(rfPower * driveFactor);                                         //
            rb.setPower(rbPower * driveFactor);                                         //
                                                                                        //
//////////////////////////////////////////////////////////////////////////////////////////
                                                                                        //
            /** CAROUSEL MOTORS **/                                                     //
            crMotor.setPower(0);                                                        //
            if (gamepad2.left_trigger > 0.1) {                                          //
                crMotor.setPower(-0.45);                                                //
            } else if (gamepad2.right_trigger > 0.1) {                                  //
                crMotor.setPower(0.45);                                                 //
            } else {                                                                    //
                crMotor.setPower(0);                                                    //
            }                                                                           //
                                                                                        //
            /** INTAKE **/                                                              //
//            if (gamepad1.dpad_down) {
//                controlled = false;
//            } else if (gamepad1.dpad_up) {
//                controlled = true;
//            }

//            if (controlled) {
//                intakeMotor.setPower(0);                                                    //
//                if (gamepad1.left_trigger > 0.2) {                                          //
//                    intakeMotor.setPower(-0.6);                                              //
//                } else if (gamepad1.right_trigger > 0.2){                                   //
//                    intakeMotor.setPower(1);                                              //
//                } else {                                                                    //
//                    intakeMotor.setPower(0);                                                //
//                }                                                                           //
//            }
            if (!controlled) {
                intakeMotor.setPower(0);
                if (gamepad1.left_trigger > 0.2) {                                          //
                    intakeMotor.setPower(-0.6);                                              //
//                } else if (gamepad1.right_trigger > 0.2){                                   //
//                    intakeMotor.setPower(1);                                              //
                } else if (driveFactor == 0.7) {
                    intakeMotor.setPower(1);
                } else if (driveFactor == 0.9) {
                    intakeMotor.setPower(0);
                }
            }

                                                                                        //
//////////////////////////////////////////////////////////////////////////////////////////
            /** SLIDE MOTORS **/                                                        //
            // reset button                                                             //
            slideMotor.setPower(slidePower);                                            //
            if (gamepad2.left_bumper && gamepad2.right_bumper) {                        //
                slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);             //
                slideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);                //
            }                                                                           //
            // down                                                                     //
            if (gamepad2.dpad_down) {                                                   //
                if (slideMotor.getCurrentPosition() <=20){                              //
                    slideMotor.setPower(slidePower);                                    //
                } else {                                                                //
                    slideMotor.setPower(-0.7);                                          //
                }                                                                       //
            }                                                                           //
            // up                                                                       //
            else if (gamepad2.dpad_up) {                                                //
                if (slideMotor.getCurrentPosition() >= 1700){                           //
                    slideMotor.setPower(slidePower);                                    //
                } else {                                                                //
                    slideMotor.setPower(1);                                           //
                }                                                                       //
            // auto 3rd level                                                           //
            }                                                                           //
//            else if (gamepad2.y) {                                                      //
//                if (slideMotor.getCurrentPosition() > 1700) {                           //
//                // up                                                                   //
//                } else if (slideMotor.getCurrentPosition() < 300) {                     //
//                    slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);         //
//                    slideMotor.setTargetPosition(1700);                                 //
//                    slideMotor.setPower(0.7);                                           //
//                    slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);                //
//                    slideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);            //
//                }                                                                       //
//            }                                                                           //
            else {                                                                      //
                slideMotor.setPower(slidePower);                                        //
            }                                                                           //
//////////////////////////////////////////////////////////////////////////////////////////
                                                                                        //
            /** Black Box **/                                                           //
            // reset                                                                    //
            if (gamepad2.a) {                                             //
                blackBox.setPosition(0.439);                                            //
            } // right                                                                  //
            else if (gamepad2.b){                                                       //
                blackBox.setPosition(0.086);                                            //
                gamepad2.rumble(1, 1, 400);                    //
                gamepad2.rumble(0, 0, 200);                    //
                gamepad2.rumble(1, 1, 400);                    //
            } // left                                                                   //
            else if (gamepad2.x){                                                       //
                blackBox.setPosition(0.80);                                             //
                gamepad2.rumble(1, 1, 400);                     //
                gamepad2.rumble(0, 0, 200);                     //
                gamepad2.rumble(1, 1, 400);                     //
            }                                                                           //
                                                                                        //
            // add rumble when a freight is in blackBox                                 //
            if (dsFreight.getDistance(DistanceUnit.INCH) < 4) {                       //
                gamepad1.rumble(0.8, 0.8, Gamepad.RUMBLE_DURATION_CONTINUOUS);
                gamepad2.rumble(0.5, 0.5, 500);                 //
            } else {                                                                    //
                gamepad1.stopRumble();                                                  //
            }                                                                           //

            /** Stuck **/
            if (gamepad2.y) {
                if (stuckPosition == 0) {
                    stuck.setPosition(0.5);
                    stuckPosition = 1;
                    gamepad2.rumble(0.5, 0.5, Gamepad.RUMBLE_DURATION_CONTINUOUS);
                } else if (stuckPosition == 1){
                    stuck.setPosition(0.17);
                    stuckPosition = 0;
                    gamepad2.stopRumble();
                }
            }

            //
            //add telemetry                                                             //
//            telemetry.addData("range-side", String.format(Locale.US, "%.01f in", dsFront.getDistance(DistanceUnit.INCH)));
//            telemetry.addData("range-box", String.format(Locale.US, "%.01f in", dsFreight.getDistance(DistanceUnit.INCH)));
            telemetry.addData("range-right", String.format(Locale.US, "%.01f in", dsRight.getDistance(DistanceUnit.INCH)));
            telemetry.addData("drive factor", driveFactor);                      //
            telemetry.addData("slide", slideMotor.getCurrentPosition());         //
            telemetry.addData("box", blackBox.getPosition());                    //
            telemetry.addData("stuck", stuck.getPosition());                    //
            telemetry.update();                                                         //
                                                                                        //
//////////////////////////////////////////////////////////////////////////////////////////

        }
    }
}