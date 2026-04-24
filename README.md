TITLE - Cloud Load Balancing Algorithms: Comparative Analysis

A simulation-based study comparing SLA-Aware, Round Robin, and Hybrid Adaptive load balancing algorithms in cloud computing using CloudSim.

Keywords - Cloud Computing, Load Balancing, SLA-Aware, Round Robin, Hybrid Adaptive, CloudSim


1. Project Overview - This project presents a simulation-based comparative analysis of three cloud load balancing algorithms: SLA-Aware (SA), Round Robin (RR), and Hybrid Adaptive (HA). The study evaluates their performance in a heterogeneous cloud environment using multiple Quality of Service (QoS) metrics such as SLA compliance, response time, energy consumption, and cost efficiency.The simulation is implemented using CloudSim 4.0 to ensure a realistic representation of cloud infrastructure and workload behavior.



2. Features
Simulation of a real-world cloud environment
Implementation of three load balancing algorithms
Multi-metric performance evaluation
Statistical validation using ANOVA
Hybrid adaptive algorithm with dynamic switching


3. Objectives
Design and simulate a realistic cloud computing environment
Implement and evaluate three load balancing algorithms
Compare performance using multiple evaluation metrics
Identify the most efficient algorithm for practical deployment


4. System Architecture
Request Generator – Generates dynamic workload requests
SLA Classifier – Assigns priority levels to tasks
Load Balancer – Implements SLA-Aware, Round Robin, and Hybrid Adaptive algorithms
VM Pool – Executes tasks on virtual machines
Monitoring System – Collects performance metrics


5. Algorithms Description
5.1 SLA-Aware Algorithm

A dynamic scheduling algorithm that assigns tasks based on SLA urgency, virtual machine load, and execution cost using a weighted scoring mechanism.

5.2 Round Robin Algorithm

A static scheduling algorithm that distributes tasks sequentially across virtual machines, ensuring equal load distribution without considering task priority.

5.3 Hybrid Adaptive Algorithm

An advanced algorithm that combines SLA-Aware and Round Robin strategies. It dynamically switches between them based on system conditions using an Exponential Moving Average (EMA) of SLA violations.



6. Dataset Description

Simulation duration is 3600 seconds representing one hour of cloud operation
A total of 5000 task requests are generated
The system consists of 200 heterogeneous virtual machines
Workload follows a tri-modal distribution pattern
Each algorithm is tested over 10 independent simulation runs for accuracy

Task Distribution
Tier-1 (Critical) represents 25 percent of tasks with deadline less than or equal to 200 milliseconds
Tier-2 (Standard) represents 55 percent of tasks with deadline less than or equal to 500 milliseconds
Tier-3 (Best Effort) represents 20 percent of tasks with no strict deadline

Workload Model
Non-homogeneous Poisson process is used
Sinusoidal arrival pattern is followed
Inspired by real-world cloud workload traces

Virtual Machine Configuration
50 high-performance machines with 8 vCPU and 32 GB RAM
80 general-purpose machines with 4 vCPU and 16 GB RAM
50 compute-optimized machines with 2 vCPU and 8 GB RAM
20 memory-optimized machines with 16 vCPU and 64 GB RAM


7. Performance Metrics
SLA violation rate
Average response time
Virtual machine utilization
Task throughput
Energy consumption
Cost efficiency
Load variance
Fault recovery rate


8. Results Summary
SLA-Aware algorithm provides low response time and better energy efficiency
Round Robin achieves high SLA compliance but results in higher response time and energy usage
Hybrid Adaptive algorithm provides balanced performance across all metrics and performs best overall


9. Key Findings
Round Robin performs best in terms of SLA compliance due to equal load distribution
SLA-Aware performs best in terms of cost and energy efficiency
Hybrid Adaptive provides the best overall performance by balancing efficiency and SLA requirements


10. Statistical Analysis
One-way ANOVA test is applied on all performance metrics
The p-value is less than 0.001, indicating statistically significant differences
This confirms that the results are reliable and not due to random variation


11. Future Work
Integration with machine learning techniques for predictive scheduling
Implementation of auto-scaling cloud environments
Extension to multi-cloud deployment scenarios
Use of real-time workload data for validation


12. Project Structure
src/ Source code
dataset/ Generated dataset
results/ Output logs and analysis
docs/ Research paper
README.md Documentation


13. How to Run
Install CloudSim 4.0
Import the project into a Java IDE such as Eclipse or IntelliJ
Run the simulation classes
Analyze the generated output logs


14. References
CloudSim Toolkit Documentation
Google Cluster Trace Dataset
Research papers on cloud load balancing


15. Author

Ishika Paunikar
B.Tech – Symbiosis Institute of Technology


16. Acknowledgment

This project was developed as part of coursework in Cloud Computing Tools and Techniques.
