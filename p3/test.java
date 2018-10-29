public class test
{
	public static void main(String [] args)
	{
		HashTable hs = new HashTable<Integer, Integer> (10, 0.5);
		for (int i = 0; i < 1000; i++)
		{
			//System.out.println(i+ " "+ (i+1));
			hs.put(i, i+1);
			//System.out.println(hs.get(i));
		}
		hs.put(12, 719);
		System.out.println(hs.get(12));
		System.out.println(hs.size());
		hs.remove(99);
		System.out.println(hs.get(99));
		System.out.println(hs.size());
	}
}