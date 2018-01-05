package com.seong.ll.SCB_Viewer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.seong.ll.SCB_Viewer.R;
import com.seong.ll.SCB_Viewer.activity.ContentsActivity;
import com.seong.ll.SCB_Viewer.dummy.DummyContent.DummyItem;
import com.seong.ll.SCB_Viewer.util.SCB_Const;

import java.util.List;


public class FolderRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext = null;
    private final List<DummyItem> mValues;
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 2;

    private interface OnListListener {
        public void onRecyclerClick(int position);
    }

    public FolderRecyclerViewAdapter(Context context, List<DummyItem> items) {
        mContext = context;
        mValues = items;
    }

    private boolean isPositionHeader(int position) {
        return position < SCB_Const.FOLDER_COLUMN_COUNT;
    }


    public int getBasicItemCount() {
        return mValues == null ? 0 : mValues.size();
    }


    @Override
    public int getItemCount() {
        return getBasicItemCount() + 1; // header;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }

        return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        if (viewType == TYPE_HEADER) {
            final View view = LayoutInflater.from(context).inflate(R.layout.recycler_header, parent, false);
            return new RecyclerHeaderViewHolder(view);
        } else {
            final View view = LayoutInflater.from(context).inflate(R.layout.folder_item, parent, false);
            return new FolderViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        //현재 positon이 Header position이 아닐 경우 in
        if (!isPositionHeader(position)) {
            FolderViewHolder holder = (FolderViewHolder) viewHolder;
            final int accuratePosition = position-SCB_Const.FOLDER_COLUMN_COUNT;
            holder.mItem = mValues.get(accuratePosition);
            holder.mIdView.setText(mValues.get(accuratePosition).id);
            holder.mContentView.setText(mValues.get(accuratePosition).content);
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,ContentsActivity.class);
                    mContext.startActivity(intent);
                    ((Activity)mContext).overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);

                }
            });
        }
    }

    /**
     * header 홀더
     */
    public class RecyclerHeaderViewHolder extends RecyclerView.ViewHolder {
        public RecyclerHeaderViewHolder(View view) {
            super(view);
        }
    }


    /**
     * folder 홀더
     */
    public class FolderViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public DummyItem mItem;

        public FolderViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }



}
