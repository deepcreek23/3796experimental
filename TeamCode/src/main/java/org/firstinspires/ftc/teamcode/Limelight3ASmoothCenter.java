package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class Limelight3ASmoothCenter extends LinearOpMode {

    private DcMotor turnMotor;
    private Limelight3A limelight;

    // Proportional control constants
    private static final double KP = 0.01;      // reduced gain for smooth movement
    private static final double MIN_POWER = 0.03;
    private static final double MAX_POWER = 0.1;
    private static final double DEADBAND = 5.0;  // degrees

    // Smoothing factor for power
    private static final double SMOOTHING = 0.3;

    @Override
    public void runOpMode() {
        // Map hardware
        
        turnMotor = hardwareMap.get(DcMotor.class, "turnMotor");
        turnMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        turnMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(10); // slower polling for smoother control
        limelight.pipelineSwitch(0); // use AprilTag pipeline
        limelight.start();

        telemetry.addLine("Limelight3A initialized — waiting for start");
        telemetry.update();

        waitForStart();

        double previousPower = 0;

        while (opModeIsActive()) {
            LLResult result = limelight.getLatestResult();

            if (result != null && result.isValid()) {
                double tx = result.getTx(); // horizontal offset
                double error = tx;

                double power;

                // Deadband: do nothing if the tag is almost centered
                if (Math.abs(error) < DEADBAND) {
                    power = 0;
                } else {
                    // proportional control
                    power = KP * error;

                    // apply minimum power threshold
                    if (Math.abs(power) < MIN_POWER) {
                        power = Math.copySign(MIN_POWER, power);
                    }

                    // clamp to max power
                    power = Math.max(-MAX_POWER, Math.min(MAX_POWER, power));

                    // smooth power to reduce jerking
                    power = SMOOTHING * power + (1 - SMOOTHING) * previousPower;
                }

                previousPower = power;
                turnMotor.setPower(power);

                telemetry.addData("Target Visible", true);
                telemetry.addData("tx (degrees)", tx);
                telemetry.addData("Motor Power", power);

            } else {
                // no valid target
                turnMotor.setPower(0);
                telemetry.addData("Target Visible", false);
            }

            telemetry.update();
        }

        // Stop motor & Limelight
        turnMotor.setPower(0);
        limelight.stop();
    }
}
