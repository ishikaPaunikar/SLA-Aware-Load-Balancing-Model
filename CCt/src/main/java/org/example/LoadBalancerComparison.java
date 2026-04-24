package org.example;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LoadBalancerComparison {

    public static void main(String[] args) throws InterruptedException {
        SimulationSupport.SlaAwareBalancer slaAware =
                new SimulationSupport.SlaAwareBalancer(SimulationSupport.createServers());
        SimulationSupport.RoundRobinBalancer roundRobin =
                new SimulationSupport.RoundRobinBalancer(SimulationSupport.createServers());

        SimulationSupport.printHeading("LOAD BALANCER COMPARISON SIMULATION");

        runSimulation(slaAware, SimulationSupport.createRequests());
        slaAware.printFullReport("MODEL 1 - SLA-AWARE LOAD BALANCER");

        runSimulation(roundRobin, SimulationSupport.createRequests());
        roundRobin.printFullReport("MODEL 2 - ROUND-ROBIN LOAD BALANCER");

        printComparisonReport(slaAware, roundRobin);
    }

    private static void runSimulation(
            SimulationSupport.BaseLoadBalancer balancer,
            List<SimulationSupport.Request> requests
    ) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(SimulationSupport.THREAD_POOL_SIZE);
        balancer.markSimulationStart();

        for (SimulationSupport.Request request : requests) {
            executor.submit(() -> balancer.handleRequest(request));
            Thread.sleep(SimulationSupport.ARRIVAL_DELAY_MS);
        }

        executor.shutdown();
        executor.awaitTermination(SimulationSupport.AWAIT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        balancer.markSimulationEnd();
    }

    private static void printComparisonReport(
            SimulationSupport.SlaAwareBalancer slaAware,
            SimulationSupport.RoundRobinBalancer roundRobin
    ) {
        SimulationSupport.printHeading("FINAL COMPARISON REPORT");

        System.out.printf("%-24s %-16s %-16s %-16s%n", "Metric", "SLA-Aware", "Round-Robin", "Best Model");
        System.out.println("-".repeat(78));
        System.out.printf("%-24s %-16d %-16d %-16s%n",
                "Processed Requests",
                slaAware.totalProcessed.get(),
                roundRobin.totalProcessed.get(),
                higherIsBetter(slaAware.totalProcessed.get(), roundRobin.totalProcessed.get()));
        System.out.printf("%-24s %-16d %-16d %-16s%n",
                "Rejected Requests",
                slaAware.totalRejected.get(),
                roundRobin.totalRejected.get(),
                lowerIsBetter(slaAware.totalRejected.get(), roundRobin.totalRejected.get()));
        System.out.printf("%-24s %-16d %-16d %-16s%n",
                "SLA Breaches",
                slaAware.totalBreaches.get(),
                roundRobin.totalBreaches.get(),
                lowerIsBetter(slaAware.totalBreaches.get(), roundRobin.totalBreaches.get()));
        System.out.printf("%-24s %-16s %-16s %-16s%n",
                "SLA Compliance",
                formatPercent(slaAware.getOverallSlaRate()),
                formatPercent(roundRobin.getOverallSlaRate()),
                higherIsBetter(slaAware.getOverallSlaRate(), roundRobin.getOverallSlaRate()));
        System.out.printf("%-24s %-16.3f %-16.3f %-16s%n",
                "Execution Time (s)",
                slaAware.getTotalExecutionSeconds(),
                roundRobin.getTotalExecutionSeconds(),
                lowerIsBetter(slaAware.getTotalExecutionSeconds(), roundRobin.getTotalExecutionSeconds()));
        System.out.printf("%-24s %-16.4f %-16.4f %-16s%n",
                "Execution Cost",
                slaAware.getTotalExecutionCost(),
                roundRobin.getTotalExecutionCost(),
                lowerIsBetter(slaAware.getTotalExecutionCost(), roundRobin.getTotalExecutionCost()));
        System.out.printf("%-24s %-16.6f %-16.6f %-16s%n",
                "Energy (kWh)",
                slaAware.getTotalEnergyKWh(),
                roundRobin.getTotalEnergyKWh(),
                lowerIsBetter(slaAware.getTotalEnergyKWh(), roundRobin.getTotalEnergyKWh()));
        System.out.println("-".repeat(78));
        System.out.printf("Conclusion: %s is stronger for SLA compliance, while %s is stronger for efficiency.%n",
                higherIsBetter(slaAware.getOverallSlaRate(), roundRobin.getOverallSlaRate()),
                lowerIsBetter(slaAware.getTotalExecutionCost(), roundRobin.getTotalExecutionCost()));
        System.out.println();
    }

    private static String higherIsBetter(double left, double right) {
        if (left == right) {
            return "Tie";
        }
        return left > right ? "SLA-Aware" : "Round-Robin";
    }

    private static String lowerIsBetter(double left, double right) {
        if (left == right) {
            return "Tie";
        }
        return left < right ? "SLA-Aware" : "Round-Robin";
    }

    private static String formatPercent(double value) {
        return String.format("%.2f%%", value);
    }
}
