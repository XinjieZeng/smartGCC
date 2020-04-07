# Team Information - 4
Group Members - Xinjie, Siming, Mostakim, Birjot, Jaskaran, Ashwin.

# SmartGCC 

SmartGCC is a self-adjusting smart graphical interface for C program development using the GCC compiler. 

It is designed for three types of users: 

1. Novice user: use compiler options, linking options, execute options and debugging options.
2. Typical Programmer: use code generation and code optimization options in addition to the options used by novice users.
3. Expert Developer: use the developer options in addition to the options used by typical programmers.

## **Getting Started**
The gcc compiler has a lot of commands and due to time and resource constraint, it is impossible to implement all of them. We decided to choose to implement some of them as a demonstration. Currently, the SmartGCC only accepts commands for C program and only able users to Open, Edit and SaveAs C files. 

### **Prerequisite** 
1. Java JDK 8  
2. GCC compiler 
3. Eclipse / IntelliJ IDE

### **Building the project** 
1. Import the project in Eclipse/IntelliJ as a Maven project.
2. Run mvn install.
3. Run mvn exec:java -Dexec.mainClass=smartgcc.MainApp

The MainApp is the main application class.

### **Note**:
1. When running the SmartGCC application for the first time, the User Type panel will show up and it allows the user to choose their user type. Once the selection is done, the user type will be saved in a temporary "userChoice.txt" file on the system and the editing panel will be adjusted accordingly. Only the appropriate options will be available, based on chosen user type.

2. The next time SmartGCC is loaded, it will load the cached user type from previous selection from the "userChoice.txt" file and directly load the editing panel without showing switch user type window.

3. Users can always change their user type level in the editing panel at any time. 

4. To display the User Type dialog when the application is launched, empty the cached file "userChoice.txt" on the system.

5. Make sure that the GCC compiler is properly installed on your system, before running the commands. The program uses Command Exec librairies to handle external processes and concurrent executions.

### **Screenshots**
![alt text](https://github.com/XinjieZeng/smartGCC/blob/master/src/main/resources/assets/editingPanel.png "editing panel")

![alt text](https://github.com/XinjieZeng/smartGCC/blob/master/src/main/resources/assets/switchUserTypePanel.png "switch User type panel")
