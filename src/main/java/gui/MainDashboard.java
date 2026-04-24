package gui;

import models.SeverityLevel;
import observer.DisasterBroadcaster;
import sensors.*;
import strategy.DisasterResponseStrategy;
import strategy.EarthquakeResponse;
import strategy.FloodResponse;
import strategy.HeatwaveResponse;
import strategy.TsunamiResponse;
import strategy.TyphoonResponse;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.LinkedHashMap;

import decorator.*;

public class MainDashboard extends JFrame {
    private ThailandMapPanel mapPanel;
    private JTextArea consoleArea;
    private JPanel sensorListPanel;
    private JLabel nationalTempLabel;
    private static final Map<String, Color> DISASTER_THEMES;

    static {
        DISASTER_THEMES = new LinkedHashMap<>();
        DISASTER_THEMES.put("TSUNAMI",    new Color(43, 108, 176)); // Steel Blue
        DISASTER_THEMES.put("FLOOD",      new Color(56, 178, 172)); // Teal
        DISASTER_THEMES.put("EARTHQUAKE", new Color(155, 107, 44)); // Bronze/Dirt
        DISASTER_THEMES.put("TYPHOON",    new Color(112, 55, 148)); // Deep Grape
        DISASTER_THEMES.put("HEATWAVE",   new Color(197, 48, 48));  // Crimson
    }

    // Geographically dispersed regions to cover all of Thailand
    private final List<String> REGIONS = Arrays.asList(
        "Bangkok",      // CENTRAL
        "Phuket",        // SOUTH / PENINSULA
        "Chiang Rai",
        "Chiang Mai",   // NORTH
        "Kanchanaburi", // WEST
        "Songkhla",
        "Khon Kaen",    // NORTHEAST   
        "Chumphon"
        
    );

    public MainDashboard() {
        setTitle("SENTRIX COMMAND CENTER");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1500, 900);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(new Color(11, 14, 20));

