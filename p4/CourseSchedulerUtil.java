
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.*;
import java.lang.Object;
import java.lang.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.*;


/**
 * Filename:   CourseSchedulerUtil.java
 * Project:    p4
 * Authors:    Debra Deppeler
 * 
 * Use this class for implementing Course Planner
 * @param <T> represents type
 */

public class CourseSchedulerUtil<T> {
    
    // can add private but not public members
    
    /**
     * Graph object
     */
    public GraphImpl<T> graphImpl;
    
    
    /**
     * constructor to initialize a graph object
     */
    public CourseSchedulerUtil() {
        this.graphImpl = new GraphImpl<T>();
    }

    public void print(T s)
    {
        System.out.println(s);
    }
    
    /**
    * createEntity method is for parsing the input json file 
    * @return array of Entity object which stores information 
    * about a single course including its name and its prerequisites
    * @throws Exception like FileNotFound, JsonParseException
    */
    @SuppressWarnings("rawtypes")
    public Entity<T>[] createEntity(String fileName) throws Exception
    {   
        File tmpDir = new File(fileName);
        if (!tmpDir.exists())
        {
            throw new FileNotFoundException();
        }
        Object obj = new JSONParser().parse(new FileReader(fileName)); 
        JSONObject jo = (JSONObject) obj;
        JSONArray ja = (JSONArray) jo.get("courses"); 
        Iterator iter = ja.iterator();
        ArrayList<Entity<String>> course_list = new ArrayList<Entity<String>>();

        int cnt = 0;
        while (iter.hasNext())
        {
            cnt++;
            Entity<String> course = new Entity<String>();
            String s = new String();
            ArrayList<String> sl= new ArrayList<String>();
            Iterator<Map.Entry> itr1 = ((Map) iter.next()).entrySet().iterator();
            while (itr1.hasNext())
            { 
                List<String> pre = new ArrayList<String>();
                Map.Entry pair = itr1.next(); 
                //System.out.println(pair.getKey()); // + " : " + pair.getValue()
                if (pair.getKey().equals("name"))
                    s = pair.getValue().toString();
                if (pair.getKey().equals("prerequisites"))
                {
                    Iterator it = ((JSONArray)pair.getValue()).iterator();
                    while(it.hasNext())
                    {
                        String tmp = (String) it.next();
                        sl.add(tmp);
                    }
                }
            } 

            course.setName(s);
            String[] ls = new String[sl.size()];
            for(int i = 0; i < sl.size(); i++)
            {  
                ls[i] = (String) sl.get(i);
            }
            course.setPrerequisites(ls);
            course_list.add(course);
        }

        Entity[] cl = new Entity [cnt];
        for(int i = 0; i < course_list.size(); i++)
            {  
                cl[i] = (Entity) course_list.get(i);
            }
        /*for (int i = 0; i < cl.length; i++)
        {
            System.out.println(cl[i].getName() + "   " + cl[i].getPrerequisites());
        }*/
        return cl;
        
    }

    
    /**
     * Construct a directed graph from the created entity object 
     * @param entities which has information about a single course 
     * including its name and its prerequisites
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void constructGraph(Entity<T>[] entities) {
        for (int i = 0; i < entities.length; i++)
        {
            Entity<T> temp = entities[i];
            graphImpl.addVertex(temp.getName());
            T[] edges = temp.getPrerequisites();
            for (int j = 0; j < edges.length; j++)
            {
                graphImpl.addEdge(temp.getName(), edges[j]);
            }
        }

    }
    
    
    /**
     * Returns all the unique available courses
     * @return the sorted list of all available courses
     */
    public Set<T> getAllCourses() {
        return this.graphImpl.getAllVertices();
    }
    
    
    /**
     * To check whether all given courses can be completed or not
     * @return boolean true if all given courses can be completed,
     * otherwise false
     * @throws Exception
     */
    public boolean canCoursesBeCompleted() throws Exception
    {
        
        return false;

    }
    
    
    /**
     * The order of courses in which the courses has to be taken
     * @return the list of courses in the order it has to be taken
     * @throws Exception when courses can't be completed in any order
     */
    public List<T> getSubjectOrder() throws Exception
    {
        Map<Integer, T> order = new HashMap<Integer, T>();
        GraphImpl<T> g = this.graphImpl;
        int num = g.order();
        Map<T, Boolean> visited = new HashMap<T, Boolean>();
        ArrayList<T> noPre = new ArrayList<T>();
        //mark all as unvisited
        for (int i = 0; i < num; i++)
        {
            visited.put(g.getVertices().get(i), false);
        }
        for (int i = 0; i < g.getVertices().size(); i++)
        {
            Boolean no_pre = true;
            for (int j = 0; j < g.getVertices().size(); j++)
            {
                if (g.getMap().get(g.getVertices().get(j)).contains(g.getVertices().get(i)))
                    no_pre = false;
            }
            if (no_pre)
            {
                noPre.add(g.getVertices().get(i));
            }
        }
        Stack<T> st = new Stack<T>();
        for (int i = 0; i < noPre.size(); i++)
        {
            //mark as visited
            visited.replace(noPre.get(i), true);
            st.push(noPre.get(i));
        }
        //clear

        while (!st.empty())
        {
            T c = st.peek();
            ArrayList<T> successors = (ArrayList<T>) g.getMap().get(c);

            Boolean condition = true;
            for (int i = 0; i < successors.size(); i++)
            {
                if (!visited.get(successors.get(i)))
                    condition = false;
            }
            //if all successors of c are visited
            if(condition)
            {
                order.put(num, st.pop());
                num--;
            }
            else
            {
                T u = (T) new Object();
                for (int i = 0; i < successors.size(); i++)
                {
                    if (!visited.get(successors.get(i)))
                    {
                        u = successors.get(i);
                        break;
                    }
                }
                visited.replace(u, true);
                st.push(u);
            }
        }

        ArrayList<T> ordered = new ArrayList<T>();
        //System.out.println("haha"+order);
        for (int i = 0; i < order.size(); i++)
        {
            ordered.add(order.get(i+1));
        }
        //System.out.println("haha"+ordered);
        return ordered;
    }
        

        
    /**
     * The minimum course required to be taken for a given course
     * @param courseName 
     * @return the number of minimum courses needed for a given course
     */
    public int getMinimalCourseCompletion(T courseName) throws Exception {
        //TODO: implement this method
        return -1;
        
    }
    
}
