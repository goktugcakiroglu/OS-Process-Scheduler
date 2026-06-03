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
import java.util.ArrayList;
import java.util.List;

public class InputPanel extends JPanel {
    private SimulatorGUI mediator;     
    private JComboBox<String> algoSelector;
    private JTextField quantumField;
    private JButton startBtn, resetBtn; 
    private JTextField txtArrival, txtCpu1, txtIo, txtCpu2;
    private JButton addProcessBtn;
    private DefaultTableModel tableModel;
    private List<Process> processList;
    private int processCounter = 1; 

    public InputPanel(SimulatorGUI mediator) {
        this.mediator = mediator;
        this.processList = new ArrayList<>();
        
        setLayout(new BorderLayout(0, 15));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(null, "Simulation Configuration", 0, 0, new Font("Arial", Font.BOLD, 14)),
            new EmptyBorder(5, 10, 5, 10) 
        ));

        Font baseFont = new Font("Arial", Font.BOLD, 14);

        JPanel controlBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        algoSelector = new JComboBox<>(new String[]{"FCFS", "RR", "STCF"});
        algoSelector.setFont(baseFont);
        
        quantumField = new JTextField("2", 3);
        quantumField.setFont(baseFont);
        quantumField.setHorizontalAlignment(JTextField.CENTER);
        quantumField.setEnabled(false); 
        
        algoSelector.addActionListener(e -> quantumField.setEnabled(algoSelector.getSelectedItem().equals("RR")));

        startBtn = new JButton("Start Simulation"); startBtn.setFont(baseFont);
        resetBtn = new JButton("Reset"); resetBtn.setFont(baseFont);

        JLabel lblAlgo = new JLabel("Algorithm:"); lblAlgo.setFont(baseFont);
        JLabel lblQ = new JLabel("Quantum:"); lblQ.setFont(baseFont);

        controlBar.add(lblAlgo); controlBar.add(algoSelector);
        controlBar.add(lblQ); controlBar.add(quantumField);
        controlBar.add(startBtn); controlBar.add(resetBtn); 

        JPanel addBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        txtArrival = new JTextField("0", 3); txtArrival.setFont(baseFont); txtArrival.setHorizontalAlignment(JTextField.CENTER);
        txtCpu1 = new JTextField("5", 3); txtCpu1.setFont(baseFont); txtCpu1.setHorizontalAlignment(JTextField.CENTER);
        txtIo = new JTextField("2", 3); txtIo.setFont(baseFont); txtIo.setHorizontalAlignment(JTextField.CENTER);
        txtCpu2 = new JTextField("3", 3); txtCpu2.setFont(baseFont); txtCpu2.setHorizontalAlignment(JTextField.CENTER);
        
        addProcessBtn = new JButton("Add Process"); addProcessBtn.setFont(baseFont);

        JLabel l1 = new JLabel("Arrival:"); l1.setFont(baseFont);
        JLabel l2 = new JLabel("CPU 1:"); l2.setFont(baseFont);
        JLabel l3 = new JLabel("I/O:"); l3.setFont(baseFont);
        JLabel l4 = new JLabel("CPU 2:"); l4.setFont(baseFont);

        addBar.add(l1); addBar.add(txtArrival);
        addBar.add(l2); addBar.add(txtCpu1);
        addBar.add(l3); addBar.add(txtIo);
        addBar.add(l4); addBar.add(txtCpu2);
        addBar.add(addProcessBtn);

        JPanel topPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        topPanel.add(controlBar);
        topPanel.add(addBar);

        String[] columns = {"ID", "Arrival", "CPU 1", "I/O", "CPU 2"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable processTable = new JTable(tableModel);
        
        processTable.setFont(new Font("Arial", Font.PLAIN, 13));
        processTable.setRowHeight(25); 
        processTable.getTableHeader().setFont(baseFont);
        
        processTable.setShowGrid(true);
        processTable.setGridColor(Color.LIGHT_GRAY);
        processTable.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < processTable.getColumnCount(); i++) {
            processTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        JScrollPane scrollPane = new JScrollPane(processTable);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setupListeners();
    }
    private void setupListeners() {
        addProcessBtn.addActionListener(e -> {
            try {
                int arr = Integer.parseInt(txtArrival.getText());
                int c1 = Integer.parseInt(txtCpu1.getText());
                int io = Integer.parseInt(txtIo.getText());
                int c2 = Integer.parseInt(txtCpu2.getText());
                String pId = "P" + processCounter++;
                processList.add(new Process(pId, arr, c1, io, c2));
                tableModel.addRow(new Object[]{pId, arr, c1, io, c2});
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        startBtn.addActionListener(e -> {
            if (processList.isEmpty()) return;
            String algo = (String) algoSelector.getSelectedItem();
            int quantum = 0;
            if (algo.equals("RR")) {
                try { quantum = Integer.parseInt(quantumField.getText()); } catch (Exception ex) { return; }
            }
            List<Process> cloned = new ArrayList<>();
            for (Process p : processList) cloned.add(new Process(p.processId, p.arrivalTime, p.cpuBurst1, p.ioBurst, p.cpuBurst2));
            mediator.startSimulation(algo, quantum, cloned);
        });

        resetBtn.addActionListener(e -> {
            processList.clear(); tableModel.setRowCount(0); processCounter = 1;
            mediator.resetDisplay();
        });
    }
}