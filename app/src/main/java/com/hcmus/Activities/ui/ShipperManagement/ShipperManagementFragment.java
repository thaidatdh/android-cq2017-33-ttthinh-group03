package com.hcmus.Activities.ui.ShipperManagement;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.hcmus.DAO.ShipperDao;
import com.hcmus.DAO.UserDao;
import com.hcmus.DTO.ShipperDto;
import com.hcmus.DTO.UserDto;
import com.hcmus.Utils.ConversionUtils;
import com.hcmus.shipe.R;

import java.util.List;

public class ShipperManagementFragment extends Fragment {
    private List<ShipperDto> listShipper;
    private ListView shipperListView;
    CustomListAdapter adapter;
    Dialog myDialog;
    private ShipperDto selectedShipper;
    private int selectedType=0;
    private boolean recordChanged = false;
    private final String[] SHIPPER_TYPE = {"Not Confirmed","Active","Inactive","All"};
    private final String[] SHIPPER_ALL_STATUS = {"Active","Inactive", "Not Confirmed"};
    private final String[] SHIPPER_STATUS = {"Active","Inactive"};
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shipper_management, container, false);
        Spinner shipperTypeSpinner = root.findViewById(R.id.shipper_type_spinner);
        shipperTypeSpinner.setAdapter(new ArrayAdapter<String>(root.getContext(),R.layout.support_simple_spinner_dropdown_item, SHIPPER_TYPE));
        shipperTypeSpinner.setSelection(3);
        shipperListView = root.findViewById(R.id.list_shipper_view);

        shipperTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedType = i;
                SetListView(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        shipperListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedShipper = listShipper.get(position);
                myDialog = new Dialog(view.getContext());
                recordChanged = false;
                ShowPopup();
            }
        });
        return root;
    }
    private void SetListView(int typeIndex) {
        switch (typeIndex) {
            case 0:
                listShipper = ShipperDao.GetUnConfirmed();
                SetListViewShipper();
                break;
            case 1:
                listShipper = ShipperDao.GetActive();
                SetListViewShipper();
                break;
            case 2:
                listShipper = ShipperDao.GetInactive();
                SetListViewShipper();
                break;
            default:
                listShipper = ShipperDao.GetAll();
                SetListViewShipper();
                break;
        }
    }
    private void ShowPopup(){
        Dialog view = myDialog;
        view.setCancelable(false);
        view.setContentView(R.layout.shipper_info_popup);
        TextView name = (TextView)view.findViewById(R.id.shipper_info_name);
        TextView address = (TextView)view.findViewById(R.id.shipper_info_address);
        TextView birthdate = (TextView)view.findViewById(R.id.shipper_info_birthdate);
        TextView phone = (TextView)view.findViewById(R.id.shipper_info_phone);
        TextView license = (TextView)view.findViewById(R.id.shipper_info_license);
        ImageView closeBtn = (ImageView)view.findViewById(R.id.shipper_info_close);
        Spinner statusSpinner = (Spinner)view.findViewById(R.id.shipper_info_status_spinner);
        if (selectedShipper!= null && !selectedShipper.getActive().equals("NULL"))
            statusSpinner.setAdapter(new ArrayAdapter<String>(view.getContext(),R.layout.support_simple_spinner_dropdown_item, SHIPPER_STATUS));
        else
            statusSpinner.setAdapter(new ArrayAdapter<String>(view.getContext(),R.layout.support_simple_spinner_dropdown_item, SHIPPER_ALL_STATUS));
        if (selectedShipper!=null) {
            UserDto user = UserDao.findById(selectedShipper.getUserId());
            if (user != null) {
                String nameStr = (user.getLastName() + ", " + user.getFirstName()).trim();
                name.setText(nameStr);
                address.setText(user.getAddress());
                birthdate.setText(user.getBirthDate());
                phone.setText(user.getPhone());
            }
            license.setText(selectedShipper.getPlateNumber());
            switch (selectedShipper.getActive()) {
                case "TRUE":
                    statusSpinner.setSelection(0);
                    break;
                case "FALSE":
                    statusSpinner.setSelection(1);
                    break;
                case "NULL":
                    statusSpinner.setSelection(2);
                    break;
            }
        }
        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (selectedShipper==null)
                    return;
                switch (i) {
                    case 0:
                        selectedShipper.setActive("TRUE");
                        break;
                    case 1:
                        selectedShipper.setActive("FALSE");
                        break;
                    default:
                        selectedShipper.setActive("NULL");
                        break;
                }
                ShipperDao.Update(selectedShipper);
                recordChanged = true;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                if (recordChanged) {
                    SetListView(selectedType);
                }
            }
        });
        myDialog.show();
    }
    private void SetListViewShipper() {
        adapter = new CustomListAdapter(getContext(), R.layout.shipper_list_item, listShipper);
        shipperListView.setAdapter(adapter);
    }
    public class CustomListAdapter extends ArrayAdapter<ShipperDto> {
        Context myContext;
        int myLayout;
        List<ShipperDto> myList;
        public CustomListAdapter(Context context, int layout, List<ShipperDto> list) {
            super(context, layout, list);
            myContext = context;
            myLayout = layout;
            myList = list;
        }
        @Override
        public int getCount() {
            if (listShipper!= null)
                return listShipper.size();
            else
                return 0;
        }

        /*@Override
        public Object getItem(int position) {
            return listShipper.get(position);
        }
*/
        @Override
        public long getItemId(int position) {
            return position;
        }
        @NonNull
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public View getView(int i, View view, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.shipper_list_item, null);
            RatingBar ratingBar = (RatingBar)view.findViewById(R.id.ratingBar_Shipper);
            TextView shipperName = (TextView)view.findViewById(R.id.textView_Name_Shipper);
            TextView shipperPlate = (TextView)view.findViewById(R.id.textView_Plate_Shipper);
            TextView shipperStatus = (TextView)view.findViewById(R.id.textView_Status_Shipper);
            ShipperDto shipperDto = myList.get(i);
            UserDto shipperUser = UserDao.findById(shipperDto.getUserId());
            if (shipperUser == null) {
                Toast.makeText(myContext,"User does not exist",Toast.LENGTH_SHORT).show();
                return view;
            }
            String name = (shipperUser.getLastName() + ", " + shipperUser.getFirstName()).trim();
            float star = ConversionUtils.Review.GetReviewStarForShipper(shipperDto.getUserId());
            ratingBar.setRating(star);
            shipperName.setText(name);
            shipperPlate.setText(shipperDto.getPlateNumber());
            switch (shipperDto.getActive()) {
                case "TRUE":
                    shipperStatus.setText(R.string.active);
                    shipperStatus.setTextColor(Color.parseColor("#15ff00"));
                    break;
                case "FALSE":
                    shipperStatus.setText(R.string.inactive);
                    shipperStatus.setTextColor(Color.parseColor("#6d736d"));
                    break;
                case "NULL":
                    shipperStatus.setText(R.string.unconfirmed);
                    shipperStatus.setTextColor(Color.parseColor("#b31919"));
                    break;
            }
            return view;
        }
    }
}