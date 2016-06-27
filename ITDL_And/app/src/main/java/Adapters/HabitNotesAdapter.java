package Adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.itdl_and.facebook.login.R;

import java.util.ArrayList;

import model.NoteEntity;
import model.OrdinaryNoteEntity;

/**
 * Created by Yasmin Abdel Latif on 6/18/2016.
 */
public class HabitNotesAdapter extends BaseAdapter {
    ArrayList<NoteEntity> MYHabitNotes = new ArrayList<NoteEntity>();
    Context context;
    LayoutInflater layoutInflater;

    public HabitNotesAdapter(Context context, ArrayList<NoteEntity> habitNotes) {
        MYHabitNotes.addAll(habitNotes);
        this.context = context;
        layoutInflater = layoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return (MYHabitNotes == null) ? 0 : MYHabitNotes.size();
    }

    @Override
    public NoteEntity getItem(int position) {
        return MYHabitNotes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.one_note, null);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String noteType = MYHabitNotes.get(position).getNoteType();
        long noteID = MYHabitNotes.get(position).getNoteId();
        if (noteType.equals("Ordinary")) {
            OrdinaryNoteEntity ordinaryNoteEntity = (OrdinaryNoteEntity) MYHabitNotes.get(position);
            String noteContent = ordinaryNoteEntity.getNoteContent();
            viewHolder.note = (CheckBox) convertView.findViewById(R.id.checkBoxNote);
            viewHolder.note.setText(noteContent);
            viewHolder.note.setTextColor(Color.BLACK);
        }


        viewHolder.note.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                getItem((Integer) buttonView.getTag()).setIschecked(isChecked);

            }
        });
        viewHolder.note.setTag(position);
        //viewHolder.category.setChecked(MYCategories.get(position).ischecked());
        return convertView;
    }

    private class ViewHolder {
        CheckBox note;
    }

    ArrayList<NoteEntity> getMYHabitNotes() {
        return MYHabitNotes;
    }

    public ArrayList<NoteEntity> getCheckedNotes() {
        ArrayList<NoteEntity> Notes = new ArrayList<NoteEntity>();
        for (NoteEntity p : MYHabitNotes) {
            if (p.ischecked()) {
                Notes.add(p);
            }
        }

        return Notes;
    }

}
