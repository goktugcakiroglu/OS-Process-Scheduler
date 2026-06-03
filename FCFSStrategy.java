//COE302 - Operating Systems
//Assignment 3 - CPU Scheduling Simulator
//Members of Group 6:
//Goktug Cakiroglu - 220611008
//Cafer Aydin - 220611035
//Muhammet Aslan - 220611009
//Muhammed Yurtseven - 220611025

import java.util.List;

public class FCFSStrategy implements SchedulingStrategy {
    @Override
    public Process getNextProcess(Process currentCpuProcess, List<Process> readyQueue, int currentQuantumTick, int quantum) {
        
        if (currentCpuProcess != null) {
            return currentCpuProcess;
        }
        
        if (!readyQueue.isEmpty()) {
            return readyQueue.remove(0);
        }
        
        return null;
    }
}