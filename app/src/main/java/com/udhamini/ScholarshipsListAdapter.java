package com.udhamini;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.udhamini.HomeFragment;

import static com.udhamini.HomeFragment.*;


//import fragments.HomeFragment;

/**
 * Created by brian on 04/Apr/2018.
 */


public class ScholarshipsListAdapter  extends RecyclerView.Adapter<ScholarshipsListAdapter.ViewHolder>{

    private List<Scholarship> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;


    // data is passed into the constructor
    public ScholarshipsListAdapter(HomeFragment context, List<Scholarship> data) {
        this.mInflater = LayoutInflater.from(context.getActivity());
        this.mData = data;
    }



    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_scholarship_list_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String thisTitle = mData.get(position).title;
        String thisDate = mData.get(position).date;

        holder.myTitle.setText(thisTitle);
        holder.myDate.setText(thisDate);

        //String animal = mData.get(position);
        // holder.myTitle.setText(animal);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTitle;
        TextView myDate;

        ViewHolder(View itemView) {
            super(itemView);
            myTitle = itemView.findViewById(R.id.s_title);
            myDate = itemView.findViewById(R.id.s_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
           // if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            // get position
            int pos = getAdapterPosition();

            // check if item still exists
            if(pos != RecyclerView.NO_POSITION){

                Scholarship clickedDataItem = mData.get(pos);



                //Toast.makeText(view.getContext(), "You clicked " + clickedDataItem.getId(), Toast.LENGTH_SHORT).show();

                SharedPrefManager.getInstance(mInflater.getContext())
                        .writeScholarshipId(clickedDataItem.getId());




               // int myId = SharedPrefManager.getInstance(mInflater.getContext())
                      //  .getScholarshipId();

                //Toast.makeText(view.getContext(), "You clicked2 " + myId, Toast.LENGTH_SHORT).show();

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new ScholarshipDetailsFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, myFragment).addToBackStack(null).commit();

            }
        }
    }

    // convenience method for getting data at click position
    int getItem(int id) {
        return mData.get(id).itemId;
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}

