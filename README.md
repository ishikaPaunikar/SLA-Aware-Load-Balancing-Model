

TITLE - Cloud Load Balancing in Cloud Computing SLA-Aware, Round Robin and Hybrid Adaptive Algorithms



Author
Ishika Paunikar
B.Tech, Symbiosis Institute of Technology, Nagpur



1 : Project Overview

This project presents a simulation-based analysis of three load balancing algorithms used in cloud computing:

Round Robin (RR)
SLA-Aware (SA)
Hybrid Adaptive (HA)

The objective is to evaluate and compare these algorithms based on key performance metrics such as SLA compliance, response time, resource utilization, energy consumption, and cost.



2 : Problem Statement

In cloud environments, improper load distribution can lead to:

Overloaded virtual machines
Increased response time
SLA violations
Higher energy and operational cost

This project addresses these challenges by analyzing different load balancing strategies under a controlled simulation environment.




3 : Methodology

The system is implemented using CloudSim 4.0 and follows a structured simulation pipeline:

Task requests are generated dynamically
Tasks are classified based on SLA priority
Load balancing algorithm assigns tasks to virtual machines
Execution is monitored for performance metrics
Dataset Description

The dataset is synthetically generated using simulation.




4 : Simulation Parameters
Total Tasks: 5000
Virtual Machines: 200
Simulation Time: 3600 seconds
Task Distribution
Tier-1 (Critical): 25%
Tier-2 (Standard): 55%
Tier-3 (Best Effort): 20%


Sample Dataset
TaskID,VMID,Tier,CPU_Util,ResponseTime,SLA_Status,Cost,Energy
1,12,T1,85,120,Met,0.012,0.0021
2,45,T2,72,160,Met,0.009,0.0018
3,78,T3,91,210,Violated,0.006,0.0025
4,23,T1,66,130,Met,0.012,0.0020
5,90,T2,88,175,Met,0.009,0.0022




5 : Mathematical Model

Priority Score Function
Score(t,v)=w1​SLA(t)+w2​Load(v)+w3​Cost(v)

This function determines the best virtual machine for task allocation.



Energy Model

P(u)=Pidle​+(Pmax​−Pidle​)u





6 : Algorithms


SLA-Aware
Uses weighted scoring based on SLA, load, and cost
Prioritizes critical tasks
Provides better response time and efficiency


Round Robin
Assigns tasks in cyclic order
Simple and fast
Does not consider task priority


Hybrid Adaptive
Combines SLA-Aware and Round Robin
Switches dynamically based on system conditions
Provides balanced performance

7 : Result Summary


| Metric         | SLA-Aware | Round Robin | Hybrid Adaptive |
| -------------- | --------- | ----------- | --------------- |
| SLA Violations | 7.21%     | 3.89%       | 4.72%           |
| Response Time  | 128 ms    | 156 ms      | 142 ms          |
| VM Utilization | 77.6%     | 72.3%       | 81.3%           |
| Throughput     | 1.307     | 1.289       | 1.342           |
| Energy (kWh)   | 1621      | 2103        | 1847            |
| Cost ($)       | 432       | 618         | 503             |




8 : Key Findings

Round Robin achieves the lowest SLA violation rate
SLA-Aware performs better in terms of energy and cost
Hybrid Adaptive provides the best overall balance



9 : Statistical Analysis
ANOVA test confirms significant differences (p < 0.001)
Algorithm choice has a strong impact on system performance
Project Structure

Cloud-LoadBalancer/
│
├── src/
│   ├── slaaware/
│   ├── roundrobin/
│   ├── hybrid/
│   └── utils/
│
├── dataset/
├── results/
├── docs/
│   └── report.docx
│
└── README.md



10 : How to Run

javac Main.java
java Main



11 : Limitations

Simulation-based results
No real cloud deployment
Synthetic dataset




12 : Future Work

Integration with real cloud platforms
AI-based load balancing
Auto-scaling mechanisms
Machine learning for workload prediction




13 : Conclusion

The study shows that no single algorithm is optimal in all scenarios.
Hybrid Adaptive load balancing provides the best trade-off between performance, efficiency, and SLA compliance, making it suitable for real-world cloud environments.




14 : Acknowledgement

I would like to express my sincere gratitude to Dr. Bhupesh Kumar Dewangan for his guidance and support throughout this project. I also thank Symbiosis Institute of Technology, Nagpur, along with my family and friends, for their encouragement and assistance.
