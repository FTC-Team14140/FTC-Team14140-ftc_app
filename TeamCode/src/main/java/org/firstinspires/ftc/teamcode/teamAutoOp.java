package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.teamLibs.teamColorSensor;
import org.firstinspires.ftc.teamcode.teamLibs.basicMovement;
import org.firstinspires.ftc.teamcode.teamLibs.linearActuator;
import org.firstinspires.ftc.teamcode.teamLibs.grabberArm;
import org.firstinspires.ftc.teamcode.teamLibs.teamUtil;
import com.qualcomm.robotcore.hardware.ColorSensor;

@Autonomous(name="Auto Depot", group="Linear Opmode")
public class teamAutoOp extends LinearOpMode {
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
        basicMove.moveInches(0.3,5);
        basicMove.moveInches(-0.3, -5);
        grabber.grabberDown();
        sleep(1000);
        grabber.wideOpen();
        sleep(500);
        grabber.holdUp();
        basicMove.moveInches(-1,-30);
        basicMove.motorsOn(-0.3);
        while (!leftColor.isOnTape()){

        }
        basicMove.motorsOff();
        teamUtil.sleep(500);
        basicMove.motorsOn(0.3);
        while (!leftColor.isOnTape()){

        }
        basicMove.motorsOff();
        //basicMove.moveInches(-1, -34);
        //basicMove.moveInches(.5,5);

        basicMove.leftSpin(0.3, 90);
        basicMove.moveInches(1,50);
        grabber.extend();
        basicMove.moveInches(0.3,10);


    }

}
