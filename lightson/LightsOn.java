/**
 *
 * Created by Dan Bent (2952542)  and Malix Moore (4971777) for COSC326, Etude 6: Lights On To Off
 *
 * A program to solve a "Lights Out"-style puzzle.
 *
 *
 *
 *
 **/




package lightson;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.Map;
import java.util.Scanner;



public class LightsOn {

    private ArrayList<Node> nodes;
    private Scanner sc;
    private int edgeCount = 0;
    private int nodeCount = 0;
    private int stepCount = 0;
    private final int MAX_ITERATIONS = 3;

    
    /**
     * Constructor - scans graph config from stdin.
     * Splits input and creates new Nodes
     **/
    public LightsOn() {
        nodes = new ArrayList<Node>();
        sc = new Scanner(System.in);
        String[] tempLights, tempJoins;

        //Split the lines (first is lights, second is joins)
        tempLights = sc.nextLine().split(" ");
        tempJoins = sc.nextLine().split(" ");
        nodeCount = tempLights.length;
        edgeCount = tempLights.length;
        // Add new nodes for each light
        for (String s : tempLights) {
            if (s.length() > 1) {
                System.out.println("Error: Node names must be a single character");
                return;
            }
            nodes.add(new Node(s.toLowerCase()));
        }
        sc.close();
        // Add the joins for each light
        for (String s : tempJoins) {
            String one = s.substring(0, 1).toLowerCase();
            String two = s.substring(1, 2).toLowerCase();
            Node tempOne = getNode(one);
            Node tempTwo = getNode(two);
            if (tempOne != null && tempTwo != null) {
                tempOne.join(tempTwo);
            }
        }
    
    }

    /**
     * Get node from array by its name
     * params - String name of light
     * returns - Node object corresponding to the given name
     **/
    public Node getNode(String name) {
        for (Node n : nodes) {
            if (n.name.equals(name)) {
                return n;
            }
        }
        return null;
    }
    
    /**
     * Prints out current state of graph to stdin
     **/
    public void print() {
        System.out.println("State:");
        for (Node n : nodes) {
            String label = n.isOn ? "-ON-" : "off";
            System.out.println(n.name.toUpperCase() + ":  " + label);
        }
    }

    public void solve() {
        System.out.println("---Solving puzzle with " + nodeCount + " lights---");
        // It may take one or two runs through this loop to achieve an 'all off' state.
        // We have limited this to 3, as any more will usually be the result of a cycle
        // that cannot be solved
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            int max = getMaxJoins();
            final int REDUCE = 2;
            // Even number of nodes in graph
            Boolean isEvenNodes = nodeCount % 2 == 0;
            while (max >= 0) {
                // If nodes count is even, reduce max by one so we deal with odd edges 
                if (isEvenNodes) {
                    // If max edges is even, reduce it to make it odd
                    if (max % 2 == 0) max--;
                    // If node count is odd and max edges is odd, reduce by one to make even (inverse parity)    
                } else {
                    if (max % 2 != 0) max--;
                    }
                for (Node n : nodes) {
                    // Check if all lights are off
                    if (areAllOff()) {
                        System.out.println("Solved in " + stepCount + " moves!");
                        print();
                        return;
                    }
                    // If the node is on, has 'max' amount of edges, all lights it affects are on
                    // and it affects at least one other light: switch it 
                    if (n.isOn
                        && n.getJoinCount() == max
                        && n.getJoinCount() == n.getOnCount()
                        && n.getJoinCount() != 0) {
                        System.out.println(++stepCount + ". Toggling light " + n.name.toUpperCase());
                        n.toggle();
                    }            
                }
                if (max == 1) {
                    max = 0;
                } else {
                    max = max - REDUCE;
                }
            }

            // If first loop didn't turn all lights off, lower the criteria:
            // If the node is on and affects at least one other light, toggle it
            // Constantly check for 'all off' state
            for (Node n : nodes) {
                
                if (areAllOff()) {
                    System.out.println("Solved in " + stepCount + " moves!");
                    print();
                    return;
                }
                if (n.isOn && n.getJoinCount() != 0) {
                    System.out.println(++stepCount + ". Toggling light " + n.name.toUpperCase());
                    n.toggle();
                }
            }

            // If some lights are still on by this stage, turn them off
            for (Node n : nodes) {
                
                if (areAllOff()) {
                    System.out.println("Solved in " + stepCount + " moves!");
                    print();
                    return;
                }
                if (n.isOn) {
                    System.out.println(++stepCount + ". Toggling light " + n.name.toUpperCase());
                    n.toggle();
                }
            }
        }
        System.out.println("Unsolvable. Closest solution was: ");
        print();
    }

    // Toggle node and all joined nodes
    public Boolean toggleNode(String s) {
        Node n = getNode(s);
        n.toggle();
        return n.isOn;
    }

    /**
     * returns - true if all lights are off, else false
     **/
    public Boolean areAllOff() {
        for (Node n : nodes) {
            if (n.isOn) return false;
        }
        return true;
    }

    /**
     * returns - maximum join count from set of all lights
     **/
    public int getMaxJoins() {
        int max = 0;
        for (Node n : nodes) {
            if (n.getJoinCount() > max) {
                max = n.getJoinCount();
            }
        }
        return max;
    }

    /**
     * A Node is a light. Stores on/off state, light name, and references to all lights it affects
     **/

    public class Node implements Comparable {
        
        public ArrayList<Node> joins;
        public Boolean isOn;
        public int joinCount = 0;
        public String name;

        public Node(String name) {
            this.isOn = true;
            this.joins = new ArrayList<Node>();
            this.name = name;
        }

        public void join(Node n) {
            if (joins.contains(n)) return;
            this.joins.add(n);
            joinCount++;
        }

        public void toggle() {
            this.isOn = !this.isOn;
            for (Node n : this.joins) {
                n.isOn = !n.isOn;
            }
        }

        public int getOnCount() {
            int count = 0;
            for (Node n : joins) {
                if (n.isOn) count++;
            }
            return count;
        }

        public int getJoinCount() {
            return joins.size();
        }

        public String toString() {
            return "Status: " + isOn;
        }

        public int compareTo(Object o) {
            Node n = (Node) o;
            return this.joinCount - n.getJoinCount();
        }

    }

    
}
