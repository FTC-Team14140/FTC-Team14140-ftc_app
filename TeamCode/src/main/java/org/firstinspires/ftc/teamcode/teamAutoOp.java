package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.teamLibs.goBuildAServo2000;
import org.firstinspires.ftc.teamcode.teamLibs.mineralDetector;
import org.firstinspires.ftc.teamcode.teamLibs.sweeperArm;
import org.firstinspires.ftc.teamcode.teamLibs.teamColorSensor;
import org.firstinspires.ftc.teamcode.teamLibs.basicMovement;
import org.firstinspires.ftc.teamcode.teamLibs.linearActuator;
import org.firstinspires.ftc.teamcode.teamLibs.grabberArm;
import org.firstinspires.ftc.teamcode.teamLibs.teamUtil;
import com.qualcomm.robotcore.hardware.ColorSensor;
import org.firstinspires.ftc.teamcode.teamLibs.distanceSensors;

@Autonomous(name="Auto Depot", group="Linear Opmode")
public class teamAutoOp extends LinearOpMode {
    private linearActuator La;
    private basicMovement basicMove;
    private grabberArm grabber;
    private teamColorSensor leftColor;
    private teamColorSensor rightColor;
    private sweeperArm sweeper;
    private mineralDetector detector;
    private distanceSensors leftDisSensor;
    private distanceSensors rightDisSensor;
    private goBuildAServo2000 craterInServo;



    @Override
    public void runOpMode() {

        teamUtil.theOpMode = this;
        // Create and initialize the Mineral Detector
        // This code should appear at the start of the opMode before initializing other
        // hardware classes otherwise we are getting wierd USB bus errors...
        teamUtil.log("creating Detector");
        detector = new mineralDetector(telemetry, hardwareMap);
        teamUtil.log("Initializing Detector");
        detector.initialize(hardwareMap.get(WebcamName.class, "Webcam 1"));
        telemetry.addData("Status", "Detector Initialized");

        //initialization code
        teamUtil.log("initializing Robot...");
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

        leftDisSensor = new distanceSensors(telemetry, hardwareMap.get(Rev2mDistanceSensor.class, "leftFront2M"));
        rightDisSensor = new distanceSensors(telemetry, hardwareMap.get(Rev2mDistanceSensor.class, "rightFront2M"));


        grabber.autoInitialize();
        sweeper.retract();
        craterInServo = new goBuildAServo2000(hardwareMap.get(Servo.class, "craterInServo"), telemetry);

        teamUtil.log("Initialized, Waiting for Start...");
        telemetry.addData("Status", "AUTO DEPOT: Ready to Ruckus!");
        telemetry.update();

        waitForStart();

        teamUtil.log("La.lowerRobot()...");
        basicMove.motorsOn(0.2);
        int cubePosition = La.extendDetect(detector);
        basicMove.motorsOff();
        telemetry.addData("cubePosition:", cubePosition);
        telemetry.update();
        teamUtil.log("CubePosition: " + cubePosition);

        //sleep(6000);

        if (cubePosition == 2 || cubePosition == 0) {
            teamUtil.log("calibrating");
            leftColor.calibrate();
            rightColor.calibrate();
            teamUtil.log("Unlatching");
            basicMove.rightSpin(0.5, 10);
            basicMove.moveInches(0.3, 6);
            basicMove.leftSpin(0.5, 10);
            La.retractFullyNoWait();
            //basicMove.moveInches(0.5, 45);
            //grabber.grabberDown();
            //sleep(1000);
            //grabber.wideOpen();
            //sleep(500);
            //grabber.holdUp();
            //grabber.skinnyOpen();
            //basicMove.rightSpin(0.3, 45);
            //basicMove.moveInches(-1, -58);
            //basicMove.moveInches(-0.3, -5);

            //new code
            basicMove.moveInches(1, 45);
            basicMove.moveInches(0.3, 5);
            basicMove.moveInches(-0.3, -5);
            grabber.grabberDown();
            sleep(1000);
            grabber.wideOpen();
            sleep(500);
            grabber.up();
            basicMove.moveInches(-1, -30);
            basicMove.motorsOn(-0.3);
            while (!leftColor.isOnTape()) {

            }
            basicMove.motorsOff();
            teamUtil.sleep(500);
            basicMove.motorsOn(0.3);
            while (!leftColor.isOnTape()) {

            }
            basicMove.motorsOff();
            //basicMove.moveInches(-1, -34);
            //basicMove.moveInches(.5,5);

            basicMove.leftSpin(0.3, 88);
            basicMove.moveInches(1, 50);
            grabber.extend();
            basicMove.moveInches(0.3, 10);
        } else if (cubePosition == 1) {
            teamUtil.log("calibrating");
            leftColor.calibrate();
            rightColor.calibrate();
            teamUtil.log("Unlatching");
            basicMove.rightSpin(0.5, 10);
            basicMove.moveInches(0.3, 6);
            basicMove.leftSpin(0.5, 10);
            La.retractFullyNoWait();

            basicMove.decelLeftSpin(0.5, 45);
            basicMove.moveInches(1, 30);
            basicMove.motorsOn(0.3);
            while (leftDisSensor.getDistance()>5) {

            }
            basicMove.motorsOff();
            basicMove.moveInches(-0.3, -3);
            basicMove.decelRightSpin(0.5, 90);
            basicMove.moveInches(0.5,40);
            grabber.grabberDown();
            sleep(1000);
            grabber.wideOpen();
            sleep(500);
            grabber.up();
            basicMove.moveInches(-1, -45);
            basicMove.decelRightSpin(0.5, 15);
            basicMove.moveInches(-0.5, -10);
            craterInServo.goTo(215);

        } else if (cubePosition == 3) {
            teamUtil.log("calibrating");
            leftColor.calibrate();
            rightColor.calibrate();
            teamUtil.log("Unlatching");
            basicMove.rightSpin(0.5, 10);
            basicMove.moveInches(0.3, 6);
            La.retractFullyNoWait();

            basicMove.decelRightSpin(0.5, 30);
            basicMove.moveInches(1, 30);
            basicMove.motorsOn(0.3);
            while (leftDisSensor.getDistance()>5) {

            }
            basicMove.motorsOff();
            basicMove.moveInches(-0.5, -12);
            basicMove.decelLeftSpin(0.5, 83);
            basicMove.moveInches(0.7,35);
            grabber.grabberDown();
            sleep(1000);
            grabber.wideOpen();
            sleep(500);
            grabber.up();
            basicMove.moveInches(-0.3, -10);
            basicMove.decelLeftSpin(0.5, 45);
            basicMove.moveInches(0.7, 25);
            basicMove.decelLeftSpin(0.5, 40);
            basicMove.moveInches(1, 43);
            grabber.extend();
            basicMove.moveInches(0.3, 10);

        }

    }

}