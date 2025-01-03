# IoT workshop for Sustainable Development @East West University, Bangladesh
This repository provides step-by-step guides for setting up the Arduino IDE, installing Java JDK, configuring the Arduino IDE for NodeMCU/ESP8266, and running a Blink program on NodeMCU. Follow the instructions and refer to the video tutorials provided for a complete setup.

## Table of Contents

1. [Installing Arduino IDE](#installing-arduino-ide)
2. [Installing Java JDK 23](#installing-java-jdk-23)
3. [Setting Up Arduino IDE for NodeMCU/ESP8266](#setting-up-arduino-ide-for-nodemcu-esp8266)
4. [Running the Blink Program on NodeMCU](#running-the-blink-program-on-nodemcu)

---

### Installing Arduino IDE

The Arduino IDE is the primary software required to program Arduino boards and compatible microcontrollers like NodeMCU or ESP8266.

#### Steps:
1. Download the Arduino IDE from the official [Arduino website](https://www.arduino.cc/en/software).
2. Install the IDE following the on-screen instructions.

#### Video Tutorial:
<a href="https://www.youtube.com/watch?v=BpjWgBRpcS0" target="_blank">
    <img src="https://img.youtube.com/vi/BpjWgBRpcS0/hqdefault.jpg" alt="Video 1 Preview" />
</a>

---

### Installing Java JDK 23

Java Development Kit (JDK) 23 is required for compiling certain tools and libraries used with the Arduino IDE. Java is an essential component in the development of HTTP servers for data visualization applications.

#### Steps:
1. Download the JDK 23 installer from the official [Oracle JDK website](https://www.oracle.com/java/technologies/downloads)
2. Install the JDK and configure the system's environment variables (JAVA_HOME).

#### Video Tutorial:
<a href="https://www.youtube.com/watch?v=7CGLfDCYoR4" target="_blank">
    <img src="https://img.youtube.com/vi/7CGLfDCYoR4/hqdefault.jpg" alt="Video 2 Preview" />
</a>

---

### Setting Up Arduino IDE for NodeMCU/ESP8266

The Arduino IDE requires additional configuration to support NodeMCU or ESP8266 boards.

#### Steps:
1. Open Arduino IDE and go to **File > Preferences**.
2. Add the ESP8266 Boards Manager URL: `http://arduino.esp8266.com/stable/package_esp8266com_index.json`.
3. Navigate to **Tools > Board > Boards Manager** and search for "ESP8266".
4. Install the ESP8266 board package.

#### Video Tutorial:
<a href="https://www.youtube.com/watch?v=psaGkSOoJpY&t=35s" target="_blank">
    <img src="https://img.youtube.com/vi/psaGkSOoJpY/hqdefault.jpg" alt="Video 3 Preview" />
</a>

---

### Running the Blink Program on NodeMCU

The Blink program is a simple test to ensure your NodeMCU is correctly set up and functioning.

#### Steps:
1. Open Arduino IDE and select **File > Examples > Basics > Blink**.
2. No Modification is needed.
3. Connect your NodeMCU to the PC using USB cable and select the correct board and COM port in **Tools**.
4. Upload the program to the NodeMCU.

#### Video Tutorial:
<a href="https://youtu.be/fALpHXah9-k?si=sAg0InqzU4kIbPnJ" target="_blank">
    <img src="https://img.youtube.com/vi/fALpHXah9-k/hqdefault.jpg" alt="Video 4 Preview" />
</a>

---

## Contribution

Feel free to contribute to this repository by adding more examples or improving documentation. Create a pull request or open an issue for discussion.

## License

This repository is licensed under the [MIT License](LICENSE).
