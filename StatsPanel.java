//COE302 - Operating Systems
//Assignment 3 - CPU Scheduling Simulator
//Members of Group 6:
//Goktug Cakiroglu - 220611008
//Cafer Aydin - 220611035
//Muhammet Aslan - 220611009
//Muhammed Yurtseven - 220611025

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StatsPanel extends JPanel {
    private DefaultTableModel model;
    private JLabel lblAverages;

    public StatsPanel() {
        setLayout(new BorderLayout(0, 10));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(null, "Simulation Statistics & Metrics", 0, 0, new Font("Arial", Font.BOLD, 14)),
            new EmptyBorder(5, 10, 5, 10)
        ));
        
        String[] cols = {"Process ID", "Finish Time", "Turnaround Time", "Waiting Time"};
        model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        lblAverages = new JLabel(" Average Waiting Time: - | CPU Utilization: - ");
        lblAverages.setFont(new Font("Arial", Font.BOLD, 15));
        
        JScrollPane scrollPane = new JScrollPane(table);
        
        add(scrollPane, BorderLayout.CENTER);
        add(lblAverages, BorderLayout.SOUTH);
    }

    public void updateStats(List<Process> terminatedProcesses, int totalTime) {
        model.setRowCount(0);
        double totalWait = 0; int busyTime = 0;
        for (Process p : terminatedProcesses) {
            int turnaround = p.finishTime - p.arrivalTime; 
            int totalBurst = p.cpuBurst1 + p.ioBurst + p.cpuBurst2;
            int waiting = turnaround - totalBurst;
            totalWait += waiting; busyTime += (p.cpuBurst1 + p.cpuBurst2);
            model.addRow(new Object[]{p.processId, p.finishTime, turnaround, waiting});
        }
        double avgWait = terminatedProcesses.isEmpty() ? 0 : totalWait / terminatedProcesses.size();
        double utilization = totalTime == 0 ? 0 : ((double) busyTime / totalTime) * 100;
        lblAverages.setText(String.format(" Average Waiting Time: %.2f | CPU Utilization: %.2f%% ", avgWait, utilization));
    }
}