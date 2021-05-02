import java.util.ArrayList;

class Test {
    public static void main(String[] args) {
        Graph nodes = new Graph();

        Vertex node_1 = new Vertex("A", 50, 50);
        nodes.addIntersection(node_1);

        Vertex node_2 = new Vertex("B", 51, 51);
        nodes.addIntersection(node_2);

        Vertex node_3 = new Vertex("C", 49, 49);
        nodes.addIntersection(node_3);

        Vertex node_4 = new Vertex("D", 47, 48);
        nodes.addIntersection(node_4);

        Vertex node_5 = new Vertex("E", 51, 50);
        nodes.addIntersection(node_5);

        // print vertices entered
        for (Vertex v : nodes.getVertices().values()) {
            System.out.println(v.toString());
        }
        System.out.println();

        Edge line = new Edge("i1", node_1, node_3);
        nodes.addRoad(line, node_1, node_3);

        Edge line2 = new Edge("i2", node_1, node_2);
        nodes.addRoad(line2, node_1, node_2);

        Edge line3 = new Edge("i3", node_2, node_5);
        nodes.addRoad(line3, node_2, node_5);

        Edge line4 = new Edge("i4", node_4, node_3);
        nodes.addRoad(line4, node_4, node_3);

        Edge line5 = new Edge("i5", node_2, node_4);
        nodes.addRoad(line5, node_2, node_4);

        Edge line6 = new Edge("i6", node_3, node_5);
        nodes.addRoad(line6, node_3, node_5);

        // print edges entered
        System.out.println();
        for (String key : nodes.getVertices().keySet()) {
            System.out.println(key);
            ArrayList<Edge> neighbours = nodes.getVertices().get(key).getAdjList();
            for (Edge e : neighbours) {
                System.out.println(e.toString());
            }
            System.out.println();
        }
        System.out.println();
        
        nodes.shortestPath(node_2, node_3);

        ArrayList<Vertex> list = retrievePath(node_3, node_2);
        System.out.println(list.size());
        for (Vertex v : list) {
            System.out.print(v.getID() + " -> ");
        }
        System.out.println();

    }

    public static ArrayList<Vertex> retrievePath(Vertex end, Vertex start) {
        ArrayList<Vertex> list = new ArrayList<Vertex>();
        Vertex current = end;

        if (current.getParent() == null) {
            System.out.println("Null Parent!!");
        }
        list.add(current);
        while (current.getParent() != null) {
            //System.out.println(current.getID());
            //System.out.println("Parent: " + current.getParent().getID());
            current = current.getParent();
            list.add(current);
        }

        return list;
    }
}