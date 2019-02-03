package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.teamLibs.goBuildAServo2000;
import org.firstinspires.ftc.teamcode.teamLibs.teamUtil;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Servo Test", group="Linear Opmode")
public class servoTest extends LinearOpMode{
        private Telemetry telemetry;
        private Servo testServo;



        @Override
        public void runOpMode() {

            testServo = hardwareMap.get(Servo.class, "testServo");

            waitForStart();

            while (opModeIsActive()) {
                if (gamepad1.y) {
                    testServo.setPosition(0.5);
                } else if (gamepad1.x) {
                    testServo.setPosition(0);
                } else if (gamepad1.b) {
                    ServoImplEx rawServo = (ServoImplEx) testServo;
                    rawServo.setPwmEnable(); // servo should be disabled
                    testServo.setPosition(.75);
                } else if (gamepad1.a) {
                    ServoImplEx rawServo = (ServoImplEx) testServo;
                    rawServo.setPwmDisable(); // servo should be disabled
                }

                if (gamepad1.left_bumper) {
                    ElapsedTime runtime = new ElapsedTime();
                    testServo.setPosition(0);
                    runtime.reset();
                    while (runtime.milliseconds() < 2000) {
                        testServo.setPosition(runtime.milliseconds()/2000);
                    }
                }

            }
        }
    }

