package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.teamLibs.HiTecServo;
import org.firstinspires.ftc.teamcode.teamLibs.basicMovement;
import org.firstinspires.ftc.teamcode.teamLibs.teamUtil;

@TeleOp(name="ArmTest", group="Linear Opmode")
public class ArmTest extends LinearOpMode {
    private DcMotor BaseMotor;
    private DcMotor joint2Motor;
    private HiTecServo TurnTable;
    private Servo ttServo;
    private basicMovement basicMove;

    public void runOpMode() {
        teamUtil.log("Gettign motor 0 from hardware map");
        BaseMotor = hardwareMap.get(DcMotor.class, "motor0");
        teamUtil.log("Gettign motor 1 from hardware map");
        joint2Motor = hardwareMap.get(DcMotor.class, "motor1");
        basicMove = new basicMovement(BaseMotor, joint2Motor, hardwareMap.get(BNO055IMU.class,"imu"), telemetry);
        TurnTable = new HiTecServo(hardwareMap.get(Servo.class, "Servo0"), telemetry);
        telemetry.addData("Status", "Waiting for Start...");
        telemetry.update();

        TurnTable.goTo(0);

        waitForStart();

        if (gamepad2.right_bumper){
            BaseMotor.setPower(0.5);
        } else if (gamepad2.left_bumper){
            BaseMotor.setPower(-0.5);
        } else {
            BaseMotor.setPower(0);
        }

    }
}
