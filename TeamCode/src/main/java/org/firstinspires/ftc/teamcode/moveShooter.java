package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class moveShooter extends OpMode{
    private DcMotor frontRight; // This one is at port 0, as of 10/18/2025 at 8:13 PM
    private DcMotor backRight;  // This one is at port 1 as of 10/18/2025 at 8:13 PM
    private DcMotor frontLeft;  // This one is at port 2 as of 10/18/2025 at 8:13 PM
    private DcMotor backLeft;   // This one is at port 3 as of 10/18/2025 at 8:13 PM
    private DcMotor shooter;  // Port 0 on expansion hub i think? i dunno im to lazy to check

    @Override
    public void init() {
        frontRight = hardwareMap.get(DcMotor.class, "port0");
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight = hardwareMap.get(DcMotor.class, "port1");
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft = hardwareMap.get(DcMotor.class, "port2");
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft = hardwareMap.get(DcMotor.class, "port3");
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shooter = hardwareMap.get(DcMotor.class, "eHub0");
        shooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }
    @Override
    public void loop(){
        double forward =-this.gamepad1.left_stick_y;
        double right = this.gamepad1.left_stick_x;
        double turn = this.gamepad1.right_stick_x;
        double velocity = gamepad1.right_trigger - gamepad1.left_trigger;

        frontRight.setPower(forward - right - turn);
        frontLeft.setPower(forward + right + turn);

        //WHY WONT IT COMMIT
        backRight.setPower(forward + right - turn);
        backLeft.setPower(forward - right + turn);
        shooter.setPower(velocity);
        if(forward == 0){
            telemetry.addData("Forward", -forward);
        }else{
            telemetry.addData("Forward", forward);
        }
        telemetry.addData("Right", right);
        telemetry.addData("Turn", turn);
        telemetry.addData("PEW PEW MOTHAS", velocity);


    }

}
