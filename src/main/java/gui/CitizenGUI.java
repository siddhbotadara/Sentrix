package gui;

import observer.DisasterBroadcaster;
import observer.Observer;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CitizenGUI extends JFrame implements Observer {
    private JTextField locationField;
    private JLabel statusLabel;
    private JTextArea instructionArea;
    private String currentRegisteredLocation = "";

    public CitizenGUI() {
        setTitle("SENTRIX - Citizen Alert Terminal");
        setSize(450, 350);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(15, 20, 25));

        // Header: Location Entry
        JPanel header = new JPanel();
        header.setBackground(new Color(30, 35, 45));
        locationField = new JTextField(12);
        JButton regBtn = new JButton("REGISTER");
        header.add(new JLabel("ENTER CITY:"));
        header.add(locationField);
        header.add(regBtn);
        add(header, BorderLayout.NORTH);

        // Body: Alert Status
        JPanel body = new JPanel(new BorderLayout(10, 10));
        body.setOpaque(false);
        body.setBorder(new EmptyBorder(20, 20, 20, 20));

        statusLabel = new JLabel("SYSTEM STATUS: SAFE", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        statusLabel.setForeground(new Color(0, 255, 150));
        
        instructionArea = new JTextArea("Waiting for regional registration...");
        instructionArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        instructionArea.setLineWrap(true);
        instructionArea.setWrapStyleWord(true);
        instructionArea.setEditable(false);
        instructionArea.setBackground(new Color(10, 12, 16));
        instructionArea.setForeground(Color.LIGHT_GRAY);

        body.add(statusLabel, BorderLayout.NORTH);
        body.add(new JScrollPane(instructionArea), BorderLayout.CENTER);
        add(body, BorderLayout.CENTER);

        regBtn.addActionListener(e -> {
            currentRegisteredLocation = locationField.getText().trim();
            if (!currentRegisteredLocation.isEmpty()) {
                DisasterBroadcaster.getInstance().registerObserver(this);
                regBtn.setEnabled(false);
                locationField.setEditable(false);
                instructionArea.setText("Registered to " + currentRegisteredLocation + " emergency network.");
            }
        });
    }

    @Override
    public void update(String type, String location, String message) {
        if (type.equals("RESET")) {
            statusLabel.setText("SYSTEM STATUS: SAFE");
            statusLabel.setForeground(new Color(0, 255, 150)); // Back to Green
            instructionArea.setText("Protocols reset. All clear.");
            return;
        }

        // FILTER: Only show alert if it matches the citizen's location
        if (currentRegisteredLocation.equalsIgnoreCase(location)) {
            // The UI updates instantly here without requiring a click
            statusLabel.setText("CRITICAL: " + type + "!");
            statusLabel.setForeground(Color.RED);
            
            // Update the text area with the safety instructions
            instructionArea.setText("LOCATION: " + location.toUpperCase() + "\n\n" + message);
            
            // Optional: You could add a beep sound here instead of a popup
            Toolkit.getDefaultToolkit().beep(); 
        }
    }
}