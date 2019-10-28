package com.hcmus.Activities.ui.ShopInformation;

import android.graphics.BlendMode;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.KeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.hcmus.DAO.ShipperDao;
import com.hcmus.DAO.ShopDao;
import com.hcmus.DTO.ShopDto;
import com.hcmus.Utils.ConversionUtils;
import com.hcmus.shipe.R;

import java.util.ArrayList;
import java.util.List;

public class ShopInformationFragment extends Fragment {

    private ShopInformationViewModel shopInformationViewModel;
    private boolean isEditing = false;
    EditText address;
    EditText open;
    EditText close;
    ImageView editBtn;
    ImageView saveBtn;
    ImageView cancelBtn;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shop_information, container, false);
        address = root.findViewById(R.id.shop_info_address);
        open = root.findViewById(R.id.shop_info_open);
        close = root.findViewById(R.id.shop_info_close);
        editBtn = root.findViewById(R.id.shop_info_edit);
        saveBtn = root.findViewById(R.id.shop_info_save);
        cancelBtn = root.findViewById(R.id.shop_info_cancel);
        ShopDto shopDto = ShopDao.findById(1);
        address.setText(shopDto.getAddress());
        open.setText(ConversionUtils.DateTime.formatTime(shopDto.getOpenTime()));
        close.setText(ConversionUtils.DateTime.formatTime(shopDto.getCloseTime()));
        disableEditText(address);
        disableEditText(open);
        disableEditText(close);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEditing) {
                    isEditing = true;
                    enableEditText(address);
                    enableEditText(open);
                    enableEditText(close);
                    editBtn.setVisibility(View.GONE);
                    saveBtn.setVisibility(View.VISIBLE);
                    cancelBtn.setVisibility(View.VISIBLE);
                }
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditing) {
                    isEditing = false;
                    disableEditText(address);
                    disableEditText(open);
                    disableEditText(close);
                    saveBtn.setVisibility(View.GONE);
                    cancelBtn.setVisibility(View.GONE);
                    editBtn.setVisibility(View.VISIBLE);
                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditing) {
                    isEditing = false;
                    disableEditText(address);
                    disableEditText(open);
                    disableEditText(close);
                    saveBtn.setVisibility(View.GONE);
                    cancelBtn.setVisibility(View.GONE);
                    editBtn.setVisibility(View.VISIBLE);
                }
            }
        });
        return root;
    }
    private void disableEditText(EditText editText) {
        editText.setEnabled(false);
        editText.setTextColor(Color.BLACK);
    }
    private void enableEditText(EditText editText) {
        editText.setEnabled(true);
    }
}
