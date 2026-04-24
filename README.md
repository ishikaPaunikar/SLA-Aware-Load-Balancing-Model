📌 SLA-Aware, Round Robin and Hybrid Adaptive Load Balancing in Cloud Computing
👤 Author

Ishika Paunikar
PRN – 23070521061
B.Tech — Symbiosis Institute of Technology, Nagpur

🚀 Project Overview

This project presents a comparative simulation-based analysis of three cloud load balancing algorithms:

Round Robin (RR) → Equal task distribution
SLA-Aware (SA) → Priority-based scheduling using SLA
Hybrid Adaptive (HA) → Dynamic switching between SA and RR

👉 Objective: Improve SLA compliance, resource utilization, response time, and cost efficiency

🧠 Core Idea

Cloud systems face problems like:

Uneven load distribution
SLA violations
High energy consumption

This project solves it using:
✔ SLA-based intelligent scheduling
✔ Dynamic hybrid switching (HA)
✔ Multi-metric optimization

📊 Dataset Description

Dataset is synthetically generated using CloudSim 4.0 simulation (as mentioned in your report ).

📌 Simulation Parameters
Simulation Time = 3600 sec
Total Tasks = 5000
Total VMs = 200
📊 Task Distribution
Tier-1 (Critical) → 25%
Tier-2 (Standard) → 55%
Tier-3 (Best Effort) → 20%
⚙️ Workload Characteristics
Task arrival → Non-homogeneous Poisson
CPU Utilization → 15% – 95%
Task Duration → ~1123 ms average
📄 Sample Dataset (Dummy Representation)
TaskID,VMID,Tier,CPU_Util,ResponseTime,SLA_Status,Cost,Energy
1,12,T1,85,120,Met,0.012,0.0021
2,45,T2,72,160,Met,0.009,0.0018
3,78,T3,91,210,Violated,0.006,0.0025
4,23,T1,66,130,Met,0.012,0.0020
5,90,T2,88,175,Met,0.009,0.0022
⚙️ Technologies Used
Language: Java
Tool: CloudSim 4.0
Concepts:
Load Balancing
SLA Management
Cloud Scheduling
🏗️ System Architecture

Main Components:

Request Generator
SLA Classifier
Load Balancer (SA / RR / HA)
VM Pool (200 VMs)
Monitoring System
🔁 Working Flow
Generate task requests
Classify tasks based on SLA
Select algorithm (SA / RR / HA)
Assign tasks to VM
Monitor performance metrics
🧮 Mathematical Model
Priority Score Function

Score(t,v)=w
1
	​

⋅SLA(t)+w
2
	​

⋅Load(v)+w
3
	​

⋅Cost(v)

Where:

SLA(t) → urgency of task
Load(v) → VM load
Cost(v) → execution cost
Energy Model

P(u)=P
idle
	​

+(P
max
	​

−P
idle
	​

)⋅u

⚙️ Algorithms Implemented
1. SLA-Aware Algorithm
Uses weighted scoring
Prioritizes critical tasks
Reduces response time
2. Round Robin
Simple cyclic allocation
Equal distribution
No priority consideration
3. Hybrid Adaptive
Switches between SA & RR
Uses EMA threshold
Best balanced performance
📈 Results Summary
Metric	SLA-Aware	Round Robin	Hybrid Adaptive
SLA Violations	7.21%	3.89%	4.72%
Response Time	128 ms	156 ms	142 ms
VM Utilization	77.6%	72.3%	81.3%
Throughput	1.307	1.289	1.342
Energy (kWh)	1621	2103	1847
Cost ($)	432	618	503
📊 Key Findings
RR → Best for SLA compliance
SA → Best for energy & cost
HA → Best overall performance

👉 Hybrid achieves balanced optimization across all metrics

⚖️ Trade-Off Analysis
Factor	SA	RR	HA
SLA Compliance	Medium	High	High
Response Time	Low	High	Medium
Energy	Low	High	Medium
Efficiency	High	Medium	Highest
🧪 Statistical Validation
ANOVA Test → p < 0.001
Significant differences between algorithms
Effect size up to 96% impact
📦 Project Structure
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
▶️ How to Run
javac Main.java
java Main
📌 Conclusion
Algorithm choice directly impacts cloud performance
Hybrid Adaptive is most effective overall
Dynamic switching improves SLA + efficiency
🔮 Future Work
AI-based load balancing
Real cloud deployment (AWS/Azure)
Auto-scaling integration
ML-based workload prediction
