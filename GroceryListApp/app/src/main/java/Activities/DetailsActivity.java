package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.grocerylistapp.R;

public class DetailsActivity extends AppCompatActivity {

    private TextView dtlName;
    private TextView dtlQty;
    private TextView dtlDateAdded;

    private Button btnEditDtl;
    private Button btnDeleteDtl;

    private int itemID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        dtlName = (TextView) findViewById(R.id.dtlItemNameID);
        dtlQty = (TextView) findViewById(R.id.dtlItemQtyID);
        dtlDateAdded = (TextView) findViewById(R.id.dtlDateAddedID);

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            dtlName.setText(bundle.getString("name"));
            dtlQty.setText(bundle.getString("quantity"));
            dtlDateAdded.setText(bundle.getString("date added"));

            itemID = bundle.getInt("id");
        }
    }
}
