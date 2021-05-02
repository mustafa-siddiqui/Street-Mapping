/**
 *  @file   Vertex.java
 *  @brief  Class to represent a vertex in a graph.
 *  @author Mustafa Siddiqui
 *  @date   04/25/2021
 */

import java.util.ArrayList;

public class Vertex implements Comparable<Vertex> {
    // basic parameters
    private double latitude;
    private double longitude;
    private String id;

    // initialize parameters for Dijkstra's algorithm 
    private double distance = Double.MAX_VALUE;
    private boolean visited = false;
    private Vertex parent = null;

    // adjacency list for storing all the edges connected to a vertex
    private ArrayList<Edge> adjList = new ArrayList<Edge>();

    /*
        Constructor for the Vertex class.
        @param: intersectionID  -   id
        @param: latitude
        @param: longitude
    */
    public Vertex(String id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /*
        Compare two vertices based on their distances.
        Returns -1 if the distance of this vertex to the start vertex 
        is less than the distance of the other vertex (node) to the start
        vertex and vice versa. Returns 0 if distance is equal.
        Used in implementing Dijkstra's algorithm.
    */
    @Override
    public int compareTo(Vertex node) {
        if (distance < node.distance) {
            return -1;
        }
        else if (distance > node.distance) {
            return 1;
        }

        return 0;
    }

    /*
        Set methods for different parameters.
    */

    void setParent(Vertex node) {
        parent = node;
    }

    void setDistance(double d) {
        distance = d;
    }

    void setVisited(boolean val) {
        visited = val;
    }

    void setAdjList(ArrayList<Edge> list) {
        adjList = list;
    }

    /*
        Get methods for different parameters.
    */

    public String getID() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Vertex getParent() {
        return parent;
    }

    public double getDistance() {
        return distance;
    }

    public boolean getVisited() {
        return visited;
    }
    
    public ArrayList<Edge> getAdjList() {
        return adjList;
    }

    /*
        Returns the vertex parameters as a string.
    */
    @Override
    public String toString() {
        return "ID: " + id + ", Lat: " + latitude + " Long: " + longitude +
                ", Parent: [" + parent + "], Visited: " + visited + ", Distance: " + distance; 
    }
}