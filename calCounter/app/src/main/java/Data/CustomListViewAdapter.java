package Data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.caloriecounterapp.calcounter.DetailsActivity;
import com.caloriecounterapp.calcounter.ListActivity;
import com.caloriecounterapp.calcounter.R;

import java.util.ArrayList;

import Model.Food;

public class CustomListViewAdapter extends ArrayAdapter<Food> {
    private int layoutResource;
    private Activity activity;
    private ArrayList<Food> foodList = new ArrayList<>();

    public CustomListViewAdapter(Activity act, int resource, ArrayList<Food> data) {
        super(act, resource, data);
        layoutResource = resource;
        activity= act;
        foodList = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Nullable
    @Override
    public Food getItem(int position) {
        return foodList.get(position);
    }

    @Override
    public int getPosition(@Nullable Food item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //where w'll inflate our views and all
        View row = convertView;
        ViewHolder holder = null;

        if (row == null || row.getTag()==null){
            LayoutInflater inflater = LayoutInflater.from(activity);
            row = inflater.inflate(layoutResource,null);
            holder = new ViewHolder();
            holder.name =  row.findViewById(R.id.rowNameID);
            holder.calories =  row.findViewById(R.id.rowCalorieCountID);
            holder.foodDate =  row.findViewById(R.id.rowDateAddedID);

            if(holder.name == null)
                Log.d("holder", "is null");

            row.setTag(holder);

        } else{
            holder = (ViewHolder) row.getTag();
        }


        holder.food = getItem(position);
        holder.name.setText(holder.food.getName());
        holder.calories.setText(String.valueOf(holder.food.getCalories()));
        holder.foodDate.setText(holder.food.getDateAdded());

        final ViewHolder finalHolder = holder;
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DetailsActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("foodObj", finalHolder.food);
                intent.putExtras(mBundle);

                activity.startActivity(intent);
            }
        });

        return row;
    }

    public class ViewHolder{
        Food food;
        TextView name;
        TextView calories;
        TextView foodDate;
    }
}
