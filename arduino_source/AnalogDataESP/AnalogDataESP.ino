#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>

// WiFi credentials
const char* ssid = "";   // wifi ssid
const char* password = "";   // wifi password

// Create an instance of the server
ESP8266WebServer server(80);

// Read analog pin (A0 on NodeMCU)
const int analogPin = A0;

void handleRoot() {
  String html = R"rawliteral(
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Analog Data Stream</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f0f0f0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background-color: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            text-align: center;
            max-width: 600px;
            width: 100%;
        }
        h1 {
            font-size: 2rem;
            color: #333;
            margin-bottom: 20px;
        }
        #analogValue {
            font-size: 3rem;
            font-weight: bold;
            color: #4CAF50;
            padding: 20px;
            border-radius: 8px;
            background-color: #e8f5e9;
            margin: 20px 0;
        }
        .status {
            font-size: 1.2rem;
            color: #777;
            margin-top: 20px;
        }
        .update {
            font-size: 1.2rem;
            color: #888;
            margin-top: 10px;
        }
    </style>
    <script>
        // Function to fetch and update the analog value in real-time
        function fetchAnalogData() {
            fetch('/readAnalog')
                .then(response => response.json())
                .then(data => {
                    document.getElementById('analogValue').innerText = "Analog Value: " + data.value;
                })
                .catch(error => console.error('Error fetching data:', error));
        }

        // Fetch data every 1 second
        setInterval(fetchAnalogData, 1000);

        // Fetch immediately on load
        window.onload = fetchAnalogData;
    </script>
</head>
<body>
    <div class="container">
        <h1>Real-Time Analog Data Stream</h1>
        <div id="analogValue">Loading...</div>
        <p class="status">Monitoring the analog input from A0 on NodeMCU.</p>
        <p class="update">Last updated: <span id="lastUpdated">Just now</span></p>
    </div>
</body>
</html>
  )rawliteral";
  server.send(200, "text/html", html);
}

void handleAnalogData() {
  int sensorValue = analogRead(analogPin); // Read the analog value
  String jsonResponse = "{\"value\": " + String(sensorValue) + "}";
  server.send(200, "application/json", jsonResponse); // Send JSON data
}

void setup() {
  Serial.begin(115200);
  
  // Connect to Wi-Fi
  WiFi.begin(ssid, password);
  Serial.print("Connecting to WiFi");
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.print(".");
  }
  Serial.println("\nConnected to WiFi");

  // Set up server routes
  server.on("/", handleRoot);           // Serve the main HTML page
  server.on("/readAnalog", handleAnalogData); // Return the analog value in JSON

  // Start the server
  server.begin();
  Serial.println("Server started at: " + WiFi.localIP().toString());
}

void loop() {
  server.handleClient(); // Handle client requests
}
