package com.example.mylib.score;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jirin.activity.base.BaseAppCompatActivity;
import com.example.jirin.activity.widget.view.dialog.EditTextDialog;
import com.example.jirin.utils.ToastUtil;
import com.example.mylib.Constants;
import com.example.mylib.R;

/**
 * @author PengFan
 */
public class ScoreActivity extends BaseAppCompatActivity implements View.OnClickListener {
    public static final String TAG = "ScoreActivity";

    private RelativeLayout mRlMoney;
    private RelativeLayout mRlPlay1;
    private RelativeLayout mRlPlay2;
    private RelativeLayout mRlPlay3;
    private TextView mTvMoney;
    private TextView mTvPlay1;
    private TextView mTvPlay2;
    private TextView mTvPlay3;
    private Button mBtnStart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        initView();
    }

    private void initView() {
        mRlMoney = findViewById(R.id.rl_money);
        mRlPlay1 = findViewById(R.id.rl_player_1);
        mRlPlay2 = findViewById(R.id.rl_player_2);
        mRlPlay3 = findViewById(R.id.rl_player_3);
        mTvMoney = findViewById(R.id.tv_money);
        mTvPlay1 = findViewById(R.id.tv_player1);
        mTvPlay2 = findViewById(R.id.tv_player2);
        mTvPlay3 = findViewById(R.id.tv_player3);
        mBtnStart = findViewById(R.id.btn_start);

        mRlMoney.setOnClickListener(this);
        mRlPlay1.setOnClickListener(this);
        mRlPlay2.setOnClickListener(this);
        mRlPlay3.setOnClickListener(this);
        mBtnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_money:
                EditTextDialog dialog = EditTextDialog.createDialog(
                        getResources().getString(R.string.text_money),
                        "2",
                        getResources().getString(R.string.hint_number),
                        EditTextDialog.TYPE_NUMBER);
                dialog.setDialogClickListener(new EditTextDialog.Callback() {
                    @Override
                    public void onPositionCallback(int id, String value) {
                        try {
                            int money = Integer.parseInt(value);
                            if (money > 0 && money % 2 == 0) {
                                mTvMoney.setText(String.valueOf(money));
                                dialog.dismiss();
                            } else {
                                ToastUtil.showShortToast(getApplicationContext(),
                                        getResources().getString(R.string.tips_params_error));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtil.showShortToast(getApplicationContext(),
                                    getResources().getString(R.string.tips_params_error));
                        }
                    }

                    @Override
                    public void onNegativeCallback() {
                        dialog.dismiss();
                    }
                });
                dialog.show(getSupportFragmentManager(), TAG);
                break;

            case R.id.rl_player_1:
                EditTextDialog playDialog1 = EditTextDialog.createDialog(
                        getResources().getString(R.string.text_input_player_name),
                        "",
                        getResources().getString(R.string.text_input_player_name),
                        EditTextDialog.TYPE_TEXT);
                playDialog1.setDialogClickListener(new EditTextDialog.Callback() {
                    @Override
                    public void onPositionCallback(int id, String value) {
                        try {
                            if (!TextUtils.isEmpty(value)) {
                                mTvPlay1.setText(String.valueOf(value));
                                playDialog1.dismiss();
                            } else {
                                ToastUtil.showShortToast(getApplicationContext(),
                                        getResources().getString(R.string.tips_params_error));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtil.showShortToast(getApplicationContext(),
                                    getResources().getString(R.string.tips_params_error));
                        }
                    }

                    @Override
                    public void onNegativeCallback() {
                        playDialog1.dismiss();
                    }
                });
                playDialog1.show(getSupportFragmentManager(), TAG);
                break;

            case R.id.rl_player_2:
                EditTextDialog playDialog2 = EditTextDialog.createDialog(
                        getResources().getString(R.string.text_input_player_name),
                        "",
                        getResources().getString(R.string.text_input_player_name),
                        EditTextDialog.TYPE_TEXT);
                playDialog2.setDialogClickListener(new EditTextDialog.Callback() {
                    @Override
                    public void onPositionCallback(int id, String value) {
                        try {
                            if (!TextUtils.isEmpty(value)) {
                                mTvPlay2.setText(String.valueOf(value));
                                playDialog2.dismiss();
                            } else {
                                ToastUtil.showShortToast(getApplicationContext(),
                                        getResources().getString(R.string.tips_params_error));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtil.showShortToast(getApplicationContext(),
                                    getResources().getString(R.string.tips_params_error));
                        }
                    }

                    @Override
                    public void onNegativeCallback() {
                        playDialog2.dismiss();
                    }
                });
                playDialog2.show(getSupportFragmentManager(), TAG);
                break;

            case R.id.rl_player_3:
                EditTextDialog playDialog3 = EditTextDialog.createDialog(
                        getResources().getString(R.string.text_input_player_name),
                        "",
                        getResources().getString(R.string.text_input_player_name),
                        EditTextDialog.TYPE_TEXT);
                playDialog3.setDialogClickListener(new EditTextDialog.Callback() {
                    @Override
                    public void onPositionCallback(int id, String value) {
                        try {
                            if (!TextUtils.isEmpty(value)) {
                                mTvPlay3.setText(String.valueOf(value));
                                playDialog3.dismiss();
                            } else {
                                ToastUtil.showShortToast(getApplicationContext(),
                                        getResources().getString(R.string.tips_params_error));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtil.showShortToast(getApplicationContext(),
                                    getResources().getString(R.string.tips_params_error));
                        }
                    }

                    @Override
                    public void onNegativeCallback() {
                        playDialog3.dismiss();
                    }
                });
                playDialog3.show(getSupportFragmentManager(), TAG);
                break;

            case R.id.btn_start:
                if (!isDoubleClick()) {
                    if (TextUtils.isEmpty(mTvMoney.getText())) {
                        ToastUtil.showShortToast(getApplicationContext(),
                                getResources().getString(R.string.tips_must_input_money));
                        return;
                    }
                    try {
                        int value = Integer.parseInt(mTvMoney.getText().toString());
                        String player1 = mTvPlay1.getText().toString();
                        String player2 = mTvPlay2.getText().toString();
                        String player3 = mTvPlay3.getText().toString();
                        if (TextUtils.isEmpty(player1)) {
                            player1 = getResources().getString(R.string.text_palyer1);
                        }
                        if (TextUtils.isEmpty(player2)) {
                            player2 = getResources().getString(R.string.text_palyer2);
                        }
                        if (TextUtils.isEmpty(player3)) {
                            player3 = getResources().getString(R.string.text_palyer3);
                        }
                        Intent intent = new Intent(this, GameActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(Constants.KEY_MONEY, value);
                        intent.putExtra(Constants.KEY_PLAY_1, player1);
                        intent.putExtra(Constants.KEY_PLAY_2, player2);
                        intent.putExtra(Constants.KEY_PLAY_3, player3);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtil.showShortToast(getApplicationContext(),
                                getResources().getString(R.string.tips_params_error));
                    }
                }
                break;

            default:
                break;
        }
    }
}