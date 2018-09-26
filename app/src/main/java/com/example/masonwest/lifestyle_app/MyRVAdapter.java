package com.example.masonwest.lifestyle_app;

import android.support.v7.widget.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyRVAdapter extends RecyclerView.Adapter<MyRVAdapter.ViewHolder> {
    private List<String> mListItems;
    private Context mContext;
    private DataPasser mDataPasser;

    public MyRVAdapter(List<String> inputList) {

        mListItems = inputList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        protected View itemLayout;
        protected TextView itemTvData;

        public ViewHolder(View view){
            super(view);
            itemLayout = view;
            itemTvData = (TextView) view.findViewById(R.id.tv_data);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        try{
            mDataPasser = (DataPasser) mContext;
        }catch(ClassCastException e){
            throw new ClassCastException(mContext.toString()+ " must implement HeaderDataPass");
        }

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View myView = layoutInflater.inflate(R.layout.view_layout,parent,false);
        return new ViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.itemTvData.setText(mListItems.get(position));
        holder.itemLayout.setOnClickListener(new View.OnClickListener(){
                                                 @Override
                                                 public void onClick(View view) {
                                                     mDataPasser.passData(position);
                                                 }
                                             }
        );

        // if position is 0
        // holder. imageview .setImageResource(R.drawable. //)
    }

    public void remove(int position){
        mListItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {

        return mListItems.size();
    }

    public static interface DataPasser{
        public void passData(int position);
    }
}
