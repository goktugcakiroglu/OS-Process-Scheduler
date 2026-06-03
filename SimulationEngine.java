//COE302 - Operating Systems
//Assignment 3 - CPU Scheduling Simulator
//Members of Group 6:
//Goktug Cakiroglu - 220611008
//Cafer Aydin - 220611035
//Muhammet Aslan - 220611009
//Muhammed Yurtseven - 220611025

import java.util.*;

public class SimulationEngine {
    
    private SchedulingStrategy currentStrategy; 
    
    private int currentTime = 0;
    private Process currentCpuProcess = null;
    private Process currentIoProcess = null;

    private List<Process> allProcesses;
    private List<Process> readyQueue = new ArrayList<>();
    private Queue<Process> ioQueue = new LinkedList<>();
    public List<Process> terminatedList = new ArrayList<>();

    public List<String> cpuTimeline = new ArrayList<>();
    public List<String> ioTimeline = new ArrayList<>();

    public SimulationEngine(List<Process> processes) {
        this.allProcesses = processes;
    }

    public void setStrategy(SchedulingStrategy strategy) {
        this.currentStrategy = strategy;
    }

    public void runSimulation(int quantum) {
        int totalProcesses = allProcesses.size();
        int currentQuantumTick = 0;
        currentTime = 0;

        while (terminatedList.size() < totalProcesses && currentTime < 10000) {
            
            for (Process p : allProcesses) {
                if (p.arrivalTime == currentTime) {
                    readyQueue.add(p);
                }
            }

            Process nextCpuProcess = currentStrategy.getNextProcess(currentCpuProcess, readyQueue, currentQuantumTick, quantum);
            if (nextCpuProcess != currentCpuProcess) {
                currentQuantumTick = 0;
                currentCpuProcess = nextCpuProcess;
            }

            if (currentIoProcess == null && !ioQueue.isEmpty()) {
                currentIoProcess = ioQueue.poll();
            }

            cpuTimeline.add(currentCpuProcess != null ? currentCpuProcess.processId : null);
            ioTimeline.add(currentIoProcess != null ? currentIoProcess.processId : null);

            boolean cpuFinishedPhase = false;
            if (currentCpuProcess != null) {
                currentQuantumTick++;
                if (currentCpuProcess.currentPhase == 0) {
                    currentCpuProcess.remainingCpu1--;
                    if (currentCpuProcess.remainingCpu1 == 0) cpuFinishedPhase = true;
                } else if (currentCpuProcess.currentPhase == 2) {
                    currentCpuProcess.remainingCpu2--;
                    if (currentCpuProcess.remainingCpu2 == 0) cpuFinishedPhase = true;
                }
            }

            boolean ioFinishedPhase = false;
            if (currentIoProcess != null) {
                currentIoProcess.remainingIo--;
                if (currentIoProcess.remainingIo == 0) ioFinishedPhase = true;
            }

            if (cpuFinishedPhase) {
                if (currentCpuProcess.currentPhase == 0) {
                    currentCpuProcess.currentPhase = 1;
                    ioQueue.add(currentCpuProcess);
                } else if (currentCpuProcess.currentPhase == 2) {
                    currentCpuProcess.finishTime = currentTime + 1; 
                    terminatedList.add(currentCpuProcess);
                }
                currentCpuProcess = null; 
            }

            if (ioFinishedPhase) {
                currentIoProcess.currentPhase = 2;
                readyQueue.add(currentIoProcess);
                currentIoProcess = null; 
            }

            currentTime++;
        }
    }
}