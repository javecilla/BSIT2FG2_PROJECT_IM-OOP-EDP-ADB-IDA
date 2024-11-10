import java.sql.SQLException;
import java.util.List;
import services.Food;

public class TestRunner {
    public static void main(String[] args) throws SQLException {
        Food foodService = new Food();

        //Get all foods
        List<Food> foods = foodService.getAllFoods();
        for (Food food : foods) {
            System.out.println(food.getFoodName());
        }

//        //Get specific food
//        Food food = foodService.getFoodById("1");
//        if (food != null) {
//            System.out.println(food.getFoodName());
//        }
    }
}
