package com.example.medhagupta.todolist;

/**
 * Created by Medha Gupta on 11/3/2016.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private List<ToDoList> doList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,details;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            //details = (TextView) view.findViewById(R.id.details);

        }
    }

    public ListAdapter(List<ToDoList> doList) {
        this.doList = doList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ToDoList list = doList.get(position);
        holder.title.setText(list.getTitle());
        //holder.details.setText(list.getDetails());

    }

    @Override
    public int getItemCount() {
        return doList.size();
    }


}
