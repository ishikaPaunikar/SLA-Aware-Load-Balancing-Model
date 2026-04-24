Cloud Load Balancing Algorithms: Comparative Analysis




Overview

This project presents a simulation-based analysis of cloud load balancing algorithms using CloudSim.

It compares three approaches:

SLA-Aware (SA) – Priority-based scheduling
Round Robin (RR) – Equal task distribution
Hybrid Adaptive (HA) – Dynamic switching between SA and RR

The goal is to evaluate their performance using metrics such as response time, SLA violations, cost, and energy consumption.

System Architecture
Request Generator – Generates incoming tasks
SLA Classifier – Assigns priority levels
Load Balancer – Applies scheduling algorithms
VM Pool – Executes tasks
Monitoring System – Tracks performance
Technologies Used
Java
CloudSim 4.0
Cloud Computing Concepts
Load Balancing Algorithms
ANOVA Statistical Analysis
Dataset
5000 tasks
200 virtual machines
3600 seconds simulation time
Tri-modal workload distribution
10 simulation runs

Task distribution:

Tier-1 (Critical): 25%
Tier-2 (Standard): 55%
Tier-3 (Best Effort): 20%


Results Summary
| Metric        | SLA-Aware | Round Robin | Hybrid Adaptive |
| ------------- | --------- | ----------- | --------------- |
| SLA Violation | Medium    | Low         | Low             |
| Response Time | Low       | High        | Medium          |
| Energy        | Low       | High        | Medium          |
| Cost          | Low       | High        | Medium          |
| Overall       | Good      | Moderate    | Best            |


Key Findings
SLA-Aware improves response time and cost efficiency
Round Robin reduces SLA violations
Hybrid Adaptive provides the best overall balance
Statistical Validation
ANOVA test applied
p-value < 0.001
Results are statistically significant
Project Structure

src/
dataset/
results/
docs/

How to Run
Install CloudSim 4.0
Import project into Eclipse or IntelliJ
Run simulation files
Analyze output results

Author

Ishika Paunikar
