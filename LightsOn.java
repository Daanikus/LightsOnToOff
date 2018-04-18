package lightson;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.Map;



public class LightsOn {

    private HashMap<String, Node> nodes = new HashMap<>();

    public LightsOn(String[] nodes, String[] joins) {
        for (String s : nodes) {
            this.nodes.put(s, new Node());
        }
        for (String s : joins) {
            this.nodes.get(s.substring(0, 1)).join(this.nodes.get(s.substring(1, 2)));
        }        
    }

    public void print() {
        for (String s : nodes.keySet()) {
            System.out.println(s + ": " + nodes.get(s).toString());
        }
    }

    public Boolean toggleNode(String s) {
        Node n = nodes.get(s);
        if (n == null) return false;
        n.toggle();
        return true;
    }

    public class Node implements Comparable {
        public ArrayList<Node> joins;
        public Boolean on;
        public int joinCount = 0;

        public Node() {
            this.on = true;
            this.joins = new ArrayList<Node>();
        }

        public void join(Node n) {
            this.joins.add(n);
            n.joins.add(this);
            joinCount++;
        }

        public void toggle() {
            this.on = !this.on;
            for (Node n : joins) {
                n.on = !n.on;
            }
        }

        public int joinCount() {
            return joins.size();
        }

        public String toString() {
            return "Status: " + on;
        }

        public int compareTo(Node n) {
            
            if (this.joinCount() > n.joinCount()) return 1;
            if (this.joinCount() == n.joinCount()) return 0;
            if (this.joinCount() < n.joinCount()) return -1;
            
        }
    }

    
}