package com.itdl_and.facebook.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import controllers.NoteController;
import model.DeadlineNoteEntity;
import model.MeetingNoteEntity;
import model.NoteEntity;
import model.OrdinaryNoteEntity;
import model.ShoppingNoteEntity;

public class ShowNoteDetailsActivity extends ActionBarActivity implements View.OnClickListener{
    TextView tvNoteDetails;
    Button btnDelete,btnEdit;
    NoteEntity noteEntity ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note_details);
        tvNoteDetails= (TextView) findViewById(R.id.tvNoteDetails);
        btnDelete= (Button) findViewById(R.id.btnNoteDelete);
        btnEdit = (Button) findViewById(R.id.btnNoteEdit);
        String note ="";
        Intent intent =getIntent();
         noteEntity = (NoteEntity) intent.getSerializableExtra("note");

        if (noteEntity.getNoteType().equals("Ordinary")){
              note= "Type : Ordinary \n " +
                    "Content : "+((OrdinaryNoteEntity) noteEntity).getNoteContent()+"\n"+
                    "Date creation : "+ noteEntity.getNoteDateCreation()+"\n"+
                    "Priority : "+noteEntity.getNotePriority()+"\n"+
                    "Done : "+noteEntity.isDone()+"\n"+
                    "Categorized : "+noteEntity.isTextCategorized()+"\n";
            }
        else if (noteEntity.getNoteType().equals("Meeting")){
            note= "Type : Meeting \n " +
                    "Title : "+((MeetingNoteEntity) noteEntity).getMeetingTitle()+"\n"+
                    "Agenda : "+((MeetingNoteEntity)noteEntity).getMeetingAgenda()+"\n"+
                    "Place : "+((MeetingNoteEntity)noteEntity).getMeetingPlace()+"\n"+
                    "Date: "+((MeetingNoteEntity)noteEntity).getMeetingNoteDate()+"\n"+
                    "Estimated time : "+((MeetingNoteEntity)noteEntity).getEstimatedTransportTime()+"\n"+
                    "Date creation : "+ noteEntity.getNoteDateCreation()+"\n"+
                    "Priority : "+noteEntity.getNotePriority()+"\n"+
                    "Done : "+noteEntity.isDone()+"\n"+
                    "Categorized : "+noteEntity.isTextCategorized()+"\n";
            }
      else  if (noteEntity.getNoteType().equals("Deadline")){
            note= "Type : Deadline \n " +
                    "Title : "+((DeadlineNoteEntity) noteEntity).getDeadLineTitle()+"\n"+
                    "Date: "+((DeadlineNoteEntity)noteEntity).getDeadLineDate()+"\n"+
                    "Progress : "+((DeadlineNoteEntity)noteEntity).getProgressPercentage()+" % \n"+
                    "Date creation : "+ noteEntity.getNoteDateCreation()+"\n"+
                    "Priority : "+noteEntity.getNotePriority()+"\n"+
                    "Done : "+noteEntity.isDone()+"\n"+
                    "Categorized : "+noteEntity.isTextCategorized()+"\n";
        }
        else if (noteEntity.getNoteType().equals("Shopping")){
            note= "Type : Shopping  \n " +
                    "Product to buy : "+((ShoppingNoteEntity) noteEntity).getProductToBuy()+"\n"+
                    "Category: "+((ShoppingNoteEntity)noteEntity).getProductCategory()+"\n"+
                    "Date creation : "+ noteEntity.getNoteDateCreation()+"\n"+
                    "Priority : "+noteEntity.getNotePriority()+"\n"+
                    "Done : "+noteEntity.isDone()+"\n"+
                    "Categorized : "+noteEntity.isTextCategorized()+"\n";
        }
        tvNoteDetails.setText(note);
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v== btnDelete){
            NoteController noteController =new NoteController();
            noteController.DeleteNoteInLocalDB(noteEntity.getNoteId());
        }

        else if (v== btnEdit){
            Intent intent = null;
            if (noteEntity.getNoteType().equals("Shopping")){
                intent = new Intent(getApplicationContext(),EditShoppingNoteActivity.class);
                intent.putExtra("note", (ShoppingNoteEntity)noteEntity);
            }
            else  if (noteEntity.getNoteType().equals("Deadline")){
                intent = new Intent(getApplicationContext(),EditDeadlineNoteActivity.class);
                intent.putExtra("note", (DeadlineNoteEntity)noteEntity);
            }
            else  if (noteEntity.getNoteType().equals("Meeting")){
                intent = new Intent(getApplicationContext(),EditMeetingNoteActivity.class);
                intent.putExtra("note", (MeetingNoteEntity)noteEntity);
            }
            else  if (noteEntity.getNoteType().equals("Ordinary")){
                intent = new Intent(getApplicationContext(),EditOrdinaryNoteActivity.class);
                intent.putExtra("note", (OrdinaryNoteEntity)noteEntity);
            }

           /* intent = new Intent(getApplicationContext(),EditOrdinaryNoteActivity.class);
            intent.putExtra("note", noteEntity);*/
           // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
