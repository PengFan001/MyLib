package com.example.jirin.activity.widget.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.jirin.R;
import com.example.jirin.utils.LogUtil;

import java.util.ArrayList;

/**
 * @ClassName ListDialog
 * @Author pengfan
 * @Date 2024/2/13
 * @Description
 */
public class ListDialog extends DialogFragment {
    private static final String TAG = "ListDialog";
    private static final String KEY_TITLE = "title";
    private static final String KEY_MENU_ITEM = "menu_item";

    private ListItemAdapter mItemAdapter;

    public interface OnItemClickListener {
        /**
         * 选项点击
         * @param position 用户点击位置
         * @param item 用户点击项
         */
        void onItemClick(int position, String item);
    }

    private OnItemClickListener mItemClickListener;
    private TextView mTvTitle;
    private ListView mLvMenuItem;

    public ListDialog() {

    }

    public static ListDialog createDialog(String title, ArrayList<String> items) {
        ListDialog fragment = new ListDialog();
        LogUtil.d(TAG, "ListDialog: list size = " + items.size());
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE, title);
        bundle.putStringArrayList(KEY_MENU_ITEM, items);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setItemClickListener(OnItemClickListener itemClick) {
        this.mItemClickListener = itemClick;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_menu_item_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        String title = getResources().getString(R.string.text_dialog_title);
        ArrayList<String> items = new ArrayList<>();
        if (arguments != null) {
            title = arguments.getString(KEY_TITLE, title);
            items = arguments.getStringArrayList(KEY_MENU_ITEM);
        }

        LogUtil.d(TAG, "onViewCreated: items count = " + items.size());
        mTvTitle = (TextView) view.findViewById(R.id.menu_item_dialog_title);
        mLvMenuItem = (ListView) view.findViewById(R.id.lv_menu);
        mTvTitle.setText(title);
        mItemAdapter = new ListItemAdapter(getContext(), items);
        mLvMenuItem.setAdapter(mItemAdapter);

        mLvMenuItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(position, mItemAdapter.getItem(position));
                }
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    private class ListItemAdapter extends BaseAdapter {
        private static final String TAG = "ListItemAdapter";
        private Context mContext;
        private ArrayList<String> items;

        public ListItemAdapter(Context context, ArrayList<String> items) {
            this.mContext = context;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items == null ? 0 : items.size();
        }

        @Override
        public String getItem(int position) {
            if (items == null) {
                return null;
            }

            if (position < 0 || position > items.size() - 1) {
                return null;
            }

            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.menu_item, null);
                viewHolder = new ViewHolder();
                viewHolder.item = convertView.findViewById(R.id.tv_menu_item);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.item.setText(getItem(position));

            return convertView;
        }

        class ViewHolder {
            TextView item;
        }
    }
}
