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

    public mineralDetector (Telemetry t, HardwareMap map) {
        telemetry = t;
        hardwareMap = map;
    }

    public void initialize (WebcamName camName) {
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia(camName);

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();

    }

    public void startTracking () {
        if (teamUtil.theOpMode.opModeIsActive()) {
            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }
        }
    }

    public void stopTracking () {
            if (tfod != null) {
                tfod.shutdown();
            }
    }

    public int detect() {

        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                teamUtil.log("# Minerals Detected: " + updatedRecognitions.size());
                // make sure we have exactly two
                if (updatedRecognitions.size() == 2) {
                    Recognition firstObject = updatedRecognitions.get(0);
                    Recognition secondObject = updatedRecognitions.get(1);
                    int firstX = -1;
                    int secondX = -1;
                    String firstLabel = firstObject.getLabel();
                    String secondLabel = secondObject.getLabel();


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
        tfodParameters.minimumConfidence = 0.90; // set the minimum confidence level for detection very high
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}
