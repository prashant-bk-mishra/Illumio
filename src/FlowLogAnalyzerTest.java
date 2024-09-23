import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;

public class FlowLogAnalyzerTest {

    public static void main(String[] args) {
        testLoadLookupTable();
        testProcessFlowLogs();
        testGetProtocolName();
        System.out.println("All tests passed!");
    }

    public static void testLoadLookupTable() {
        try {
            // Create a sample lookup CSV content
            String lookupCsv = "dstport,protocol,tag\n" + "25,tcp,sv_P1\n" + "68,udp,sv_P2\n" + "443,tcp,sv_P2\n" + "110,tcp,email\n" + "993,tcp,email\n" + "143,tcp,email\n";

            // Write to a temporary file
            String lookupFilePath = "test_lookup.csv";
            writeToFile(lookupFilePath, lookupCsv);

            Map<String, String> lookupMap = FlowLogAnalyzer.loadLookupTable(lookupFilePath);

            // Assertions
            assert lookupMap.size() == 6 : "Lookup map size should be 6";
            assert "sv_P1".equals(lookupMap.get("25:tcp")) : "Tag for 25:tcp should be sv_P1";
            assert "email".equals(lookupMap.get("110:tcp")) : "Tag for 110:tcp should be email";

            // Clean up
            new java.io.File(lookupFilePath).delete();

        } catch (Exception e) {
            e.printStackTrace();
            assert false : "Exception occurred in testLoadLookupTable";
        }
    }

    public static void testProcessFlowLogs() {
        try {
            // Create a sample flow log content
            String flowLogContent = "2 123456789012 eni-0a1b2c3d 10.0.1.201 198.51.100.2 443 25 6 25 20000 1620140761 1620140821 ACCEPT OK\n" + "2 123456789012 eni-1a2b3c4d 192.168.1.6 87.250.250.242 49152 110 6 5 2500 1620140661 1620140721 ACCEPT OK\n" + "2 123456789012 eni-2d2e2f3g 192.168.2.7 77.88.55.80 49153 993 6 7 3500 1620140661 1620140721 ACCEPT OK\n" + "2 123456789012 eni-4h5i6j7k 172.16.0.2 192.0.2.146 49154 80 6 9 4500 1620140661 1620140721 ACCEPT OK\n";

            // Write to a temporary file
            String flowLogFilePath = "test_flowlogs.txt";
            writeToFile(flowLogFilePath, flowLogContent);

            // Create a sample lookup map
            Map<String, String> lookupMap = new HashMap<>();
            lookupMap.put("25:tcp", "sv_P1");
            lookupMap.put("110:tcp", "email");
            lookupMap.put("993:tcp", "email");

            Map<String, Integer> tagCounts = new HashMap<>();
            Map<String, Integer> portProtocolCounts = new HashMap<>();

            FlowLogAnalyzer.processFlowLogs(flowLogFilePath, lookupMap, tagCounts, portProtocolCounts);

            // Assertions for tag counts
            assert tagCounts.get("sv_P1") == 1 : "sv_P1 count should be 1";
            assert tagCounts.get("email") == 2 : "email count should be 2";
            assert tagCounts.get("Untagged") == 1 : "Untagged count should be 1";

            // Assertions for port/protocol counts
            assert portProtocolCounts.get("25,tcp") == 1 : "25,tcp count should be 1";
            assert portProtocolCounts.get("110,tcp") == 1 : "110,tcp count should be 1";
            assert portProtocolCounts.get("993,tcp") == 1 : "993,tcp count should be 1";
            assert portProtocolCounts.get("80,tcp") == 1 : "80,tcp count should be 1";

            // Clean up
            new java.io.File(flowLogFilePath).delete();

        } catch (Exception e) {
            e.printStackTrace();
            assert false : "Exception occurred in testProcessFlowLogs";
        }
    }

    public static void testGetProtocolName() {
        // Test known protocols
        assert "icmp".equals(ProtocolName.getProtocolName("1")) : "Protocol 1 should be icmp";
        assert "tcp".equals(ProtocolName.getProtocolName("6")) : "Protocol 6 should be tcp";
        assert "udp".equals(ProtocolName.getProtocolName("17")) : "Protocol 17 should be udp";

        // Test unknown protocol
        assert "123".equals(ProtocolName.getProtocolName("123")) : "Protocol 123 should return '123'";
    }

    // Utility method to write content to a file
    private static void writeToFile(String filePath, String content) throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(content);
        writer.close();
    }
}