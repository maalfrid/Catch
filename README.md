<img src="https://github.com/maalfrid/Catch/blob/dev/app/src/main/res/drawable/txt_title.png" alt="" width="600"/>
CATCH is an intuitive and easily accessible mobile game that gets you hooked from the first game. Control your sprite by touch, catch the good objects falling down and be rewarded, but by all means avoid the bad objects or you will be punished!  

# Outline
- [1 Getting Started](#getting-started)
  * [1.1 Prerequesites](#prerequisites)
  * [1.2 Installing](#installing)
- [2 Play Guide](#play)
- [3 Built With](#built-with)
- [4 Versioning](#versioning)
- [5 Authors](#authors)
- [6 License](#license)

<a name="getting-started"></a>
# 1 Getting Started

<a name="prerequisites"></a>
## 1.1 Prerequisites
- An Android device or emulator supporting google play games services with API version 21 or later versions
- Create a user in the [Google Play Games](https://play.google.com/store/apps/details?id=com.google.android.play.games&hl=en) app in order to play multiplayer
- In order to test the multiplayer game, you must be added as a tester on the [Google Developer Console](https://console.developers.google.com) by one of the contributors of Catch
- In multiplayer mode, you need to be connected to the internet

<a name="installing"></a>
## 1.2 Install, Compile and Run
- Download [Android Studio](https://developer.android.com/studio) on you computer
- Clone the [project](https://github.com/maalfrid/Catch) from terminal
  * ```cd your-git-directory```
  * ```git clone https://github.com/maalfrid/Catch.git```
- Open the project in Android Studio: <br/>
<img src="https://user-images.githubusercontent.com/16878457/55806313-4d71f980-5ae0-11e9-9fcd-d7bba2d94e3b.png" alt="" width="500"/>

- Choose the cloned Catch directory: <br />
<img src="https://user-images.githubusercontent.com/16878457/55806350-611d6000-5ae0-11e9-8a6a-206080391535.png" alt="" width="500"/>

- Press the Open button and wait for the Gradle build is finished: <br />
<img src="https://user-images.githubusercontent.com/16878457/55803520-c3736200-5ada-11e9-92ff-715e5d350716.png" alt="" width="250"/>

- Run the project by pressing the green "play" button: <br />
<img src="https://user-images.githubusercontent.com/16878457/55806258-37fccf80-5ae0-11e9-869b-7032bdc3ffd4.png" alt="" width="250"/>

- Further instructions for running the app: 
  * Go to [this section](#installing_device) if you have a device.
  * Go to [this section](#installing_emulator) if you don't have a device.

<a name="installing_device"></a>
### 1.2.1 With Device
- Connect device to the computer with USB
- One the device: Enable permission to access the device from the computer
- Select your device appearing in "Connected Devices"
- Press OK to run the app on your device

<a name="installing_emulator"></a>
### 1.2.2 With Emulator
- Create a new device: <br />
<img src="https://user-images.githubusercontent.com/16878457/55806203-20254b80-5ae0-11e9-90b4-1c0a10dfccc5.png" alt="" width="500"/>

- Choose one of the devices with a play store symbol (e.g. NEXUS 5X): <br />
<img src="https://user-images.githubusercontent.com/16878457/55806189-1865a700-5ae0-11e9-982e-ddd0938614ae.png" alt="" width="100"/>

- Press next
- Choose a version of the Android mobile operating system having an API level 21 or later versions (e.g. Pie)
  * Press the Download button for the chosen version
- Press next
- Press finish
- Wait until the virtual device is installed
- Press OK: <br />
<img src="https://user-images.githubusercontent.com/16878457/55806208-2287a580-5ae0-11e9-89a6-38b46255d356.png" alt="" width="500"/>

<a name="play"></a>
# 2 Play Guide

<a name="play-single"></a>
## 2.1 Play Single player
- Press Play
- Choose Singleplayer
- Choose difficulty - Easy, Medium or Hard
- Move the player by touch!
  * Catch good objects and power-ups, but avoid bad objects.
  * The scoring system is described [here](#play-scoring)

![playguide-spg](https://user-images.githubusercontent.com/16878457/56753679-5d9a0200-678b-11e9-8d80-5d04aa662a3c.gif)

<a name="play-multi"></a>
## 2.2 Play Multiplayer
- In the landing page, press Play and further Multiplayer
- Sign in to your google play games account
- Choose Quick game:
  * Wait until your opponent joines the quick game
  * When the opponent has joined, the game will start automatically
  * As in single player - Catch good objects and power-ups, but avoid bad objects. If you catch a power-up it will affect your opponent and and vice versa.
  * The scoring system is described [here](#play-scoring)

<a name="play-scoring"></a>
## 2.3 Scoring system
There are good objects, bad objects and power-ups objects following down in the Catch game. A description of their scoring or behavior follows. 

### Good objects

#### 5 points
<img src="https://user-images.githubusercontent.com/16878457/56754590-be2a3e80-678d-11e9-8f68-08701a5a453e.png" alt="" width="30"/> 

#### 3 points
<img src="https://user-images.githubusercontent.com/16878457/56754604-c4201f80-678d-11e9-82a4-30e291f6ef10.png" alt="" width="30"/> 

#### 2 points
<img src="https://user-images.githubusercontent.com/16878457/56754597-c3878900-678d-11e9-9708-85c6d5d68b0b.png" alt="" width="30"/><img src="https://user-images.githubusercontent.com/16878457/56754598-c3878900-678d-11e9-8438-d8844f818503.png" alt="" width="30"/><img src="https://user-images.githubusercontent.com/16878457/56754600-c3878900-678d-11e9-97e5-3e460e0039cf.png" alt="" width="30"/><img src="https://user-images.githubusercontent.com/16878457/56754601-c3878900-678d-11e9-9874-8f145d811960.png" alt="" width="30"/> 

#### 1 point
<img src="https://user-images.githubusercontent.com/16878457/56754602-c4201f80-678d-11e9-9aaf-5a78fe7b1a25.png" alt="" width="30"/><img src="https://user-images.githubusercontent.com/16878457/56754599-c3878900-678d-11e9-8551-4c81e1541c97.png" alt="" width="30"/> 

### Bad objects

#### -3 points
<img src="https://user-images.githubusercontent.com/16878457/56755181-32b1ad00-678f-11e9-85f4-e64398930a78.png" alt="" width="30"/>

#### -2 points
<img src="https://user-images.githubusercontent.com/16878457/56754586-bcf91180-678d-11e9-9ed6-db9b8473aa85.png" alt="" width="30"/>

### Power-ups
The effect of a power-up is active from you catch it and in 10 seconds.

#### If you catch a...
- <img src="https://user-images.githubusercontent.com/16878457/56754609-c4b8b600-678d-11e9-9f56-61eb2e18645d.png" alt="" width="30"/> - Good objects are getting bigger, bad objects are getting smaller
- <img src="https://user-images.githubusercontent.com/16878457/56754606-c4201f80-678d-11e9-801b-f01728fa297b.png" alt="" width="30"/> - You only get good falling objects
- <img src="https://user-images.githubusercontent.com/16878457/56754608-c4201f80-678d-11e9-92e6-66526fc99071.png" alt="" width="30"/> - You get an extra life
- <img src="https://user-images.githubusercontent.com/16878457/56754607-c4201f80-678d-11e9-8a1c-cde9ab043006.png" alt="" width="30"/> - You don't loose lives of missing good objects, and don't loose points of catching bad objects

#### If your opponent catches a... 
- <img src="https://user-images.githubusercontent.com/16878457/56754609-c4b8b600-678d-11e9-9f56-61eb2e18645d.png" alt="" width="30"/> - Bad objects are getting bigger, good objects are getting smaller
- <img src="https://user-images.githubusercontent.com/16878457/56754606-c4201f80-678d-11e9-801b-f01728fa297b.png" alt="" width="30"/> - You only get bad falling objects
- <img src="https://user-images.githubusercontent.com/16878457/56754607-c4201f80-678d-11e9-8a1c-cde9ab043006.png" alt="" width="30"/> - You loose double score when catching bad objects

<a name="built-with"></a>
# 3 Built With
- Android Studio
- [Google Play Games Services](https://developers.google.com/games/services/) (for multiplayer) 

<a name="versioning"></a>
# 4 Versioning
Build gradle version 3.3.2.

<a name="authors"></a>
# 5 Authors

List of [contributors](https://github.com/maalfrid/Catch/graphs/contributors) who participated in this project.

<a name="license"></a>
# 6 License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
