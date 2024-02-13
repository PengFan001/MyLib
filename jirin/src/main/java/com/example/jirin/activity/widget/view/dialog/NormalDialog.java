package com.example.jirin.activity.widget.view.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.jirin.R;

/**
 * @ClassName NormalDialog
 * @Author pengfan
 * @Date 2024/2/13
 * @Description
 */
public class NormalDialog extends DialogFragment {
    private static final String TAG = "NormalDialog";
    private static final String KEY_TITLE = "title";
    private static final String KEY_MESSAGE = "message";

    public interface Callback{
        /**
         * 确认按钮回调
         */
        void onPositionCallback();

        /**
         * 取消按钮回调
         */
        void onNegativeCallback();
    }

    private Callback callback;
    private TextView mTvTitle;
    private TextView mTvMessage;
    private TextView mTvCancel;
    private TextView mTvEnsure;

    public NormalDialog() {

    }

    public static NormalDialog createDialog(String title, String message) {
        NormalDialog fragment = new NormalDialog();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE, title);
        bundle.putString(KEY_MESSAGE, message);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setDialogClickListener(Callback callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_normal_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        String title = getResources().getString(R.string.text_dialog_title);
        String message = getResources().getString(R.string.text_dialog_message);
        if (arguments != null) {
            title = arguments.getString(KEY_TITLE, title);
            message = arguments.getString(KEY_MESSAGE, message);
        }
        mTvTitle = (TextView) view.findViewById(R.id.nor_dialog_title);
        mTvMessage = (TextView) view.findViewById(R.id.nor_dialog_message);
        mTvCancel = (TextView) view.findViewById(R.id.nor_dialog_cancel);
        mTvEnsure = (TextView) view.findViewById(R.id.nor_dialog_ensure);
        mTvTitle.setText(title);
        mTvMessage.setText(message);
        mTvEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.onPositionCallback();
                }
            }
        });

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.onNegativeCallback();
                }
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}
