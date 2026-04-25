import com.formdev.flatlaf.FlatDarkLaf;
import gui.MainDashboard;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            UIManager.put("ProgressBar.arc", 999);
            UIManager.put("Component.arc", 10);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            MainDashboard dashboard = new MainDashboard();
            dashboard.setVisible(true);
            dashboard.log("SENTRIX Initialized. Waiting for disasters...");
        });
    }
}