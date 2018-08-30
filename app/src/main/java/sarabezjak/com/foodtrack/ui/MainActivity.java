package sarabezjak.com.foodtrack.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import sarabezjak.com.foodtrack.R;
import sarabezjak.com.foodtrack.adapter.MealViewAdapter;
import sarabezjak.com.foodtrack.data.Meal;
import sarabezjak.com.foodtrack.viewmodel.MealViewModel;

public class MainActivity extends AppCompatActivity {

    private MealViewModel viewModel;
    private MealViewAdapter adapter;
    private MealViewAdapter.OnItemClickListener mListener;
    private EditText mealNameEditText;
    private EditText caloriesLeftEditText;
    private EditText mealCaloriesEditText;
    private EditText goalCaloriesEditText;
    private String calories;
    private String meal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goalCaloriesEditText = findViewById(R.id.et_goal);
        mealNameEditText = findViewById(R.id.et_meal_name);
        mealCaloriesEditText = findViewById(R.id.et_meal_calories);
        caloriesLeftEditText = findViewById(R.id.et_calories_left);
        Button clearButton = findViewById(R.id.btn_clear);


        FloatingActionButton fab = findViewById(R.id.add_meal);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calories = caloriesLeftEditText.getText().toString();
                meal = mealNameEditText.getText().toString();


                if (TextUtils.isEmpty(goalCaloriesEditText.getText()) ||
                        TextUtils.isEmpty(mealNameEditText.getText()) ||
                        TextUtils.isEmpty(mealCaloriesEditText.getText())) {

                    Toast.makeText(MainActivity.this,
                            "Please enter required information",
                            Toast.LENGTH_SHORT).show();

                } else {

                    if (calories.equals("")) {
                        saveFirstEntry();

                    } else {
                        saveEntry();
                    }
                }
            }
        });


        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new MealViewAdapter(this, mListener);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Divider line decoration
        DividerItemDecoration itemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                layoutManager.getOrientation()
        );

        recyclerView.addItemDecoration(itemDecoration);


        // Use ViewModelProviders to associate your ViewModel with your UI controller. When your
        // app first starts, the ViewModelProviders creates the ViewModel. When the activity is
        // destroyed, for example through a configuration change, the ViewModel persists.
        // When the activity is re-created, the ViewModelProviders return the existing ViewModel.
        viewModel = ViewModelProviders.of(this).get(MealViewModel.class);

        //  Observer for the LiveData returned by getAllMeals(). When the observed data changes
        // while the activity is in the foreground, the onChanged() method is invoked and updates
        // the data cached in the adapter.
        viewModel.getAllMeals().observe(MainActivity.this, new Observer<List<Meal>>() {
            @Override
            public void onChanged(@Nullable List<Meal> meals) {
                adapter.setMeals(meals);
            }
        });

        viewModel.getGoal().observe(MainActivity.this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer goal) {
                if(goal != null){
                    goalCaloriesEditText.setText(String.valueOf(goal));
                }

            }
        });

        viewModel.getCaloriesLeft().observe(MainActivity.this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer calories) {
                if(calories != null){
                    caloriesLeftEditText.setText(String.valueOf(calories));
                }
                else{
                    caloriesLeftEditText.getText().clear();
                }
            }
        });

        // Swipe items in the RecyclerView to delete
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                        // Gets the position of the viewHolder that was swiped
                        int position = viewHolder.getAdapterPosition();
                        Meal meal = adapter.getMealAtPosition(position);

                        // Toast message to show deleted item
                        Toast.makeText(MainActivity.this,
                                " Deleted " + meal.getName(),
                                Toast.LENGTH_SHORT).show();

                        // Delete meal
                        viewModel.delete(meal);


                    }
                }
        );

        // Attach to RecyclerView
        helper.attachToRecyclerView(recyclerView);


        //Clear Button
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });

    }

    private void saveFirstEntry(){

        int caloriesGoal = Integer.parseInt(goalCaloriesEditText.getText().toString());
        int caloriesMeal = Integer.parseInt(mealCaloriesEditText.getText().toString());
        int caloriesLeft;

        caloriesLeft = caloriesGoal - caloriesMeal;

        viewModel.insert(new Meal(caloriesGoal, caloriesLeft, meal, caloriesMeal));

        mealNameEditText.getText().clear();
        MainActivity.this.mealCaloriesEditText.getText().clear();
        caloriesLeftEditText.setText(String.valueOf(caloriesLeft));

    }

    private void saveEntry(){

        int caloriesGoal = Integer.parseInt(goalCaloriesEditText.getText().toString());
        int caloriesLeft = Integer.parseInt(caloriesLeftEditText.getText().toString());
        int caloriesMeal = Integer.parseInt(mealCaloriesEditText.getText().toString());

        caloriesLeft = caloriesLeft - caloriesMeal;

        viewModel.insert(new Meal(caloriesGoal, caloriesLeft, meal, caloriesMeal));

        mealNameEditText.getText().clear();
        MainActivity.this.mealCaloriesEditText.getText().clear();
        caloriesLeftEditText.setText(String.valueOf(caloriesLeft));
    }

    //Method for clearing form
    private void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }

            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                clearForm((ViewGroup) view);
        }
    }

    //Method for showing confirmation dialog when Clear button pressed
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.delete_dialog_title);
        builder.setMessage(R.string.delete_dialog_message);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                clearForm((ViewGroup) findViewById(R.id.root_layout));
                viewModel.deleteAll();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}