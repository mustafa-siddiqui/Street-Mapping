/**
 *  @file   Canvas.java
 *  @brief  Class to implement Java Graphics to draw the
 *          map using JFrames.
 *  @author Mustafa Siddiqui
 *  @date   04/28/2021
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;
import javax.swing.JComponent;
import java.util.ArrayList;

public class Canvas extends JComponent {

    @Override
    public void paintComponent(Graphics g) {
        for (String key : Graph.vertices.keySet()) {
            ArrayList<Edge> neighbours = Graph.vertices.get(key).getAdjList();

            for (Edge e : neighbours) {
                // latitude -> y-coordinate, longitude -> x-coordinate
                Vertex start = Graph.vertices.get(key);
                Vertex end = Graph.vertices.get(e.getEndVertexID());
                
                g.drawLine(Math.abs(scaleLong(start.getLongitude())), Math.abs(getHeight() - scaleLat(start.getLatitude())), 
                            Math.abs(scaleLong(end.getLongitude())), Math.abs(getHeight() - scaleLat(end.getLatitude())));
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
}
