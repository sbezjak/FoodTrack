package sarabezjak.com.foodtrack.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Meal.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MealDao mealDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {

        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "foodtrack_db")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }

        }

        return INSTANCE;
    }
}

