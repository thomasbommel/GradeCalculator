package at.sallaberger.thomas.myapplication.recyclerview.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import at.sallaberger.thomas.myapplication.R;
import model.database.objects.User;
import model.database.tables.UserTable;

/**
 * Created by Sallaberger on 24.06.2017.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder>{
    private final ListItemClickListener onClickListener;
    private  List<User> userList;

    public UserListAdapter(ListItemClickListener listener, List<User> userList){
        this.onClickListener=listener;
        this.userList = userList;
    }

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

    @Override
    public UserListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.user_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem,parent,shouldAttachToParentImmediately);
        return new UserListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserListViewHolder holder, int position) {
        Log.d(getClass().toString(),"# "+position);
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void refresh(SQLiteDatabase database){
       userList =  UserTable.getInstance().getAllFromDataBase(database);
        Log.e(this.getClass().toString(),"refresh ...userListSize "+userList.size());
       notifyDataSetChanged();
    }


    /**
     * determines how an individual user_list entry is displayed
     */
    public class UserListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        private TextView tvUserListItem;

        public UserListViewHolder(View itemView) {
            super(itemView);
            tvUserListItem = (TextView) itemView.findViewById(R.id.tv_user_list_item);
            itemView.setOnClickListener(this);
        }

        protected void bind(int position){
             tvUserListItem.setText(userList.get(position).getName());
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            onClickListener.onListItemClick(clickedPosition);
        }
    }





}
