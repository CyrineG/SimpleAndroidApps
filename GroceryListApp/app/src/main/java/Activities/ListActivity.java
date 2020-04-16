package Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.grocerylistapp.R;

import java.util.ArrayList;
import java.util.List;

import Data.DatabaseHandler;
import Model.Grocery;
import UI.RecyclerViewAdapter;

public class ListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Grocery> groceryList;
    private List<Grocery> listItems;
    private DatabaseHandler db;
    private AlertDialog.Builder dialogBuilder;
    private Dialog dialog;
    private EditText groceryItemName;
    private EditText groceryItemQty;
    private Button btnSaveItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemPopup();
            }
        });

        db = new DatabaseHandler(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        groceryList = new ArrayList<>();
        listItems = new ArrayList<>();
        //get item from db
        groceryList = db.getAllGrocery();
        for (Grocery c : groceryList){
            Grocery grocery = new Grocery();
            grocery.setName(c.getName());
            grocery.setQuantity("Qty : "+ c.getQuantity());
            grocery.setDateItemAdded("Added on " + c.getDateItemAdded());
            grocery.setId(c.getId());

            listItems.add(grocery);
        }

        //Log.d("number of groceries ", String.valueOf(listItems.size()));
        recyclerViewAdapter = new RecyclerViewAdapter(this, listItems);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();



    }

    public void addItemPopup(){
        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);
        groceryItemName = (EditText) view.findViewById(R.id.itemNameID);
        groceryItemQty = (EditText) view.findViewById(R.id.itemQtyID);
        btnSaveItem = (Button) view.findViewById(R.id.btnSaveItem);
        dialogBuilder.setView(view);

        dialog = dialogBuilder.create();
        dialog.show();

        btnSaveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( (!groceryItemName.getText().toString().isEmpty()) && (!groceryItemQty.getText().toString().isEmpty())) {
                    Log.d("retreaving grocery data", "done");
                    saveGroceryItemToDB(v);
                }
            }
        });
    }

    private void saveGroceryItemToDB(View v) {
        Grocery item = new Grocery();
        String itemName = groceryItemName.getText().toString();
        String itemQty = groceryItemQty.getText().toString();

        item.setName(itemName);
        item.setQuantity(itemQty);

        db.addGrocery(item);
        recyclerViewAdapter.notifyDataSetChanged();
        this.recreate();

        Snackbar.make(v,"new grocery item created!", Snackbar.LENGTH_LONG).show();
        //Log.d("Item added id ", String.valueOf(db.getGroceryCount()) );
        dialog.dismiss();

    }

}
