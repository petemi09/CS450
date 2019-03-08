/*Mitchell Petellin
cs450 Project2
Laser turret that uses a stepper motor to move the x-axis
and the servo controls the y-axis
and included a laser button to turn on and off
*/

//for the stepper motor
#include "Stepper.h"
#define STEPS  32   // Number of steps for one revolution of Internal shaft
                    // 2048 steps for one revolution of External shaft

volatile boolean TurnDetected;  // need volatile for Interrupts
volatile boolean rotationdirection;  // CW or CCW rotation

const int PinCLK=2;   // Generating interrupts using CLK signal
const int PinDT=3;    // Reading DT signal
const int PinSW=4;    // Reading Push Button switch

int RotaryPosition=0;    // To store Stepper Motor Position

int PrevPosition;     // Previous Rotary position Value to check accuracy
int StepsToTake;      // How much to move Stepper

//stepper motor pins
Stepper small_stepper(STEPS, 8, 10, 9, 11);


//for servo
#include <Servo.h>

Servo myservo;
int pos = 0;


const int buttonPin = 24;   //buttons for moving the servo (y-axis) up and down
const int buttonPin2 = 26;  //same thing as line above ^^^
       

// Variables will change:     //servo arm will start at 90 (or close to it)
int buttonPushCounter = 90;   // counter for the number of button presses
int buttonState = 0; 
int buttonState2 = 0;         // current state of the button
int lastButtonState = 0;     // previous state of the button
int lastButtonState2 = 0;




 
//for the laser
int inPin = 53;         // the number of the input pin (button)
int outPin = 13;       // the number of the output pin

int state = HIGH;      // the current state of the output pin
int reading;           // the current reading from the input pin
int previous = LOW;    // the previous reading from the input pin

// the follow variables are long's because the time, measured in miliseconds,
// will quickly become a bigger number than can be stored in an int.
long time = 0;         // the last time the output pin was toggled
long debounce = 200;   // the debounce time, increase if the output flickers





// Interrupt routine runs if CLK goes from HIGH to LOW
void isr ()  {
  delay(4);  // delay for Debouncing
  if (digitalRead(PinCLK))
    rotationdirection= digitalRead(PinDT);
  else
    rotationdirection= !digitalRead(PinDT);
  TurnDetected = true;
}



void setup()
{
  Serial.begin(9600);


  
  //for stepper motor
  pinMode(PinCLK,INPUT);
  pinMode(PinDT,INPUT);  
  pinMode(PinSW,INPUT);
  digitalWrite(PinSW, HIGH); // Pull-Up resistor for switch
  //for when button is clicked
  attachInterrupt (0,isr,FALLING); // interrupt 0 always connected to pin 2 on Arduino UNO



  //for servo
  pinMode(buttonPin, INPUT);
  pinMode(buttonPin2, INPUT);
  myservo.attach(31);

  //for the laser
  pinMode(inPin, INPUT);
  pinMode(outPin, OUTPUT);


}

void loop()
{
  //for servo
  
  buttonState = digitalRead(buttonPin);
  buttonState2 = digitalRead(buttonPin2);

  
  //for the laser
  reading = digitalRead(inPin);

  if (reading == HIGH && previous == LOW && millis() - time > debounce) {
    if (state == HIGH)
      state = LOW;
    else
      state = HIGH;

    time = millis();    
  }

  digitalWrite(outPin, state);



  //for servo moving position

  if (buttonState == HIGH) {
      
      if (buttonPushCounter < 180) {
      buttonPushCounter+=2;
      //serial was used for testing position of buttonPushCounter
      Serial.print("number of button pushes: ");
      Serial.println(buttonPushCounter);
      myservo.write(buttonPushCounter);
      } else {
        buttonPushCounter+=0;
      }
      
    
  }
 
    if (buttonState2 == HIGH) {
      
      if (buttonPushCounter > 0) {
      buttonPushCounter-=2;
      //serial was used for testing position of buttonPushCounter
      Serial.print("number of button pushes: ");
      Serial.println(buttonPushCounter);
      myservo.write(buttonPushCounter);
      }else {
        buttonPushCounter+=0;
      }
    
    }




  //for stepper motor
  small_stepper.setSpeed(600); //Max seems to be 700
  if (!(digitalRead(PinSW))) { // check if button is pressed
    if (RotaryPosition == 0) {  // check if button was already pressed
    } else {
        small_stepper.step(-(RotaryPosition*50));
        myservo.write(90);
        RotaryPosition=0; // Reset position to ZERO
        
      }
    }
    

  // Runs if rotation was detected
  if (TurnDetected)  {
    PrevPosition = RotaryPosition; // Save previous position in variable
    if (rotationdirection) {
      RotaryPosition=RotaryPosition-1;} // decrase Position by 1
    else {
      RotaryPosition=RotaryPosition+1;} // increase Position by 1

    TurnDetected = false;  // do NOT repeat IF loop until new rotation detected

    // Which direction to move Stepper motor
    if ((PrevPosition + 1) == RotaryPosition) { // Move motor CW
      StepsToTake=50; 
      small_stepper.step(StepsToTake);
      //delay(200);
    }

    if ((RotaryPosition + 1) == PrevPosition) { // Move motor CCW
      StepsToTake=-50;
      small_stepper.step(StepsToTake);
      
      //delay(200);
    }
    
  }


  //underneath is for the laser
  previous = reading;
}
