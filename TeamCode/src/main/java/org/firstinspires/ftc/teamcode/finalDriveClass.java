package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class finalDriveClass extends OpMode {

    // Mechanism motors
    private DcMotor intake;     // Pulls artifacts in
    private DcMotor turn;       // Aims the launcher slowly/precisely
    private DcMotor shoot;      // Flywheel shooter

    // Drivetrain motors
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor frontLeft;
    private DcMotor backLeft;

    // Driver scaling multiplier (0–1)
    double mult = 1.0;

    // Mechanism control variables
    double intakePower = 0;
    double turnPower = 0;

    // --- Rumble control ---
    boolean hasRumbled = false;     // prevents repeated rumbling
    long rumbleCooldownEnd = 0;     // millis timestamp

    @Override
    public void init() {

        // ---- Mechanism Motors ----
        intake = hardwareMap.get(DcMotor.class, "port1");
        setupMotor(intake);

        turn = hardwareMap.get(DcMotor.class, "port2");
        setupMotor(turn);

        shoot = hardwareMap.get(DcMotor.class, "port0");
        shoot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shoot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // ---- Drivetrain Motors ----
        frontRight = hardwareMap.get(DcMotor.class, "eHub0");
        setupMotor(frontRight);

        backRight = hardwareMap.get(DcMotor.class, "eHub1");
        setupMotor(backRight);

        frontLeft = hardwareMap.get(DcMotor.class, "eHub2");
        setupMotor(frontLeft);

        backLeft = hardwareMap.get(DcMotor.class, "eHub3");
        setupMotor(backLeft);

        telemetry.addLine("Robot Initialized");
    }

    /** Helper method to apply common motor settings */
    private void setupMotor(DcMotor m) {
        m.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        m.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {

        // ==========================================================
        //                     SHOOTER CONTROL + RUMBLE
        // ==========================================================

        // Shooter controlled by right trigger
        double shootVelocity = mult * gamepad1.right_trigger;
        shoot.setPower(shootVelocity);

        boolean shooterAtSpeed = shootVelocity > 0.8; // 80% spin
        long now = System.currentTimeMillis();

        // Conditions for rumble:
        // 1. Shooter spinning fast
        // 2. Trigger pressed hard
        // 3. Haven't rumbled yet OR cooldown expired
        if (shooterAtSpeed && gamepad1.right_trigger > 0.8 && (!hasRumbled || now > rumbleCooldownEnd)) {

            // Fire a strong, short rumble (ready-to-shoot feedback)
            gamepad1.rumble(1, 1, 500);

            // Record rumble event state
            hasRumbled = true;
            rumbleCooldownEnd = now + 1000; // 1 second cooldown
        }

        // Reset rumble flag when trigger is released
        if (gamepad1.right_trigger < 0.2) {
            hasRumbled = false;
        }


        // ==========================================================
        //                     INTAKE CONTROL
        // ==========================================================

        if (gamepad1.x) {
            intakePower = mult;
        } else if (gamepad1.y) {
            intakePower = -mult;
        } else {
            intakePower = 0;
        }
        intake.setPower(intakePower);


        // ==========================================================
        //                    TURN MOTOR CONTROL
        // ==========================================================

        if (gamepad1.a) {
            turnPower = mult * 0.5;  // slow precise aiming
        } else if (gamepad1.b) {
            turnPower = -mult * 0.5;
        } else {
            turnPower = 0;
        }
        turn.setPower(turnPower);


        // ==========================================================
        //                DRIVER SPEED MULTIPLIER
        // ==========================================================

        if (gamepad1.dpadUpWasReleased()) {
            mult += 0.05;
        } else if (gamepad1.dpadDownWasReleased()) {
            mult -= 0.05;
        }
        // clamp 0 to 1
        mult = Math.max(0, Math.min(mult, 1));


        // ==========================================================
        //                        DRIVETRAIN
        // ==========================================================

        double forward = -gamepad1.left_stick_y;
        double strafe  =  gamepad1.left_stick_x;
        double rotate  =  gamepad1.right_stick_x;

        double fr = forward - strafe - rotate;
        double fl = forward + strafe + rotate;
        double br = forward + strafe - rotate;
        double bl = forward - strafe + rotate;

        frontRight.setPower(fr * mult);
        frontLeft.setPower(fl * mult);
        backRight.setPower(br * mult);
        backLeft.setPower(bl * mult);


        // ==========================================================
        //                         TELEMETRY
        // ==========================================================
        telemetry.addData("Multiplier", mult);
        telemetry.addData("Shooter Power", shootVelocity);
        telemetry.addData("Intake Power", intakePower);
        telemetry.addData("Turn Power", turnPower);
        telemetry.addData("Forward", forward);
        telemetry.addData("Strafe", strafe);
        telemetry.addData("Rotate", rotate);
        telemetry.addData("Rumble Ready?", shooterAtSpeed);
    }
}

