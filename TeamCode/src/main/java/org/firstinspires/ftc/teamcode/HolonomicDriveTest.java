package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.teamLibs.basicMovement;
import org.firstinspires.ftc.teamcode.teamLibs.teamUtil;
import org.firstinspires.ftc.teamcode.teamLibs.HiTecServo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.teamLibs.teamUtil;

@TeleOp(name="HolonomicDriveTrainTest", group="Linear Opmode")
public class HolonomicDriveTest extends LinearOpMode{
    public void runOpMode() {

        double speede;
        DcMotor motorBackLeft;
        DcMotor motorBackRight;
        DcMotor motorFrontRight;
        DcMotor motorFrontLeft;
        boolean moveTest = this.gamepad1.a;
        boolean turnTest = this.gamepad1.x;
    // left stick controls direction
        // right stick X controls rotation

        motorFrontRight = hardwareMap.get(DcMotor.class, "fr");
        teamUtil.log("Getting Right Front Motor from hardware map");
        motorFrontLeft = hardwareMap.get(DcMotor.class, "fl");
        teamUtil.log("Getting Left Front Motor from hardware map");
        motorBackRight = hardwareMap.get(DcMotor.class, "br");
        teamUtil.log("Getting Right Back Motor from hardware map");
        motorBackLeft = hardwareMap.get(DcMotor.class, "bl");
        teamUtil.log("Getting Left Back Motor from hardware map");
        telemetry.addData("Status", "Waiting for Start...");
        telemetry.update();
        speede = .5;
        // Wait for the game to start (driver presses PLAY)

        waitForStart();




        while (opModeIsActive()) {

            float gamepad1LeftY = -gamepad1.left_stick_y;
            float gamepad1LeftX = gamepad1.left_stick_x;
            float gamepad1RightX = gamepad1.right_stick_x;


            // holonomic formulas

            float FrontLeft = -gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
            float FrontRight = gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
            float BackRight = gamepad1LeftY + gamepad1LeftX - gamepad1RightX;
            float BackLeft = -gamepad1LeftY + gamepad1LeftX - gamepad1RightX;

            // clip the right/left values so that the values never exceed +/- 1
            FrontRight = Range.clip(FrontRight, -1, 1);
            FrontLeft = Range.clip(FrontLeft, -1, 1);
            BackLeft = Range.clip(BackLeft, -1, 1);
            BackRight = Range.clip(BackRight, -1, 1);

            // write the values to the motors
            motorFrontRight.setPower(FrontRight);
            motorFrontLeft.setPower(FrontLeft);
            motorBackLeft.setPower(BackLeft);
            motorBackRight.setPower(BackRight);

            if (gamepad1.dpad_up) {
                speede = speede + .05;
                teamUtil.sleep(550);

            }
            if (gamepad1.dpad_down) {
                speede = speede - .05;
                teamUtil.sleep(550);

            }

            if (gamepad1.a) {
                motorFrontRight.setPower(speede);
                motorFrontLeft.setPower(-speede);
                motorBackLeft.setPower(-speede);
                motorBackRight.setPower(speede);
                teamUtil.sleep(1000);
                motorFrontRight.setPower(0);
                motorFrontLeft.setPower(0);
                motorBackLeft.setPower(0);
                motorBackRight.setPower(0);
            }
            if (gamepad1.b) {
                motorFrontRight.setPower(speede);
                motorFrontLeft.setPower(speede);
                motorBackLeft.setPower(speede);
                motorBackRight.setPower(speede);
                teamUtil.sleep(1000);
                motorFrontRight.setPower(0);
                motorFrontLeft.setPower(0);
                motorBackLeft.setPower(0);
                motorBackRight.setPower(0);
            }


            /*
             * Telemetry for debugging
             */
            telemetry.addData("Text", "*** Robot Data***");
            telemetry.addData("Joy XL YL XR", String.format("%.2f", gamepad1LeftX) + " " +
                    String.format("%.2f", gamepad1LeftY) + " " + String.format("%.2f", gamepad1RightX));
            telemetry.addData("f left pwr", "front left  pwr: " + String.format("%.2f", FrontLeft));
            telemetry.addData("f right pwr", "front right pwr: " + String.format("%.2f", FrontRight));
            telemetry.addData("b right pwr", "back right pwr: " + String.format("%.2f", BackRight));
            telemetry.addData("b left pwr", "back left pwr: " + String.format("%.2f", BackLeft));
            telemetry.addData("speed", ": " + String.format("%.2f", speede));
            telemetry.update();
        }
    }
}

