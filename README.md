Hello, this is the code for team 4925 "The Resistance" for our 2024 robot SHAKIRA!

SHAKIRA is a 30x30in swerve drive with a doublejojnted arm
conbined intake/shooter mechanism functioning as the end effector and
second segment of the arm.

 // IF YOU ARE NOT OPERATING THE BOT IGNORE THIS BIT
For any future referance the starting config is with the arm in a collapsed
pose on the side of the Battery (wich is the BACK side of the robot for odemetry 
purposes) -- NEO's should be facing away from DS -- or Speaker if were just driving it for pracitice

//USE PATHPLANNER TO VEIW AUTOS + SHOW THEM TO OTHER TEAMS -- CHOOSER IS ON DASHBOARD

We use tradtional command based structure overall
for detailed system control or special stuff look below

* SHUFFLEBOARD for driver feedback + control
* YAGSL for swerve (To set up for your own bot just edit the JSONS in the deploy folder)
    *Our swerve is using 8 Falcon500 motors with Talon FX controlers
     We do not use any CANIVORES and its all on one bus
* REV SmartMotion with our SPARKMAX's and NEO 1.1's to operate our arm
    * Thankyou to the LAKERBOTS team 8046 for the help with this as well
      as providing public acess to an excelent GITHUB with organized code.
      We used their swerve code as a basis for ours to garner better auton
      functionality.
* LIMELIGHT for vision (NOT CURRENTLY FUNCTIONALY IMPLEMENTED)
    * The bot uses two limelights, a forward and backward 2 @ 3 respectivly
* PathPlanner + PathPlanner LIB
    * We use pathplanner and their AUTOBUILDER functionality to generate
      high level motion profiles and complex autonomous routines 
  
