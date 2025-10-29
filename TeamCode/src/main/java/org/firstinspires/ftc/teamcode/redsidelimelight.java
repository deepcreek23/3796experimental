package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class redsidelimelight extends OpMode {
    Limelight3A limelight;
    private DcMotor shooter;
    private DcMotor aim;

    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100); // This sets how often we ask Limelight for data (100 times per second)
        limelight.start(); // This tells Limelight to start looking!
        limelight.pipelineSwitch(0);

        shooter = hardwareMap.get(DcMotor.class, "shooter");
        shooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        aim = hardwareMap.get(DcMotor.class, "aim");
        aim.setMode((DcMotor.RunMode.RUN_WITHOUT_ENCODER));
        aim.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            double tx = result.getTx(); // How far up or down the target is (degrees)
            double ty = result.getTy(); // How far up or down the target is (degrees)
            double ta = result.getTa(); // How big the target looks (0%-100% of the image)

            telemetry.addData("Target X", tx);
            telemetry.addData("Target Y", ty);
            telemetry.addData("Target Area", ta);
            if(tx >= 0){
                while(tx >= 0.1 && tx <= 0.2) {
                    aim.setPower(0.1);
                }
            } else if (tx <= 0) {
                while((-0.2 <= tx && tx <= 0)) {
                    aim.setPower(-0.1);
                }
            }
        } else {
            telemetry.addData("Limelight", "No Targets");
        }
    }
}
