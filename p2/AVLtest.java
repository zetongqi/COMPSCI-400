import java.util.*;

public class AVLtest
{
	public static void main(String[] args) throws Exception
	{
		AVLTree<Integer> t = new AVLTree<Integer>();
		t.insert(50);
		t.insert(30);
		t.insert(60);
		t.insert(70);
		t.insert(55);
		t.insert(52);
		t.insert(56);
		t.insert(57);
		/*t.insert(1);
		t.insert(2);
		t.insert(3);
		t.insert(4);*/
		//System.out.println(t.getHeight());
		//t.delete(1);
		//t.Print();
		//t.printRoot();
		System.out.println(t.checkForBinarySearchTree());
	}
}