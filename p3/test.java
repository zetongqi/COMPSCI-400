public class test
{
	public static void main(String [] args)
	{
		HashTable hs = new HashTable<Integer, Integer> (10, 0.5);
		//33 elements
		hs.put(1,2);
		hs.put(2,3);
		hs.put(3,5);
		hs.put(4,8);
		hs.put(5,6);
		hs.put(16,2);
		hs.put(22,2);
		hs.put(34,2);
		hs.put(56,3);
		hs.put(14,5);
		hs.put(68,8);
		hs.put(69,6);
		hs.put(70,2);
		hs.put(71,2);
		hs.put(72,2);
		hs.put(73,3);
		hs.put(74,5);
		hs.put(75,8);
		hs.put(76,6);
		hs.put(77,2);
		hs.put(78,2);
		hs.put(79,2);
		hs.put(80,3);
		hs.put(81,5);
		hs.put(82,8);
		hs.put(83,6);
		hs.put(85,2);
		hs.put(86,2);
		hs.put(91,2);
		hs.put(92,3);
		hs.put(93,5);
		hs.put(94,8);
		hs.put(95,6);
		//hs.get_info();
		System.out.println(hs.size());
	}
}