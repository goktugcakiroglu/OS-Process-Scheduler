//COE302 - Operating Systems
//Assignment 3 - CPU Scheduling Simulator
//Members of Group 6:
//Goktug Cakiroglu - 220611008
//Cafer Aydin - 220611035
//Muhammet Aslan - 220611009
//Muhammed Yurtseven - 220611025

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SimulatorGUI extends JFrame {
    
    private InputPanel inputPanel;
    private GanttPanel ganttPanel;
    private StatsPanel statsPanel;
    private SimulationEngine engine;
    private JScrollPane ganttScroll; 

    public SimulatorGUI() {
        super("CPU Scheduling Simulator (Group 6)");
        setSize(1100, 750); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        inputPanel = new InputPanel(this); 
        ganttPanel = new GanttPanel();
        statsPanel = new StatsPanel();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        gbc.gridy = 0;
        gbc.weighty = 0.5; 
        gbc.insets = new Insets(5, 5, 5, 5);
        add(inputPanel, gbc);

        ganttScroll = new JScrollPane(ganttPanel);
        ganttScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        ganttScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        ganttScroll.setBorder(BorderFactory.createTitledBorder(null, "Gantt Chart Visualization", 0, 0, new Font("Arial", Font.BOLD, 14)));
        
        ganttScroll.setPreferredSize(new Dimension(0, 200)); 
        ganttScroll.setMinimumSize(new Dimension(0, 200));
        
        gbc.gridy = 1;
        gbc.weighty = 0.0; 
        gbc.insets = new Insets(0, 5, 5, 5);
        add(ganttScroll, gbc);

        gbc.gridy = 2;
        gbc.weighty = 0.5;
        gbc.insets = new Insets(0, 5, 5, 5);
        add(statsPanel, gbc);
    }

    public void startSimulation(String algo, int quantum, List<Process> processes) {
        engine = new SimulationEngine(processes);
        if (algo.equals("FCFS")) engine.setStrategy(new FCFSStrategy());
        else if (algo.equals("RR")) engine.setStrategy(new RRStrategy());
        else if (algo.equals("STCF")) engine.setStrategy(new STCFStrategy());

        engine.runSimulation(quantum);

        ganttPanel.updateData(engine.cpuTimeline, engine.ioTimeline);
        statsPanel.updateStats(engine.terminatedList, engine.cpuTimeline.size());
    }

    public void resetDisplay() {
        ganttPanel.updateData(null, null); 
        statsPanel.updateStats(new ArrayList<>(), 0);
        SwingUtilities.invokeLater(() -> ganttScroll.getHorizontalScrollBar().setValue(0));
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new SimulatorGUI().setVisible(true));
    }
}