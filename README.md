# AI-Project-Spring-2021

Note: To start this app you will need to give camera permission from application settings.


AI in Motion Detection





28 March, 2021

  Shoaib Ahmed (62884 - MCS) 
  Waleed Ahmed (10274 - MCS)




Prepared for
Artificial Intelligence & Expert System

Abstract
In this project, we will discuss how principles of AI can be applied on a Motion Detection application by implementing AI to detect motion and then AI will decide appropriate action plan as per the real time situation.
By using mobile’s camera, we will compare the images which is stored in mobile app then we will output the difference between those images. 

1 Introduction and Background
Motion detection is the process of detecting a change in the position of an object relative to its surroundings or a change in the surroundings relative to an object. Detection of Motion can be achieved by either mechanical or electronic methods.

1.1	Security Purpose

Motion detection is an important tool for securing your business or building. It alerts you when someone is on your property that isn't authorized. Understanding how this technology helps you set up better motion detection regions and alerts

1.2	Results 

When you set up motion detection, you select a region or area to monitor, say a doorway. The way it works is to compare sequential images and if enough of the pixels have changed between those frames, the camera software determines something moved and sends you an alert.

2 Overview of the architecture

1.	Access camera by android application.
2.	After clicking capture button it will take picture and save it in a byte form then decode them into bitmap
3.	In every second it will take pictures automatically then again safe it as bitmap
4.	Then it will check with the last pictures with a new picture and check the difference in height, width and as well as RGB.
5.	If differences are more than 10 percent then it will save the new picture and send an alert, else it will continuously capture pictures in every second until you click the stop button
3 Result
Save pictures and give alert if any motion detected.


