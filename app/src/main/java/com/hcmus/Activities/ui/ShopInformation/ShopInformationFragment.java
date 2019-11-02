package com.hcmus.Activities.ui.ShopInformation;

import android.app.TimePickerDialog;
import android.content.Context;
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
import android.widget.TimePicker;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.List;

public class ShopInformationFragment extends Fragment {

    private ShopInformationViewModel shopInformationViewModel;
    private boolean isEditing = false;
    private EditText address;
    private EditText open;
    private EditText close;
    private TextView nameText;
    private ImageView editBtn;
    private ImageView saveBtn;
    private ImageView cancelBtn;
    private ShopDto shopDto;
    private int currentHour;
    private int currentMin;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shop_information, container, false);
        address = root.findViewById(R.id.shop_info_address);
        open = root.findViewById(R.id.shop_info_open);
        close = root.findViewById(R.id.shop_info_close);
        nameText = root.findViewById(R.id.shop_info_name);
        editBtn = root.findViewById(R.id.shop_info_edit);
        saveBtn = root.findViewById(R.id.shop_info_save);
        cancelBtn = root.findViewById(R.id.shop_info_cancel);
        shopDto = ShopDao.findById(1);
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
                    UpdateShopInfo();
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
                    RestoreShopInfo();
                }
            }
        });
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMin = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = String.format("%02d:%02d",hourOfDay,minute);
                        open.setText(time);
                    }
                }, currentHour,currentMin,true);
                timePickerDialog.show();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = String.format("%02d:%02d",hourOfDay,minute);
                        close.setText(time);
                    }
                }, currentHour,currentMin,true);
                timePickerDialog.show();
            }
        });
        return root;
    }
    private void RestoreShopInfo() {
        nameText.setText(shopDto.getName());
        address.setText(shopDto.getAddress());
        open.setText(ConversionUtils.DateTime.formatTime(shopDto.getOpenTime()));
        close.setText(ConversionUtils.DateTime.formatTime(shopDto.getCloseTime()));
    }
    private void UpdateShopInfo() {
        ShopDto dto = new ShopDto();
        String name = nameText.getText().toString();
        String addressStr = address.getText().toString();
        String openStr = open.getText().toString();
        String closeStr = close.getText().toString();
        dto.setName(name);
        dto.setAddress(addressStr);
        dto.setOpenTime(openStr);
        dto.setCloseTime(closeStr);
        dto.setShopId(1);
        boolean result = ShopDao.Update(dto);
        if (result)
            shopDto = dto;
        else
            Toast.makeText(getContext(), "Error update Shop Information", Toast.LENGTH_LONG).show();
    }
    private void disableEditText(EditText editText) {
        editText.setEnabled(false);
        editText.setTextColor(Color.BLACK);
    }
    private void enableEditText(EditText editText) {
        editText.setEnabled(true);
    }
}
