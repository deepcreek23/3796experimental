package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class flywheel extends OpMode{
    private DcMotor shooter;  // Port 0 on expansion hub i think? i dunno im to lazy to check
    double mult = 0;

    @Override
    public void init() {
        shooter = hardwareMap.get(DcMotor.class, "port0");
        shooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }
    @Override
    public void loop(){
        double forward =-this.gamepad1.left_stick_y;
        double right = this.gamepad1.left_stick_x;
        double brake = gamepad1.left_trigger;
        double brake2 = 0;
        if(brake >= 0){
            brake2 = 0.1;
        }
        double velocity = mult*(gamepad1.right_trigger - brake2);

        shooter.setPower(velocity);
        if(forward == 0){
            telemetry.addData("Forward", -forward);
        }else{
            telemetry.addData("Forward", forward);
        }
        telemetry.addData("Right", right);
        telemetry.addData("Multiplier", mult);
        telemetry.addData("PEW PEW", velocity);

        if(gamepad1.dpadDownWasReleased()){
            mult = mult - 0.05;
        } else if (gamepad1.dpadUpWasReleased()) {
            mult += 0.05;
        }



    }

}
