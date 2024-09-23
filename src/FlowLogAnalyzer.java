import java.io.*;
import java.util.*;

/**
 * FlowLogAnalyzer parses flow logs and maps each flow to a tag based on a lookup table.
 * It generates counts of matches for each tag and counts of matches for each port/protocol combination.
 */
public class FlowLogAnalyzer {

    private static final String DST_PORT_TEXT = "dstport";
    private static final boolean DEBUG = true;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java FlowLogAnalyzer <flowLogFilePath> <lookupFilePath>");
            return;
        }
        String flowLogFilePath = args[0];
        String lookupFilePath = args[1];

        // Print file locations for debugging.
        if (DEBUG) {
            File directory = new File("./");
            System.out.println("Current working directory: " + directory.getAbsolutePath());
            System.out.println("Flow log file path: " + flowLogFilePath);
            System.out.println("Lookup file path: " + lookupFilePath);
        }

        try {
            Map<String, String> lookupMap = loadLookupTable(lookupFilePath);

            // Print lookup map for debugging.
            if (DEBUG) {
                System.out.println("\nLookup map:");
                System.out.println(lookupMap);
            }

            Map<String, Integer> tagCounts = new HashMap<>();
            Map<String, Integer> portProtocolCounts = new HashMap<>();
            processFlowLogs(flowLogFilePath, lookupMap, tagCounts, portProtocolCounts);

            outputTagCounts(tagCounts);
            outputPortProtocolCounts(portProtocolCounts);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the lookup table from a CSV file.
     *
     * @param lookupFilePath Path to the lookup CSV file.
     * @return A map with keys as "dstPort:protocol" and values as tags.
     * @throws IOException If an I/O error occurs.
     */
    public static Map<String, String> loadLookupTable(String lookupFilePath) throws IOException {
        Map<String, String> lookupMap = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(lookupFilePath));
        String line;
        while ((line = reader.readLine()) != null) {
            // Skip header or empty lines.
            if (line.trim().isEmpty() || line.trim().toLowerCase().startsWith(DST_PORT_TEXT)) {
                continue;
            }
            String[] tokens = line.split(",");
            // Skip invalid lines.
            if (tokens.length != 3) {
                if (DEBUG) {
                    System.out.println("Invalid line in lookup table: " + line);
                }
                continue;
            }
            String dstPort = tokens[0].trim();
            String protocol = tokens[1].trim().toLowerCase();
            String tag = tokens[2].trim();
            String key = dstPort + ":" + protocol;
            lookupMap.put(key.toLowerCase(), tag);
        }
        reader.close();
        return lookupMap;
    }

    /**
     * Processes the flow logs and updates the tag counts and port/protocol counts.
     *
     * @param flowLogFilePath    Path to the flow log file.
     * @param lookupMap          The lookup table map.
     * @param tagCounts          Map to store counts of tags.
     * @param portProtocolCounts Map to store counts of port/protocol combinations.
     * @throws IOException If an I/O error occurs.
     */
    public static void processFlowLogs(String flowLogFilePath, Map<String, String> lookupMap, Map<String, Integer> tagCounts, Map<String, Integer> portProtocolCounts) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(flowLogFilePath));
        String line;
        while ((line = reader.readLine()) != null) {
            // Skip empty lines.
            if (line.trim().isEmpty()) {
                continue;
            }
            String[] tokens = line.trim().split("\\s+");
            // Skip invalid lines.
            if (tokens.length != 14) {
                if (DEBUG) {
                    System.out.println("Invalid line in flow log: " + line);
                }
                continue;
            }
            String dstPort = tokens[6].trim();
            String protocolNumber = tokens[7].trim();
            String protocol = ProtocolName.getProtocolName(protocolNumber).toLowerCase();

            // Could not identify the protocol but continue anyway.
            if (DEBUG && (protocol == protocolNumber)) {
                System.out.println("Invalid protocol in line flow log: " + line);
            }

            // Build key for port/protocol counts
            String portProtocolKey = dstPort + "," + protocol;
            portProtocolCounts.put(portProtocolKey, portProtocolCounts.getOrDefault(portProtocolKey, 0) + 1);

            // Build key for lookup
            String lookupKey = dstPort + ":" + protocol;
            String tag = lookupMap.get(lookupKey.toLowerCase());

            if (tag != null) {
                tagCounts.put(tag, tagCounts.getOrDefault(tag, 0) + 1);
            } else {
                tagCounts.put("Untagged", tagCounts.getOrDefault("Untagged", 0) + 1);
            }
        }
        reader.close();
    }

    /**
     * Outputs the counts of matches for each tag.
     *
     * @param tagCounts Map containing tag counts.
     */
    public static void outputTagCounts(Map<String, Integer> tagCounts) {
        System.out.println("\nTag Counts:");
        System.out.println("Tag,Count");
        for (Map.Entry<String, Integer> entry : tagCounts.entrySet()) {
            System.out.println(entry.getKey() + "," + entry.getValue());
        }
    }

    /**
     * Outputs the counts of matches for each port/protocol combination.
     *
     * @param portProtocolCounts Map containing port/protocol counts.
     */
    public static void outputPortProtocolCounts(Map<String, Integer> portProtocolCounts) {
        System.out.println("\nPort/Protocol Combination Counts:");
        System.out.println("Port,Protocol,Count");
        for (Map.Entry<String, Integer> entry : portProtocolCounts.entrySet()) {
            String[] keyParts = entry.getKey().split(",");
            String port = keyParts[0];
            String protocol = keyParts[1];
            System.out.println(port + "," + protocol + "," + entry.getValue());
        }
    }
}