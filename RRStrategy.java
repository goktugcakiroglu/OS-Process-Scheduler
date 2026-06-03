//COE302 - Operating Systems
//Assignment 3 - CPU Scheduling Simulator
//Members of Group 6:
//Goktug Cakiroglu - 220611008
//Cafer Aydin - 220611035
//Muhammet Aslan - 220611009
//Muhammed Yurtseven - 220611025

import java.util.List;

public class RRStrategy implements SchedulingStrategy {
    @Override
    public Process getNextProcess(Process currentCpuProcess, List<Process> readyQueue, int currentQuantumTick, int quantum) {
        
        if (currentCpuProcess != null && currentQuantumTick >= quantum) {
            readyQueue.add(currentCpuProcess); 
            currentCpuProcess = null;          
        }
        
        if (currentCpuProcess == null && !readyQueue.isEmpty()) {
            return readyQueue.remove(0);
        }
        
        return currentCpuProcess; 
    }
}