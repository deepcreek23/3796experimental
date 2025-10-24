package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class moveCharlieControls extends OpMode{
    private DcMotor frontRight; // This one is at port 0, as of 10/18/2025 at 8:13 PM
    private DcMotor backRight;  // This one is at port 1 as of 10/18/2025 at 8:13 PM
    private DcMotor frontLeft;  // This one is at port 2 as of 10/18/2025 at 8:13 PM
    private DcMotor backLeft;   // This one is at port 3 as of 10/18/2025 at 8:13 PM
    int mode = 0;

    @Override
    public void init() {
        frontRight = hardwareMap.get(DcMotor.class, "port0");
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight = hardwareMap.get(DcMotor.class, "port1");
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft = hardwareMap.get(DcMotor.class, "port2");
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft = hardwareMap.get(DcMotor.class, "port3");
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    @Override
    public void loop(){
        if(this.gamepad1.dpad_up){
            mode += 1;
        } else if (this.gamepad1.dpad_down) {
            mode -= 1;
        }
        double forward =-this.gamepad1.left_stick_y;
        double right = this.gamepad1.left_stick_x;
        double turna = this.gamepad1.right_stick_x;
        double turnb = this.gamepad1.right_stick_x;
        double turnc = this.gamepad1.right_stick_x;
        double turnd = this.gamepad1.right_stick_x;
        if(mode == 0){
            turna = 0;
            turnb = 0;
            turnc = 0;
            turnd = 0;
        } else if (mode == 1) {
            telemetry.addData("Stupid", mode);
        } else if (mode == 2) {
            turna = 0;
            turnc = 0;
        } else if (mode == 3) {
            turnc = 0;
            turnd = 0;
        }


        frontRight.setPower(forward - right - turna);
        frontLeft.setPower(forward + right + turnb);
        backRight.setPower(forward + right - turnc);
        backLeft.setPower(forward - right + turnd);

        if(forward == 0){
            telemetry.addData("Forward", -forward);
        }else{
            telemetry.addData("Forward", forward);
        }
        telemetry.addData("Right", right);
        telemetry.addData("Turn", mode);

    }

}
