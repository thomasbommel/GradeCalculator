package recyclerview;

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
import model.SchoolYear;
import model.SchoolYearTable;
import model.User;

/**
 * Created by Sallaberger on 24.06.2017.
 */

public class SchoolYearListAdapter extends RecyclerView.Adapter<SchoolYearListAdapter.SchoolYearListViewHolder>{
    private final ListItemClickListener onClickListener;
    private List<SchoolYear> schoolYearList;

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

    public SchoolYearListAdapter(ListItemClickListener listener, List<SchoolYear> schoolYearModelList){
        this.onClickListener=listener;
        this.schoolYearList = schoolYearModelList;
    }

    @Override
    public SchoolYearListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.schoolyear_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem,parent,shouldAttachToParentImmediately);
        return new SchoolYearListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SchoolYearListViewHolder holder, int position) {
        Log.d(getClass().toString(),"# "+position);
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return schoolYearList.size();
    }

    public void refresh(SQLiteDatabase database, User user){
        schoolYearList = SchoolYearTable.getInstance().getAllFromDataBaseForUser(database, user);
        notifyDataSetChanged();
    }

    /**
     * determines how an individual user_list entry is displayed
     */
    public class SchoolYearListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        private TextView tvSchoolYearItem;

        public SchoolYearListViewHolder(View itemView) {
            super(itemView);
            tvSchoolYearItem = (TextView) itemView.findViewById(R.id.tv_schoolyear_list_item);
            itemView.setOnClickListener(this);
        }

        protected void bind(int position){
            if(schoolYearList.size()>position){
                tvSchoolYearItem.setText(schoolYearList.get(position).getId()+" "+schoolYearList.get(position).getName()+" "+schoolYearList.get(position).getUserId());
            }
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            onClickListener.onListItemClick(clickedPosition);
        }
    }





}
