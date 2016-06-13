package Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import com.itdl_and.facebook.login.R;

import java.util.ArrayList;

import model.Category;

/**
 * Created by samah on 19/05/2016.
 */
public class PereferenceAdapter extends BaseAdapter {

    ArrayList<Category> MYPereferences =new ArrayList<Category>();
    Context context;
    LayoutInflater layoutInflater ;
    int percent ;
    public PereferenceAdapter(Context context, ArrayList<Category> MYPereferences){
       // fillcategory();
        this.MYPereferences=MYPereferences;
        this.context=context;
        layoutInflater=layoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return (MYPereferences == null) ? 0 : MYPereferences.size();
    }

    @Override
    public Category getItem(int position) {
        return MYPereferences.get(position);    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null){
            convertView=layoutInflater.inflate(R.layout.one_pereference_perecent,null);
            viewHolder=new ViewHolder();
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.category= (TextView) convertView.findViewById(R.id.textViewcategory);
        viewHolder.percent= (SeekBar) convertView.findViewById(R.id.SeekparcategoryPercent);
        viewHolder.displayperecent = (TextView) convertView.findViewById(R.id.textViewpercent);
        viewHolder.category.setText(MYPereferences.get(position).getCategoryName());

        viewHolder.category.setTextColor(Color.BLACK);
        viewHolder.displayperecent.setTextColor(Color.BLACK);
        viewHolder.displayperecent.setText(viewHolder.percent.getProgress() +"%");

        final int  pos=position;

        viewHolder.percent.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                percent=progress;
                viewHolder.displayperecent.setText(""+progress+"%");
                MYPereferences.get(pos).setCategoryPercentage(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                viewHolder.displayperecent.setText(""+percent+"%");
                MYPereferences.get(pos).setCategoryPercentage(percent);

            }

        });

        return convertView;
    }

    private class ViewHolder{
        TextView category,displayperecent;
        SeekBar percent;

    }
}
