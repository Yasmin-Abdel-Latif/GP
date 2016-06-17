package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.itdl_and.facebook.login.R;

import java.util.ArrayList;

import controllers.NoteController;
import model.DeadlineNoteEntity;
import model.MeetingNoteEntity;
import model.NoteEntity;
import model.OrdinaryNoteEntity;
import model.ShoppingNoteEntity;

/**
 * Created by samah on 06/04/2016.
 */
public class NoteAdapter extends BaseAdapter {

    ArrayList<NoteEntity> MYNotes = new ArrayList<NoteEntity>();
    Context context;
    LayoutInflater layoutInflater;

    public NoteAdapter(Context context) {
        NoteController noteController = new NoteController();
        MYNotes = noteController.ShowAllNotes();
        this.context = context;
        layoutInflater = layoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return (MYNotes == null) ? 0 : MYNotes.size();
    }

    @Override
    public NoteEntity getItem(int i) {
        return MYNotes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.single_note_row, null);
            viewHolder = new ViewHolder();
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        String NoteType = MYNotes.get(position).getNoteType();
        final int id = MYNotes.get(position).getNoteId();
        final String priority;
        if (NoteType.equals("Ordinary")) {
            OrdinaryNoteEntity ordinaryNoteEntity = (OrdinaryNoteEntity) MYNotes.get(position);
            String content = ordinaryNoteEntity.getNoteContent();
            priority = ordinaryNoteEntity.getNotePriority();
            viewHolder.tvNoteType = SetTextToTV(view, R.id.tvnoteType, "Type : Ordinary");
            viewHolder.tvPriority = SetTextToTV(view, R.id.tvPriority, "Priority : " + priority);
            viewHolder.tvTitleOrContentOrProduct = SetTextToTV(view, R.id.tvTitleOrcontentOrproduct, "Content : " + content);
        } else if (NoteType.equals("Meeting")) {
            MeetingNoteEntity meetingNoteEntity = (MeetingNoteEntity) MYNotes.get(position);
            String Title = meetingNoteEntity.getMeetingTitle();
            priority = meetingNoteEntity.getNotePriority();
            viewHolder.tvNoteType = SetTextToTV(view, R.id.tvnoteType, "Type : Meeting");
            viewHolder.tvPriority = SetTextToTV(view, R.id.tvPriority, "Priority : " + priority);
            viewHolder.tvTitleOrContentOrProduct = SetTextToTV(view, R.id.tvTitleOrcontentOrproduct, "Meeting Title : " + Title);
        } else if (NoteType.equals("Deadline")) {
            DeadlineNoteEntity deadlineNoteEntity = (DeadlineNoteEntity) MYNotes.get(position);
            String Title = deadlineNoteEntity.getDeadLineTitle();
            priority = deadlineNoteEntity.getNotePriority();
            viewHolder.tvNoteType = SetTextToTV(view, R.id.tvnoteType, "Type : Deadline");
            viewHolder.tvPriority = SetTextToTV(view, R.id.tvPriority, "Priority : " + priority);
            viewHolder.tvTitleOrContentOrProduct = SetTextToTV(view, R.id.tvTitleOrcontentOrproduct, "Deadline Title :" + Title);
        } else if (NoteType.equals("Shopping")) {
            final ShoppingNoteEntity shoppingNoteEntity = (ShoppingNoteEntity) MYNotes.get(position);
            String Product = shoppingNoteEntity.getProductToBuy();
            priority = shoppingNoteEntity.getNotePriority();
            //final int id =shoppingNoteEntity.getNoteId();
            viewHolder.tvNoteType = SetTextToTV(view, R.id.tvnoteType, "Type : Shopping");
            viewHolder.tvPriority = SetTextToTV(view, R.id.tvPriority, "Priority : " + priority);
            viewHolder.tvTitleOrContentOrProduct = SetTextToTV(view, R.id.tvTitleOrcontentOrproduct, "You want to buy : " + Product);

        }

        viewHolder.btnDetails = (Button) view.findViewById(R.id.btnDetails);
        viewHolder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteController noteController = new NoteController();
                noteController.GetNoteDetails(id);
            }
        });
        return view;
    }

    private TextView SetTextToTV(View v, int tvid, String text) {
        TextView tv = (TextView) v.findViewById(tvid);
        tv.setText(text);
        return tv;
    }

    private class ViewHolder {
        TextView tvPriority, tvNoteType, tvTitleOrContentOrProduct;
        Button btnDetails;
    }
}
