
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
    private GraphImpl<T> graphImpl;
    
    
    /**
     * constructor to initialize a graph object
     */
    public CourseSchedulerUtil() {
        this.graphImpl = new GraphImpl<T>();
    }

    public void print(String s)
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

            //System.out.println(s.getClass());
            //System.out.println(sl);
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
        return cl;
        
    }

    
    /**
     * Construct a directed graph from the created entity object 
     * @param entities which has information about a single course 
     * including its name and its prerequisites
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void constructGraph(Entity<T>[] entities) {
        GraphImpl<T> g = new GraphImpl<T>();
        for (int i = 0; i < entities.length; i++)
        {
            Entity<T> temp = entities[i];
            g.addVertex(temp.getName());
            T[] edges = temp.getPrerequisites();
            for (int j = 0; j < edges.length; j++)
            {
                g.addEdge(temp.getName(), edges[j]);
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

        ArrayList<T> v = this.GraphImpl.getVertices();
        Map<T, List<T>> vmap = this.Graphimpl.getMap();
        for (int i = 0; i < v.size(); i++)
        {
            ArrayList<T> ls = vmap.get(v.get(i));
            for (int j = 0; j < ls.size(); j++)
            {
                if (!v.contains(ls.get(j)))
                    return false;
            }
        }
        return true;

    }
    
    
    /**
     * The order of courses in which the courses has to be taken
     * @return the list of courses in the order it has to be taken
     * @throws Exception when courses can't be completed in any order
     */
    public List<T> getSubjectOrder() throws Exception {
        //TODO: implement this method
        return null;

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
