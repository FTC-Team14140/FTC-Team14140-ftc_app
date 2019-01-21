package org.firstinspires.ftc.teamcode.teamLibs;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

public class mineralDetector {
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private static final String VUFORIA_KEY = "AS4C9AT/////AAABmWPn8BKhgkppteJIWeu23pd3sH+HzHIII5D39Gs8ElMdsVxuXflUZtrLJFi2vf1DJwhAJ0HBGSK0ZWTrg5jiVxe3UKqBkMuqxK1oJvDBfuepZaVgv8/PSHkWZ/5e5wyet+sGou6WAcYdZelyRmyKvjnH37mwiomXiXORJZZ0TJXTnAlTbWxD50rnHyssTSk+NtV538b2FP1wTeuFVZigD2Yj7yDjkEqES2tgmFfstLmXMFoBz0fxsgmhZ1pesVcihAFG8h4uskXtOVZP0Kf1QseqwgIWtrSMyRAYlnDC9kbTwYMU9I2mXRQV+VOe2Bl2cRxOY8Zavm8D9FDcLtD6JW83/u5vXMTPN6cEukC5hiYc";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    private Telemetry telemetry;
    private HardwareMap hardwareMap;
    private int[] Voting = new int[4];

    public mineralDetector (Telemetry t, HardwareMap map) {
        telemetry = t;
        hardwareMap = map;
    }

    public void resetVote() {
        for (int i = 0; i < 4; i++ ){
            Voting [i] = 0;
        }
    }

    public int getVote() {
        if (Voting[0] > Voting[1] && Voting[0] > Voting[2] && Voting[0] > Voting[3]) {
            return (0);
        } else if (Voting[1] > Voting[0] && Voting[1] > Voting[2] && Voting[1] > Voting[3]) {
            return (1);
        } else if (Voting[2] > Voting[0] && Voting[2] > Voting[1] && Voting[2] > Voting[3]) {
            return (2);
        } else return (3);
    }

    public void initialize (WebcamName camName) {
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        teamUtil.log("Initializing Vuforia");
        initVuforia(camName);

        teamUtil.log("Initializing TFOD");
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }
    }

    public void startTracking () {
        teamUtil.log("Trying to Start Tracking");
        if (teamUtil.theOpMode.opModeIsActive()) {
            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                teamUtil.log("Calling activate() on tfod");
                tfod.activate();
            }
        }
    }

    public void stopTracking () {
            if (tfod != null) {
                teamUtil.log("Calling shutdown() on tfod");
                tfod.shutdown();
            }
    }

    public int detect() {
    final int THRESHOLD = 200;
        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            teamUtil.log("Detecting ");
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                teamUtil.log(getRecognizedObjectsString(updatedRecognitions));
                // make sure we have exactly two
                if (updatedRecognitions.size() > 2) {
                    Recognition firstObject = null;
                    Recognition secondObject = null;
                    for (Recognition recognition : updatedRecognitions) {
                        if (recognition.getBottom() > THRESHOLD) {
                            if (firstObject == null) {
                                firstObject = recognition;
                            }else if (secondObject == null){
                                secondObject = recognition;
                            }else {
                                return (0);
                            }
                        }
                    }
                    if (firstObject == null || secondObject == null) {
                        return  (0);
                    }
                    String firstLabel = firstObject.getLabel();
                    String secondLabel = secondObject.getLabel();
                    String logString = "";
                    logString += " | ";
                    logString += firstObject.getLabel();
                    logString += ((int) (firstObject.getConfidence() * 100)) + ",";
                    logString += ((int) firstObject.getBottom());
                    logString += " | ";
                    logString += secondObject.getLabel();
                    logString += ((int) (secondObject.getConfidence() * 100)) + ",";
                    logString += ((int) secondObject.getBottom());
                    teamUtil.log(logString);
                    if ((firstLabel == LABEL_SILVER_MINERAL) && (secondLabel == LABEL_SILVER_MINERAL)) {
                        // gold is far left
                        return(1);
                    } else if (firstLabel == LABEL_GOLD_MINERAL) {
                        if (firstObject.getLeft() < secondObject.getLeft()) {
                            // gold is in the middle
                            return(2);
                        } else {
                            // gold is on the far right
                            return(3);
                        }
                    } else {
                        if (firstObject.getLeft() > secondObject.getLeft()) {
                            // gold is in the middle
                            return(2);
                        } else  {
                            // gold is on the far right
                            return(3);
                        }
                    }
                }
            }
        }
        return (0);
    }

    public void detectVote() {
        Voting[detect()] += 1;
    }


    // format a string for the log showing what we have detected
    private String getRecognizedObjectsString (List<Recognition> theList) {
        String logString = "";
        logString += "#: " + theList.size();
        for (Recognition recognition : theList) {
            logString += " | ";
            if (recognition.getLabel() == LABEL_SILVER_MINERAL) {
                logString += "S,";
            } else {
                logString += "G,";
            }
            logString += ((int) (recognition.getConfidence() * 100)) + ",";
            logString += ((int) recognition.getBottom());
        }
        return logString;
    }
    /*
        /**
         * Initialize the Vuforia localization engine.
         */
    private void initVuforia(WebcamName camName) {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = camName;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.60; // set the minimum confidence level for detection
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}
