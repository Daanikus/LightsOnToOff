package lightson;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class LightsOnApp {

    public static void main(String[] args) {
        int lineCount = 0;
        Scanner scan = new Scanner(System.in);
        String[] lights = {};
        String[] joins = {};



        while (lineCount < 2) {
            if (scan.hasNextLine()) {
                if (lineCount == 0) lights = scan.nextLine().split(" ");
                if (lineCount == 1) joins = scan.nextLine().split(" ");
                lineCount++;
            } else {
                System.out.println("Please provide an input file");
                break;
            }
        }

        for (String s : lights) {
            s = s.toLowerCase();
        }
        for (String s : joins) {
            s = s.toLowerCase();
        }

        LightsOn lo = new LightsOn(lights, joins);
        lo.print();
        lo.toggleNode("b");
        lo.toggleNode("c");
        lo.print();

        
        scan.close();
        
    }
}