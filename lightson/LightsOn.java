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

    public LightsOn() {
        nodes = new ArrayList<Node>();
        sc = new Scanner(System.in);
        String[] tempLights, tempJoins;

        

        if (!sc.hasNextLine()) {
            System.out.println("Enter an input file on stdin");
            return;
        }
        //Split the lines (first is lights, second is joins)
        tempLights = sc.nextLine().split(" ");
        tempJoins = sc.nextLine().split(" ");
        nodeCount = tempLights.length;
        edgeCount = tempLights.length;
        // Add new nodes for each light
        for (String s : tempLights) {
            nodes.add(new Node(s.toLowerCase()));
        }
        sc.close();
        // Add the joins for each light
        for (String s : tempJoins) {
            String one = s.substring(0, 1).toLowerCase();
            String two = s.substring(1, 2).toLowerCase(); // Change these names
            Node tempOne = getNode(one);
            Node tempTwo = getNode(two);
            if (tempOne != null && tempTwo != null) {
                tempOne.join(tempTwo);
            }
        }
    
    }

    // Get node from array by its name
    public Node getNode(String name) {
        for (Node n : nodes) {
            if (n.name.equals(name)) {
                return n;
            }
        }
        return null;    }

    public void print() {
        System.out.println("State:");
        for (Node n : nodes) {
            System.out.println(n.name + " " + n.isOn);
        }
    }

    public void solve() {
        int max = getMaxJoins();
        Boolean isEvenNodes = nodeCount % 2 == 0;
        if (isEvenNodes) {
            if (max % 2 == 0) max--;
        } else {
            if (max % 2 != 0) max--;
        }
        while (max > 0) {
            for (Node n : nodes) {
                if (areAllOff()) {
                    System.out.println("Solved in " + stepCount + " moves!");
                    return;
                }
                if (n.isOn && n.getJoinCount() == max) {
                    System.out.println(++stepCount + ". Toggling " + n.name);
                    n.toggle();
                    print();
                }            
            }
            max = max - 2;
        }
        max = getMaxJoins();
        if (isEvenNodes) {
            if (max % 2 != 0) max--;
        } else {
            if (max % 2 == 0) max--;
        }
        while (max > 0) {
            for (Node n : nodes) {
                if (areAllOff()) {
                    System.out.println("Solved in " + stepCount + " moves!");
                    return;
                }
                if (n.isOn && n.getJoinCount() == max) {
                    System.out.println(++stepCount + ". Toggling " + n.name);
                    n.toggle();
                    print();
                }            
            }
            max = max - 2;
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

    public Boolean areAllOff() {
        for (Node n : nodes) {
            if (n.isOn) return false;
        }
        return true;
    }

    public int getMaxJoins() {
        int max = 0;
        for (Node n : nodes) {
            if (n.getJoinCount() > max) {
                max = n.getJoinCount();
            }
        }
        return max;
    }

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
