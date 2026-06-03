//COE302 - Operating Systems
//Assignment 3 - CPU Scheduling Simulator
//Members of Group 6:
//Goktug Cakiroglu - 220611008
//Cafer Aydin - 220611035
//Muhammet Aslan - 220611009
//Muhammed Yurtseven - 220611025

public class Process {
    public String processId;
    public int arrivalTime;
    public int cpuBurst1;
    public int ioBurst;
    public int cpuBurst2;

    public int remainingCpu1;
    public int remainingIo;
    public int remainingCpu2;

    public int currentPhase = 0; 

    public int finishTime = 0;
    public int turnaroundTime = 0;
    public int waitingTime = 0;

    public Process(String processId, int arrivalTime, int cpuBurst1, int ioBurst, int cpuBurst2) {
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.cpuBurst1 = cpuBurst1;
        this.ioBurst = ioBurst;
        this.cpuBurst2 = cpuBurst2;
        
        this.remainingCpu1 = cpuBurst1;
        this.remainingIo = ioBurst;
        this.remainingCpu2 = cpuBurst2;
    }

    public int getCurrentCpuRemainingTime() {
        if (currentPhase == 0) return remainingCpu1;
        if (currentPhase == 2) return remainingCpu2;
        return 0; 
    }
}