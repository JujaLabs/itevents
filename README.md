# itevents
Resource to subscribe on it events 

        Instructions about deploying REST service

1. Open Command line in directory 'restservice'.

2. Start the following command: 
        
        gradle build

You should see 'BUILD SUCCESSFUL' after process end.

3. Start the next command: 
        
        gradle jettyRun

4. After moment that you see about that:
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

5. Run the next in browser command line:

        http://localhost:8080/restservice/events/{number}
        
where number is from 1 to 4.
Page with xml structure of Event should be shown.
