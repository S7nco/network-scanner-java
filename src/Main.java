//Phase 1
// Scan IP Range; Detect Alive Hosts
// Completed
//Phase 2
// Add port scanning
// Not Started Working On
//Phase 3
//Add multithreading
// Completed
//Phase 4
//look professional - Maybe GUI or touch ups
//Might make this into a wider network scanner with user
//selecting scan modes: wide or narrow
//Wide being current version plue some udpates
//Narrow being single or range of addreses: Hostname, Relay time, maybe traceroute info

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(50);

        String baseIP = "X.X.X."; //Change to correct network
        List<String> reachableHosts = Collections.synchronizedList(new ArrayList<>());
        System.out.println("Scanning network: " + baseIP + "1-254");

        for (int i = 1; i <= 254; i++) {
            String host = baseIP + i;
            System.out.println("Checking: " + host); //This is so I could tell it was scanning the network

            executor.execute(() -> {
                try {
                    InetAddress address = InetAddress.getByName(host);
                    if (address.isReachable(2000)) { // 2 second timeout
                        reachableHosts.add(host);
                    }
                } catch (Exception e) {
                    System.out.println(host + " is not reachable");
                }
            });
        }
        executor.shutdown(); //Stops accepting new tasks
        try {
            executor.awaitTermination(10, TimeUnit.MINUTES); //Wait for all tasks
        } catch (InterruptedException e) {
            System.out.println("Scan was interrupted.");
            e.printStackTrace();
        }

        //Sort IP addresses
        reachableHosts.sort((ip1, ip2) -> {
            String[] parts1 = ip1.split("\\.");
            String[] parts2 = ip2.split("\\.");
            for (int i = 0; i < 4; i++) {
                int diff = Integer.parseInt(parts1[i]) - Integer.parseInt(parts2[i]);
                if (diff != 0) return diff;
            }
            return 0;
        });

        System.out.println("\nScan Complete");
        System.out.println("Reachable Hosts:");
        for (String ip : reachableHosts) {
            System.out.println("- " + ip);
        }
    }
}