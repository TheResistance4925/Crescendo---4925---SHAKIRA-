Hello, this is the code for team 4925 "The Resistance" for our 2024 robot SHAKIRA!

SHAKIRA is a 30x30in swerve drive with a doublejojnted arm
conbined intake/shooter mechanism functioning as the end effector and
second segment of the arm.

 // IF YOU ARE NOT OPERATING THE BOT IGNORE THIS BIT
For any future referance the starting config is with the arm in a collapsed
pose on the side of the Battery (wich is the BACK side of the robot) 
-- NEO's should be facing away from DS or Speaker



We use tradtional command based structure overall
for detailed system control or special stuff look below

* SHUFFLEBOARD for driver feedback + control
* BaseFalconSwerve
* REV SmartMotion with our SPARKMAX's and NEO 1.1's to operate our arm
    * Thankyou to the LAKERBOTS team 8046 for the help with this as well
      as providing public acess to an excelent GITHUB with organized code.
      We used their swerve code as a basis for ours to garner better auton
      functionality.
* LIMELIGHT for vision (NOT CURRENTLY IMPLEMENTED)
    * The bot uses two limelights, a forward and backward 2 @ 3 respectivly
    * The LL3 faces the intake side for ML based NOTE identifacation + targeting
    * THE LL2 faces the BACK of the bot where we mainly shoot from and handles APRILTAGS
%
  
