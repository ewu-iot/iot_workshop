#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>

const char* ssid = "";   // wifi ssid
const char* password = "";   // wifi password
const char* serverUrl = "http://192.168.31.215:8001/update";

WiFiClient client; // Create a WiFiClient instance

void setup() {
    Serial.begin(115200);
    WiFi.begin(ssid, password);
    while (WiFi.status() != WL_CONNECTED) {
        delay(1000);
        Serial.println("Connecting to WiFi...");
    }
    Serial.println("Connected to WiFi");
}

void loop() {
    if (WiFi.status() == WL_CONNECTED) {
        HTTPClient http;

        // Simulate analog data
        int analogValue = analogRead(A0);

        // Create JSON payload
        String payload = "{\"AnalogData\":" + String(analogValue) + "}";

        // Start the connection using the WiFiClient instance
        http.begin(client, serverUrl);
        http.addHeader("Content-Type", "application/json");

        int httpResponseCode = http.POST(payload);

        if (httpResponseCode > 0) {
            Serial.println("Data sent successfully");
        } else {
            Serial.printf("Error sending data: %d\n", httpResponseCode);
        }

        http.end();
    } else {
        Serial.println("WiFi not connected");
    }

    delay(5000); // Send data every 5 seconds
}
