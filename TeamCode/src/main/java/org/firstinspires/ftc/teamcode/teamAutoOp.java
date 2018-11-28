package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.coachcode.autoDriver;
import org.firstinspires.ftc.teamcode.teamLibs.basicMovement;
import org.firstinspires.ftc.teamcode.teamLibs.linearActuator;
import org.firstinspires.ftc.teamcode.teamLibs.grabberArm;
import org.firstinspires.ftc.teamcode.teamLibs.teamUtil;

@Autonomous(name="Auto Depot", group="Linear Opmode")
public class teamAutoOp extends LinearOpMode {
    private linearActuator La;
    private basicMovement basicMove;
    private grabberArm grabber;

    @Override
    public void runOpMode() {

        //initialization code
        teamUtil.log("initializing Robot...");
        teamUtil.log("initializing linearActuator...");
        La = new linearActuator(telemetry, hardwareMap.get(DcMotor.class, "LAMotor"));
        teamUtil.log("initializing grabberArm...");
        grabber = new grabberArm(telemetry, hardwareMap, "grabberServo", "liftServo");

        teamUtil.log("initializing basicMovement...");
        basicMove = new basicMovement(hardwareMap.get(DcMotor.class, "motorLeft"), hardwareMap.get(DcMotor.class, "motorRight"), hardwareMap.get(BNO055IMU.class,"imu"), telemetry);
        // Wait for the game to start of the game(drver presses PLAY)

        teamUtil.log("Initialized, Waiting for Start...");

        grabber.autoInitialize();

        waitForStart();

        teamUtil.log("La.lowerRobot()...");
        La.lowerRobot();

        teamUtil.log("Unlatching");
        basicMove.rightSpin(0.5, 10);
        basicMove.moveInches(0.3, 6);
        basicMove.leftSpin(0.5, 12);
        La.retractMoving();
        basicMove.moveInches(0.5, 45);
        grabber.grabberDown();
        sleep(1000);
        grabber.wideOpen();
        sleep(500);
        grabber.holdUp();
        grabber.skinnyOpen();
        basicMove.rightSpin(0.3, 45);
        basicMove.moveInches(-1, -58);
        basicMove.moveInches(-0.3, -5);

    }

}