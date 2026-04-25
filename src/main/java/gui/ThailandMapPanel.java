package gui;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGElement;
import com.kitfox.svg.SVGUniverse;
import com.kitfox.svg.SVGException;
import com.kitfox.svg.animation.AnimationElement;
import models.SeverityLevel;
import models.AmbulanceAnimation;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ThailandMapPanel extends JPanel {
    private SVGUniverse svgUniverse = new SVGUniverse();
    private SVGDiagram diagram;
    private DisasterOverlay disasterOverlay;

    private BufferedImage ambulanceImg;
    private AmbulanceAnimation ambAnim = new AmbulanceAnimation();

    private BufferedImage helicopterImg;
    private AmbulanceAnimation heliAnim = new AmbulanceAnimation();
    
    private Point baseStation = new Point(449, 460);

    private BufferedImage wallImg, drainageImg, droneImg, shieldImg, coolingImg;
    private String criticalDisasterType = null;
    private String criticalCity = null;
    private double animTimer = 0; 

    public ThailandMapPanel() {
        setBackground(new Color(11, 14, 20));
        this.disasterOverlay = new DisasterOverlay();
        loadMap();
        loadAssets();

        Timer timer = new Timer(16, e -> {
            if (ambAnim.active) ambAnim.update();
            if (heliAnim.active) heliAnim.update(); 
            if (ambAnim.active || heliAnim.active) repaint();
            animTimer += 0.05;
        });
        timer.start();
    }

    private void loadMap() {
        try {
            URL url = getClass().getResource("/images/thailand_map_3.svg");
            if (url != null) {
                diagram = svgUniverse.getDiagram(svgUniverse.loadSVG(url));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAssets() {
        try {
            URL ambUrl = getClass().getResource("/images/ambulance.png");
            helicopterImg = ImageIO.read(getClass().getResource("/images/helicopter.png"));
            wallImg = ImageIO.read(getClass().getResource("/images/seawall.png"));
            drainageImg = ImageIO.read(getClass().getResource("/images/fan.png"));
            droneImg = ImageIO.read(getClass().getResource("/images/drone.png"));
            shieldImg = ImageIO.read(getClass().getResource("/images/shield.png"));
            coolingImg = ImageIO.read(getClass().getResource("/images/coolingcenter.png"));
            if (ambUrl != null) {
                ambulanceImg = ImageIO.read(ambUrl);
            }
        } catch (Exception e) {
            System.err.println("Error loading ambulance asset: " + e.getMessage());
        }
    }

    public void showCriticalSolution(String type, String city) {
        this.criticalDisasterType = type;
        this.criticalCity = city;
        this.animTimer = 0; 
        repaint();
    }

    public void deployHelicopter(String cityName) {
        Point target = disasterOverlay.getCityLocation(cityName);
        if (target != null) {
            heliAnim.speed = 8.0; 
            heliAnim.start(baseStation, target);
        }
    }

    public void deployAmbulance(String cityName) {
        Point target = disasterOverlay.getCityLocation(cityName);
        if (target != null) {
            ambAnim.start(baseStation, target);
        }
    }

    public void triggerDisasterOverlay(String cityName, int radius, Color color) {
        disasterOverlay.setEffect(cityName, radius, color);
        repaint();
    }

    public void clearOverlay() {
        disasterOverlay.clear();
        this.criticalDisasterType = null;
        this.criticalCity = null;
        repaint();
    }

    public void setProvinceStatus(String id, SeverityLevel level) {
        if (diagram == null) return;
        try {
            SVGElement element = diagram.getElement(id);
            if (element != null) {
                Color c = level.getCoreColor(); 
                String hex = String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
                element.setAttribute("fill", AnimationElement.AT_XML, hex);
                diagram.updateTime(0);
                repaint();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawCriticalSolutions(Graphics2D g2) {
        if (criticalDisasterType == null || criticalCity == null) return;
        
        Point p = disasterOverlay.getCityLocation(criticalCity);
        if (p == null) return;

        int x = p.x;
        int y = p.y;

        switch (criticalDisasterType) {
            case "TSUNAMI":
                int wallHeight = (int) Math.min(30, animTimer * 10);
                g2.setColor(new Color(100, 100, 100, 200));
                g2.fillRect(x - 40, y + 20 - wallHeight, 80, wallHeight);
                if (wallImg != null) g2.drawImage(wallImg, x - 40, y + 20 - wallHeight, 80, wallHeight, null);
                break;

            case "FLOOD":
                if (drainageImg != null) {
                    AffineTransform at = new AffineTransform();
                    at.translate(x, y);
                    at.rotate(animTimer * 2); 
                    at.translate(-25, -25);
                    g2.drawImage(drainageImg, at, null);
                }
                break;

            case "EARTHQUAKE":

                int radius = 50;
                int dx = (int) (x + radius * Math.cos(animTimer));
                int dy = (int) (y + radius * Math.sin(animTimer));
                if (droneImg != null) g2.drawImage(droneImg, dx - 15, dy - 15, 30, 30, null);
                break;

            case "TYPHOON":

                float scale = 0.8f + (float) Math.abs(Math.sin(animTimer)) * 0.4f;
                if (shieldImg != null) {
                    int sw = (int)(30 * scale);
                    g2.drawImage(shieldImg, x - sw/2, y - sw/2, sw, sw, null);
                }
                break;

            case "HEATWAVE":

                g2.setColor(new Color(0, 200, 255, 50));
                int glow = 40 + (int)(10 * Math.sin(animTimer * 2));
                g2.fillOval(x - glow/2, y - glow/2, glow, glow);
                if (coolingImg != null) g2.drawImage(coolingImg, x - 20, y - 20, 40, 40, null);
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (diagram == null) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double scaleX = (double) getWidth() / diagram.getWidth();
        double scaleY = (double) getHeight() / diagram.getHeight();
        double scale = Math.min(scaleX, scaleY) * 0.95;

        AffineTransform oldTransform = g2.getTransform();
        g2.translate((getWidth() - diagram.getWidth() * scale) / 2, 
                     (getHeight() - diagram.getHeight() * scale) / 2);
        g2.scale(scale, scale);

        try {
            diagram.render(g2);
            disasterOverlay.draw(g2);
            drawCriticalSolutions(g2);

            if (ambAnim.active && ambulanceImg != null) {
                AffineTransform atAmb = new AffineTransform();
                atAmb.translate(ambAnim.currentX, ambAnim.currentY);
                atAmb.rotate(ambAnim.angle);
                atAmb.translate(-ambulanceImg.getWidth() / 2.0, -ambulanceImg.getHeight() / 2.0);
                g2.drawImage(ambulanceImg, atAmb, null);
            }

            if (heliAnim.active && helicopterImg != null) {
                AffineTransform atHeli = new AffineTransform();
                atHeli.translate(heliAnim.currentX, heliAnim.currentY);
                atHeli.rotate(heliAnim.angle);
                atHeli.translate(-helicopterImg.getWidth() / 2.0, -helicopterImg.getHeight() / 2.0);
                g2.drawImage(helicopterImg, atHeli, null);
            }

        } catch (SVGException e) {
            e.printStackTrace();
        }
        g2.setTransform(oldTransform);
    }
}