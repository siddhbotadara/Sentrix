package gui;

import models.SeverityLevel;
import sensors.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.Arrays;

public class MainDashboard extends JFrame {
    private ThailandMapPanel mapPanel;
    private JTextArea consoleArea;
    private JPanel sensorListPanel;
    private JLabel nationalTempLabel;

    // Geographically dispersed regions to cover all of Thailand
    private final List<String> REGIONS = Arrays.asList(
        "Chiang Mai",   // NORTH
        "Khon Kaen",    // NORTHEAST
        "Bangkok",      // CENTRAL
        "Kanchanaburi", // WEST
        "Chon Buri",    // EAST / COASTAL
        "Phuket"        // SOUTH / PENINSULA
    );

    public MainDashboard() {
        setTitle("SENTRIX COMMAND CENTER");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1400, 850);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(11, 14, 20));

        initUI();
    }

    private void initUI() {
        // --- 1. CENTER: THE MAP ---
        mapPanel = new ThailandMapPanel();
        add(mapPanel, BorderLayout.CENTER);

        // --- 2. WEST: SENSOR PANEL (TELEMETRY) ---
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(320, 0));
        sidebar.setBackground(new Color(21, 25, 33));
        sidebar.setBorder(new EmptyBorder(15, 15, 15, 15));

        // National Header Card
        JPanel nationalCard = new JPanel(new GridLayout(2, 1));
        nationalCard.setBackground(new Color(0, 229, 255, 30));
        nationalCard.setBorder(BorderFactory.createLineBorder(new Color(0, 229, 255), 1));
        
        JLabel natTitle = new JLabel(" THAILAND (OVERALL)", SwingConstants.LEFT);
        natTitle.setForeground(new Color(0, 229, 255));
        natTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        nationalTempLabel = new JLabel(" Synchronizing...", SwingConstants.LEFT);
        nationalTempLabel.setForeground(Color.WHITE);
        
        nationalCard.add(natTitle);
        nationalCard.add(nationalTempLabel);
        sidebar.add(nationalCard, BorderLayout.NORTH);

        // Region List
        sensorListPanel = new JPanel();
        sensorListPanel.setLayout(new BoxLayout(sensorListPanel, BoxLayout.Y_AXIS));
        sensorListPanel.setBackground(new Color(21, 25, 33));
        
        JScrollPane sensorScroll = new JScrollPane(sensorListPanel); // Renamed to avoid error
        sensorScroll.setBorder(null);
        sensorScroll.getVerticalScrollBar().setUnitIncrement(12);
        sidebar.add(sensorScroll, BorderLayout.CENTER);

        add(sidebar, BorderLayout.WEST);

        // --- 3. EAST: CONTROL PANEL (RESTORED) ---
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setPreferredSize(new Dimension(250, 0));
        controlPanel.setBackground(new Color(21, 25, 33));
        controlPanel.setBorder(new EmptyBorder(20, 15, 20, 15));

        JLabel ctrlTitle = new JLabel("SYSTEM CONTROLS");
        ctrlTitle.setForeground(new Color(255, 80, 80));
        ctrlTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        controlPanel.add(ctrlTitle);
        controlPanel.add(Box.createVerticalStrut(20));

        JButton tsunamiBtn = createStyledButton("TRIGGER TSUNAMI", new Color(255, 0, 85));
        tsunamiBtn.addActionListener(e -> triggerEvent("TSUNAMI"));
        controlPanel.add(tsunamiBtn);
        controlPanel.add(Box.createVerticalStrut(10));

        JButton resetBtn = createStyledButton("SYSTEM RESET", new Color(100, 100, 100));
        resetBtn.addActionListener(e -> triggerEvent("RESET"));
        controlPanel.add(resetBtn);

        add(controlPanel, BorderLayout.EAST);

        // --- 4. SOUTH: CONSOLE ---
        initConsole();

        // Start Telemetry Refresh (Every 1 minute)
        Timer timer = new Timer(60000, e -> refreshSensors());
        timer.start();
        refreshSensors();
    }

    private void initConsole() {
        consoleArea = new JTextArea(8, 0);
        consoleArea.setBackground(new Color(5, 7, 10));
        consoleArea.setForeground(new Color(0, 229, 255));
        consoleArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        consoleArea.setEditable(false);
        
        JScrollPane consoleScroll = new JScrollPane(consoleArea); // Renamed to avoid error
        consoleScroll.setBorder(BorderFactory.createLineBorder(new Color(40, 45, 60)));
        add(consoleScroll, BorderLayout.SOUTH);
    }

    private void refreshSensors() {
        log("Querying regional relay stations...");
        new Thread(() -> {
            // Update National Overview first
            WeatherData national = SensorFactory.fetchRealTimeData("Thailand (Overall)");
            SwingUtilities.invokeLater(() -> 
                nationalTempLabel.setText(" AVG TEMP: " + national.temp + "°C | " + national.description.toUpperCase()));

            // Update Regions
            sensorListPanel.removeAll();
            for (String city : REGIONS) {
                WeatherData data = SensorFactory.fetchRealTimeData(city);
                SwingUtilities.invokeLater(() -> {
                    sensorListPanel.add(createRegionRow(data));
                    sensorListPanel.revalidate();
                    sensorListPanel.repaint();
                });
            }
        }).start();
    }

    private JPanel createRegionRow(WeatherData data) {
        JPanel p = new JPanel(new GridLayout(2, 1));
        p.setBackground(new Color(30, 35, 45));
        p.setMaximumSize(new Dimension(300, 70));
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(45, 50, 65)),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel name = new JLabel(data.cityName.toUpperCase() + "  " + data.temp + "°C");
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SansSerif", Font.BOLD, 12));
        
        JLabel stats = new JLabel("Wind: " + data.windSpeed + "m/s | Hum: " + data.humidity + "%");
        stats.setFont(new Font("Monospaced", Font.PLAIN, 10));
        stats.setForeground(new Color(150, 150, 150));

        p.add(name);
        p.add(stats);
        return p;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setMaximumSize(new Dimension(220, 45));
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("SansSerif", Font.BOLD, 11));
        return b;
    }

    private void triggerEvent(String action) {
        if (action.equals("TSUNAMI")) {
            mapPanel.setProvinceStatus("TH-83", SeverityLevel.CRITICAL); // Phuket
            log("CRITICAL: Seismic anomaly detected in TH-83. Deploying coastal barriers.");
        } else {
            mapPanel.setProvinceStatus("TH-83", SeverityLevel.SAFE);
            log("SYSTEM: Emergency protocols disengaged. All provinces nominal.");
        }
    }

    public void log(String message) {
        if (consoleArea != null) {
            consoleArea.append("[" + new java.util.Date().toString().substring(11, 19) + "] " + message + "\n");
            consoleArea.setCaretPosition(consoleArea.getDocument().getLength());
        }
    }
}