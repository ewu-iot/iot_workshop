import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.net.InetSocketAddress;

public class AnalogData3DGaugeServer {

    private static Gauge3DPanel gaugePanel;

    public static void main(String[] args) throws IOException {
        // Set up GUI
        JFrame frame = new JFrame("3D Analog Data Gauge");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        gaugePanel = new Gauge3DPanel();

        frame.add(gaugePanel, BorderLayout.CENTER);
        frame.setVisible(true);

        // Set up HTTP server
        HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);
        server.createContext("/update", new AnalogDataHandler());
        server.setExecutor(null);
        server.start();

        System.out.println("Server is listening on port 8001...");
    }

    static class AnalogDataHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                InputStream inputStream = exchange.getRequestBody();
                String body = new BufferedReader(new InputStreamReader(inputStream))
                        .lines()
                        .reduce("", (acc, line) -> acc + line);

                System.out.println("Received data: " + body);
                updateAnalogData(body);

                String response = "Analog data updated successfully";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.close();
            } else {
                exchange.sendResponseHeaders(405, -1); // Method not allowed
            }
        }

        private void updateAnalogData(String json) {
            SwingUtilities.invokeLater(() -> {
                try {
                    String processedJson = json.replace("{", "").replace("}", "").replace("\"", "");
                    String[] pairs = processedJson.split(",");

                    for (String pair : pairs) {
                        String[] keyValue = pair.split(":");
                        String key = keyValue[0].trim();
                        String value = keyValue[1].trim();

                        if (key.equals("AnalogData")) {
                            int analogValue = Integer.parseInt(value);
                            gaugePanel.setValue(analogValue);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}

// Custom JPanel for 3D Gauge
class Gauge3DPanel extends JPanel {
    private int value = 0;

    public void setValue(int value) {
        this.value = Math.min(Math.max(value, 0), 1023); // Ensure value is within [0, 1023]
        repaint(); // Redraw the panel
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Gauge dimensions
        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = Math.min(width, height) / 3;

        // 3D Background
        g2.setPaint(new GradientPaint(centerX - radius, centerY - radius, Color.DARK_GRAY, centerX + radius, centerY + radius, Color.LIGHT_GRAY));
        g2.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);

        // Inner 3D shadow
        g2.setPaint(new GradientPaint(centerX - radius, centerY - radius, Color.LIGHT_GRAY, centerX + radius, centerY + radius, Color.DARK_GRAY));
        g2.fillOval(centerX - (radius - 10), centerY - (radius - 10), 2 * (radius - 10), 2 * (radius - 10));

        // Gauge ticks
        g2.setColor(Color.BLACK);
        for (int i = 0; i <= 10; i++) {
            double angle = Math.toRadians(180 + i * 18);
            int x1 = centerX + (int) (radius * Math.cos(angle));
            int y1 = centerY + (int) (radius * Math.sin(angle));
            int x2 = centerX + (int) ((radius - 20) * Math.cos(angle));
            int y2 = centerY + (int) ((radius - 20) * Math.sin(angle));
            g2.drawLine(x1, y1, x2, y2);
        }

        // Needle shadow
        double needleAngle = Math.toRadians(180 + (value / 1023.0) * 180);
        int shadowX = centerX + (int) ((radius - 30) * Math.cos(needleAngle));
        int shadowY = centerY + (int) ((radius - 30) * Math.sin(needleAngle));

        g2.setColor(new Color(100, 100, 100, 100));
        g2.setStroke(new BasicStroke(4));
        g2.drawLine(centerX + 2, centerY + 2, shadowX + 2, shadowY + 2);

        // Needle
        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(centerX, centerY, shadowX, shadowY);

        // Center cap
        g2.setColor(Color.BLACK);
        g2.fillOval(centerX - 5, centerY - 5, 10, 10);

        // Value display
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.setColor(Color.BLACK);
        g2.drawString("Analog Value: " + value, centerX - 50, centerY + radius + 30);
    }
}
