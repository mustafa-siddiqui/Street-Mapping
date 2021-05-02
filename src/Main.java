/**
 *  @file   Main.java
 *  @brief  Main driver code for the project.
 *  @author Mustafa Siddiqui
 *  @date   04/27/2021
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Main extends JFrame {
    /*
        Static variables to be used in drawing the map and the shortest path.
        Maximum latitude and longitude values used for normalizing values.
    */

    // lowest negative value possible
    static double maxLat = Double.MAX_VALUE * -1;
    static double maxLong = Double.MAX_VALUE * -1;

    // highest positive value possible
    static double minLat = Double.MAX_VALUE;
    static double minLong = Double.MAX_VALUE;

    /*
        Constructor to create a blank canvas for the map.
    */
    public Main() {
        // set a rectangular window
        setSize(900, 700);
        setTitle("Shortest Path Map");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new Canvas());
    }

    public static void main(String[] args) throws FileNotFoundException {
        // exit if non-sufficient arguments
        if (args.length < 2) {
            System.out.println("Usage: java Main map.txt [-show]" + 
                                "[-directions startIntersection endIntersection] [-meridianMap]");
            System.exit(0);
        }

        // process through arguments and set flags for what to do
        // arguments format: java program_name map.txt [-show] [-directions start end] [-meridianmap]
        boolean ShowMap = false;
        boolean ShortestPath = false;
        StringBuilder StartID = new StringBuilder("");
        StringBuilder EndID = new StringBuilder("");
        boolean MinSpanningTree = false;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-show")) {
                ShowMap = true;
            }
            else if (args[i].equals("-directions")) {
                ShortestPath = true;
                StartID.insert(0, args[++i]);
                EndID.insert(0, args[++i]);
            }
            else if (args[i].equals("-meridianmap")) {
                MinSpanningTree = true;
            }
        }

        // read file & open input file stream
        FileInputStream inputFile = null;
        Scanner input = null;
        try {
            inputFile = new FileInputStream(args[0]);
            input = new Scanner(inputFile);
        }
        catch (FileNotFoundException e) {
            System.out.println("'" + args[0] + "' does not exist!");
            System.exit(0);
        }
        
        // declare graph
        Graph mapPaths = new Graph();

        int i = 0;
        while (input.hasNextLine()) {
            String[] line = input.nextLine().split("\t");

            // intersection -> vertex
            if (line[0].equals("i")) {
                Vertex intersection = new Vertex(line[1], Double.parseDouble(line[2]), Double.parseDouble(line[3]));
                mapPaths.addIntersection(intersection);

                // set global max and min
                if (intersection.getLatitude() > maxLat)
                    maxLat = intersection.getLatitude();
                
                if (intersection.getLongitude() > maxLong)
                    maxLong = intersection.getLongitude();

                if (intersection.getLatitude() < minLat)
                    minLat = intersection.getLatitude();

                if (intersection.getLongitude() < minLong)
                    minLong = intersection.getLongitude();
            }
            // road -> edge
            else if (line[0].equals("r")) {
                // all intersections are declared before road data in data file
                Edge road = new Edge(line[1], mapPaths.getVertices().get(line[2]), mapPaths.getVertices().get(line[3]));
                mapPaths.addRoad(road, mapPaths.getVertices().get(line[2]), mapPaths.getVertices().get(line[3]));
            }

            ++i;
        }

        // close file since no longer needed
        input.close();

        System.out.println("Num: " + i);

        // show output based on command line arguments
        if (ShowMap) {
            new Main().setVisible(true);
        }

        if (ShortestPath) {
            Vertex start = mapPaths.getVertices().get(StartID.toString());
            Vertex end = mapPaths.getVertices().get(EndID.toString());

            if (mapPaths.shortestPath(start, end)) {
                // returns the path from end to start
                ArrayList<Vertex> pathList = mapPaths.getPath(end, start);
                
                int j;
                for (j = pathList.size() - 1; j > 0; j--) {
                    System.out.print(pathList.get(j).getID() + " -> ");
                }
                System.out.println(pathList.get(j).getID());
            }
        }

        if (MinSpanningTree) {
            // show min spanning tree
        }
    }
}
