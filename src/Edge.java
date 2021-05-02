/**
 *  @file   Edge.java
 *  @brief  Class to represent an edge in a graph.
 *  @author Mustafa Siddiqui
 *  @date   04/26/2021
 */

import java.lang.Math;

public class Edge implements Comparable<Edge> {
    private String id;
    private String startVertexID;
    private String endVertexID;
    private double weight;

    /*
        Constructor for the Edge class.
        @param: roadID  -   id
        @param: vertex1 -   start
        @param: vertex2 -   end
    */
    public Edge(String id, Vertex start, Vertex end) {
        this.id = id;
        this.startVertexID = start.getID();
        this.endVertexID = end.getID();
        this.weight = calcWeight(start, end);
    }

    /*
        Calculate the distance between two intersections (or the length of
        the road aka weight of the edge) using the haversine formula:
        https://www.movable-type.co.uk/scripts/latlong.html 
    */
    public double calcWeight(Vertex start, Vertex end) {
        // use haversine formula
        // radius of earth in miles
        double R = 3958.8;

        double deltaLat = Math.toRadians(start.getLatitude() - end.getLatitude());
        double delatLong = Math.toRadians(start.getLongitude() - end.getLongitude());

        double a = (Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)) +
                    Math.cos(Math.toRadians(start.getLatitude())) * Math.cos(Math.toRadians(end.getLatitude())) * 
                    (Math.sin(delatLong / 2) * Math.sin(delatLong / 2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (R * c);
    }

    /*
        Method to compare two edges based on their weights.
        Returns a positive integer if this edge has a larger weight
        and negative if other edge has a larger weight.
    */
    @Override
    public int compareTo(Edge other) {
        return (int)(this.getWeight() - other.getWeight());
    }

    /*
        Get methods for different parameters.
    */

    public String getID() {
        return id;
    }

    public String getStartVertexID() {
        return startVertexID;
    }

    public String getEndVertexID() {
        return endVertexID;
    }

    public double getWeight() {
        return weight;
    }

    /*
        Returns the edge parameters as a string.
    */
    @Override
    public String toString() {
        return "ID: " + id + ", Weight: " + weight + ", Start: " + startVertexID + ", End: " + endVertexID;
    }
}
