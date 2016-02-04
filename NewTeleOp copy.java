package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class NewTeleOp extends OpMode {


    DcMotor motorLeft1;
    DcMotor motorLeft2;
    DcMotor motorRight1;
    DcMotor motorRight2;
    DcMotor motorWhite;
    DcMotor motorIntake;
    DcMotor motorLift;
    DcMotor motorTilt;
    Servo doorLeft;
    Servo doorRight;
    Servo leftZipline;
    Servo rightZipline;
    Servo climber;


    float leftPosition;
    float rightPosition;
    float climberPosition;
    float climberDelta;
    float leftZipPosition;
    float leftZipDelta;
    float rightZipPosition;
    float rightZipDelta;
    double tiltTime;
    float intake = 0;

    final static float SERVO_MIN_RANGE  = 0.00f;
    final static float SERVO_MAX_RANGE  = 1.00f;

    final static float LEFTZIP_MIN_RANGE  = 0.00f;
    final static float LEFTZIP_MAX_RANGE  = 0.65f;

    final static float RIGHTZIP_MIN_RANGE = 0.05f;
    final static float RIGHTZIP_MAX_RANGE = 0.70f;




    public NewTeleOp() {

    }


    @Override
    public void init() {



        motorLeft1 = hardwareMap.dcMotor.get("motor_1");
        motorLeft2 = hardwareMap.dcMotor.get("motor_2");
        motorRight1 = hardwareMap.dcMotor.get("motor_3");
        motorRight2 = hardwareMap.dcMotor.get("motor_4");
        motorWhite = hardwareMap.dcMotor.get("motor_5");
        motorIntake = hardwareMap.dcMotor.get("motor_6");
        motorLift = hardwareMap.dcMotor.get("motor_7");
        motorTilt = hardwareMap.dcMotor.get("motor_8");
        doorLeft = hardwareMap.servo.get("servo_1");
        doorRight = hardwareMap.servo.get("servo_2");
        leftZipline = hardwareMap.servo.get("servo_3");
        rightZipline = hardwareMap.servo.get("servo_4");
        climber = hardwareMap.servo.get("servo_6");
        motorRight1.setDirection(DcMotor.Direction.REVERSE);
        motorRight2.setDirection(DcMotor.Direction.REVERSE);

        doorLeft.setPosition(0.51f);
        doorRight.setPosition(0.51f);

        climberPosition = 1.0f;
        climber.setPosition(climberPosition);

        leftZipPosition = 0.65f;
        leftZipline.setPosition(leftZipPosition);

        rightZipPosition = 0.05f;
        rightZipline.setPosition(rightZipPosition);

    }


    @Override
    public void loop() {


        float leftThrottle = -gamepad1.left_stick_y;
        float leftDirection = gamepad1.left_stick_x;
        float left = leftThrottle + leftDirection;
        left = Range.clip(left, -1, 1);
        left =  (float)scaleInput(left);
        motorLeft1.setPower(left);
        motorLeft2.setPower(left);

        float rightThrottle = -gamepad1.right_stick_y;
        float rightDirection = gamepad1.right_stick_x;
        float right = rightThrottle + rightDirection;
        right = Range.clip(right, -1, 1);
        right =  (float)scaleInput(right);
        motorRight1.setPower(right);
        motorRight2.setPower(right);

        float flowerPower = 0;

        if(left != 0)
            flowerPower = -left;

        if(flowerPower == 0 && right != 0)
            flowerPower = right;

        motorWhite.setPower(flowerPower);



        float liftThrottle = gamepad2.left_stick_y;
        float liftDirection = gamepad2.left_stick_x;
        float lift = liftThrottle + liftDirection;
        lift = Range.clip(lift, -1, 1);
        lift =  (float)scaleInput(lift);

        motorLift.setPower(lift);


        float tilt;

        if(gamepad2.x) {
            tiltTime = this.time;
            tilt = 0.25f;
            motorTilt.setPower(tilt);
        } else if (gamepad2.b) {
            tiltTime = this.time;
            tilt = -0.25f;
            motorTilt.setPower(tilt);
        }

        if(this.time > tiltTime + 0.1) {
            tilt = 0;
            motorTilt.setPower(tilt);
        }


        if (gamepad1.left_bumper) {
            intake = -1;
            intake = (float) scaleInput(intake);
            motorIntake.setPower(intake);
        }  else if (gamepad1.right_bumper) {
            intake = 1;
            intake = (float) scaleInput(intake);
            motorIntake.setPower(intake);
        }

        if (gamepad1.right_trigger > 0) {
            intake = 0;
            intake = (float) scaleInput(intake);
            motorIntake.setPower(intake);
        }

        leftPosition = 0;
        rightPosition = 0;

        float dLeft;
        float dRight;

        if (gamepad2.left_trigger > 0){
            dRight = 1;
            doorLeft.setPosition(dRight);
        } else if (gamepad2.left_bumper){
            dRight = 0;
            doorLeft. setPosition(dRight);
        } else if (gamepad2.right_trigger > 0){
            dLeft = 0;
            doorRight.setPosition(dLeft);
        } else if (gamepad2.right_bumper){
            dLeft = 1;
            doorRight.setPosition(dLeft);
        } else {
            dRight = 0.51f;
            doorLeft.setPosition(dRight);
            dLeft= 0.51f;
            doorRight.setPosition(dLeft);
        }

        climberPosition = 1.0f;
        climber.setPosition(climberPosition);
        climberDelta = 1.0f;

        if(gamepad2.dpad_down){
            climberPosition -= climberDelta;
            climberPosition = Range.clip(climberPosition, SERVO_MIN_RANGE, SERVO_MAX_RANGE);
            climber.setPosition(climberPosition);
        } else if(gamepad2.dpad_up){
            climberPosition += climberDelta;
            climberPosition = Range.clip(climberPosition, SERVO_MIN_RANGE, SERVO_MAX_RANGE);
            climber.setPosition(climberPosition);
        }


        leftZipDelta = 0.65f;

        if(gamepad1.b){
            leftZipPosition -= leftZipDelta;
            leftZipPosition = Range.clip(leftZipPosition, LEFTZIP_MIN_RANGE, LEFTZIP_MAX_RANGE);
            leftZipline.setPosition(leftZipPosition);
        } else if(gamepad1.y){
            leftZipPosition += leftZipDelta;
            leftZipPosition = Range.clip(leftZipPosition, LEFTZIP_MIN_RANGE, LEFTZIP_MAX_RANGE);
            leftZipline.setPosition(leftZipPosition);
        }

        rightZipDelta = 0.65f;

        if(gamepad1.a){
            rightZipPosition += rightZipDelta;
            rightZipPosition = Range.clip(rightZipPosition, RIGHTZIP_MIN_RANGE, RIGHTZIP_MAX_RANGE);
            rightZipline.setPosition(rightZipPosition);

        } else if(gamepad1.x) {
            rightZipPosition -= rightZipDelta;
            rightZipPosition = Range.clip(rightZipPosition, RIGHTZIP_MIN_RANGE, RIGHTZIP_MAX_RANGE);
            rightZipline.setPosition(rightZipPosition);
        }



        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("Left Motor Power",  ": " + String.format("%.2f", left));
        telemetry.addData("Right Motor Power",  ": " + String.format("%.2f", right));
        //telemetry.addData("intake tgt pwr",  "intake  pwr: " + String.format("%.2f", intake));
        telemetry.addData("Lift Power",  ": " + String.format("%.2f", lift));
        telemetry.addData("Climber Servo Position: ", + climberPosition);
        telemetry.addData("Left Zipline", + leftZipPosition);
        telemetry.addData("Right Zipline", + rightZipPosition );
        //telemetry.addData("Lift Motor Position", ": " + Double.toString(liftPostion));
        //telemetry.addData("tilt tgt pwr",  "tilt  pwr: " + String.format("%.2f", tilt));
        //telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));

    }

    /*
     * Code to run when the op mode is first disabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
     */


    @Override
    public void stop() {

    }


    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

}