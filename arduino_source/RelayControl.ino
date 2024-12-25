#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>

const char* ssid = "";
const char* password = "";

ESP8266WebServer server(80);

// Relay pins
#define RELAY1 05
#define RELAY2 04
#define RELAY3 16

// Initial state of relays
bool relayState1 = false;
bool relayState2 = false;
bool relayState3 = false;

void handleRoot() {
  String html = R"rawliteral(
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Relay Control</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            margin: 20px;
            padding: 0;
            background-color: #f9f9f9;
        }
        h1 {
            color: #333;
        }
        .button-container {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            margin: 20px 0;
        }
        button {
            padding: 15px 20px;
            font-size: 16px;
            margin: 10px;
            cursor: pointer;
            border-radius: 8px;
            border: none;
            width: 100%;
            max-width: 200px;
        }
        .on {
            background-color: #4CAF50;
            color: white;
        }
        .off {
            background-color: #f44336;
            color: white;
        }
        .status {
            margin-top: 20px;
            font-size: 20px;
            color: #555;
        }
        @media (min-width: 768px) {
            button {
                font-size: 18px;
                padding: 15px 30px;
                margin: 15px;
            }
        }
        @media (min-width: 1024px) {
            button {
                font-size: 20px;
                margin: 20px;
            }
        }
    </style>
</head>
<body>
    <h1>Relay Control</h1>
    <div class="button-container">
        <button class="on" onclick="controlRelay(1, 'on')">Relay 1 ON</button>
        <button class="off" onclick="controlRelay(1, 'off')">Relay 1 OFF</button>
    </div>
    <div class="button-container">
        <button class="on" onclick="controlRelay(2, 'on')">Relay 2 ON</button>
        <button class="off" onclick="controlRelay(2, 'off')">Relay 2 OFF</button>
    </div>
    <div class="button-container">
        <button class="on" onclick="controlRelay(3, 'on')">Relay 3 ON</button>
        <button class="off" onclick="controlRelay(3, 'off')">Relay 3 OFF</button>
    </div>
    <p class="status" id="status">Status: Ready</p>
    <script>
        function controlRelay(relay, state) {
            fetch(`/control?relay=${relay}&state=${state}`)
                .then(response => response.text())
                .then(data => {
                    document.getElementById('status').innerText = data;
                })
                .catch(error => {
                    document.getElementById('status').innerText = "Error: " + error.message;
                });
        }
    </script>
</body>
</html>
  )rawliteral";
  server.send(200, "text/html", html);
}

void handleControl() {
  String relay = server.arg("relay");
  String state = server.arg("state");
  String response;

  if (relay == "1") {
    relayState1 = (state == "on");
    digitalWrite(RELAY1, relayState1 ? LOW : HIGH);
    response = "Relay 1 turned " + state;
  } else if (relay == "2") {
    relayState2 = (state == "on");
    digitalWrite(RELAY2, relayState2 ? LOW : HIGH);
    response = "Relay 2 turned " + state;
  } else if (relay == "3") {
    relayState3 = (state == "on");
    digitalWrite(RELAY3, relayState3 ? LOW : HIGH);
    response = "Relay 3 turned " + state;
  } else {
    response = "Invalid relay!";
  }

  server.send(200, "text/plain", response);
}

void setup() {
  Serial.begin(115200);
  WiFi.begin(ssid, password);
  Serial.print("Connecting to Wi-Fi");
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.print(".");
  }
  Serial.println("\nConnected!");

  pinMode(RELAY1, OUTPUT);
  pinMode(RELAY2, OUTPUT);
  pinMode(RELAY3, OUTPUT);

  digitalWrite(RELAY1, HIGH); // Relays are usually active LOW
  digitalWrite(RELAY2, HIGH);
  digitalWrite(RELAY3, HIGH);

  server.on("/", handleRoot);
  server.on("/control", handleControl);

  server.begin();
  Serial.println("Server started at: " + WiFi.localIP().toString());
}

void loop() {
  server.handleClient();
}
