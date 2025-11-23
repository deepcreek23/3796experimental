package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name="MotorCRServoTest", group="Test")
public class MotorServoTest extends LinearOpMode {

    private DcMotor motor;
    private CRServo crServo;

    @Override
    public void runOpMode() {

        // Hardware mapping
        motor = hardwareMap.get(DcMotor.class, "port0 ");
        crServo = hardwareMap.get(CRServo.class, "crServo");

        // Optional: reverse directions if needed
        // motor.setDirection(DcMotor.Direction.REVERSE);
        // crServo.setDirection(CRServo.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {

            // Motor control: left stick Y (invert so up is positive)
            double power = -gamepad1.left_stick_y;
            motor.setPower(power);

            // Continuous Servo Control
            if (gamepad1.a) {
                crServo.setPower(1.0);   // full forward
            }
            else if (gamepad1.b) {
                crServo.setPower(-1.0);  // full reverse
            }
            else {
                crServo.setPower(0.0);   // stop when no button pressed
            }

            telemetry.addData("Motor Power", power);
            telemetry.addData("CR Servo Power", crServo.getPower());
            telemetry.update();
        }
    }
}
