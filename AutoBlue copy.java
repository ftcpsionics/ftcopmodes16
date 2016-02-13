package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;

public class AutoBlue extends OpMode {

    final static double MOTOR_POWER = 0.15;

    DcMotor motorLeft1;
    DcMotor motorLeft2;
    DcMotor motorRight1;
    DcMotor motorRight2;
    DcMotor motorIntake;

    //double armPosition;
    //double clawPosition;
    //double armDelta = 0.01;


    public AutoBlue() {

    }

    @Override
    public void init() {

        motorLeft1 = hardwareMap.dcMotor.get("motor_1");
        motorLeft2 = hardwareMap.dcMotor.get("motor_2");
        motorRight1 = hardwareMap.dcMotor.get("motor_3");
        motorRight2 = hardwareMap.dcMotor.get("motor_4");
        motorIntake = hardwareMap.dcMotor.get("motor_6");
        motorRight1.setDirection(DcMotor.Direction.REVERSE);
        motorRight2.setDirection(DcMotor.Direction.REVERSE);


    }

    @Override
    public void loop() {
        //double reflection = 0.0;
        double left = 0.0;
        double right = 0.0;
        double intake = 0;

        if(this.time > 0 && this.time <=3.00){
            left = 0.0;
            right = 0.0;
        }
        else if(this.time > 3.00 && this.time <= 10){
            left = .3;
            right = .3;
            intake = -1;
        }

        motorRight1.setPower(right);
        motorRight2.setPower(right);
        motorLeft1.setPower(left);
        motorLeft2.setPower(left);
        motorIntake.setPower(intake);

        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("Time", ": " + Double.toString(this.time));
        //telemetry.addData("reflection", "reflection:  " + Double.toString(reflection));
        telemetry.addData("Left Power",  ": " + Double.toString(left));
        telemetry.addData("Right Power", ": " + Double.toString(right));


    }

    @Override
    public void stop() {

    }

}