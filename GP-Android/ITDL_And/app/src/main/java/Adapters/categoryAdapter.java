package Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.itdl_and.facebook.login.R;

import java.util.ArrayList;

import model.Category;

/**
 * Created by samah on 18/05/2016.
 */

public class categoryAdapter extends BaseAdapter {
    ArrayList<Category> MYCategories =new ArrayList<Category>();
    Context context;
    LayoutInflater layoutInflater ;

    public categoryAdapter(Context context){
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView=layoutInflater.inflate(R.layout.one_category,null);
            viewHolder=new ViewHolder();
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.category= (CheckBox) convertView.findViewById(R.id.checkBoxCategory);
        viewHolder.category.setText(MYCategories.get(position).getCategoryName());
        viewHolder.category.setTextColor(Color.BLACK);

        viewHolder.category.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                getItem((Integer) buttonView.getTag()).setIschecked(isChecked);

            }
        });
        viewHolder.category.setTag(position);
        //viewHolder.category.setChecked(MYCategories.get(position).ischecked());
        return convertView;
    }



    private  class ViewHolder{
      CheckBox category;
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
    ArrayList<Category> getMYCategories(){
        return  MYCategories;
    }

    public ArrayList<Category> getCheckedCategories() {
       ArrayList<Category> Categories = new ArrayList<Category>();
       for (Category p : MYCategories) {
            if (p.ischecked()){
                Categories.add(p);
            }
           }

       return Categories;
  }

}
