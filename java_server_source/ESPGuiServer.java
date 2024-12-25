import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.InetSocketAddress;

public class ESPGuiServer {
    private static JLabel relay1Label;
    private static JLabel relay2Label;
    private static JLabel relay3Label;

    public static void main(String[] args) {
        // Start the GUI
        SwingUtilities.invokeLater(() -> createAndShowGUI());

        // Start the HTTP server
        new Thread(ESPGuiServer::startServer).start();
    }

    private static void createAndShowGUI() {
        // Create the main window
        JFrame frame = new JFrame("ESP Relay State Monitor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        // Create labels to display relay states
        relay1Label = new JLabel("Relay 1: OFF", SwingConstants.CENTER);
        relay2Label = new JLabel("Relay 2: OFF", SwingConstants.CENTER);
        relay3Label = new JLabel("Relay 3: OFF", SwingConstants.CENTER);

        relay1Label.setFont(new Font("Arial", Font.BOLD, 18));
        relay2Label.setFont(new Font("Arial", Font.BOLD, 18));
        relay3Label.setFont(new Font("Arial", Font.BOLD, 18));

        // Arrange labels in a vertical layout
        frame.setLayout(new GridLayout(3, 1));
        frame.add(relay1Label);
        frame.add(relay2Label);
        frame.add(relay3Label);

        // Show the GUI
        frame.setVisible(true);
    }

    private static void startServer() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            System.out.println("Server started on port 8080");

            // Define the endpoint and handler
            server.createContext("/update", new RelayHandler());
            server.setExecutor(null); // Use the default executor
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class RelayHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStream inputStream = exchange.getRequestBody();
                String body = new BufferedReader(new InputStreamReader(inputStream))
                        .lines()
                        .reduce("", (acc, line) -> acc + line);

                System.out.println("Received data: " + body);
                updateRelayStates(body);

                String response = "State updated successfully";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.close();
            } else {
                exchange.sendResponseHeaders(405, -1); // Method not allowed
            }
        }

        private void updateRelayStates(String json) {
            SwingUtilities.invokeLater(() -> {
                try {
                    String processedJson = json.replace("{", "").replace("}", "").replace("\"", "");
                    String[] pairs = processedJson.split(",");

                    for (String pair : pairs) {
                        String[] keyValue = pair.split(":");
                        String key = keyValue[0].trim();
                        String value = keyValue[1].trim();

                        if (key.equals("Relay1")) {
                            relay1Label.setText("Relay 1: " + value.toUpperCase());
                            relay1Label.setForeground(value.equals("ON") ? Color.GREEN : Color.RED);
                        } else if (key.equals("Relay2")) {
                            relay2Label.setText("Relay 2: " + value.toUpperCase());
                            relay2Label.setForeground(value.equals("ON") ? Color.GREEN : Color.RED);
                        } else if (key.equals("Relay3")) {
                            relay3Label.setText("Relay 3: " + value.toUpperCase());
                            relay3Label.setForeground(value.equals("ON") ? Color.GREEN : Color.RED);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
