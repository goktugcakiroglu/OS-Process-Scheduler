//COE302 - Operating Systems
//Assignment 3 - CPU Scheduling Simulator
//Members of Group 6:
//Goktug Cakiroglu - 220611008
//Cafer Aydin - 220611035
//Muhammet Aslan - 220611009
//Muhammed Yurtseven - 220611025

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GanttPanel extends JPanel {
    private List<String> cpuTimeline;
    private List<String> ioTimeline;
    private Map<String, Color> processColors = new HashMap<>();

    public GanttPanel() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1000, 180));
    }

    public void updateData(List<String> cpu, List<String> io) {
        this.cpuTimeline = cpu;
        this.ioTimeline = io;
        if (cpu == null || cpu.isEmpty()) {
            processColors.clear();
            setPreferredSize(new Dimension(1000, 180));
        } else {
            generateColors();
            setPreferredSize(new Dimension(Math.max(1000, (cpu.size() * 35) + 100), 180));
        }
        revalidate(); repaint();
    }

    private void generateColors() {
        Color[] palette = {new Color(199, 21, 133), new Color(0, 139, 139), new Color(218, 165, 32), new Color(178, 34, 34), new Color(72, 61, 139)};
        int colorIdx = 0;
        Set<String> allPids = new HashSet<>();
        if (cpuTimeline != null) allPids.addAll(cpuTimeline);
        if (ioTimeline != null) allPids.addAll(ioTimeline);
        
        for (String pid : allPids) {
            if (pid != null && !processColors.containsKey(pid)) {
                processColors.put(pid, palette[colorIdx % palette.length]);
                colorIdx++;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (cpuTimeline == null) return;
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int boxW = 35, boxH = 40; 
        
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.drawString("CPU Row", 10, 65);
        g2.drawString("I/O Row", 10, 125);

        for (int t = 0; t < cpuTimeline.size(); t++) {
            drawBox(g2, cpuTimeline.get(t), 80 + (t * boxW), 40, boxW, boxH);
            drawBox(g2, ioTimeline.get(t), 80 + (t * boxW), 100, boxW, boxH);
            
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.PLAIN, 12));
            FontMetrics fm = g2.getFontMetrics();
            int textW = fm.stringWidth(String.valueOf(t));
            g2.drawString(String.valueOf(t), 80 + (t * boxW) - (textW / 2), 35);
        }
        
        if (!cpuTimeline.isEmpty()) {
            int lastT = cpuTimeline.size();
            g2.setColor(Color.BLACK);
            FontMetrics fm = g2.getFontMetrics();
            int textW = fm.stringWidth(String.valueOf(lastT));
            g2.drawString(String.valueOf(lastT), 80 + (lastT * boxW) - (textW / 2), 35);
        }
    }

    private void drawBox(Graphics2D g2, String pid, int x, int y, int w, int h) {
        if (pid != null) {
            g2.setColor(processColors.get(pid));
            g2.fillRect(x, y, w, h);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 13));
            
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(pid);
            int textHeight = fm.getAscent();
            g2.drawString(pid, x + (w - textWidth) / 2, y + ((h + textHeight) / 2) - 2);
        }
        
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1)); 
        g2.drawRect(x, y, w, h);
    }
}