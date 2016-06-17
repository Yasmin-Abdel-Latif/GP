package com.itdl_and.facebook.login;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import controllers.NoteController;
import controllers.UserController;

public class ShoppingNoteActivity extends ActionBarActivity {
    Spinner spinnerCategory;
    String Categories[] = {"Select a category", "Food", "Sport", "Fashion"};
    String category = "";
    EditText productName;
    Button Add;
    RadioGroup priorityRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_note);
        spinnerCategory = (Spinner) findViewById(R.id.spinnercategory);
        productName = (EditText) findViewById(R.id.etProductname);
        Add = (Button) findViewById(R.id.btnSaveShopingnote);
        priorityRadioGroup = (RadioGroup) findViewById(R.id.radioGroupPriority);

        ArrayAdapter<String> adpter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, Categories);

        spinnerCategory.setAdapter(adpter);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int position = spinnerCategory.getSelectedItemPosition();
                category = adapterView.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String productname = productName.getText().toString();
                int selectedId = priorityRadioGroup.getCheckedRadioButtonId();
                RadioButton btnpriority = (RadioButton) findViewById(selectedId);
                String priority = btnpriority.getText().toString();
                UserController userController = UserController.getInstance();
                boolean isConnected = userController.isNetworkConnected(getApplicationContext());
                NoteController noteController = new NoteController();

                if (!isConnected) {
                    noteController.AddShoppingNoteInLocalDB(productname, priority, category, false, 0);

                } else {

                    // Toast.makeText(getApplicationContext(), " Connected ", Toast.LENGTH_LONG).show();
                    noteController.addShoppingNote(productname, priority, category);
                }

            }
        });
    }

}
