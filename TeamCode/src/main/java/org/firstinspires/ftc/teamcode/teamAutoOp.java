package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.teamLibs.linearActuater;

@TeleOp(name="Team Auto", group="Linear Opmode")
public class teamAutoOp extends LinearOpMode {
    private linearActuater La;


    @Override
    public void runOpMode() {
        //initialization code

        // Wait for the game to start of the game(drver presses PLAY)
        waitForStart();
        La.lowerRobot();

    }
    public void runOpmode() {
        waitForStart();
        La.retractFully();
    }
}
