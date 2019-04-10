package sarabezjak.com.foodtrack.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import sarabezjak.com.foodtrack.data.AppDatabase;
import sarabezjak.com.foodtrack.data.Meal;
import sarabezjak.com.foodtrack.data.MealDao;

public class MealRepository {

    private MealDao mMealDao;
    private LiveData<List<Meal>> mAllMeals;
    private LiveData<Integer> mGoalCalories;
    private LiveData<Integer> mCaloriesLeft;


    public MealRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mMealDao = db.mealDao();
        mAllMeals = mMealDao.getAllMeals();
        mGoalCalories = mMealDao.getGoal();
        mCaloriesLeft = mMealDao.getCaloriesLeft();
    }

    public LiveData<List<Meal>> getAllMeals(){
        return mAllMeals;
    }
    public LiveData<Integer> getGoal(){
        return mGoalCalories;
    }
    public LiveData<Integer> getCaloriesLeft(){
        return mCaloriesLeft;
    }

    // Wrapper for the insert asynctask
    public void insert(Meal meal) {
        new InsertAsyncTask(mMealDao).execute(meal);
    }

    // Wrapper for update asynctask
    public void update(Meal meal) {
        new UpdateAsyncTask(mMealDao).execute(meal);
    }

    // Wrapper for delete asynctask
    public void delete(Meal meal) {
        new DeleteMealAsyncTask(mMealDao).execute(meal);

    }
    // Wrapper for the deleteAll asynctask
    public void deleteAll() {
        new DeleteAllMealsAsyncTask(mMealDao).execute();
    }

    // AsyncTask for insert
    private static class InsertAsyncTask extends AsyncTask<Meal, Void, Void> {

        private MealDao mAsyncTaskDao;

        InsertAsyncTask(MealDao dao) {
            mAsyncTaskDao = dao;
        }

        private void updateDatabaseInsert(Meal meal){
            int calories = meal.getCaloriesLeft();
            mAsyncTaskDao.updateDatabaseInsert(calories);
        }

        @Override
        protected Void doInBackground(final Meal... params) {
            mAsyncTaskDao.insert(params[0]);
            updateDatabaseInsert(params[0]);
            return null;
        }
    }

    // AsyncTask for update
    private static class UpdateAsyncTask extends AsyncTask<Meal, Void, Void>{

        private MealDao mAsyncTaskDao;

        UpdateAsyncTask(MealDao dao) {
            mAsyncTaskDao = dao;
        }

        private void updateDatabaseInsert(Meal meal){
            int calories = meal.getCaloriesLeft();
            mAsyncTaskDao.updateDatabaseInsert(calories);
        }

        @Override
        protected Void doInBackground(final Meal... params) {
            mAsyncTaskDao.update(params[0]);
            updateDatabaseInsert(params[0]);
            return null;
        }
    }

    // AsyncTask for delete
    private static class DeleteMealAsyncTask extends AsyncTask<Meal, Void, Void> {

        private MealDao mAsyncTaskDao;

        DeleteMealAsyncTask(MealDao dao) {
            mAsyncTaskDao = dao;
        }

        private void updateDatabaseDelete(Meal meal){
            int calories = meal.getMealCalories();
            mAsyncTaskDao.updateDatabaseDelete(calories);
        }

        @Override
        protected Void doInBackground(Meal... params) {
            mAsyncTaskDao.delete(params[0]);
            updateDatabaseDelete(params[0]);
            return null;
        }
    }

    // AsyncTask for deleting all Meals
    private static class DeleteAllMealsAsyncTask extends AsyncTask<Void, Void, Void> {

        private MealDao mAsyncTaskDao;

        DeleteAllMealsAsyncTask(MealDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

}

