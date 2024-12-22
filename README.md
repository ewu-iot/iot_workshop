# IoT workshop for Sustainable Development @EWU
IoT for sustainable development


# Arduino and NodeMCU/ESP8266 Setup Guide for IoT workshop

This repository provides step-by-step guides for setting up the Arduino IDE, installing Java JDK, configuring the Arduino IDE for NodeMCU/ESP8266, and running a Blink program on NodeMCU. Follow the instructions and refer to the video tutorials provided for a complete setup.

## Table of Contents

1. [Installing Arduino IDE](#installing-arduino-ide)
2. [Installing Java JDK 23](#installing-java-jdk-23)
3. [Setting Up Arduino IDE for NodeMCU/ESP8266](#setting-up-arduino-ide-for-nodemcu-esp8266)
4. [Running the Blink Program on NodeMCU](#running-the-blink-program-on-nodemcu)

---

### Installing Arduino IDE

The Arduino IDE is the primary software required to program Arduino boards and compatible microcontrollers like NodeMCU.

#### Steps:
1. Download the Arduino IDE from the official [Arduino website](https://www.arduino.cc/en/software).
2. Install the IDE following the on-screen instructions.

#### Video Tutorial:
[![Installing Arduino IDE](https://img.youtube.com/vi/sample_video_id1/0.jpg)](https://www.youtube.com/watch?v=sample_video_id1)

---

### Installing Java JDK 23

Java Development Kit (JDK) 23 is required for compiling certain tools and libraries used with the Arduino IDE.

#### Steps:
1. Download the JDK 23 installer from the official [Oracle JDK website](https://www.oracle.com/java/technologies/javase-downloads.html).
2. Install the JDK and configure the system's environment variables (JAVA_HOME).

#### Video Tutorial:
[![Installing Java JDK 23](https://img.youtube.com/vi/sample_video_id2/0.jpg)](https://www.youtube.com/watch?v=sample_video_id2)

---

### Setting Up Arduino IDE for NodeMCU/ESP8266

The Arduino IDE requires additional configuration to support NodeMCU or ESP8266 boards.

#### Steps:
1. Open Arduino IDE and go to **File > Preferences**.
2. Add the ESP8266 Boards Manager URL: `http://arduino.esp8266.com/stable/package_esp8266com_index.json`.
3. Navigate to **Tools > Board > Boards Manager** and search for "ESP8266".
4. Install the ESP8266 board package.

#### Video Tutorial:
[![Setting Up Arduino IDE for NodeMCU](https://img.youtube.com/vi/sample_video_id3/0.jpg)](https://www.youtube.com/watch?v=sample_video_id3)

---

### Running the Blink Program on NodeMCU

The Blink program is a simple test to ensure your NodeMCU is correctly set up and functioning.

#### Steps:
1. Open Arduino IDE and select **File > Examples > Basics > Blink**.
2. Modify the `LED_BUILTIN` definition to `D0` for NodeMCU compatibility.
3. Connect your NodeMCU to the PC and select the correct board and COM port in **Tools**.
4. Upload the program to the NodeMCU.

#### Video Tutorial:
[![Running Blink Program on NodeMCU](https://img.youtube.com/vi/sample_video_id4/0.jpg)](https://www.youtube.com/watch?v=sample_video_id4)

---

## Contribution

Feel free to contribute to this repository by adding more examples or improving documentation. Create a pull request or open an issue for discussion.

## License

This repository is licensed under the [MIT License](LICENSE).