        initUI();
    }

    private void initUI() {

        // --- 1. CENTER: THE MAP ---
        mapPanel = new ThailandMapPanel();
        add(mapPanel, BorderLayout.CENTER);

        // --- 2. WEST: SENSOR PANEL (TELEMETRY) ---
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(430, 10));
        sidebar.setBackground(new Color(21, 25, 33));
        sidebar.setBorder(new EmptyBorder(15, 15, 15, 15));

        // National Header Card
        JPanel nationalCard = new JPanel(new GridLayout(2, 1));
        nationalCard.setBackground(new Color(0, 229, 255, 30));
        nationalCard.setBorder(BorderFactory.createLineBorder(new Color(0, 229, 255), 1));
        
        JLabel natTitle = new JLabel(" THAILAND (OVERALL)", SwingConstants.LEFT);
        natTitle.setForeground(new Color(0, 229, 255));
        natTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        
        nationalTempLabel = new JLabel(" Synchronizing...", SwingConstants.LEFT);
        nationalTempLabel.setForeground(Color.WHITE);
        
        nationalCard.add(natTitle);
        nationalCard.add(nationalTempLabel);
        sidebar.add(nationalCard, BorderLayout.NORTH);

        // Region List
        sensorListPanel = new JPanel();
        sensorListPanel.setLayout(new BoxLayout(sensorListPanel, BoxLayout.Y_AXIS));
        sensorListPanel.setBackground(new Color(21, 25, 33));
        
        JScrollPane sensorScroll = new JScrollPane(sensorListPanel);
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
        ctrlTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        ctrlTitle.setForeground(new Color(255, 80, 80));
        ctrlTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        controlPanel.add(ctrlTitle);

        controlPanel.add(Box.createVerticalStrut(30));

        // Map disaster names to their actual Strategy objects
        Map<String, DisasterResponseStrategy> simulations = new LinkedHashMap<>();
        simulations.put("TSUNAMI", new TsunamiResponse());
        simulations.put("FLOOD", new FloodResponse());
        simulations.put("EARTHQUAKE", new EarthquakeResponse());
        simulations.put("TYPHOON", new TyphoonResponse());
        simulations.put("HEATWAVE", new HeatwaveResponse());


        // DYNAMICALLY CREATE 5 BUTTONS
        for (String type : simulations.keySet()) {
            Color themeColor = DISASTER_THEMES.getOrDefault(type, Color.RED);
            JButton btn = createStyledButton("TRIGGER " + type, themeColor);
            // Inside the button loop in initUI()
            btn.addActionListener(e -> {
                // 1. Select Severity
                SeverityLevel[] levels = SeverityLevel.values();
                SeverityLevel selectedLevel = (SeverityLevel) JOptionPane.showInputDialog(
                        this, "Select Severity for " + type, "SENTRIX SECURE",
                        JOptionPane.QUESTION_MESSAGE, null, levels, levels[1]);

                if (selectedLevel == null) return;

                // 2. Determine Target City (Java 8 Switch)
                String targetCity;
                switch (type) {
                    case "TSUNAMI":    targetCity = "Phuket"; break;
                    case "EARTHQUAKE": targetCity = "Chiang Rai"; break;
                    case "FLOOD":      targetCity = "Chiang Mai"; break;
                    case "TYPHOON":    targetCity = "Chumphon"; break;
                    case "HEATWAVE":   targetCity = "Khon Kaen"; break;
                    default:           targetCity = "Bangkok"; break;
                }

                // 3. Generate Message based on Severity
                String helpMsg = getCustomMessage(type, selectedLevel);

                // 4. Implement Decorator Chain
                Alert alertChain = new MapVisualDecorator(
                    new CitizenBroadcasterDecorator(
                        new BasicAlert(this)
                    ), mapPanel
                );

                // 5. Fire Alert
                alertChain.issue(type, targetCity, helpMsg, selectedLevel);
            });

            controlPanel.add(btn);
            controlPanel.add(Box.createVerticalStrut(55));
        }

        controlPanel.add(Box.createVerticalGlue()); 

        JButton openCitizenBtn = createStyledButton("OPEN CITIZEN APP", new Color(70, 70, 70));
        openCitizenBtn.addActionListener(e -> {
            new CitizenGUI().setVisible(true);
        });
        controlPanel.add(openCitizenBtn);

        // SYSTEM RESET BUTTON
        JButton resetBtn = createStyledButton("SYSTEM RESET", new Color(100, 100, 100));
        resetBtn.addActionListener(e -> {
            mapPanel.clearOverlay();
            log("SYSTEM: Recovery protocols complete. Map overlays cleared.");
            // Notify all observers to reset
            DisasterBroadcaster.getInstance().notifyObservers("RESET", "ALL", "Clear", SeverityLevel.SAFE);
        });
        controlPanel.add(Box.createVerticalGlue());
        controlPanel.add(resetBtn);

        controlPanel.add(Box.createVerticalStrut(10));

        add(controlPanel, BorderLayout.EAST);

        // --- 4. SOUTH: CONSOLE ---   
        consoleArea = new JTextArea(7, 0); // INCREASED HEIGHT (8 -> 12 lines)
        consoleArea.setBackground(new Color(10, 12, 16));
        consoleArea.setForeground(new Color(0, 255, 100)); // Terminal Green
        consoleArea.setFont(new Font("Monospaced", Font.BOLD, 17)); // LARGER TEXT
        consoleArea.setMargin(new Insets(15, 15, 15, 15));
        
        JScrollPane consoleScroll = new JScrollPane(consoleArea);
        consoleScroll.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(40, 45, 60)));
        add(consoleScroll, BorderLayout.SOUTH);

        // Start Telemetry
        new Timer(30000, e -> refreshSensors()).start();
        refreshSensors();
    }

    // Helper method for messages
    private String getTargetCity(String type) {
        // Java 8 Switch Syntax
        switch (type) {
            case "TSUNAMI":    return "Phuket";
            case "EARTHQUAKE": return "Chiang Rai";
            case "FLOOD":      return "Chiang Mai";
            case "TYPHOON":    return "Chumphon";
            case "HEATWAVE":   return "Khon Kaen";
            default:           return "Bangkok";
        }
    }

    private String getCustomMessage(String type, SeverityLevel level) {
        if (level == SeverityLevel.LOW) return "Minor " + type + " activity detected.";
        if (level == SeverityLevel.MEDIUM) return "WARNING: High " + type + " risk. Prepare kits.";
        return "CRITICAL: " + type + " IMPACT IMMINENT. EVACUATE.";
    }

    private void initConsole() {
        consoleArea = new JTextArea(12, 0);
        consoleArea.setBackground(new Color(5, 7, 10));
        consoleArea.setForeground(new Color(0, 229, 255));
        consoleArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        consoleArea.setEditable(false);
        
        JScrollPane consoleScroll = new JScrollPane(consoleArea);
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
        JPanel p = new JPanel(new GridLayout(3, 1)); // Increased to 3 rows
        p.setBackground(new Color(30, 35, 45));
        p.setMaximumSize(new Dimension(500, 120)); // Wider and taller
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(45, 50, 65)),
            new EmptyBorder(15, 20, 15, 20)
        ));
        
        JLabel name = new JLabel(data.cityName.toUpperCase() + "  " + data.temp + "°C");
        name.setForeground(new Color(0, 229, 255)); // Cyber Cyan
        name.setFont(new Font("SansSerif", Font.BOLD, 15));
        
        // Line 1: Basic Stats
        JLabel stats1 = new JLabel("WIND: " + data.windSpeed + "m/s | HUM: " + data.humidity + "%");
        stats1.setFont(new Font("Monospaced", Font.PLAIN, 14));
        stats1.setForeground(Color.LIGHT_GRAY);

        // Line 2: Advanced Stats (New)
        String visStr = String.format("%.1fkm", data.visibility / 1000.0);
        JLabel stats2 = new JLabel("PRES: " + data.pressure + "hPa | VIS: " + visStr + " | RAIN: " + data.rain + "mm");
        stats2.setFont(new Font("Monospaced", Font.PLAIN, 13));
        stats2.setForeground(new Color(100, 180, 100)); // Tactical Green

        p.add(name);
        p.add(stats1);
        p.add(stats2);
        return p;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton b = new JButton(text);
        
        // INCREASE THESE NUMBERS for bigger buttons
        // Width 240 -> 260 | Height 60 -> 70
        Dimension size = new Dimension(260, 50); 
        
        b.setPreferredSize(size);
        b.setMinimumSize(size);
        b.setMaximumSize(size); // Critical for BoxLayout to prevent stretching
        
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(bg.brighter(), 2));
        b.setFont(new Font("SansSerif", Font.BOLD, 14)); // Slightly larger font
        
        // FORCE CENTER ALIGNMENT
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { b.setBackground(bg.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { b.setBackground(bg); }
        });
        
        return b;
    }

    public void log(String message) {
        if (consoleArea != null) {
            consoleArea.append("[" + new java.util.Date().toString().substring(11, 19) + "] " + message + "\n");
            consoleArea.setCaretPosition(consoleArea.getDocument().getLength());
        }
    }
}