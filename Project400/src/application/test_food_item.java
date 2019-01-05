import java.util.HashMap;

public class test_food_item
{
	public static void main(String [] args)
	{
		/*FoodItem fd = new FoodItem("1023ada", "Mi Rancho");
		fd.addNutrient("calories", 1000);
		fd.addNutrient("fat", 900);
		fd.addNutrient("carbonhydrate", 500);
		fd.addNutrient("fiber", 200);
		fd.addNutrient("potein", 1200);
		HashMap<String, Double> nutrients = fd.getNutrients();
		System.out.println(nutrients);
		System.out.println(fd.getName());
		System.out.println(fd.getID());
		System.out.println(fd.getNutrientValue("potein"));*/

		FoodData fd = new FoodData();
		fd.loadFoodItems("./foodItems.csv");
		fd.saveFoodItems("./foodItems_sorted.csv");

	}
}