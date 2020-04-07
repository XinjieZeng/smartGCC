# SmartGCC 

SmartGCC is a self-adjusting smart graphical interface for C program development using the GCC compiler. 

It is designed for three types of users: 

1. Novice user: use compiler options, linking options, execute options and debugging options.
2. Typical Programmer: use code generation and code optimization options in addition to the options used by novice users.
3. Expert Developer: use the developer options in addition to the options used by typical programmers.


## **Getting Started**
The gcc compiler has a lot of commands, which would be impossible to implement all of them. We choose some to implement as a demonstration. Currently, The smartGCC only accepts commands for c program and only able users to open, edit and save as c files. 


### **Prerequisite** 
1. have Java JDK 8 installed on your computer 
2. install gcc compiler on your computer 
   
### **Building the project** 
1. Import the project as maven project
2. Run mvn install
3. Run mvn exec:java -Dexec.mainClass=smartgcc.MainApp

### **Note**:
1. When running the smartGCC first time, the swtich user type panel will show up for users to choose their user type. The user type will be saved in userChoice.txt and the editing panel will be adjusted accordingly based on chosen user type.
2. When user reopen the smartGCC, it will load the user type from userChoice.txt and directly load the editing panel according different user type without showing switch user type window. 
3. Users will be able to change their user type in the editing panel if they change their minds. 
4. If user wants to reset to the status back to running the smartGCC first time, please empty the content in the userChoice.txt 

### **Screenshots**
![alt text](https://github.com/XinjieZeng/smartGCC/blob/master/src/main/resources/assets/editingPanel.png "editing panel")

![alt text](https://github.com/XinjieZeng/smartGCC/blob/master/src/main/resources/assets/switchUserTypePanel.png "switch User type panel")
