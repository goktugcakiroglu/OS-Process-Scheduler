//COE302 - Operating Systems
//Assignment 3 - CPU Scheduling Simulator
//Members of Group 6:
//Goktug Cakiroglu - 220611008
//Cafer Aydin - 220611035
//Muhammet Aslan - 220611009
//Muhammed Yurtseven - 220611025

import java.util.List;

public class STCFStrategy implements SchedulingStrategy {
    @Override
    public Process getNextProcess(Process currentCpuProcess, List<Process> readyQueue, int currentQuantumTick, int quantum) {
        if (readyQueue.isEmpty()) {
            return currentCpuProcess;
        }

        Process shortestProcess = readyQueue.get(0);
        for (Process p : readyQueue) {
            if (p.getCurrentCpuRemainingTime() < shortestProcess.getCurrentCpuRemainingTime()) {
                shortestProcess = p;
            }
        }

        if (currentCpuProcess != null) {
            if (shortestProcess.getCurrentCpuRemainingTime() < currentCpuProcess.getCurrentCpuRemainingTime()) {
                readyQueue.add(currentCpuProcess);  
                readyQueue.remove(shortestProcess); 
                return shortestProcess;             
            }
            return currentCpuProcess; 
        } else {
            
            readyQueue.remove(shortestProcess);
            return shortestProcess;
        }
    }
}