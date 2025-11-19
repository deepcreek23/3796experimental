package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp
public class finalDriveClass extends OpMode {
    private DcMotor intake;
    private DcMotor turn;
    private DcMotor shoot;
    double mult;

    @Override
    public void init() {
        intake = hardwareMap.get(DcMotor.class, "port1");
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        turn = hardwareMap.get(DcMotor.class, "port2");
        turn.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        turn.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shoot = hardwareMap.get(DcMotor.class, "port0");
        shoot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shoot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        double velocity = mult*(gamepad1.right_trigger - gamepad1.left_trigger);
        double velo2 = mult*(gamepad1.left_stick_x);
        double velo3 = mult*(gamepad1.right_stick_x);
        intake.setPower(velo3);
        turn.setPower(-velo2 / 2);
        shoot.setPower(velocity);
        telemetry.addData("Multiplier", mult);
        telemetry.addData("PEW PEW", velocity);

        if(gamepad1.dpadDownWasReleased()){
            mult = mult - 0.05;
        } else if (gamepad1.dpadUpWasReleased()) {
            mult = mult +0.05;
        }
    }
}
