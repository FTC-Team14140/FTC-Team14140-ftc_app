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
import org.firstinspires.ftc.teamcode.teamLibs.linearActuator;
import org.firstinspires.ftc.teamcode.teamLibs.teamUtil;

@Autonomous(name="Team Auto", group="Linear Opmode")
public class teamAutoOp extends LinearOpMode {
    private linearActuator La;
    private basicMovement basicMove;

    @Override
    public void runOpMode() {

        //initialization code
        teamUtil.log("initializing Robot...");
        teamUtil.log("initializing linearActuator...");
        La = new linearActuator(telemetry, hardwareMap.get(DcMotor.class, "LAMotor"));
        teamUtil.log("initializing basicMovement...");
        basicMove = new basicMovement(hardwareMap.get(DcMotor.class, "motorLeft"), hardwareMap.get(DcMotor.class, "motorRight"), hardwareMap.get(BNO055IMU.class,"imu"), telemetry);
        // Wait for the game to start of the game(drver presses PLAY)
        teamUtil.log("Initialized, Waiting for Start...");

        waitForStart();

        teamUtil.log("La.lowerRobot()...");
        //La.lowerRobot();

        teamUtil.log("Unlatching");
        basicMove.rightSpin(0.3, 30);
        basicMove.moveInches(0.3, 3);
        basicMove.leftSpin(0.3, 30);

    }

}
