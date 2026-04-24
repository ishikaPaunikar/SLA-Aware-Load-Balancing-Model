package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

final class SimulationSupport {

    static final int REQUEST_COUNT = 30;
    static final int THREAD_POOL_SIZE = 10;
    static final int ARRIVAL_DELAY_MS = 50;
    static final int AWAIT_TIMEOUT_SECONDS = 30;
    static final long REQUEST_SEED = 42L;

    private SimulationSupport() {
    }

    enum SLATier {
        PLATINUM(50, 20, 59),
        GOLD(150, 60, 159),
        SILVER(400, 150, 399),
        BRONZE(1000, 300, 899);

        final int maxResponseTimeMs;
        final int minBaseProcessingMs;
        final int maxBaseProcessingMs;

        SLATier(int maxResponseTimeMs, int minBaseProcessingMs, int maxBaseProcessingMs) {
            this.maxResponseTimeMs = maxResponseTimeMs;
            this.minBaseProcessingMs = minBaseProcessingMs;
            this.maxBaseProcessingMs = maxBaseProcessingMs;
        }
    }

    record ServerProfile(
            int id,
            String hostName,
            String vmName,
            int capacity,
            double costPerSecond,
            double powerWatts
    ) {
        ServerProfile {
            if (id <= 0 || capacity <= 0 || costPerSecond < 0 || powerWatts < 0) {
                throw new IllegalArgumentException("Invalid server profile values");
            }
            Objects.requireNonNull(hostName, "hostName");
            Objects.requireNonNull(vmName, "vmName");
        }
    }

    static final class ServerNode {
        final ServerProfile profile;
        final AtomicInteger load = new AtomicInteger(0);
        final AtomicInteger totalRequests = new AtomicInteger(0);
        final AtomicInteger slaBreaches = new AtomicInteger(0);
        final AtomicLong totalResponseTimeMs = new AtomicLong(0);
        final AtomicLong totalBusyTimeMs = new AtomicLong(0);
        volatile boolean healthy = true;

        ServerNode(ServerProfile profile) {
            this.profile = profile;
        }

        boolean isAvailable() {
            return healthy && load.get() < profile.capacity();
        }

        double getAverageResponseTimeMs() {
            int handled = totalRequests.get();
            return handled == 0 ? 0.0 : (double) totalResponseTimeMs.get() / handled;
        }

        double getSlaComplianceRate() {
            int handled = totalRequests.get();
            return handled == 0 ? 100.0 : (double) (handled - slaBreaches.get()) / handled * 100.0;
        }

        double getExecutionSeconds() {
            return totalBusyTimeMs.get() / 1000.0;
        }

        double getExecutionCost() {
            return getExecutionSeconds() * profile.costPerSecond();
        }

        double getEnergyKWh() {
            return (profile.powerWatts() * getExecutionSeconds()) / 3_600_000.0;
        }
    }

    static final class Request {
        final int id;
        final SLATier slaTier;
        final int baseProcessingMs;
        final long arrivalTimeMs;
        volatile ServerNode assignedServer;
        volatile long startTimeMs;
        volatile long endTimeMs;
        volatile boolean slaViolated;

        Request(int id, SLATier slaTier, int baseProcessingMs) {
            this.id = id;
            this.slaTier = Objects.requireNonNull(slaTier, "slaTier");
            this.baseProcessingMs = baseProcessingMs;
            this.arrivalTimeMs = System.currentTimeMillis();
        }

        long getResponseTimeMs() {
            return endTimeMs - startTimeMs;
        }
    }

    static final class TierStats {
        final AtomicInteger total = new AtomicInteger(0);
        final AtomicInteger breaches = new AtomicInteger(0);
    }

    abstract static class BaseLoadBalancer {
        final String modelName;
        final List<ServerNode> servers;
        final Map<SLATier, TierStats> tierStats = new EnumMap<>(SLATier.class);
        final AtomicInteger totalProcessed = new AtomicInteger(0);
        final AtomicInteger totalRejected = new AtomicInteger(0);
        final AtomicInteger totalBreaches = new AtomicInteger(0);
        private volatile long simulationStartMs;
        private volatile long simulationEndMs;

        BaseLoadBalancer(String modelName, List<ServerNode> servers) {
            this.modelName = modelName;
            this.servers = List.copyOf(servers);
            for (SLATier tier : SLATier.values()) {
                tierStats.put(tier, new TierStats());
            }
        }

        abstract ServerNode selectServer(Request request);

        abstract String noCapacityAction();

        void markSimulationStart() {
            simulationStartMs = System.currentTimeMillis();
        }

        void markSimulationEnd() {
            simulationEndMs = System.currentTimeMillis();
        }

