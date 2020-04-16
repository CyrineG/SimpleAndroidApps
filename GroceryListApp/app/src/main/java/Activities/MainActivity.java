package Activities;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.grocerylistapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import Data.DatabaseHandler;
import Model.Grocery;

public class MainActivity extends AppCompatActivity {
    private AlertDialog.Builder dialogBuilder;
    private Dialog dialog;
    private EditText groceryItemName;
    private EditText groceryItemQty;
    private Button btnSaveItem;
    private DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseHandler(this);
        byPassActivity();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemPopup();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

        Snackbar.make(v,"new grocery item created!", Snackbar.LENGTH_LONG).show();
        //Log.d("Item added id ", String.valueOf(db.getGroceryCount()) );

        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                dialog.dismiss();
                //start a new activity
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        }, 1200); //1 second
    }

    public void byPassActivity(){
        //if db not empty then load list of items (ListActivity)
        if(db.getGroceryCount()>0){
            startActivity(new Intent(this, ListActivity.class));
            finish();
        }
    }

}
