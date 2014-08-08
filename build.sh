#!/bin/bash

mkdir -p build-jar
mkdir -p build-jar/class
mkdir -p build-jar/bin


/usr/bin/javac -encoding SJIS -s ./src -d build-jar/class src/MobileRobot.java src/MobileRobotComp.java src/MobileRobotImpl.java src/SimParam.java src/Vehicle.java src/VehicleSimPanel.java src/VehicleSimTime.java src/VehicleSimWindow.java -cp jar/OpenRTM-aist-1.1.0.jar:jar/commons-cli-1.1.jar

cd build-jar/class

/usr/bin/jar cfv ../bin/MobileRobot.jar MobileRobot.class MobileRobotComp.class MobileRobotImpl.class SimParam.class Vehicle.class VehicleSimPanel$RepaintThread.class VehicleSimPanel.class VehicleSimTime.class VehicleSimWindow.class

