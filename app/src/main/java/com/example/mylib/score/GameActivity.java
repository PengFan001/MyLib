package com.example.mylib.score;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jirin.activity.base.BaseAppCompatActivity;
import com.example.jirin.activity.widget.view.dialog.ListDialog;
import com.example.jirin.activity.widget.view.dialog.NormalDialog;
import com.example.jirin.utils.LogUtil;
import com.example.jirin.utils.ToastUtil;
import com.example.mylib.Constants;
import com.example.mylib.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * @author PengFan
 */
public class GameActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private static final String TAG = "GameActivity";

    private RelativeLayout mRlBoomCount;
    private RelativeLayout mRlSpring;
    private RelativeLayout mRlLand;
    private RelativeLayout mRlWinner;
    private TextView mTvBoomCount;
    private TextView mTvSpring;
    private TextView mTvLand;
    private TextView mTvWinner;
    private TextView mTvCount1;
    private TextView mTvCount2;
    private TextView mTvCount3;
    private Button mBtnScore;
    private TextView mTvName1;
    private TextView mTvName2;
    private TextView mTvName3;

    private ArrayList<String> mBoomCountList = new ArrayList<>();
    private ArrayList<String> mSpringList = new ArrayList<>();
    private ArrayList<String> mPlayerList = new ArrayList<>();
    private HashMap<String, Integer> mScore = new HashMap<>(3);
    private int mMoney;
    /**
     * 翻倍倍数
     */
    private int mMultiple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initView();
        initData();
    }

    private void initData() {
        mBoomCountList.clear();
        mBoomCountList.add("0");
        mBoomCountList.add("1");
        mBoomCountList.add("2");
        mBoomCountList.add("3");
        mBoomCountList.add("4");
        mBoomCountList.add("5");

        Intent intent = getIntent();
        String name1 = getResources().getString(R.string.text_palyer1);
        String name2 = getResources().getString(R.string.text_palyer2);
        String name3 = getResources().getString(R.string.text_palyer3);
        if (intent != null) {
            mMoney = intent.getIntExtra(Constants.KEY_MONEY, 2);
            name1 = intent.getStringExtra(Constants.KEY_PLAY_1);
            name2 = intent.getStringExtra(Constants.KEY_PLAY_2);
            name3 = intent.getStringExtra(Constants.KEY_PLAY_3);
        }
        mTvName1.setText(name1);
        mTvName2.setText(name2);
        mTvName3.setText(name3);
        mPlayerList.clear();
        mPlayerList.add(name1);
        mPlayerList.add(name2);
        mPlayerList.add(name3);
        mMultiple = 1;

        mSpringList.clear();
        mSpringList.add(getResources().getString(R.string.text_yes));
        mSpringList.add(getResources().getString(R.string.text_no));

        mScore.clear();
        mScore.put(name1, 0);
        mScore.put(name2, 0);
        mScore.put(name3, 0);
    }

    private void initView() {
        mRlBoomCount = findViewById(R.id.rl_boom);
        mRlSpring = findViewById(R.id.rl_spring);
        mRlLand = findViewById(R.id.rl_landlord);
        mRlWinner = findViewById(R.id.rl_winner);
        mTvBoomCount = findViewById(R.id.tv_boom_number);
        mTvSpring = findViewById(R.id.tv_spring);
        mTvLand = findViewById(R.id.tv_landlord);
        mTvWinner = findViewById(R.id.tv_winner);
        mTvCount1 = findViewById(R.id.tv_count_1);
        mTvCount2 = findViewById(R.id.tv_count_2);
        mTvCount3 = findViewById(R.id.tv_count_3);
        mBtnScore = findViewById(R.id.btn_submit);
        mTvName1 = findViewById(R.id.tv_name1);
        mTvName2 = findViewById(R.id.tv_name2);
        mTvName3 = findViewById(R.id.tv_name3);

        mRlBoomCount.setOnClickListener(this);
        mRlSpring.setOnClickListener(this);
        mRlLand.setOnClickListener(this);
        mRlWinner.setOnClickListener(this);
        mBtnScore.setOnClickListener(this);
    }

    private void restInfo() {
        mTvBoomCount.setText(String.valueOf(0));
        mTvSpring.setText(getResources().getString(R.string.text_no));
        mTvLand.setText("");
        mTvWinner.setText("");
        mMultiple = 1;
    }

    @Override
    public void onClick(View v) {
        ListDialog dialog;
        switch (v.getId()) {
            case R.id.rl_boom:
                dialog = ListDialog.createDialog(getResources().getString(R.string.text_boom_number),
                        mBoomCountList);
                dialog.setItemClickListener(new ListDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, String item) {
                        int boomCount = Integer.parseInt(item);
                        for (int i = 0; i < boomCount; i++) {
                            mMultiple = mMultiple * 2;
                        }
                        mTvBoomCount.setText(item);
                        dialog.dismiss();
                    }
                });
                dialog.show(getSupportFragmentManager(), TAG);
                break;

            case R.id.rl_spring:
                dialog = ListDialog.createDialog(getResources().getString(R.string.text_weather_spring),
                        mSpringList);
                dialog.setItemClickListener(new ListDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, String item) {
                        if (getResources().getString(R.string.text_yes).equals(item)) {
                            mMultiple = mMultiple * 2;
                        }
                        mTvSpring.setText(item);
                        dialog.dismiss();
                    }
                });
                dialog.show(getSupportFragmentManager(), TAG);
                break;

            case R.id.rl_landlord:
                dialog = ListDialog.createDialog(getResources().getString(R.string.text_landlord_player),
                        mPlayerList);
                dialog.setItemClickListener(new ListDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, String item) {
                        mTvLand.setText(item);
                        dialog.dismiss();
                    }
                });
                dialog.show(getSupportFragmentManager(), TAG);
                break;

            case R.id.rl_winner:
                dialog = ListDialog.createDialog(getResources().getString(R.string.text_landlord_player),
                        mPlayerList);
                dialog.setItemClickListener(new ListDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, String item) {
                        mTvWinner.setText(item);
                        dialog.dismiss();
                    }
                });
                dialog.show(getSupportFragmentManager(), TAG);
                break;

            case R.id.btn_submit:
                verifyInfo();
                break;

            default:
                break;
        }
    }

    private void verifyInfo() {
        if (TextUtils.isEmpty(mTvLand.getText().toString())
                || TextUtils.isEmpty(mTvWinner.getText().toString())) {
            ToastUtil.showShortToast(getApplicationContext(),
                    getResources().getString(R.string.tips_input_winner));
            return;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("计分前请确认本局信息:").append("\n")
                .append("炸弹个数：").append(mTvBoomCount.getText().toString()).append("\n")
                .append("是否春天：").append(mTvSpring.getText().toString()).append("\n")
                .append("地主：").append(mTvLand.getText().toString()).append("\n")
                .append("赢家：").append(mTvWinner.getText().toString()).append("\n");
        String result = builder.toString();
        NormalDialog dialog = NormalDialog.createDialog(getResources().getString(R.string.text_dialog_title),
                result);
        dialog.setDialogClickListener(new NormalDialog.Callback() {
            @Override
            public void onPositionCallback() {
                getScore();
                dialog.dismiss();
            }

            @Override
            public void onNegativeCallback() {
                dialog.dismiss();
            }
        });
        dialog.show(getSupportFragmentManager(), TAG);
    }

    private void getScore() {
        int total = mMoney * mMultiple;
        int average = total / 2;
        String winner = mTvWinner.getText().toString();
        String land = mTvLand.getText().toString();
        LogUtil.d(TAG, "getScore: mMoney = " + mMoney + "   mMultiple = " + mMultiple
                + "   total = " + total + "   average = " + average);

        //计分
        Set<String> keys = mScore.keySet();
        LogUtil.d(TAG, "getScore: keys = " + keys.size());
        if (winner.equals(land)) {
            for (String name : keys) {
                int value = mScore.get(name);
                if (name.equals(winner)) {
                    mScore.put(name, value + total);
                } else {
                    mScore.put(name, value - average);
                }
            }
        } else {
            for (String name : keys) {
                int value = mScore.get(name);
                if (name.equals(winner)) {
                    mScore.put(name, value + average);
                } else {
                    if (name.equals(land)) {
                        mScore.put(name, value - total);
                    } else {
                        mScore.put(name, value + average);
                    }
                }
            }
        }

        for (String name : keys) {
            int value = mScore.get(name);
            LogUtil.d(TAG, "getScore: name = " + name + "   value = " + value);
            if (name.equals(mTvName1.getText().toString())) {
                mTvCount1.setText(String.valueOf(value));
            } else if (name.equals(mTvName2.getText().toString())) {
                mTvCount2.setText(String.valueOf(value));
            } else if (name.equals(mTvName3.getText().toString())) {
                mTvCount3.setText(String.valueOf(value));
            } else {
                LogUtil.e(TAG, "getScore: error name = " + name);
            }
        }

        restInfo();
    }

    @Override
    public void onBackPressed() {
        NormalDialog dialog = NormalDialog.createDialog(getResources().getString(R.string.text_dialog_title),
                getResources().getString(R.string.tips_exit));
        dialog.setDialogClickListener(new NormalDialog.Callback() {
            @Override
            public void onPositionCallback() {
                dialog.dismiss();
                finish();
            }

            @Override
            public void onNegativeCallback() {
                dialog.dismiss();
            }
        });
        dialog.show(getSupportFragmentManager(), TAG);
    }
}