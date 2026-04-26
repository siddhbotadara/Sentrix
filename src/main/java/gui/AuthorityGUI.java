package gui;

import observer.DisasterBroadcaster;
import observer.Observer;
import models.SeverityLevel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AuthorityGUI extends JFrame implements Observer {
    private JTextArea dispatchLogs;
    private JLabel statusLabel;

    public AuthorityGUI() {
        setTitle("SENTRIX - Authority Command Center");
        setSize(500, 450);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(20, 25, 35));

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(new Color(40, 45, 60));
        header.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel title = new JLabel("EMERGENCY DISPATCH SYSTEM");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        header.add(title);
        add(header, BorderLayout.NORTH);

        dispatchLogs = new JTextArea();
        dispatchLogs.setEditable(false);
        dispatchLogs.setBackground(new Color(10, 15, 20));
        dispatchLogs.setForeground(new Color(0, 255, 100));
        dispatchLogs.setFont(new Font("Monospaced", Font.PLAIN, 13));
        dispatchLogs.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(new JScrollPane(dispatchLogs), BorderLayout.CENTER);

        statusLabel = new JLabel("STANDBY: READY FOR DISPATCH", SwingConstants.CENTER);
        statusLabel.setOpaque(true);
        statusLabel.setBackground(Color.DARK_GRAY);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setPreferredSize(new Dimension(0, 50));
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(statusLabel, BorderLayout.SOUTH);

        DisasterBroadcaster.getInstance().registerObserver(this);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void update(String type, String location, String message, SeverityLevel level) {

        if (type.equals("RESET")) {
            dispatchLogs.append("--- SYSTEM RESET: STANDBY MODE ---\n\n");
            statusLabel.setText("STANDBY: READY FOR DISPATCH");
            statusLabel.setBackground(Color.DARK_GRAY);
            return;
        }

        if (level == SeverityLevel.MEDIUM || level == SeverityLevel.CRITICAL) {
            String time = new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
            String logEntry = String.format("[%s] DEPLOYING %s UNITS to %s\n> Status: %s\n\n", 
                                            time, type, location.toUpperCase(), message);
            
            dispatchLogs.append(logEntry);
            statusLabel.setText("ACTIVE EMERGENCY: " + location.toUpperCase());
            statusLabel.setBackground(level.getCoreColor());
            
            // Notification sound
            Toolkit.getDefaultToolkit().beep();
        }
    }
}