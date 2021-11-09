# DemoCompanyAutomation
Below are the steps to run the tests.

1.      Create a Folder à Right click in the folder and select Git Bash and execute the below command.

            git clone https://github.com/MithunDahivale/DemoCompanyAutomation.git

 

2.      Open Eclipse. In Project Explorer, Right Click and select Import->Maven->Existing Maven Project. Browse the project ‘DemoCompanyAutomation’ as per step 1 and click on Finish.

3.      Give the location of firefox.exe (I tested version 73.0.1 which is compatible) path in the config.properties file under DemoCompanyAutomation\config

Variable is: firefoxPath

4.      When the project is cloned now, in the Eclipse under File -> Open project From File systems -> select the project that is clone from directory à Click on Finish

5.      If it gives any errors regarding the JRE, make sure Installed JRE and the Java compiler is set to JDK 1.8 window à Preferences or Project à Build Path

6.      Close the Firefox browser if open.

7.      Now right click on the Project à Run As à Maven Test

8.      After execution is done, Log file, Snapshots and Report (Using Extend Reports) are generated under

DemoCompanyAutomation\TestLogs  (you need to refresh the project to see latest test logs in Eclipse).
