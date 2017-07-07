package recyclerview;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import at.sallaberger.thomas.myapplication.MainActivity;
import at.sallaberger.thomas.myapplication.R;
import database.DatabaseUtils;
import database.GradeCalculatorContract;
import model.User;

/**
 * Created by Sallaberger on 24.06.2017.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder>{

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

    final private ListItemClickListener onClickListener;
    private List<User> userList;

    public UserListAdapter(ListItemClickListener listener, List<User> userList){
        this.onClickListener=listener;
        this.userList = userList;
    }

    @Override
    public UserListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.user_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem,parent,shouldAttachToParentImmediately);
        UserListViewHolder viewHolder = new UserListViewHolder(view);
        return viewHolder;
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
        final StringBuilder builder = new StringBuilder();
        final Cursor userCursor = DatabaseUtils.getAllUsers(database);

        try {
            int i = 0;
            userList.clear();
            while (userCursor.moveToNext()) {
                userList.add(0,new User(userCursor.getString(userCursor.getColumnIndex(GradeCalculatorContract.User.COLUMN_NAME)) + " " + ++i));
            }
        }finally {
            userCursor.close();
        }

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
            if(userList.size()>position){
                tvUserListItem.setText("pos "+position+" "+userList.get(position).getName());
            }
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            onClickListener.onListItemClick(clickedPosition);
        }
    }





}
