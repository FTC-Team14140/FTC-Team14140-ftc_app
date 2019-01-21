package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.teamLibs.basicMovement;
import org.firstinspires.ftc.teamcode.teamLibs.grabberArm;
import org.firstinspires.ftc.teamcode.teamLibs.linearActuator;
import org.firstinspires.ftc.teamcode.teamLibs.mineralDetector;
import org.firstinspires.ftc.teamcode.teamLibs.sweeperArm;
import org.firstinspires.ftc.teamcode.teamLibs.teamUtil;
import com.qualcomm.robotcore.hardware.ColorSensor;
import org.firstinspires.ftc.teamcode.teamLibs.teamColorSensor;

@Autonomous(name="Auto Crater", group="Linear Opmode")
public class autoCrater extends LinearOpMode{
        private linearActuator La;
        private basicMovement basicMove;
        private grabberArm grabber;
        private teamColorSensor leftColor;
        private teamColorSensor rightColor;
        private sweeperArm sweeper;
        private mineralDetector detector;

        @Override
        public void runOpMode() {

            teamUtil.theOpMode = this;
            //initialization code
            teamUtil.log("initializing Robot...");

            // Create and initialize the Mineral Detector
            // This code should appear at the start of the opMode before initializing other
            // hardware classes otherwise we are getting wierd USB bus errors...
            teamUtil.log("creating Detector");
            detector = new mineralDetector(telemetry, hardwareMap);
            teamUtil.log("Initializing Detector");
            detector.initialize(hardwareMap.get(WebcamName.class, "Webcam 1"));
            telemetry.addData("Status", "Detector Initialized");

            teamUtil.log("initializing linearActuator...");
            La = new linearActuator(telemetry, hardwareMap.get(DcMotor.class, "LAMotor"));
            teamUtil.log("initializing grabberArm...");
            grabber = new grabberArm(telemetry, hardwareMap, "grabberServo", "liftServo");

            teamUtil.log("initializing basicMovement...");
            basicMove = new basicMovement(hardwareMap.get(DcMotor.class, "motorLeft"), hardwareMap.get(DcMotor.class, "motorRight"), hardwareMap.get(BNO055IMU.class,"imu"), telemetry);
            // Wait for the game to start of the game(drver presses PLAY)
            teamUtil.log("initializing colorSensors...");
            leftColor = new teamColorSensor(telemetry,hardwareMap.get(ColorSensor.class,"leftRearColor"));
            rightColor = new teamColorSensor(telemetry,hardwareMap.get(ColorSensor.class,"rightRearColor"));
            sweeper = new sweeperArm(telemetry, hardwareMap, "retrieveBaseServo", "retrieveArmServo");

            grabber.autoInitialize();
            sweeper.retract();

            teamUtil.log("Initialized, Waiting for Start...");
            telemetry.addData("Status", "AUTO CRATER: Ready to Ruckus!");
            telemetry.update();

            waitForStart();

            teamUtil.log("La.lowerRobot()...");


            basicMove.motorsOn(0.2);
            int cubePosition = La.extendDetect(detector);
            basicMove.motorsOff();
            telemetry.addData("cubePosition:", cubePosition);
            telemetry.update();
            teamUtil.log("CubePosition: " + cubePosition);

            basicMove.moveInches(-0.2, -.5);
            teamUtil.log("calibrating");
            leftColor.calibrate();
            rightColor.calibrate();
            teamUtil.log("Unlatching");
            basicMove.rightSpin(0.5, 10);
            basicMove.moveInches(0.3, 6);
            basicMove.leftSpin(0.5, 10);
            teamUtil.log("retracting");
            La.retractFullyNoWait();

            if (cubePosition == 2 || cubePosition == 0) {
                basicMove.moveInches(0.6,19);
                basicMove.moveInches(-0.6, -10);
                basicMove.leftSpin(0.4,85);
                teamUtil.log("heading = "+basicMove.getHeading());
                basicMove.moveInches(1,52);
                basicMove.moveInches(0.3, 5);
                teamUtil.log("spinning left = "+(40+(basicMove.getHeading()+85)));
                basicMove.leftSpin(0.4,40+(basicMove.getHeading()+85));
                basicMove.moveInches(1, 37);
                basicMove.moveInches(0.3,5);
                teamUtil.log("dropping");
                grabber.grabberDown();
                sleep(1000);
                grabber.wideOpen();
                sleep(500);
                grabber.holdUp();
                grabber.skinnyOpen();
                basicMove.moveInches(-1,-65);
                basicMove.moveInches(-0.3,-5);
            } else if (cubePosition == 3) {
                basicMove.moveInches(0.5, 5);
                basicMove.decelLeftSpin(0.5, 115);
                basicMove.moveInches(-0.5, -5);
                basicMove.moveInches(0.5, 5);
                basicMove.decelRightSpin(0.5, 30);
                basicMove.moveInches(1,57);
                basicMove.moveInches(0.3, 5);
                teamUtil.log("spinning left = "+(40+(basicMove.getHeading()-25)));
                basicMove.leftSpin(0.4,40+(basicMove.getHeading()-25));
                basicMove.moveInches(1, 37);
                basicMove.moveInches(0.3,5);
                teamUtil.log("dropping");
                grabber.grabberDown();
                sleep(1000);
                grabber.wideOpen();
                sleep(500);
                grabber.holdUp();
                grabber.skinnyOpen();
                basicMove.moveInches(-1,-65);
                basicMove.moveInches(-0.3,-5);

            } else if (cubePosition == 1) {
                basicMove.decelLeftSpin(0.5, 45);
                basicMove.moveInches(0.5, 25);
                basicMove.moveInches(-0.3, -10);
                basicMove.decelLeftSpin(0.5, 40);
                basicMove.moveInches(1,47);
                basicMove.moveInches(0.3, 5);
                teamUtil.log("spinning left = "+(40+(basicMove.getHeading()+35)));
                basicMove.leftSpin(0.4,40+(basicMove.getHeading()+35));
                basicMove.moveInches(1, 30);
                basicMove.moveInches(0.3,3);
                teamUtil.log("dropping");
                grabber.grabberDown();
                sleep(1000);
                grabber.wideOpen();
                sleep(500);
                grabber.holdUp();
                grabber.skinnyOpen();
                basicMove.moveInches(-1,-60);
                basicMove.moveInches(-0.3,-5);
            }

        }

    }


