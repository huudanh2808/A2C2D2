package com.nguyenthanhchung.carop2p.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.nguyenthanhchung.carop2p.FragmentCallBacks;
import com.nguyenthanhchung.carop2p.Helper.MySharedPreferences;
import com.nguyenthanhchung.carop2p.R;
import com.nguyenthanhchung.carop2p.activity.KhoiDongGameActivity;

public class SettingFragment extends Fragment implements FragmentCallBacks {
    KhoiDongGameActivity main;
    Context context;
    TextView txtPlayerName;
    Button btnSave;
    Button btnCancel;
    Switch swSound;

    public static SettingFragment newInstance(String strArgs) {
        SettingFragment fragment = new SettingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Setting", strArgs);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity();
            main = (KhoiDongGameActivity) getActivity();
        } catch (IllegalStateException e) {
            throw new IllegalStateException("KhoiDong must implement Callback");
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(
                R.layout.layout_setting_fragment, null);
        txtPlayerName = layout.findViewById(R.id.txtPlayerName);
        txtPlayerName.setText(MySharedPreferences.getStringSharedPreferences(main,"Player","name"));
        btnSave = layout.findViewById(R.id.btnSave);
        btnCancel = layout.findViewById(R.id.btnCancel);
        swSound = layout.findViewById(R.id.swSound);
        swSound.setTextOff("Off");
        swSound.setTextOn("On");
        Integer isSwitchChecked = MySharedPreferences.getIntergerSharedPreferences(main,"Setting","sound");
        if(isSwitchChecked >= 0){
            swSound.setChecked(true);
        }
        else
        {
            swSound.setChecked(false);
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySharedPreferences.saveStringSharedPreferences(main,"Player","name",txtPlayerName.getText().toString());
                if(swSound.isChecked()){
                    MySharedPreferences.saveIntegerSharedPreferences(main,"Setting","sound",1);
                }
                else {
                    MySharedPreferences.saveIntegerSharedPreferences(main,"Setting","sound",-1);
                }
                Toast.makeText(main, "Lưu cài đặt thành công", Toast.LENGTH_SHORT).show();
                main.onMsgFromFragmentToMainGame("SettingFragmentClose","sound");

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.onMsgFromFragmentToMainGame("SettingFragmentClose", "close");
            }
        });
        swSound = layout.findViewById(R.id.swSound);


        //Event
        return layout;
    }

    @Override
    public void onMsgFromMainToFrag(String strValue) {

    }
}