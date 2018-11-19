import java.util.*;

class GraphTest
{
	public static void main(String[] args)
	{
		CourseSchedulerUtil<String> cs = new CourseSchedulerUtil<String>();
		try
		{
			cs.constructGraph(cs.createEntity("valid.json"));
			//cs.graphImpl.printGraph();
			System.out.println(cs.getSubjectOrder());

		}
		catch (Exception e)
		{
			System.out.println("here" + e.getClass().getSimpleName());
		}

	}
}