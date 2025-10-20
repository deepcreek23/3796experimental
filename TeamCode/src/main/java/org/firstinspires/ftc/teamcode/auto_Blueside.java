package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


@Autonomous
public class auto_Blueside extends OpMode {

    private DcMotor frontRight; // This one is at port 0, as of 10/18/2025 at 8:13 PM
    private DcMotor backRight;  // This one is at port 1 as of 10/18/2025 at 8:13 PM
    private DcMotor frontLeft;  // This one is at port 2 as of 10/18/2025 at 8:13 PM
    private DcMotor backLeft;   // This one is at port 3 as of 10/18/2025 at 8:13 PM
    private DcMotor turner;  // Port 0 on expansion hub i think? i dunno im to lazy to check

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
        turner = hardwareMap.get(DcMotor.class, "rot");
        turner.setTargetPosition(0);
        turner.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        turner.setPower(.5);
    }

    @Override
    public void loop() {

    }
}
