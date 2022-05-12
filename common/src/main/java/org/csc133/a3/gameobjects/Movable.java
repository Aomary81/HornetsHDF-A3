package org.csc133.a3.gameobjects;

public abstract class Movable extends GameObject {
    private int speed;
    private double heading;

    public Movable(){
        speed = 0;
        heading = 90;
    }

    public void move(){
        if (speed > 0){
            translate(7*speed*Math.cos(Math.toRadians(heading)),
                    -7*speed*Math.sin(Math.toRadians(heading)));
        }
    }

    public void adjustSpeed(int speedChange){
        if (speed + speedChange <= 10 && speed + speedChange >= 0){
            speed += speedChange;
        }
    }

    public void turn(int turnAmount){
        heading = heading + turnAmount;
        rotate(-turnAmount);
    }

    public int getHeading(){
        if(heading - 270 <0){
            return (int) (heading +=360);
        }else if(heading - 270 ==360){
            return (int) (heading-=360);
        }else {
            return (int) heading - 270;
        }
    }

    public int getSpeed(){
        return speed;
    }

    protected void setHeading(int heading) {
        this.heading = heading -90;
    }

    protected void setSpeed(int speed) {
        this.speed = speed;
    }
}
