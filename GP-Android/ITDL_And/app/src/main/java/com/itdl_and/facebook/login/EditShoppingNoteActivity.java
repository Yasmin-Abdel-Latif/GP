package com.itdl_and.facebook.login;

import android.content.Intent;
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
import android.widget.Toast;

import controllers.NoteController;
import controllers.UserController;
import model.ShoppingNoteEntity;

public class EditShoppingNoteActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {
Spinner spinnerCategories;
    int spinnerPosition;
    String newCategory = "",oldPname,oldpriority,oldCategory;
    EditText productName;
    Button UpdateNote;
    RadioGroup priorityRadioGroup;
    RadioButton RbtnMedium,RbtnHigh,RbtnLow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shopping_note);

        Intent intent =getIntent();
        final ShoppingNoteEntity noteEntity = (ShoppingNoteEntity) intent.getSerializableExtra("note");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Categories,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategories= (Spinner) findViewById(R.id.spinnercategory);
        productName= (EditText) findViewById(R.id.etProductname);
        UpdateNote = (Button)findViewById(R.id.btnUpdateShopingnote);
        priorityRadioGroup = (RadioGroup)findViewById(R.id.radioGroupPriority);
        spinnerCategories.setAdapter(adapter);
        spinnerCategories.setOnItemSelectedListener(this);
        spinnerPosition = 0;
         oldCategory=noteEntity.getProductCategory();
        spinnerPosition = adapter.getPosition(oldCategory);
        spinnerCategories.setSelection(spinnerPosition);
        oldPname=noteEntity.getProductToBuy();
        oldpriority=noteEntity.getNotePriority();
        productName.setText(oldPname);
        RbtnHigh=(RadioButton) findViewById(R.id.radioHigh);
        RbtnMedium=(RadioButton) findViewById(R.id.radioMedium);
        RbtnLow=(RadioButton) findViewById(R.id.radioLow);

        if (noteEntity.getNotePriority().equals("High")){
            RbtnHigh.setChecked(true);
        }
        else if (noteEntity.getNotePriority().equals("Medium")){
            RbtnMedium.setChecked(true);

        }
        else if (noteEntity.getNotePriority().equals("Low")){
            RbtnLow.setChecked(true);
        }

        UpdateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String productname =productName.getText().toString();
                int selectedId = priorityRadioGroup.getCheckedRadioButtonId();
                RadioButton btnpriority = (RadioButton) findViewById(selectedId);
                String priority = btnpriority.getText().toString();

                if (productname.equals(oldPname)&&priority.equals(oldpriority)&&oldCategory.equals(newCategory)){
                    Toast.makeText(getApplicationContext(), " no changes happened  ", Toast.LENGTH_LONG).show();

                }
                else {
                    UserController userController = UserController.getInstance();
                    boolean isConnected=userController.isNetworkConnected(getApplicationContext());
                    NoteController noteController = new NoteController();
                    int noteid=noteEntity.getNoteId();

                    if (!isConnected) {
                   noteController.UpdateShoppingNoteInLocalDB(productname, priority,newCategory, noteid);

                    }
                    else{
                        noteController.UpdateShoppingNoteInLocalDB(productname, priority,newCategory, noteid);

                        //noteController.addShoppingNoteToServer(productname,priority,category);
                        //noteController.AddShoppingNoteInLocalDB(productname, priority,category, true);
                        }
                    }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int psition = spinnerCategories.getSelectedItemPosition();
        newCategory  = parent.getItemAtPosition(psition).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
