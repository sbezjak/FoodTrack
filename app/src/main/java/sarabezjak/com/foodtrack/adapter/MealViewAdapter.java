package sarabezjak.com.foodtrack.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sarabezjak.com.foodtrack.R;
import sarabezjak.com.foodtrack.data.Meal;

public class MealViewAdapter extends RecyclerView.Adapter<MealViewAdapter.MealViewHolder> {

    private final LayoutInflater mLayoutInflater;

    private List<Meal> mMeals; // cached copy of words

    private static String LOG_TAG = "MealViewAdapter";

    private OnItemClickListener mListener;

    public MealViewAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.rv_item, parent, false);
        return new MealViewHolder(itemView, mListener);
    }

    public interface OnItemClickListener {
        void onEditClick(int position);

        void onDeleteClick(int position);
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

    class MealViewHolder extends RecyclerView.ViewHolder {

        private TextView mealTextView;
        private TextView caloriesTextView;
        private ImageView mDeleteIcon;
        private ImageView mEditIcon;

        private MealViewHolder(final View itemView, final OnItemClickListener listener) {
            super(itemView);
            mealTextView = itemView.findViewById(R.id.tv_meal_name);
            caloriesTextView = itemView.findViewById(R.id.tv_calories);
            mDeleteIcon = itemView.findViewById(R.id.icon_delete);
            mEditIcon = itemView.findViewById(R.id.icon_edit);


            mEditIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            listener.onEditClick(position);
                        }
                    }
                }
            });

            mDeleteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

        }

    }

    public Meal getMealAtPosition(int position) {
        return mMeals.get(position);
    }

}
