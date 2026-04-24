Cloud Load Balancing Algorithms: Comparative Analysis

A simulation-based study comparing SLA-Aware, Round Robin, and Hybrid Adaptive load balancing algorithms in cloud computing using CloudSim.

Keywords

Cloud Computing, Load Balancing, SLA-Aware, Round Robin, Hybrid Adaptive, CloudSim

Project Overview

This project presents a simulation-based comparative analysis of three cloud load balancing algorithms: SLA-Aware (SA), Round Robin (RR), and Hybrid Adaptive (HA). The study evaluates their performance in a heterogeneous cloud environment using multiple Quality of Service (QoS) metrics such as SLA compliance, response time, energy consumption, and cost efficiency.

The simulation is implemented using CloudSim 4.0 to ensure a realistic representation of cloud infrastructure and workload behavior.

Features
Simulation of a real-world cloud environment
Implementation of three load balancing algorithms
Multi-metric performance evaluation
Statistical validation using ANOVA
Hybrid adaptive algorithm with dynamic switching
Objectives
Design and simulate a realistic cloud computing environment
Implement and evaluate three load balancing algorithms
Compare performance using multiple evaluation metrics
Identify the most efficient algorithm for practical deployment
System Architecture

The system follows a pipeline-based architecture consisting of:

Request Generator – Generates dynamic workload requests
SLA Classifier – Assigns priority levels to tasks
Load Balancer – Implements SA, RR, and HA algorithms
VM Pool – Executes tasks on virtual machines
Monitoring System – Collects performance metrics
Algorithms Description
SLA-Aware Algorithm

A dynamic scheduling algorithm that assigns tasks based on SLA urgency, virtual machine load, and execution cost using a weighted scoring mechanism.

Round Robin Algorithm

A static scheduling algorithm that distributes tasks sequentially across virtual machines, ensuring equal load distribution without considering task priority.

Hybrid Adaptive Algorithm

An advanced algorithm that combines SLA-Aware and Round Robin strategies. It dynamically switches between them based on system conditions using an Exponential Moving Average (EMA) of SLA violations.

Dataset Description

The dataset used in this project is a synthetic dataset generated using CloudSim 4.0, designed to simulate real-world cloud workload characteristics.

Dataset Parameters
Parameter	Value
Simulation Duration	3600 seconds
Total Tasks	5000
Total Virtual Machines	200
Workload Type	Tri-modal distribution
Simulation Runs	10
Task Distribution
SLA Tier	Percentage	Description
Tier-1 (Critical)	25%	Deadline ≤ 200 ms
Tier-2 (Standard)	55%	Deadline ≤ 500 ms
Tier-3 (Best Effort)	20%	No strict deadline
Workload Model
Non-homogeneous Poisson process
Sinusoidal arrival pattern
Inspired by real-world cloud workload traces
Virtual Machine Configuration
VM Category	Count	Configuration
High Performance	50	8 vCPU, 32 GB RAM
General Purpose	80	4 vCPU, 16 GB RAM
Compute Optimized	50	2 vCPU, 8 GB RAM
Memory Optimized	20	16 vCPU, 64 GB RAM
Performance Metrics
SLA Violation Rate
Average Response Time
Virtual Machine Utilization
Task Throughput
Energy Consumption
Cost Efficiency
Load Variance
Fault Recovery Rate
Results Summary
Metric	SLA-Aware	Round Robin	Hybrid Adaptive
SLA Compliance	Moderate	High	High
Response Time	Low	High	Medium
Energy Efficiency	High	Low	Medium
Cost Efficiency	High	Low	Medium
Overall Performance	Good	Moderate	Best
Key Findings
Round Robin achieves the lowest SLA violation rate due to uniform distribution
SLA-Aware provides better energy and cost efficiency
Hybrid Adaptive provides the best overall balanced performance
Statistical Analysis
One-way ANOVA test applied
p-value < 0.001 indicating statistically significant differences
Confirms reliability of results
Future Work
Integration with machine learning techniques
Auto-scaling cloud environments
Multi-cloud deployment
Real-time workload prediction
Project Structure
src/                # Source code  
dataset/            # Generated dataset  
results/            # Output logs and analysis  
docs/               # Research paper  
README.md           # Documentation  
How to Run
Install CloudSim 4.0
Import the project into a Java IDE (Eclipse or IntelliJ)
Run simulation classes
Analyze generated output logs
References
CloudSim Toolkit Documentation
Google Cluster Trace Dataset
Research papers on cloud load balancing
Author

Ishika Paunikar
B.Tech – Symbiosis Institute of Technology

Acknowledgment

This project was developed as part of coursework in Cloud Computing Tools and Techniques.
