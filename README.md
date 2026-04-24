Cloud Load Balancing Algorithms: Comparative Analysis
Overview

This project implements and compares three cloud load balancing algorithms for efficient resource management:

SLA-Aware (SA) → Optimizes task scheduling based on SLA priority, load, and cost
Round Robin (RR) → Ensures uniform distribution of tasks across virtual machines
Hybrid Adaptive (HA) → Dynamically switches between SA and RR for balanced performance

Unlike simple implementations, this system is designed using CloudSim 4.0, providing a realistic cloud simulation environment with heterogeneous virtual machines and dynamic workloads.

The project evaluates how different load balancing strategies behave under real-world cloud conditions instead of treating all algorithms as identical.

Core Insight (Important)

This is not just a basic comparison of algorithms.

SLA-Aware focuses on minimizing response time and improving cost efficiency
Round Robin focuses on reducing SLA violations through equal distribution
Hybrid Adaptive balances both by dynamically adjusting strategy

Key differences arise due to:

Different workload handling approaches
Different decision-making strategies
Dynamic vs static behavior
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

The system consists of the following components:

Request Generator – Generates dynamic workload requests
SLA Classifier – Assigns priority levels to tasks
Load Balancer – Implements SLA-Aware, Round Robin, and Hybrid Adaptive algorithms
VM Pool – Executes tasks on virtual machines
Monitoring System – Collects performance metrics
Algorithms
SLA-Aware

A dynamic scheduling algorithm that assigns tasks based on SLA urgency, VM load, and execution cost using a weighted scoring mechanism.

Round Robin

A simple and efficient algorithm that distributes tasks sequentially across virtual machines without considering system state.

Hybrid Adaptive

An advanced algorithm that dynamically switches between SLA-Aware and Round Robin using Exponential Moving Average (EMA) of SLA violations.

Dataset

The dataset is synthetically generated using CloudSim to simulate realistic cloud workloads.

5000 task requests
200 heterogeneous virtual machines
Tri-modal workload distribution
Three SLA tiers (Critical, Standard, Best Effort)
10 simulation runs for accuracy

Workload characteristics include:

Non-homogeneous Poisson distribution
Sinusoidal arrival pattern
Real-world inspired cloud behavior
Performance Metrics
SLA violation rate
Response time
VM utilization
Throughput
Energy consumption
Cost efficiency
Load variance
Fault recovery rate
Results Summary
SLA-Aware achieves better response time and cost efficiency
Round Robin achieves better SLA compliance
Hybrid Adaptive provides the best overall balanced performance
Key Findings
Round Robin minimizes SLA violations
SLA-Aware optimizes cost and energy
Hybrid Adaptive achieves the best trade-off between performance and efficiency
Statistical Analysis

All results are validated using ANOVA testing.

p-value < 0.001
Results are statistically significant
Confirms reliability of performance comparison
Future Work
Integration with machine learning techniques
Auto-scaling cloud environments
Multi-cloud deployment
Real-time workload prediction
Project Structure
src/ – Source code
dataset/ – Generated dataset
results/ – Output logs and analysis
docs/ – Research paper
README.md – Documentation
How to Run
Install CloudSim 4.0
Import the project into Eclipse or IntelliJ
Run simulation classes
Analyze output logs
Author

Ishika Paunikar
B.Tech – Symbiosis Institute of Technology

Acknowledgment

This project was developed as part of coursework in Cloud Computing Tools and Techniques.
