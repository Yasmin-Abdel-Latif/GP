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
 * Created by samah on 13/06/2016.
 */
public class PreferenceAdapter extends BaseAdapter {
    ArrayList<Category> MYCategories =new ArrayList<Category>();

    Context context;
    LayoutInflater layoutInflater ;
    int percent ;

    public PreferenceAdapter(Context context){
        fillcategory();
        this.context=context;
        layoutInflater=layoutInflater.from(this.context);
    }



    @Override
    public int getCount() {
        return (MYCategories == null) ? 0 : MYCategories.size();
    }

    @Override
    public Category getItem(int position) {
        return MYCategories.get(position);
    }

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
        viewHolder.category.setText(MYCategories.get(position).getCategoryName());

        viewHolder.category.setTextColor(Color.BLACK);
        viewHolder.displayperecent.setTextColor(Color.BLACK);
        viewHolder.displayperecent.setText(viewHolder.percent.getProgress() +"%");

        final int  pos=position;

        viewHolder.percent.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                percent=progress;
                viewHolder.displayperecent.setText(""+progress+"%");
                MYCategories.get(pos).setCategoryPercentage((double)progress/100);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                viewHolder.displayperecent.setText(""+percent+"%");
                MYCategories.get(pos).setCategoryPercentage((double)percent/100);

            }

        });

        return convertView;
    }

    private class ViewHolder{
        TextView category,displayperecent;
        SeekBar percent;

    }

    void fillcategory(){
        MYCategories.add(new Category("Arts and Entertainment"));
        MYCategories.add(new Category("Movies"));
        MYCategories.add(new Category("Music"));
        MYCategories.add(new Category("Food and Drinks"));
        MYCategories.add(new Category("Technology"));
        MYCategories.add(new Category("Sports"));
        MYCategories.add(new Category("Health"));
        MYCategories.add(new Category("Religion"));
        MYCategories.add(new Category("Education"));
        MYCategories.add(new Category("Pets and Animals"));
        MYCategories.add(new Category("Fashion"));
        MYCategories.add(new Category("Readings"));

    }

    public ArrayList<Category> getMYCategories(){

        return MYCategories;
    }
}
