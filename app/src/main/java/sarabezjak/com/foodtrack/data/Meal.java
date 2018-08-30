package sarabezjak.com.foodtrack.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "meals")
public class Meal {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "goal_calories")
    private int goal;
    @ColumnInfo(name = "calories_left")
    private int caloriesLeft;
    @ColumnInfo(name = "meal_calories")
    private int mealCalories;

    public Meal(int goal, int caloriesLeft, String name, int mealCalories) {

        this.goal = goal;
        this.caloriesLeft = caloriesLeft;
        this.name = name;
        this.mealCalories = mealCalories;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getCaloriesLeft() {
        return caloriesLeft;
    }

    public void setCaloriesLeft(int caloriesLeft) {
        this.caloriesLeft = caloriesLeft;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMealCalories() {
        return mealCalories;
    }

    public void setMealCalories(int mealCalories) {
        this.mealCalories = mealCalories;
    }

    }

