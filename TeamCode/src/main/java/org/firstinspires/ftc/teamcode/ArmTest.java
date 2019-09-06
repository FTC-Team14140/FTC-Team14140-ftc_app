package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.teamLibs.goBuildAServo2000;


import org.firstinspires.ftc.teamcode.teamLibs.HiTecServo;
import org.firstinspires.ftc.teamcode.teamLibs.basicMovement;
import org.firstinspires.ftc.teamcode.teamLibs.teamUtil;

@TeleOp(name="ArmTest", group="Linear Opmode")
public class ArmTest extends LinearOpMode {
    private DcMotor BaseMotor;
    private DcMotor joint2Motor;
    private HiTecServo TurnTable;
    private goBuildAServo2000 grabber;

    int MIN_ARM_DEGREES = 45;
    int MAX_ARM_DEGREES = 180;
    double ARM_DOWN = -.5;
    double  ARM_UP = .5;

    public void runOpMode() {
        teamUtil.theOpMode = this;

        teamUtil.log("Gettign motor 0 from hardware map");
        BaseMotor = hardwareMap.get(DcMotor.class, "motor0");
        teamUtil.log("Gettign motor 1 from hardware map");
        joint2Motor = hardwareMap.get(DcMotor.class, "motor1");
        grabber = new goBuildAServo2000(hardwareMap.get(Servo.class, "Servo1"), telemetry);
        TurnTable = new HiTecServo(hardwareMap.get(Servo.class, "Servo0"), telemetry);

        int baseMin=0, baseMax=0, armMin=0, armMax=0, turnMin=0, turnMax=0;

        int tDegrees = 75; // start at middle


        TurnTable.goTo(tDegrees);
        grabber.goTo(215);
        //initArm();

        telemetry.addData("Status", "Waiting for Start...");
        telemetry.update();
        waitForStart();

        // first we are going to have a skilled operator set the limits
        while (opModeIsActive() && ((baseMin == 0) || (baseMax == 0) || (armMin == 0) || (armMax ==0)) || (turnMin ==0) || (turnMax ==0)) {
            if (gamepad2.left_stick_y>0) {
                BaseMotor.setPower(0.5);
            } else if (gamepad2.left_stick_y <0) {
                BaseMotor.setPower(-0.5);
            } else {
                BaseMotor.setPower(0);
            }
            if (gamepad2.right_stick_y>0) {
                joint2Motor.setPower(1);
            } else if (gamepad2.right_stick_y <0) {
                joint2Motor.setPower(-1);
            } else {
                joint2Motor.setPower(0);
            }
            //alternative turn table
            if (gamepad2.left_stick_x>0) {
                tDegrees++;
                TurnTable.goTo(tDegrees);
            } else if (gamepad2.left_stick_x <0) {
                tDegrees--;
                TurnTable.goTo(tDegrees);
            }
            if (gamepad2.a) {
                baseMin = BaseMotor.getCurrentPosition();
            }
            if (gamepad2.b) {
                baseMax = BaseMotor.getCurrentPosition();
            }
            if (gamepad2.x) {
                armMin = joint2Motor.getCurrentPosition();
            }
            if (gamepad2.y) {
                armMax = joint2Motor.getCurrentPosition();
            }
            if (gamepad2.left_bumper) {
                turnMin = tDegrees;
            }
            if (gamepad2.right_bumper){
                turnMax = tDegrees;
            }
            telemetry.addLine("SET LIMITS!!!!");
            telemetry.addData("base", BaseMotor.getCurrentPosition());
            telemetry.addData("arm", joint2Motor.getCurrentPosition());
            telemetry.addData("turn", tDegrees);
            telemetry.addLine("Base Min:"+baseMin+" Max:"+ baseMax);
            telemetry.addLine("Arm Min:"+armMin+" Max:"+ armMax);
            telemetry.addLine("Turn Min:"+turnMin+" Max:"+ turnMax);
            telemetry.update();

            teamUtil.sleep(50);
        }
        while (opModeIsActive()) {

            // base motor
            if ((gamepad2.left_stick_y>.25) && (BaseMotor.getCurrentPosition() < baseMax)) {
                BaseMotor.setPower(0.5);
            } else if ((gamepad2.left_stick_y <-.25) && (BaseMotor.getCurrentPosition() > baseMin)) {
                BaseMotor.setPower(-0.5);
            } else {
                BaseMotor.setPower(0);
            }
            // arm motor
            if ((gamepad2.right_stick_y>.25) && (joint2Motor.getCurrentPosition() < armMax)) {
                joint2Motor.setPower(1);
            } else if ((gamepad2.right_stick_y <-.25) && (joint2Motor.getCurrentPosition() > armMin)) {
                joint2Motor.setPower(-1);
            } else {
                joint2Motor.setPower(0);
            }
            //alternative turn table
            if ((gamepad2.left_stick_x>.25) && (tDegrees < turnMax)) {
                tDegrees++;
                TurnTable.goTo(tDegrees);
            } else if ((gamepad2.left_stick_x <-.25) && (tDegrees > turnMin)) {
                tDegrees--;
                TurnTable.goTo(tDegrees);
            }

            //grabber Servo
            grabber.goTo((int) (215+(250-215)*gamepad2.right_trigger));

            teamUtil.sleep(50);


            telemetry.addLine("Running...");
            telemetry.addData("base", BaseMotor.getCurrentPosition());
            telemetry.addData("arm", joint2Motor.getCurrentPosition());
            telemetry.addData("tDegrees", tDegrees);
            telemetry.addLine("Base Min:"+baseMin+" Max:"+ baseMax);
            telemetry.addLine("Arm Min:"+armMin+" Max:"+ armMax);
            telemetry.addLine("Turn Min:"+turnMin+" Max:"+ turnMax);
            telemetry.update();

        }
    }

    int initArm() {
        int lastPosition;//Creates last position
        teamUtil.log("init arm");

        joint2Motor.setDirection(DcMotorSimple.Direction.REVERSE);
        joint2Motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        joint2Motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        joint2Motor.setPower(ARM_DOWN);
        teamUtil.log("stalling arm");
        lastPosition = joint2Motor.getCurrentPosition();//sets last position to the current encoder position of the robot
        teamUtil.sleep(100);//wait's 100ms so the motor has time to run a little
        while (teamUtil.theOpMode.opModeIsActive() && (lastPosition != joint2Motor.getCurrentPosition())) {
            lastPosition=joint2Motor.getCurrentPosition();//This while loop makes it so if the positon of the motor is the same
            teamUtil.sleep(100);           //after 100ms than we know it hit the bottom and stalled
        }
        joint2Motor.setPower(0);//Then turn the motor off to power 0
        teamUtil.log("arm stalled");

        teamUtil.log("reset encoder");
        joint2Motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);//Reset the encoder on the motor (fully down is our zero position)

        joint2Motor.setMode(DcMotor.RunMode.RUN_TO_POSITION); // put the motor back in encoder mode
        joint2Motor.setTargetPosition(0); // put the motor back in encoder mode
        joint2Motor.setPower(0);//Then turn the motor off to power 0

        return (joint2Motor.getCurrentPosition());
    }
    void armGoTo(int degrees){
        double targetPosition = (int)((degrees-45)/360.001*4000);
        teamUtil.log("set target position for arm to " + targetPosition);
        joint2Motor.setTargetPosition((int)(targetPosition));
    }
}
