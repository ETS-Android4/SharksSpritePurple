package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

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
            if (gamepad2.a) {                                                           //
                crMotor.setPower(-0.45);                                                //
            } else if (gamepad2.y) {                                                    //
                crMotor.setPower(0.45);                                                 //
            } else {                                                                    //
                crMotor.setPower(0);                                                    //
            }                                                                           //
                                                                                        //
            /** INTAKE **/                                                              //
            intakeMotor.setPower(0);                                                    //
            if (gamepad1.right_trigger > 0.2) {                                         //
                intakeMotor.setPower(-0.3);                                             //
            } else if (gamepad1.left_trigger > 0.2){                                    //
                intakeMotor.setPower(1);                                                //
            } else {                                                                    //
                intakeMotor.setPower(0);                                                //
            }                                                                           //
                                                                                        //
            /** SLIDE MOTORS **/                                                        //
            // reset button                                                             //
            slideMotor.setPower(slidePower);                                            //
            if (gamepad2.left_bumper && gamepad2.right_bumper) {                        //
                slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);             //
                slideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);                //
            }                                                                           //
            // down                                                                     //
            if (gamepad2.dpad_down) {                                                   //
                if (slideMotor.getCurrentPosition() <= 0){                              //
                    slideMotor.setPower(slidePower);                                    //
                } else {                                                                //
                    slideMotor.setPower(-0.8);                                          //
                }                                                                       //
            }                                                                           //
            // up                                                                       //
            else if (gamepad2.dpad_up) {                                                //
                if (slideMotor.getCurrentPosition() >= 1720){                           //
                    slideMotor.setPower(slidePower);                                    //
                } else {                                                                //
                    slideMotor.setPower(0.8);                                           //
                }                                                                       //
            } else {                                                                    //
                slideMotor.setPower(slidePower);                                        //
            }                                                                           //
                                                                                        //
            /** Black Box **/                                                           //
            blackBox.setPower(0);                                                       //
            // in                                                                       //
            if (gamepad2.right_stick_x == 0){                                           //
                blackBox.setPower(0);                                                   //
            }                                                                           //
            else if (gamepad2.right_stick_x < -0.05 || gamepad2.right_stick_x > 0.05){  //
                blackBox.setPower(-0.4 * gamepad2.right_stick_x);                        //
            }                                                                           //
                                                                                        //
            //add telemetry                                                             //
//            telemetry.addData("freight distance", dsFreight.getDistance(DistanceUnit.INCH));
//            telemetry.addData("front distance", dsFront.getDistance(DistanceUnit.INCH));
            telemetry.addData("time elapsed", String.format(java.util.Locale.US, "%.1f", runtime.seconds()));
            telemetry.addData("drive factor", driveFactor);                      //
            telemetry.addData("slide", slideMotor.getCurrentPosition());         //
            telemetry.update();                                                         //
                                                                                        //
//////////////////////////////////////////////////////////////////////////////////////////
                                                                                        //
            // create gamepad rumble effects                                            //
            if (runtime.seconds() == HALF_TIME) {
                gamepad1.rumble(300);
                gamepad2.rumble(300);
            } else if (runtime.seconds() == END_GAME) {
                gamepad1.rumble(1000);
                gamepad2.rumble(1000);
            }
        }
    }
}