package sarabezjak.com.foodtrack.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import sarabezjak.com.foodtrack.data.Meal;
import sarabezjak.com.foodtrack.repository.MealRepository;

public class MealViewModel extends AndroidViewModel {

    private final LiveData<List<Meal>> mAllMeals;
    private  LiveData<Integer> mGoalCalories;
    private  LiveData<Integer> mCaloriesLeft;
    private MealRepository mRepository;
    private int calories;


    public MealViewModel(Application application) {
        super(application);

        mRepository = new MealRepository(application);
        mAllMeals = mRepository.getAllMeals();
        mGoalCalories = mRepository.getGoal();
        mCaloriesLeft = mRepository.getCaloriesLeft();

    }

    // "getter" method that gets all the meals. This completely hides the implementation from the UI.
    public LiveData<List<Meal>> getAllMeals() {
        return mAllMeals;

    }

    public LiveData<Integer> getGoal() {
        return mGoalCalories;
    }

    public LiveData<Integer> getCaloriesLeft() {
        return mCaloriesLeft;
    }
/*
    public void updateDatabaseDelete(int calories){
        mRepository.updateDatabaseDelete(calories);
    }*/

    // Wrapper insert() method that calls the Repository's insert() method. In this way,
    // the implementation of insert() is completely hidden from the UI.
    public void insert(Meal meal) {
        mRepository.insert(meal);
    }

/*    // Wrapper method that calls the Repository's method for updating a meal
    public void updateDatabaseDelete() {
        mRepository.updateDatabaseDelete();
    }*/

    // Wrapper method that calls the Repository's method for deleting all Meals
    public void deleteAll() {
        mRepository.deleteAll();
    }

    // Wrapper method that calls the Repository's method to delete a single Meal
    public void delete(Meal meal) {
        mRepository.delete(meal);

    }
}
