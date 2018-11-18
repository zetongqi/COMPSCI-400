import java.util.*;

class GraphTest
{
	public static void main(String[] args)
	{
		//GraphImpl<Integer> g = new GraphImpl<Integer>();
		/*g.addVertex(1);
		g.addVertex(2);
		g.addVertex(3);
		g.addVertex(4);
		g.addVertex(5);
		g.addVertex(6);
		g.addEdge(1,2);
		g.addEdge(1,5);
		g.addEdge(2,3);
		g.addEdge(2,5);
		g.addEdge(1,3);
		g.addEdge(4,5);
		g.addEdge(5,6);
		*/
		//Set<Integer> vs = g.getAllVertices();
		//System.out.println(vs);
		//System.out.println(g.getAdjacentVerticesOf(1).getClass());
		//List<Integer> al = g.getAdjacentVerticesOf(1);
		//System.out.println(g.order() + "    " + g.size());
		//g.printGraph();

		CourseSchedulerUtil<String> cs = new CourseSchedulerUtil<String>();
		try
		{
			cs.createEntity("valid.json");
			System.out.println(cs.getClass());
		}
		catch (Exception e)
		{
			System.out.println("here" + e.getClass().getSimpleName());
		}

	}
}