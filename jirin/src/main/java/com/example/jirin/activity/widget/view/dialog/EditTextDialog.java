package com.example.jirin.activity.widget.view.dialog;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.jirin.R;

/**
 * @ClassName EditTextDialog
 * @Author pengfan
 * @Date 2024/2/13
 * @Description
 */
public class EditTextDialog extends DialogFragment {
    private static final String TAG = "EditTextDialog";

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_VALUE = "value";
    private static final String KEY_HINT = "hint";
    private static final String KEY_EDIT_TYPE = "type";

    public static final int DEFAULT_ID = 1;

    public static final int TYPE_NUMBER = 0;
    public static final int TYPE_TEXT_PASSWORD = 1;
    public static final int TYPE_TEXT = 2;
    public static final int TYPE_NUMBER_AND_COMMA = 3;

    public interface Callback{
        /**
         * 确认按钮回调
         * @param id Dialog id
         * @param value 用户输入的值
         */
        void onPositionCallback(int id, String value);

        /**
         * 取消按钮回调
         */
        void onNegativeCallback();
    }

    private Callback callback;
    private TextView mTvTitle;
    private EditText mEtValue;
    private ImageView mIvPwdShow;

    private boolean showPwd = false;

    public EditTextDialog() {

    }

    public static EditTextDialog createDialog(String title, String value, String hint, int type) {
        return createDialog(0, title, value, hint, type);
    }

    public static EditTextDialog createDialog(int id, String title, String value, String hint,
                                              int type) {
        EditTextDialog dialog = new EditTextDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ID, id);
        bundle.putString(KEY_TITLE, title);
        bundle.putString(KEY_VALUE, value);
        bundle.putString(KEY_HINT, hint);
        bundle.putInt(KEY_EDIT_TYPE, type);
        dialog.setArguments(bundle);
        return dialog;
    }

    public void setDialogClickListener(Callback callBack) {
        this.callback = callBack;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_edit_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        String title = getResources().getString(R.string.text_dialog_title);
        String value = getResources().getString(R.string.dialog_default_message);
        String hint = "";
        int type = TYPE_NUMBER;
        int id = DEFAULT_ID;

        Bundle argument = getArguments();
        if (argument != null) {
            id = argument.getInt(KEY_ID, id);
            title = argument.getString(KEY_TITLE, title);
            value = argument.getString(KEY_VALUE, value);
            hint = argument.getString(KEY_HINT, hint);
            type = argument.getInt(KEY_EDIT_TYPE, type);
        }

        mTvTitle = (TextView) view.findViewById(R.id.edit_dialog_title);
        LinearLayout mLlPwdDialog = (LinearLayout) view.findViewById(R.id.ll_dialog_value_password);

        if (type == TYPE_TEXT) {
            view.findViewById(R.id.edit_dialog_value_number).setVisibility(View.GONE);
            view.findViewById(R.id.edit_dialog_value_group).setVisibility(View.GONE);
            mLlPwdDialog.setVisibility(View.GONE);
            mEtValue = (EditText) view.findViewById(R.id.edit_dialog_value_nor);
        } else if (type == TYPE_NUMBER_AND_COMMA) {
            view.findViewById(R.id.edit_dialog_value_number).setVisibility(View.GONE);
            view.findViewById(R.id.edit_dialog_value_nor).setVisibility(View.GONE);
            mLlPwdDialog.setVisibility(View.GONE);
            mEtValue = (EditText) view.findViewById(R.id.edit_dialog_value_group);
        } else if (type == TYPE_TEXT_PASSWORD) {
            view.findViewById(R.id.edit_dialog_value_number).setVisibility(View.GONE);
            view.findViewById(R.id.edit_dialog_value_group).setVisibility(View.GONE);
            view.findViewById(R.id.edit_dialog_value_nor).setVisibility(View.GONE);
            mLlPwdDialog.setVisibility(View.VISIBLE);
            mEtValue = (EditText) view.findViewById(R.id.edit_dialog_value_password);
            mIvPwdShow = (ImageView) view.findViewById(R.id.iv_pwdshow);
            mIvPwdShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOrHiddenPwd();
                }
            });
        } else {
            view.findViewById(R.id.edit_dialog_value_nor).setVisibility(View.GONE);
            view.findViewById(R.id.edit_dialog_value_group).setVisibility(View.GONE);
            view.findViewById(R.id.edit_dialog_value_password).setVisibility(View.GONE);
            mEtValue = (EditText) view.findViewById(R.id.edit_dialog_value_number);
        }

        mEtValue.setVisibility(View.VISIBLE);
        TextView mTvEnsure = (TextView) view.findViewById(R.id.edit_dialog_ensure);
        TextView mTvCancel = (TextView) view.findViewById(R.id.edit_dialog_cancel);

        mTvTitle.setText(title);
        mEtValue.requestFocus();
        mEtValue.setText(value);
        mEtValue.setHint(hint);
        mEtValue.setSelection(mEtValue.getText().length());
        int finalId = id;
        mTvEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onPositionCallback(finalId, mEtValue.getText().toString());
                }
            }
        });

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onNegativeCallback();
                }
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void showOrHiddenPwd(){
        if(!showPwd){
            showPwd = true;
            mIvPwdShow.setImageResource(R.mipmap.icon_eye_open);
            mEtValue.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }else{
            showPwd = false;
            mIvPwdShow.setImageResource(R.mipmap.icon_eye_close);
            mEtValue.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        mEtValue.setSelection(mEtValue.getText().length());
    }
}
