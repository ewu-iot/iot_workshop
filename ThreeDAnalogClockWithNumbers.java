import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ThreeDAnalogClockWithNumbers extends JPanel {

    private int centerX, centerY;
    private int radius;

    public ThreeDAnalogClockWithNumbers() {
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        radius = Math.min(centerX, centerY) - 10;

        drawClockFace(g);
        drawClockHands(g);
    }

    private void drawClockFace(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
        g.setColor(Color.BLACK);
        g.drawOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);

        // Draw clock dial numbers
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        for (int i = 1; i <= 12; i++) {
            double angle = Math.toRadians(360 - i * 30);
            int numberX = (int) (centerX + (radius - 20) * Math.cos(angle));
            int numberY = (int) (centerY - (radius - 20) * Math.sin(angle));
            g.drawString(Integer.toString(i), numberX, numberY);
        }
    }

    private void drawClockHands(Graphics g) {
        Calendar calendar = new GregorianCalendar();
        int hours = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);

        int secondAngle = seconds * 6;
        int minuteAngle = minutes * 6 + secondAngle / 60;
        int hourAngle = (hours * 30 + minuteAngle / 12) % 360;

        drawHand(g, centerX, centerY, radius * 0.5, secondAngle, Color.RED);
        drawHand(g, centerX, centerY, radius * 0.4, minuteAngle, Color.BLUE);
        drawHand(g, centerX, centerY, radius * 0.3, hourAngle, Color.GREEN);
    }

    private void drawHand(Graphics g, int x, int y, double length, int angle, Color color) {
        double radians = Math.toRadians(angle - 90);
        int x2 = (int) (x + length * Math.cos(radians));
        int y2 = (int) (y + length * Math.sin(radians));

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(5));
        g2d.setColor(color);
        g2d.drawLine(centerX, centerY, x2, y2);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("3D Analog Clock with Numbers");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 400);
                frame.add(new ThreeDAnalogClockWithNumbers());
                frame.setVisible(true);
            }
        });
    }
}
