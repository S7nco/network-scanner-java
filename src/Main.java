//Phase 1
// Scan IP Range; Detect Alive Hosts
//Phase 2
// Add port scanning
//Phase 3
//Add multithreading
//Phase 4
//look professional

import java.net.InetAddress;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String baseIP = "192.168.1."; //Change to correct network
        ArrayList<String> reachableHosts = new ArrayList<>();
        System.out.println("Scanning network: " + baseIP + "1-254");

        for (int i = 1; i <= 254; i++) {
            String host = baseIP + i;
            System.out.println("Checking: " + host); //This is so I could tell it was scanning the network
            try {
                InetAddress address = InetAddress.getByName(host);
                if (address.isReachable(2000)) { // 2 second timeout
                    reachableHosts.add(host);
                }
            } catch (Exception e) {
                System.out.println(host + " is not reachable");
            }
        }
        System.out.println("\nScan Complete");
        System.out.println("Reachable Hosts:");
        for (String ip : reachableHosts) {
            System.out.println("- " + ip);
        }
    }
}