public class test
{
	public static void main(String [] args)
	{
		HashTable hs = new HashTable<Integer, Integer> (10, 0.5);
		//33 elements
		for (int i = 0; i < 1000; i++)
		{
			System.out.println(i+ " "+ (i+1));
			hs.put(i, i+1);
			System.out.println(hs.get(i));
		}
		System.out.println(hs.size());
		hs.remove(99);
		//hs.get_info();
		System.out.println(hs.size());
	}
}