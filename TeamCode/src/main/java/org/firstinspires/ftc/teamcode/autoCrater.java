package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.teamLibs.basicMovement;
import org.firstinspires.ftc.teamcode.teamLibs.grabberArm;
import org.firstinspires.ftc.teamcode.teamLibs.linearActuator;
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

        @Override
        public void runOpMode() {

            teamUtil.theOpMode = this;
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

            teamUtil.log("Initialized, Waiting for Start...");

            grabber.autoInitialize();

            waitForStart();

            teamUtil.log("La.lowerRobot()...");
            basicMove.motorsOn(0.2);
            La.extendFully();
            basicMove.motorsOff();
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

        }

    }


