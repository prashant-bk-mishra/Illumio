/**
 * ProtocolName has the sole purpose of mapping protocol numbers to protocol names.
 * Reference: <a href="https://www.iana.org/assignments/protocol-numbers/protocol-numbers.xhtml">...</a>
 */

public class ProtocolName {

    /**
     * Maps protocol numbers to protocol names.
     *
     * @param protocolNumber The protocol number as a string.
     * @return The protocol name (e.g., "tcp", "udp", "icmp").
     */
    public static String getProtocolName(String protocolNumber) {
        return switch (protocolNumber) {
            case "0" -> "hopopt";
            case "1" -> "icmp";
            case "2" -> "igmp";
            case "3" -> "ggp";
            case "4" -> "ip-in-ip";
            case "5" -> "st";
            case "6" -> "tcp";
            case "7" -> "cbt";
            case "8" -> "egp";
            case "9" -> "igp";
            case "10" -> "bbn-rcc-mon";
            case "11" -> "nvp-ii";
            case "12" -> "pup";
            case "13" -> "argus";
            case "14" -> "emcon";
            case "15" -> "xnet";
            case "16" -> "chaos";
            case "17" -> "udp";
            case "18" -> "mux";
            case "19" -> "dcn-meas";
            case "20" -> "hmp";
            case "21" -> "prm";
            case "22" -> "xns-idp";
            case "23" -> "trunk-1";
            case "24" -> "trunk-2";
            case "25" -> "leaf-1";
            case "26" -> "leaf-2";
            case "27" -> "rdp";
            case "28" -> "irtp";
            case "29" -> "iso-tp4";
            case "30" -> "netblt";
            case "31" -> "mfe-nsp";
            case "32" -> "merit-inp";
            case "33" -> "dccp";
            case "34" -> "3pc";
            case "35" -> "idpr";
            case "36" -> "xtp";
            case "37" -> "ddp";
            case "38" -> "idpr-cmtp";
            case "39" -> "tp++";
            case "40" -> "il";
            case "41" -> "ipv6";
            case "42" -> "sdrp";
            case "43" -> "ipv6-route";
            case "44" -> "ipv6-frag";
            case "45" -> "idrp";
            case "46" -> "rsvp";
            case "47" -> "gre";
            case "48" -> "dsr";
            case "49" -> "bna";
            case "50" -> "esp";
            case "51" -> "ah";
            case "52" -> "i-nlsp";
            case "53" -> "swipe";
            case "54" -> "narp";
            case "55" -> "mobile";
            case "56" -> "tlsp";
            case "57" -> "skip";
            case "58" -> "ipv6-icmp";
            case "59" -> "ipv6-nonxt";
            case "60" -> "ipv6-opts";
            case "62" -> "cftp";
            case "64" -> "sat-expak";
            case "65" -> "kryptolan";
            case "66" -> "rvd";
            case "67" -> "ippc";
            case "69" -> "sat-mon";
            case "70" -> "visa";
            case "71" -> "ipcu";
            case "72" -> "cpnx";
            case "73" -> "cphb";
            case "74" -> "wsn";
            case "75" -> "pvp";
            case "76" -> "br-sat-mon";
            case "77" -> "sun-nd";
            case "78" -> "wb-mon";
            case "79" -> "wb-expak";
            case "80" -> "iso-ip";
            case "81" -> "vmtp";
            case "82" -> "secure-vmtp";
            case "83" -> "vines";
            case "84" -> "iptm";
            case "85" -> "nsfnet-igp";
            case "86" -> "dgp";
            case "87" -> "tcf";
            case "88" -> "eigrp";
            case "89" -> "ospf";
            case "90" -> "sprite-rpc";
            case "91" -> "larp";
            case "92" -> "mtp";
            case "93" -> "ax.25";
            case "94" -> "os";
            case "95" -> "micp";
            case "96" -> "scc-sp";
            case "97" -> "etherip";
            case "98" -> "encap";
            case "100" -> "gmtp";
            case "101" -> "ifmp";
            case "102" -> "pnni";
            case "103" -> "pim";
            case "104" -> "aris";
            case "105" -> "scps";
            case "106" -> "qnx";
            case "107" -> "a/n";
            case "108" -> "ipcomp";
            case "109" -> "snp";
            case "110" -> "compaq-peer";
            case "111" -> "ipx-in-ip";
            case "112" -> "vrrp";
            case "113" -> "pgm";
            case "115" -> "l2tp";
            case "116" -> "ddx";
            case "117" -> "iatp";
            case "118" -> "stp";
            case "119" -> "srp";
            case "120" -> "uti";
            case "121" -> "smp";
            case "122" -> "sm";
            case "123" -> "ptp";
            case "125" -> "fire";
            case "126" -> "crtp";
            case "127" -> "crudp";
            case "128" -> "sscopmce";
            case "129" -> "iplt";
            case "130" -> "sps";
            case "131" -> "pipe";
            case "132" -> "sctp";
            case "133" -> "fc";
            case "134" -> "rsvp-e2e-ignore";
            case "136" -> "udplite";
            case "137" -> "mpls-in-ip";
            case "138" -> "manet";
            case "139" -> "hip";
            case "140" -> "shim6";
            case "141" -> "wesp";
            case "142" -> "rohc";
            case "143" -> "ethernet";
            case "144" -> "aggfrag";
            case "145" -> "nsh";
            // Return the number itself for unknown protocols.
            default -> protocolNumber;
        };
    }
}