        void handleRequest(Request request) {
            ServerNode server = selectServer(request);
            if (server == null) {
                totalRejected.incrementAndGet();
                System.out.printf("[%s][%s] Req#%02d (%s) - no server available%n",
                        modelName, noCapacityAction(), request.id, request.slaTier);
                return;
            }

            request.assignedServer = server;
            request.startTimeMs = System.currentTimeMillis();
            int currentLoad = server.load.incrementAndGet();
            server.totalRequests.incrementAndGet();

            int processingTimeMs = request.baseProcessingMs + currentLoad * 5;
            sleep(processingTimeMs);

            request.endTimeMs = System.currentTimeMillis();
            server.load.decrementAndGet();

            long responseTime = request.getResponseTimeMs();
            server.totalResponseTimeMs.addAndGet(responseTime);
            server.totalBusyTimeMs.addAndGet(responseTime);

            TierStats stats = tierStats.get(request.slaTier);
            stats.total.incrementAndGet();

            if (responseTime > request.slaTier.maxResponseTimeMs) {
                request.slaViolated = true;
                server.slaBreaches.incrementAndGet();
                stats.breaches.incrementAndGet();
                totalBreaches.incrementAndGet();
                System.out.printf("[%s][BREACH] Req#%02d (%s) on %s/%s | rt=%dms limit=%dms%n",
                        modelName,
                        request.id,
                        request.slaTier,
                        server.profile.hostName(),
                        server.profile.vmName(),
                        responseTime,
                        request.slaTier.maxResponseTimeMs);
            } else {
                System.out.printf("[%s][OK] Req#%02d (%s) on %s/%s | rt=%dms%n",
                        modelName,
                        request.id,
                        request.slaTier,
                        server.profile.hostName(),
                        server.profile.vmName(),
                        responseTime);
            }

            totalProcessed.incrementAndGet();
        }

        double getOverallSlaRate() {
            int processed = totalProcessed.get();
            return processed == 0 ? 100.0 : (double) (processed - totalBreaches.get()) / processed * 100.0;
        }

        double getWallClockSeconds() {
            return (simulationEndMs - simulationStartMs) / 1000.0;
        }

        double getTotalExecutionSeconds() {
            return servers.stream().mapToDouble(ServerNode::getExecutionSeconds).sum();
        }

        double getTotalExecutionCost() {
            return servers.stream().mapToDouble(ServerNode::getExecutionCost).sum();
        }

        double getTotalEnergyKWh() {
            return servers.stream().mapToDouble(ServerNode::getEnergyKWh).sum();
        }

        void printFullReport(String heading) {
            printHeading(heading);
            printHostInventory();
            printPerServerMetrics();
            printTierMetrics();
            printSummary();
        }

        void printHostInventory() {
            System.out.println("HOST AND VM INVENTORY");
            System.out.printf("%-4s %-12s %-16s %-10s %-12s %-12s%n",
                    "ID", "Host", "VM", "Capacity", "Cost/sec", "Power(W)");
            System.out.println("-".repeat(74));
            for (ServerNode server : servers) {
                System.out.printf("%-4d %-12s %-16s %-10d %-12.4f %-12.1f%n",
                        server.profile.id(),
                        server.profile.hostName(),
                        server.profile.vmName(),
                        server.profile.capacity(),
                        server.profile.costPerSecond(),
                        server.profile.powerWatts());
            }
            System.out.println("-".repeat(74));
            System.out.println();
        }

        void printPerServerMetrics() {
            System.out.println("PER-SERVER EXECUTION METRICS");
            System.out.printf("%-12s %-16s %-9s %-12s %-12s %-12s %-12s %-12s%n",
                    "Host", "VM", "Requests", "Avg RT(ms)", "SLA Rate", "Exec(s)", "Cost", "Energy(kWh)");
            System.out.println("-".repeat(112));
            for (ServerNode server : servers) {
                System.out.printf("%-12s %-16s %-9d %-12.1f %-12s %-12.3f %-12.4f %-12.6f%n",
                        server.profile.hostName(),
                        server.profile.vmName(),
                        server.totalRequests.get(),
                        server.getAverageResponseTimeMs(),
                        String.format("%.2f%%", server.getSlaComplianceRate()),
                        server.getExecutionSeconds(),
                        server.getExecutionCost(),
                        server.getEnergyKWh());
            }
            System.out.println("-".repeat(112));
            System.out.println();
        }

        void printTierMetrics() {
            System.out.println("SLA TIER PERFORMANCE");
            System.out.printf("%-10s %-12s %-12s %-12s%n", "Tier", "Processed", "Breaches", "SLA Rate");
            System.out.println("-".repeat(52));
            for (SLATier tier : SLATier.values()) {
                TierStats stats = tierStats.get(tier);
                int processed = stats.total.get();
                int breaches = stats.breaches.get();
                double rate = processed == 0 ? 100.0 : (double) (processed - breaches) / processed * 100.0;
                System.out.printf("%-10s %-12d %-12d %-12s%n", tier, processed, breaches, String.format("%.2f%%", rate));
            }
            System.out.println("-".repeat(52));
            System.out.println();
        }

