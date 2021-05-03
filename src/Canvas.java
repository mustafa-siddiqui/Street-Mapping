/**
 *  @file   Canvas.java
 *  @brief  Class to implement Java Graphics to draw the
 *          map using JFrames.
 *  @author Mustafa Siddiqui
 *  @date   05/02/2021
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import javax.swing.JComponent;
import java.util.ArrayList;

public class Canvas extends JComponent {

    @Override
    public void paintComponent(Graphics g) {
        // draw the base map (involves redrawing over lines)
        for (String key : Graph.vertices.keySet()) {
            ArrayList<Edge> neighbours = Graph.vertices.get(key).getAdjList();

            for (Edge e : neighbours) {
                // latitude -> y-coordinate, longitude -> x-coordinate
                Vertex start = Graph.vertices.get(key);
                Vertex end = Graph.vertices.get(e.getEndVertexID());
                
                sketchLine(g, start, end);
            }
        }

        // draw shortest path if found
        if (Main.ShortestPathFound) {
            DecimalFormat df = new DecimalFormat("#.##");

            // list is in reverse
            Vertex start = Main.ShortestPathList.get(Main.ShortestPathList.size() - 1);
            Vertex end = Main.ShortestPathList.get(0);

            // indicate start vertex
            g.setColor(Color.DARK_GRAY);
            g.fillOval(Math.abs(scaleLong(start.getLongitude())), Math.abs(getHeight() - scaleLat(start.getLatitude())), 10, 10);

            // mention distance travelled on top of canvas
            g.setColor(Color.BLACK);
            g.setFont(new Font("Serif", Font.PLAIN, 15));
            g.drawString("Distance: " + df.format(Main.ShortestPathList.get(0).getDistance()) + " mi", getWidth()/2 - 60, 20);

            // indicate end vertex
            g.setColor(Color.DARK_GRAY);
            g.fillOval(Math.abs(scaleLong(end.getLongitude())), Math.abs(getHeight() - scaleLat(end.getLatitude())), 10, 10);

            // draw path in red
            Graphics2D g2 = (Graphics2D)g;
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(3));

            for (int i = 0; i < Main.ShortestPathList.size() - 1; i++) {
                Vertex curr = Main.ShortestPathList.get(i); 
                Vertex next = Main.ShortestPathList.get(i + 1);
                sketchLine(g2, curr, next);
            }
        }

        // draw minimum spanning tree
        if (Main.KruskalTree) {
            // mention min spanning tree at top of canvas
            g.setColor(Color.BLACK);
            g.setFont(new Font("Serif", Font.PLAIN, 15));
            g.drawString("Minimum Spanning Tree", getWidth()/2 - 60, 20);

            // draw path in red
            Graphics2D g2 = (Graphics2D)g;
            g2.setColor(Color.RED);

            for (Edge e : Main.MinSpanningTreeList) {
                Vertex curr = Graph.vertices.get(e.getStartVertexID());
                Vertex next = Graph.vertices.get(e.getEndVertexID());
                sketchLine(g2, curr, next);
            }
        }
    }

    /*
        Scale the longitude to the canvas.
        x = normalized / range * scale_middle + scale_sides
    */
    public int scaleLong(double longitude) {
        double range = Main.maxLong - Main.minLong;

        // middle = 90% of width, sides = 0.05% on each side
        int scaleFactor_middle = 9 * getWidth() / 10;
        int scaleFactor_sides = 1 * getWidth() / 20;

        return (int)((longitude - Main.minLong)/(range) * scaleFactor_middle + scaleFactor_sides);
    }

    /*
        Scale the latitude to the canvas.
        y = normalized / range * scale_middle + scale_sides
    */
    public int scaleLat(double latitude) {
        double range = Main.maxLat - Main.minLat;

        // middle = 70% of height, sides = 15% on each side
        int scaleFactor_middle = 7 * getHeight() / 10;
        int scaleFactor_sides = 3 * getHeight() / 20;

        return (int)((latitude - Main.minLat)/(range) * scaleFactor_middle + scaleFactor_sides);
    }

    /*
        Method to draw a line on the map given two vertices.
    */
    public void sketchLine(Graphics g, Vertex start, Vertex end) {
        g.drawLine(Math.abs(scaleLong(start.getLongitude())), Math.abs(getHeight() - scaleLat(start.getLatitude())), 
                    Math.abs(scaleLong(end.getLongitude())), Math.abs(getHeight() - scaleLat(end.getLatitude())));
    }
}
