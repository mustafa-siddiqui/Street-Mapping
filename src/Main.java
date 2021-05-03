/**
 *  @file   Main.java
 *  @brief  Main driver code for the project.
 *  @author Mustafa Siddiqui
 *  @date   05/01/2021
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

    // used to draw paths over map
    static boolean ShortestPathFound = false;
    static boolean KruskalTree = false;
    static ArrayList<Vertex> ShortestPathList;
    static ArrayList<Edge> MinSpanningTreeList;

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

        // read through file and create graph
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
        }

        // close file since no longer needed
        input.close();

        // show output based on command line arguments
        if (ShortestPath) {
            Vertex start = mapPaths.getVertices().get(StartID.toString());
            Vertex end = mapPaths.getVertices().get(EndID.toString());

            ShortestPathFound = mapPaths.shortestPath(start, end);
            if (ShortestPathFound) {
                // returns the path from end to start
                ShortestPathList = mapPaths.getPath(end, start);
                
                // print intersections & distance travelled to console
                System.out.println("Shortest Path from " + start.getID() + " to " + end.getID() + ":");
                int j;
                for (j = ShortestPathList.size() - 1; j > 0; j--) {
                    System.out.print(ShortestPathList.get(j).getID() + " -> ");
                }
                System.out.println(ShortestPathList.get(j).getID());
                System.out.printf("Distance Travelled: %.2f miles\n", ShortestPathList.get(j).getDistance());
            }
        }

        if (MinSpanningTree) {
            // show min spanning tree
            MinSpanningTreeList = mapPaths.minSpanningTree();
            if (!MinSpanningTreeList.isEmpty()) {
                KruskalTree = true;

                // print roads to console
                System.out.println("Minimum Spanning Tree:");
                int c;
                for (c = 0; c < MinSpanningTreeList.size() - 1; c++) {
                    System.out.print(MinSpanningTreeList.get(c).getID() + ", ");
                }
                System.out.println(MinSpanningTreeList.get(c).getID());
            }
            else {
                System.out.println("Could not compute Minimum Spanning Tree");
            }
        }

        if (ShowMap) {
            new Main().setVisible(true);
        }
    }
}
