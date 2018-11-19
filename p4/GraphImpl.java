import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.*;


/**
 * Filename:   GraphImpl.java
 * Project:    p4
 * Course:     cs400 
 * Authors:    
 * Due Date:   
 * 
 * T is the label of a vertex, and List<T> is a list of
 * adjacent vertices for that vertex.
 *
 * Additional credits: 
 *
 * Bugs or other notes: 
 *
 * @param <T> type of a vertex
 */
public class GraphImpl<T> implements GraphADT<T> {

    // YOU MAY ADD ADDITIONAL private members
    // YOU MAY NOT ADD ADDITIONAL public members

    /**
     * Store the vertices and the vertice's adjacent vertices
     */
    private Map<T, List<T>> verticesMap; 
    private ArrayList<T> vertices;
    
    private static <T> Set<T> convertArrayToSet(ArrayList<T> ara) 
    { 
  
        // Create an empty Set 
        Set<T> set = new HashSet<>(); 
  
        // Iterate through the array 
        for (T t : ara) { 
            // Add each element into the set 
            set.add(t); 
        } 
  
        // Return the converted Set 
        return set; 
    } 
    
    /**
     * Construct and initialize and empty Graph
     */ 
    public GraphImpl() {
        verticesMap = new HashMap<T, List<T>>();
        vertices = new ArrayList<T>();
    }

    public void addVertex(T vertex)
    {
        if (!this.vertices.contains(vertex))
        {
            this.vertices.add(vertex);
            ArrayList<T> l = new ArrayList<T>();
            this.verticesMap.put(vertex, l);        
        }
    }

    public void removeVertex(T vertex)
    {
        if (this.vertices.contains(vertex))
        {
            this.vertices.remove(vertex);
            this.verticesMap.remove(vertex);
            for (Map.Entry<T,List<T>> entry : this.verticesMap.entrySet())  
            {
                entry.getValue().remove(vertex);

            }
        }
    }

    public void addEdge(T vertex1, T vertex2)
    {
        if (!this.vertices.contains(vertex1))
        {
            this.vertices.add(vertex1);
            ArrayList<T> l = new ArrayList<T>();
            this.verticesMap.put(vertex1, l);
        }
        if (!this.vertices.contains(vertex2))
        {
            this.vertices.add(vertex2);
            ArrayList<T> l = new ArrayList<T>();
            this.verticesMap.put(vertex2, l);
        }
            this.verticesMap.get(vertex1).add(vertex2);
    }

    
    public void removeEdge(T vertex1, T vertex2)
    {
        if (this.vertices.contains(vertex1))
            if(this.verticesMap.get(vertex1).contains(vertex2))
                this.verticesMap.get(vertex1).remove(vertex2);
            else
                System.out.println(vertex1 + " is not connected to " + vertex2);
        else
            System.out.println(vertex1 + " has not been added!");
    }    
    
    public Set<T> getAllVertices() {
        return this.convertArrayToSet(this.vertices);
    }

    public List<T> getAdjacentVerticesOf(T vertex) {
        return this.verticesMap.get(vertex);
    }
    
    public boolean hasVertex(T vertex) {
        return this.vertices.contains(vertex);
    }

    public int order() {
        return this.vertices.size();
    }

    public int size() {
        int cnt = 0;
        for (Map.Entry<T,List<T>> entry : this.verticesMap.entrySet())  
            cnt = cnt + entry.getValue().size(); 
        return cnt;
    }

    public ArrayList<T> getVertices()
    {
        return this.vertices;
    }

    public Map<T, List<T>> getMap()
    {
        return this.verticesMap;
    }


    /**
     * Prints the graph for the reference
     * DO NOT EDIT THIS FUNCTION
     * DO ENSURE THAT YOUR verticesMap is being used 
     * to represent the vertices and edges of this graph.
     */
    public void printGraph() {

        for ( T vertex : verticesMap.keySet() ) {
            if ( verticesMap.get(vertex).size() != 0) {
                for (T edges : verticesMap.get(vertex)) {
                    System.out.println(vertex + " -> " + edges + " ");
                }
            } else {
                System.out.println(vertex + " -> " + " " );
            }
        }
    }



}
