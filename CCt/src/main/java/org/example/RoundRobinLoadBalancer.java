package org.example;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RoundRobinLoadBalancer {

    public static void main(String[] args) throws InterruptedException {
        SimulationSupport.RoundRobinBalancer balancer =
                new SimulationSupport.RoundRobinBalancer(SimulationSupport.createServers());
        List<SimulationSupport.Request> requests = SimulationSupport.createRequests();

        SimulationSupport.printHeading("ROUND-ROBIN LOAD BALANCING SIMULATION");
        runSimulation(balancer, requests);
        balancer.printFullReport("ROUND-ROBIN LOAD BALANCER REPORT");
    }

    private static void runSimulation(
            SimulationSupport.RoundRobinBalancer balancer,
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
}