        void printSummary() {
            System.out.println("SIMULATION SUMMARY");
            System.out.printf("Processed requests     : %d%n", totalProcessed.get());
            System.out.printf("Rejected requests      : %d%n", totalRejected.get());
            System.out.printf("Total SLA breaches     : %d%n", totalBreaches.get());
            System.out.printf("Overall SLA compliance : %.2f%%%n", getOverallSlaRate());
            System.out.printf("Wall clock time        : %.3f s%n", getWallClockSeconds());
            System.out.printf("Execution time total   : %.3f s%n", getTotalExecutionSeconds());
            System.out.printf("Execution cost total   : %.4f%n", getTotalExecutionCost());
            System.out.printf("Energy consumption     : %.6f kWh%n", getTotalEnergyKWh());
            System.out.println();
        }
    }

    static final class SlaAwareBalancer extends BaseLoadBalancer {
        private final Map<SLATier, List<ServerNode>> preferredServers = new EnumMap<>(SLATier.class);

        SlaAwareBalancer(List<ServerNode> servers) {
            super("SLA-AWARE", servers);
            preferredServers.put(SLATier.PLATINUM, List.of(servers.get(0), servers.get(1)));
            preferredServers.put(SLATier.GOLD, List.of(servers.get(1), servers.get(2)));
            preferredServers.put(SLATier.SILVER, List.of(servers.get(2), servers.get(3)));
            preferredServers.put(SLATier.BRONZE, List.copyOf(servers));
        }

        @Override
        ServerNode selectServer(Request request) {
            ServerNode preferred = preferredServers.get(request.slaTier).stream()
                    .filter(ServerNode::isAvailable)
                    .min(Comparator.comparingInt(server -> server.load.get()))
                    .orElse(null);
            if (preferred != null) {
                return preferred;
            }
            return servers.stream()
                    .filter(ServerNode::isAvailable)
                    .min(Comparator.comparingInt(server -> server.load.get()))
                    .orElse(null);
        }

        @Override
        String noCapacityAction() {
            return "QUEUED";
        }
    }

    static final class RoundRobinBalancer extends BaseLoadBalancer {
        private final AtomicInteger nextIndex = new AtomicInteger(0);

        RoundRobinBalancer(List<ServerNode> servers) {
            super("ROUND-ROBIN", servers);
        }

        @Override
        ServerNode selectServer(Request request) {
            int size = servers.size();
            for (int attempt = 0; attempt < size; attempt++) {
                int index = Math.floorMod(nextIndex.getAndIncrement(), size);
                ServerNode candidate = servers.get(index);
                if (candidate.isAvailable()) {
                    return candidate;
                }
            }
            return null;
        }

        @Override
        String noCapacityAction() {
            return "DROPPED";
        }
    }

    static List<ServerNode> createServers() {
        List<ServerProfile> profiles = List.of(
                new ServerProfile(1, "Host-A", "VM-High-1", 5, 0.0120, 210.0),
                new ServerProfile(2, "Host-A", "VM-High-2", 5, 0.0120, 210.0),
                new ServerProfile(3, "Host-B", "VM-Mid-1", 8, 0.0090, 180.0),
                new ServerProfile(4, "Host-C", "VM-Std-1", 10, 0.0060, 150.0)
        );

        List<ServerNode> servers = new ArrayList<>();
        for (ServerProfile profile : profiles) {
            servers.add(new ServerNode(profile));
        }
        return servers;
    }

    static List<Request> createRequests() {
        List<Request> requests = new ArrayList<>();
        java.util.Random seededRandom = new java.util.Random(REQUEST_SEED);
        SLATier[] tiers = SLATier.values();

        for (int index = 1; index <= REQUEST_COUNT; index++) {
            SLATier tier = tiers[seededRandom.nextInt(tiers.length)];
            int range = tier.maxBaseProcessingMs - tier.minBaseProcessingMs + 1;
            int baseProcessing = tier.minBaseProcessingMs + seededRandom.nextInt(range);
            requests.add(new Request(index, tier, baseProcessing));
        }

        return requests;
    }

    static void printHeading(String title) {
        String border = "=".repeat(96);
        System.out.println();
        System.out.println(border);
        System.out.println(center(title, 96));
        System.out.println(border);
        System.out.println();
    }

    static String center(String text, int width) {
        if (text.length() >= width) {
            return text;
        }
        int left = (width - text.length()) / 2;
        int right = width - text.length() - left;
        return " ".repeat(left) + text + " ".repeat(right);
    }

    static void sleep(int durationMs) {
        try {
            Thread.sleep(durationMs);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}
