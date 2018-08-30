package sarabezjak.com.foodtrack.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface MealDao {

    @Query("SELECT * FROM meals")
    LiveData<List<Meal>> getAllMeals();

    @Query("SELECT MAX(goal_calories) FROM meals")
    LiveData<Integer> getGoal();

    @Query("SELECT MIN(calories_left) FROM meals")
    LiveData<Integer> getCaloriesLeft();

    @Query("UPDATE meals SET calories_left = calories_left + :mealCalories")
    void updateDatabaseDelete(int mealCalories);

    @Query("UPDATE meals SET calories_left = :caloriesLeft")
    void updateDatabaseInsert(int caloriesLeft);

    @Query("DELETE FROM meals")
    void deleteAll();

    @Insert(onConflict = REPLACE)
    void insert(Meal meal);

    @Update
    void update(Meal meal);

    @Delete
    void delete(Meal meal);

}
