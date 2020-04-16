package UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerylistapp.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import Activities.DetailsActivity;
import Data.DatabaseHandler;
import Model.Grocery;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Grocery> groceryList;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    public RecyclerViewAdapter(Context context, List<Grocery> groceryList) {
        this.context = context;
        this.groceryList = groceryList;
    }

    public void setGroceryList(List<Grocery> groceryList) {
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

        //Log.d("position of grocery is", String.valueOf(position));
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

                    // go to item details
                    int position = getAdapterPosition();
                    Grocery item = groceryList.get(position);
                    Intent intent = new Intent(ctx, DetailsActivity.class);
                    intent.putExtra("name",item.getName());
                    intent.putExtra("quantity",item.getQuantity());
                    intent.putExtra("date added",item.getDateItemAdded());
                    intent.putExtra("id", item.getId());

                    ctx.startActivity(intent);
                }
            });

        }

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btnDelete :
                    int position =getAdapterPosition();
                    Grocery item = groceryList.get(position);
                    deleteItem(item.getId());
                    break;
                case R.id.btnEdit :
                    position =getAdapterPosition();
                    item = groceryList.get(position);
                    updateItem(item);
                    break;
            }
        }

        public void deleteItem(final int id){
            dialogBuilder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);

            View view = inflater.inflate(R.layout.delete_confirmation_dialog,null);
            Button btnNo = view.findViewById(R.id.btnNo);
            Button btnYes = view.findViewById(R.id.btnYes);

            dialogBuilder.setView(view);
            dialog = dialogBuilder.create();
            dialog.show();

            btnNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //delete item
                    DatabaseHandler db= new DatabaseHandler(context);

                    db.deleteGrocery(id);
                    groceryList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    dialog.dismiss();

                }
            });


        }

        public void updateItem(final Grocery item){
            dialogBuilder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.popup, null);


            final EditText groceryItemName = (EditText) view.findViewById(R.id.itemNameID);
            final EditText groceryItemQty = (EditText) view.findViewById(R.id.itemQtyID);
            Button btnSaveItem = (Button) view.findViewById(R.id.btnSaveItem);

            TextView popupTitle = (TextView) view.findViewById(R.id.popupTitleID);
            popupTitle.setText("Edit grocery item");
            dialogBuilder.setView(view);

            dialog = dialogBuilder.create();
            dialog.show();

            btnSaveItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler db = new DatabaseHandler(context);
                    item.setName(groceryItemName.getText().toString());
                    item.setQuantity(groceryItemQty.getText().toString());


                    if( (!groceryItemName.getText().toString().isEmpty()) && (!groceryItemQty.getText().toString().isEmpty())) {
                        Log.d("retreaving grocery data", "done");
                        db.editGrocery(item);
                        notifyItemChanged(getAdapterPosition(), item);
                    } else {
                        Snackbar.make(v, "Add item name and quantity!", Snackbar.LENGTH_LONG).show();
                    }

                    dialog.dismiss();
                }

            });
        }
    }
}
