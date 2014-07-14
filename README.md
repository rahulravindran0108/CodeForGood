CodeForGood
===========

# Yuva Parivartan Android application
time alloted:24 hours.
Runners up in "Code For Good challenge" held by JP Morgan

# Requirements
- Create an android application capable of sending data to the servers.
- The android application must be capable of sending data in offline mode also.

# Key Features of the application
- Works in both Online and Offline Mode.
- Push notification which allows the admin to notify the camp coordinator of any changes in the schedule, cancellation of camps.
- Generation of bar graphs which displays attendance vs camp code and attendance vs day of meet.
- Scalable and Robust Application based on existing technology that help make it so.
- Unlimited burst notications, multicast and unicast support.

# Architecture Design
- A typical Client Server Architecture
- broadcast receivers for the push notification and network notifications
- Automatic Synch on network connection
- carefuly cached data on disk to avoid crashing of application when operating in offline mode.
![Alt text](https://raw.githubusercontent.com/rahulravindran0108/CodeForGood/master/screenshots/architecture.jpg "Architecture")

# The Data cook-book
Following are the required data:

- Date in format(YYY-MM-DD), failure to do so, will cause the application to not queue the data to be sent at the back end.
- Location, max of 30 chars, no numericals allowed here
- Attendance max number is 99, no negative numbers or 0 allowed. Again failure to do so will cause immediate rejection of data
- Camp code, alpha numeric string of length 10, no special symbols allowed
- fees collected, strictly integer rounded off.

# The User Interface Design
- Navigation Bar- action bar sherlock, portability upto froyo.
- From first to last buttons include the synch button,log button, report button
- Synch button to manually synch data
- Log button to view logs and delete archived message.
- Report button to view back end report generated.
![Alt text](![Alt text](https://raw.githubusercontent.com/rahulravindran0108/CodeForGood/master/screenshots/photo-grid-android.jpg "UI Design")
