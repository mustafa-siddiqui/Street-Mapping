/**
 *  @file   Graph.java
 *  @brief  Class to represent a graph ADT. Uses the
 *          Vertex and Edge classes.
 *  @author Mustafa Siddiqui
 *  @date   04/27/2021
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Graph {
    // hashmap for O(1) vertex retrieval
    public static HashMap<String, Vertex> vertices;
    
    /*
        Constructor for the graph class.
    */
    public Graph() {
        vertices = new HashMap<String, Vertex>();
    }

    /*
        Add a vertex (intersection) to the graph structure.
    */
    public void addIntersection(Vertex node) {
        vertices.put(node.getID(), node);
    }

    /*
        Add ane edge (road) to the adjacency lists of the vertices 
        (intersections) it connects.
    */
    public void addRoad(Edge connect, Vertex node1, Vertex node2) {
        vertices.get(node1.getID()).getAdjList().add(connect);
        vertices.get(node2.getID()).getAdjList().add(connect);
        //System.out.println(connect.getID() + " added to " + node1.getID() + " & " + node2.getID());
    }

    /*
        Access the vertices stored in the graph.
    */
    public HashMap<String, Vertex> getVertices() {
        return vertices;
    }

    /*
        Implement Dijkstra's algorithm to find the shortest path
        between two vertices on the map. Updates the parent vertex
        for each vertex, and then the path can be retraced by following
        the parent vertices starting from the end vertex.
    */
    public boolean shortestPath(Vertex start, Vertex end) {
        // initial values of parameters already set during vertex instantiation
        // distance -> INF, visited -> false, parent -> null
        // minimum priority queue of unvisited vertices
        PriorityQueue<Vertex> unvisited = new PriorityQueue<Vertex>();

        // start vertex has distance 0 from itself
        start.setDistance(0);
        unvisited.add(start);

        // visit every vertex once
        while (!unvisited.isEmpty()) {
            // retrieve vertex with the smallest distance
            Vertex current = unvisited.poll();
            current.setVisited(true);

            for (Edge e : current.getAdjList()) {
                Vertex adjacent = vertices.get(e.getEndVertexID());
                // take care of a flipped road
                if (adjacent == current) {
                    adjacent = vertices.get(e.getStartVertexID());
                }

                if (!adjacent.getVisited()) {
                    double adjDistance = current.getDistance() + e.getWeight();
                    if (adjDistance < adjacent.getDistance()) {
                        // update distance and parent vertex
                        adjacent.setDistance(adjDistance);
                        adjacent.setParent(current);

                        // add adjacent nodes to priority queue
                        unvisited.add(adjacent);
                        adjacent.setVisited(true);
                    }
                }
            }
            
            // break out of the loop if destination is reached
            if (end.getVisited()) {
                System.out.println("Shortest path found!");
                return true;
            }
            
        }

        return false;
    }

    /*
        Retrieve the path by tracing the parent vertices of
        multiple vertices starting from the destination vertex.

        Path is stored as a list of vertices.
        Path is: destination -> start
    */
    public ArrayList<Vertex> getPath(Vertex end, Vertex start) {
        ArrayList<Vertex> pathList = new ArrayList<Vertex>();

        Vertex current = end;
        pathList.add(current);

        // stop at vertex whose parent is the start vertex
        while (current.getParent() != start) {
            current = current.getParent();
            pathList.add(current);
        }

        // add the start vertex to list
        pathList.add(start);

        return pathList;
    }

    public HashSet<Edge> minSpanningTree() {
        PriorityQueue<Edge> edgeList = new PriorityQueue<Edge>();
        
        // store all edges in the graph in min priority queue
        for (String key : vertices.keySet()) {
            ArrayList<Edge> temp = vertices.get(key).getAdjList();
            for (Edge e : temp) {
                edgeList.add(e);
            }
        }

        HashSet<HashSet<Vertex>> vertexSet = new HashSet<HashSet<Vertex>>();
    } 
}
