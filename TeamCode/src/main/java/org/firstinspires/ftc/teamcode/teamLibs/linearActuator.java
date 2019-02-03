package org.firstinspires.ftc.teamcode.teamLibs;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class linearActuator {

    private DcMotor motor;
    private Telemetry telemetry;
    private boolean isBusy = false;
    private final int GROUND_POSITION=25800;//This is the position on the floor on the robot attached to the hook
    private final int END_POSITION=GROUND_POSITION / 2;//The end of the match on top of the latch
    private final int RETRACT_ACTUATOR=-1;//This power retracts the actuator
    private final int EXTEND_ACTUATOR=1;//This power extends the actuator
    private final int MOTOR_OFF=0;//This power stops the motor
    private final int RETRACT_WHILE_MOVING_POSITION=500;





    public linearActuator(Telemetry thetelemetry, DcMotor motor1) {
        telemetry = thetelemetry;
        motor = motor1;
        //motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    public void extend () {
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);//Get out of encoder mode so we can run the motor and detect a stall detection
        motor.setPower (EXTEND_ACTUATOR);
    }

    public void retract () {
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);//Get out of encoder mode so we can run the motor and detect a stall detection
        motor.setPower (RETRACT_ACTUATOR);
    }

    public void stopMotor () {
        if(isBusy==false) {
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);//Get out of encoder mode so we can run the motor and detect a stall detection
            motor.setPower(MOTOR_OFF);
        }
    }

    public void lowerRobot() {

        retractFully();
        extendActuater(EXTEND_ACTUATOR, GROUND_POSITION);//Set the power so it goes down and takes the ground position to get down

    }
    public void retractMoving(){
        motor.setPower(RETRACT_ACTUATOR);
        motor.setTargetPosition(RETRACT_WHILE_MOVING_POSITION);

    }

    // This method runs the retractFully method in a separate thread and returns control to the caller
    public void retractFullyNoWait () {
        if (isBusy == false) {
            isBusy = true;
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    retractFully();
                }
            });
            t1.start();
        }
    }

    // This method runs the extendFully method in a separate thread and returns control to the caller
    public void extendFullyNoWait () {
        if (isBusy == false) {
            isBusy = true;
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    extendFully();
                }
            });
            t1.start();
        }
    }

    public void retractFully() {
        int lastPosition;//Creates last position

        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);//Get out of encoder mode so we can run the motor and detect a stall detection

        motor.setPower(RETRACT_ACTUATOR);//Set's motor power to -1
        lastPosition = motor.getCurrentPosition();//sets last position to the current encoder position of the robot
        teamUtil.sleep(100);//wait's 100ms so the motor has time to run a little
        while (teamUtil.theOpMode.opModeIsActive() && (lastPosition != motor.getCurrentPosition())) {
            lastPosition=motor.getCurrentPosition();//This while loop makes it so if the positon of the motor is the same
            teamUtil.sleep(100);           //after 100ms than we know it hit the bottom and stalled
        }
        motor.setPower(0);//Then turn the motor off to power 0

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);//Reset the encoder on the motor (fully retracted is our zero position)

        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION); // put the motor back in encoder mode
    isBusy = false;
}
     public void extendActuatorWhileMoving()

    {
        //This set the power of the robot and then you can up in the postiton for the motor to go
        motor.setPower(EXTEND_ACTUATOR);
        motor.setTargetPosition(GROUND_POSITION);
    }

    public void lift() {
        //lift's the liner actuater
        retractActuator(RETRACT_ACTUATOR,END_POSITION);//Sets power to -1 and will go to the top position
        telemetry.addData("actuaterPosition", motor.getCurrentPosition());//Gets the position of the motor
        //The lowest point on the robot has to be over 4in above the ground of the mat
    }



    private void extendActuater(double power, int encoderPosition)  {

        motor.setPower(power);
        motor.setTargetPosition(encoderPosition);
        while (motor.isBusy()) {

        }
        motor.setPower(0);
    }
      private void retractActuator(double power, int encoderPosition)  {

        motor.setPower(power);
        motor.setTargetPosition(encoderPosition);
        while (motor.isBusy()) {

        }
        motor.setPower(0);
    }
    public void extendFully() {
        int lastPosition;//Creates last position

        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);//Get out of encoder mode so we can run the motor and detect a stall detection

        motor.setPower(EXTEND_ACTUATOR);//Set's motor power to -1
        lastPosition = motor.getCurrentPosition();//sets last position to the current encoder position of the robot
        teamUtil.sleep(100);//wait's 100ms so the motor has time to run a little
        while (teamUtil.theOpMode.opModeIsActive()&& (lastPosition != motor.getCurrentPosition())) {
            lastPosition=motor.getCurrentPosition();//This while loop makes it so if the positon of the motor is the same
            teamUtil.sleep(100);           //after 100ms than we know it hit the bottom and stalled
        }
        motor.setPower(0);//Then turn the motor off to power 0

        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION); // put the motor back in encoder mode
        isBusy = false;
    }
    public int extendDetect(mineralDetector detector) {
        int lastPosition;//Creates last position
        detector.resetVote();
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);//Get out of encoder mode so we can run the motor and detect a stall detection

        motor.setPower(EXTEND_ACTUATOR);//Set's motor power to -1
        lastPosition = motor.getCurrentPosition();//sets last position to the current encoder position of the robot
        teamUtil.sleep(100);//wait's 100ms so the motor has time to run a little
        detector.startTracking();
        while (teamUtil.theOpMode.opModeIsActive()&& (lastPosition != motor.getCurrentPosition())) {
            detector.detectVote();
            lastPosition=motor.getCurrentPosition();//This while loop makes it so if the positon of the motor is the same
            teamUtil.sleep(100);           //after 100ms than we know it hit the bottom and stalled
        }
        motor.setPower(0);//Then turn the motor off to power 0
        detector.stopTracking();
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION); // put the motor back in encoder mode
        isBusy = false;
        return (detector.getVote());
    }

    public void stop () {
        //stop's the liner actuater
        motor.setPower(0);//Sets the motor power to 0
        telemetry.addData ( "actuaterPosition", motor.getCurrentPosition());
        //Gets the psotiton of the motor encoder





    }





}








