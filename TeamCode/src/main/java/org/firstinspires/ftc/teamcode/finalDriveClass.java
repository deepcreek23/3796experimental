package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp
public class finalDriveClass extends OpMode {
    private DcMotor intake;
    private DcMotor turn;
    private DcMotor shoot;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor frontLeft;
    private DcMotor backLeft;
    double mult;
    double velo2;
    double velo3;


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
        frontRight = hardwareMap.get(DcMotor.class, "eHub0");
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight = hardwareMap.get(DcMotor.class, "eHub1");
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft = hardwareMap.get(DcMotor.class, "eHub2");
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft = hardwareMap.get(DcMotor.class, "eHub3");
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }

    @Override
    public void loop() {
        double velocity = mult*(gamepad1.right_trigger - gamepad1.left_trigger);
        if(gamepad1.a){
            velo2 = mult;
        }else if(gamepad1.b){
            velo2 = -1 * mult;
        }
        if(gamepad1.x){
            velo3 = mult;
        } else if(gamepad1.y){
            velo3 = -1 * mult;
        }
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

        if(mult >= 1){
            mult = 1;
        } else if (mult <= 0){
            mult = 0;
        }
        double forward =-this.gamepad1.left_stick_y;
        double right = this.gamepad1.left_stick_x;
        double turn = this.gamepad1.right_stick_x;

        frontRight.setPower(mult *(forward - right - turn));
        frontLeft.setPower(mult * (forward + right + turn));
        backRight.setPower(mult * (forward + right - turn));
        backLeft.setPower(mult * (forward - right + turn));

        if(forward == 0){
            telemetry.addData("Forward", -forward);
        }else{
            telemetry.addData("Forward", forward);
        }
        telemetry.addData("Right", right);
        telemetry.addData("Turn", turn);
        telemetry.addData("Speed", mult);
    }
}
