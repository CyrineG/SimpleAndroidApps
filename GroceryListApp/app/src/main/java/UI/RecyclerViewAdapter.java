package UI;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerylistapp.R;

import java.util.List;

import Model.Grocery;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    public Context context;
    public List<Grocery> groceryList;

    public RecyclerViewAdapter(Context context, List<Grocery> groceryList) {
        this.context = context;
        this.groceryList = groceryList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Grocery item = groceryList.get(position);

        Log.d("position of grocery is", String.valueOf(position));
        holder.itemName.setText(item.getName());
        holder.quantity.setText(item.getQuantity());
        holder.dateAdded.setText(item.getDateItemAdded());
        holder.id = item.getId();
    }

    @Override
    public int getItemCount() {
        return groceryList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView itemName;
        public TextView quantity;
        public TextView dateAdded;
        public int id;
        public Button btnEdit;
        public Button btnDelete;
        public Context ctx;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            itemName = (TextView) itemView.findViewById(R.id.lstItemNameID);
            quantity = (TextView) itemView.findViewById(R.id.lstItemQtyID);
            dateAdded = (TextView) itemView.findViewById(R.id.lstDateAddedID);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
            btnEdit = (Button) itemView.findViewById(R.id.btnEdit);
            ctx = context;

            btnEdit.setOnClickListener(this);
            btnDelete.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // go t next screen
                }
            });





        }

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btnDelete :
                    //
                    break;
                case R.id.btnEdit :
                    //
                    break;
            }
        }
    }
}
