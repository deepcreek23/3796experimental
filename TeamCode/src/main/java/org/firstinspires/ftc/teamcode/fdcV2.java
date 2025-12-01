package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class fdcV2 extends OpMode {
    // Innie thing and outie thing motors
    private DcMotor intake;     // Pulls artifacts in
    private DcMotor turn;       // Aims the launcher slowly/precisely
    private DcMotor shoot;      // Flywheel shooter
    // Drivetrain motors
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private Limelight3A limelight;
    double driveMult = 1;
    double inMult = 1;
    double fr = 0;
    double br = 0;
    double fl = 0;
    double bl = 0;
    static int leftBound = -350; //Units:Ticks
    static int rightBound = 350; //Units:Ticks
    static double motorPower = 0.7;
    static double TICKS_PER_REV = 2150.8; //19.2:1 Motor
    static double DEGREES_PER_TICK = 360 / TICKS_PER_REV; //19.2:1 Motor
    double outMult = 1;

    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        // ---- Mechanism Motors ----
        intake = hardwareMap.get(DcMotor.class, "motorIntake");
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        turn = hardwareMap.get(DcMotor.class, "motorTurn");
        turn.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        turn.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        shoot = hardwareMap.get(DcMotor.class, "motorShoot");
        shoot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shoot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // ---- Drivetrain Motors ----
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        // Start of drive section
        if(gamepad1.xWasPressed()){
            driveMult = driveMult - 0.1;
        } else if(gamepad1.bWasPressed()){
            driveMult = driveMult + 0.1;
        }
        double forward = (-gamepad1.left_stick_y * driveMult);
        double strafe = (gamepad1.left_stick_x * driveMult);
        double rotate = (gamepad1.right_stick_x * driveMult);
        fr = (forward - strafe - rotate);
        br = (forward + strafe - rotate);
        fl = -(forward + strafe + rotate);
        bl = -(forward - strafe + rotate);
        frontRight.setPower(fr);
        backRight.setPower(br);
        frontLeft.setPower(fl);
        backLeft.setPower(bl);
        // End of drive section

        // Start of Intake/Turn/Shoot section
        // First, intake :|
        if(gamepad2.xWasPressed()){
            inMult = inMult - 0.1;
        } else if(gamepad2.bWasPressed()){
            inMult = inMult + 0.1;
        }
        intake.setPower(-gamepad2.left_stick_y * inMult);
        // Then, turn?
        LLResult llResult = limelight.getLatestResult();
        double tx = llResult.getTx();
        int currentPos = turn.getCurrentPosition();
        double txToTicks = (int) (tx / (DEGREES_PER_TICK)); //(degree)/(degree/ticks)

        turn.setTargetPosition(currentPos - (int) txToTicks);


        if (currentPos >= leftBound && currentPos <= rightBound) {
            if (txToTicks > 0) {
                turn.setPower(motorPower);
            } else if (txToTicks < 0) {
                turn.setPower(-motorPower);
            }
        } else if (currentPos > rightBound && txToTicks > 0) {
            turn.setPower(-motorPower);
        } else if (currentPos < leftBound && txToTicks < 0) {
            turn.setPower(motorPower);
        } else {
            turn.setPower(0);
        }
        // Then shoot
        if(gamepad2.aWasPressed()){
            outMult = outMult - 0.1;
        } else if(gamepad2.yWasPressed()){
            outMult = outMult + 0.1;
        }
        shoot.setPower(outMult * (gamepad2.right_trigger - gamepad2.left_trigger));
    }
}
