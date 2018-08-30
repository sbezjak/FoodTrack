package sarabezjak.com.foodtrack.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import sarabezjak.com.foodtrack.R;
import sarabezjak.com.foodtrack.data.Meal;

public class MealViewAdapter extends RecyclerView.Adapter<MealViewAdapter.MealViewHolder> {

    private final LayoutInflater mLayoutInflater;

    private List<Meal> mMeals; // cached copy of words

    private static String LOG_TAG = "MealViewAdapter";

    private OnItemClickListener mListener;

    public MealViewAdapter(Context context, OnItemClickListener listener) {
        this.mListener = mListener;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.rv_item, parent, false);
        return new MealViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, final int position) {
        final Meal meal = mMeals.get(position);
        holder.mealTextView.setText(meal.getName());
        holder.caloriesTextView.setText(String.valueOf(meal.getMealCalories()));

    }

    public void setMeals(List<Meal> meals){
        mMeals = meals;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mMeals != null)
            return mMeals.size();
        else return 0;
    }

    class MealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mealTextView;
        private TextView caloriesTextView;

        private MealViewHolder(final View itemView) {
            super(itemView);
            mealTextView = itemView.findViewById(R.id.tv_meal_name);
            caloriesTextView = itemView.findViewById(R.id.tv_calories);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Log.d(LOG_TAG, "Item clicked");

        }
    }

    public Meal getMealAtPosition(int position) {
        return mMeals.get(position);
    }

}
