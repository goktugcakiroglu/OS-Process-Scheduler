# OS Process Scheduler Simulator (Java & Swing)

**Course:** COE 302 — Operating Systems Assignment 3  
**University:** İstanbul Sağlık ve Teknoloji Üniversitesi  

A comprehensive, custom-built CPU Scheduling Simulator developed in Java. This project models the behavior of multi-phase operating system processes (characterized by dual CPU bursts and an intervening I/O burst) and visually demonstrates the internal stages of scheduling via a dynamic, custom-rendered Graphical User Interface.

**Note:** This simulator does not rely on external chart libraries. The Dual-Row Gantt Chart rendering, queue management, and context-switching logic are entirely hand-coded using the Strategy Design Pattern.

## Requirements

To build and run this project, you need a standard Java development environment:
* **Java Standard:** JDK 8 or higher
* **Framework:** Java Swing (javax.swing, java.awt)
* **Build Tool:** Standard `javac` compiler or any Java IDE (Eclipse, IntelliJ IDEA, VS Code)

## Build & Run

1. Clone the repository and navigate to the project folder:
```bash
git clone [https://github.com/goktugcakiroglu/OS-Process-Scheduler.git](https://github.com/goktugcakiroglu/OS-Process-Scheduler.git)
cd OS-Process-Scheduler
```

2. Compile the Java files:
```bash
javac *.java
```

3. Execute the application:
```bash
java SimulatorGUI
```

## How It Works
The simulator architecture follows the **Model-View-Controller (MVC)** pattern. The core simulation engine continuously manages independent Ready and I/O queues at each "tick", safely routing processes through their lifecycle phases (CPU1 -> IO -> CPU2). The engine utilizes the **Strategy Pattern** to dynamically switch between scheduling algorithms at runtime without modifying the core simulation loop.

## Supported Scheduling Algorithms

| Algorithm | Preemption Type | Description |
| :--- | :--- | :--- |
| **FCFS** | Non-preemptive | First-Come, First-Served. Strict FIFO queue management. Maximizes CPU utilization but suffers from the "convoy effect." |
| **Round Robin** | Preemptive | Aggressively multiplexes the CPU using a customizable Time Quantum. Ensures fairness but introduces context-switching overhead. |
| **STCF** | Preemptive | Shortest Time-to-Completion First. Dynamically preempts running processes if a newly arrived process has a shorter remaining burst. |

## 📊 Performance Evaluation Highlights

We conducted a critical evaluation using a specific dataset with varying arrival times and I/O bursts. A summary of our findings is presented below:

| Algorithm | Avg. Waiting Time | CPU Utilization | Preemption Type |
| :--- | :--- | :--- | :--- |
| **FCFS** | 5.33 units | 100.00% | Non-preemptive |
| **RR (Q=2)** | 5.00 units | 94.12% | Preemptive |
| **STCF** | 4.00 units | 100.00% | Preemptive |

* **STCF** proved to be the optimal algorithm in terms of minimizing turnaround and waiting times.
* **FCFS** provided the easiest implementation and introduces minimal context-switching overhead, but lacked responsiveness.
* **Round Robin (Quantum Impact):** We proved that a larger quantum reduces the frequency of context switches, pushing the RR behavior closer to FCFS. Changing the Quantum from 2 to 4 decreased the Average Waiting Time from 5.00 to 4.67 units.

## Project Structure
```text
OS-Process-Scheduler/
├── SimulatorGUI.java        # Main Application & Controller logic
├── SimulationEngine.java    # Core tick-based CPU & I/O management
├── Process.java             # Entity class with phase state-machine
├── SchedulingStrategy.java  # Strategy Pattern Interface
├── FCFSStrategy.java        # FCFS implementation
├── RRStrategy.java          # Round Robin implementation with Quantum logic
├── STCFStrategy.java        # STCF implementation with dynamic preemption
├── InputPanel.java          # Configuration and data entry UI
├── GanttPanel.java          # Custom Dual-Row Graphics2D rendering
└── StatsPanel.java          # Metrics calculation and table rendering
```
