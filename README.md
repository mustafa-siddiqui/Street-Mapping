# Street-Mapping

## Overview
The program reads an input file containing information about intersections and roads for an area. It maps a scalable, resizable graph using the Java Graphics Library, computes the shortest path between two intersections (nodes/vertices) provided by the user using Dijkstra's algorithm and plots in on the map, and computes the minimum spanning tree of a graph using Kruskal's algorithm (the implementation is computationally inefficient for very large datasets due to lack of O(1) data retrieval times for sets.

## Performance
Tested with three files (also in the repo) in increasing order of information:
 * ur.txt (~300 vertices and edges combined)
 * monroe.txt (~150,000 vertices and edges combined)
 * nys.txt (>1,000,000 vertices and edges combined)

#### Shortest Path Runtimes (Macbook Pro with Intel i5)
 * ur.txt: <1 sec
 * monroe.txt: ~1 sec
 * nys.txt: <2 sec

## Compilation
To compile, navigate to the directory containing the code source files and type:
```bash
javac Main.java
java Main [file_name] [-show] [-directions startIntersection endIntersection] [-meridianmap]
```

Flags (can be used in combination with one another):  
-show: to graph the map of the data  
-directions startIntersection endIntersection: compute shortest path between two intersections  
-meridianmap: compute the minimum spanning tree

** need to have at least one optional flag to run.

## Results
### Shortest Paths
#### UoR Campus
![ur.txt](https://github.com/mustafa-siddiqui/Street-Mapping/blob/master/image_results/UR_Campus.png)

#### Monroe County in NY State
![monroe.txt](https://github.com/mustafa-siddiqui/Street-Mapping/blob/master/image_results/Monroe.png)

#### NY State
![nys.txt](https://github.com/mustafa-siddiqui/Street-Mapping/blob/master/image_results/NYS.png)

#### Minimum Spanning Tree
![kruskal](https://github.com/mustafa-siddiqui/Street-Mapping/blob/master/image_results/MinSpanningTree.png)
