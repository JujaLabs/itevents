[![Stories in Ready](https://badge.waffle.io/JuniorsJava/itevents.png?label=ready&title=Ready)](https://waffle.io/JuniorsJava/itevents)
# itevents

Resource to subscribe on it events 

<b>Instructions about deploying REST service</b>

-- Open Command line in directory 'restservice'.

-- Start the following command: 
        
        gradle build

You should see 'BUILD SUCCESSFUL' after process end.

-- Start the next command: 
        
        gradle jettyRun

-- After moment that you see about that:
        BUILD SUCCESSFUL
        Total time: 11.802 secs
        D:\Java\Work\itevents\restservice>gradle jettyRun
        :restservice:compileJava UP-TO-DATE
        :restservice:processResources UP-TO-DATE
        :restservice:classes UP-TO-DATE
        > Building 75% > :restservice:jettyRun > Running at http://localhost:8080/restservice

you can run the following in browser command line:

        http://localhost:8080/restservice
        
and test page of our application should be shown.

-- Run the next in browser command line:

        http://localhost:8080/restservice/events/{number}
        
where number is from 1 to 4.
Page with xml structure of Event should be shown.

-- To stop Jetty correctly open another Command line in directory 'restservice'.
Start the following command: 
        
        gradle jettyStop
        
-- Now you can close both instances of Command line.
