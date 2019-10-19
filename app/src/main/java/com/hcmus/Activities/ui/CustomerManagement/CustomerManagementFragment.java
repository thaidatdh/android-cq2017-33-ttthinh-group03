package com.hcmus.Activities.ui.CustomerManagement;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.hcmus.Activities.ui.BillManagement.BillManagementFragment;
import com.hcmus.DAO.BillDao;
import com.hcmus.DAO.BillDetailDao;
import com.hcmus.DAO.UserDao;
import com.hcmus.DTO.BillDetailDto;
import com.hcmus.DTO.BillDto;
import com.hcmus.DTO.UserDto;
import com.hcmus.Utils.ConversionUtils;
import com.hcmus.shipe.R;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CustomerManagementFragment extends Fragment {
    private List<UserDto> listCustomer;
    private ListView customerListView;
    private CustomListAdapter adapter;
    private UserDto selectedUser;
    private Dialog userDialog;
    private Dialog billDialog;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_customer_management, container, false);
        customerListView = root.findViewById(R.id.list_customer_view);
        listCustomer = UserDao.GetAllCustomer();
        adapter = new CustomListAdapter(getContext(), R.layout.customer_list_item, listCustomer);
        customerListView.setAdapter(adapter);
        customerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedUser = listCustomer.get(position);
                userDialog = new Dialog(view.getContext(),android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                ShowUserDialog();
            }
        });
        return root;
    }
    private BillDto selectedBill = null;
    private List<BillDto> listBill;
    public void ShowUserDialog() {
        if (selectedUser == null)
            return;
        Dialog view = userDialog;
        view.setCancelable(true);
        view.setContentView(R.layout.customer_manager_info_popup);
        listBill = BillDao.FindByCustomer(selectedUser.getUserId());
        TextView nameView = view.findViewById(R.id.customer_manager_name);
        TextView usernameView = view.findViewById(R.id.customer_manager_username);
        TextView birthdateView = view.findViewById(R.id.customer_manager_birthdate);
        TextView phoneView = view.findViewById(R.id.customer_manager_phone);
        ListView billListView = view.findViewById(R.id.customer_manager_bill_listview);
        ImageView backBtn = (ImageView)view.findViewById(R.id.customer_manager_popup_back_tool);
        //
        String phone = "Phone: " + selectedUser.getPhone();
        String name = (selectedUser.getLastName() + " " + selectedUser.getFirstName()).trim();
        String username = selectedUser.getUsername();
        String birthdate = "Birthdate: " + ConversionUtils.DateTime.formatDate(selectedUser.getBirthDate());
        nameView.setText(name);
        usernameView.setText(username);
        birthdateView.setText(birthdate);
        phoneView.setText(phone);
        //
        BillManagementFragment.CustomListAdapter adapter = new BillManagementFragment.CustomListAdapter(getContext(), R.layout.bill_list_item, listBill);
        billListView.setAdapter(adapter);
        billListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedBill = listBill.get(position);
                billDialog = new Dialog(view.getContext());
                ShowPopup();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDialog.dismiss();
            }
        });
        userDialog.show();
    }
    private void ShowPopup(){
        Dialog view = billDialog;
        view.setCancelable(true);
        view.setContentView(R.layout.bill_info_popup);
        TextView customer = (TextView)view.findViewById(R.id.bill_info_customer);
        TextView shipper= (TextView)view.findViewById(R.id.bill_info_shipper);
        TextView description = (TextView)view.findViewById(R.id.bill_info_description);
        TextView created = (TextView)view.findViewById(R.id.bill_info_created);
        TextView delivery = (TextView)view.findViewById(R.id.bill_info_delivery);
        TextView statusSpinner = (TextView)view.findViewById(R.id.bill_info_status);
        ImageView closeBtn = (ImageView)view.findViewById(R.id.bill_info_close);
        if (selectedBill!=null) {
            UserDto billCustomer = UserDao.findById(selectedBill.getCustomerId());
            UserDto billShipper = UserDao.findById(selectedBill.getShipperId());
            String namecustomer = "";
            String nameshipper = "";
            if (billCustomer != null) {
                String fcustomer = "", lcustomer = "";
                if (billCustomer.getLastName() != null){
                    lcustomer = billCustomer.getLastName().trim();
                }
                if (billCustomer.getFirstName() != null){
                    fcustomer = billCustomer.getFirstName().trim();
                }
                namecustomer = lcustomer + " " + fcustomer;
            }
            if (billShipper != null) {
                String fshipper = "", lshipper = "";
                if (billShipper.getLastName() != null){
                    lshipper = billShipper.getLastName().trim();
                }
                if (billShipper.getFirstName() != null){
                    fshipper = billShipper.getFirstName().trim();
                }
                nameshipper = (lshipper + " " + fshipper).trim();
            }
            customer.setText(namecustomer);
            shipper.setText(nameshipper);
            description.setText(selectedBill.getDescription());
            created.setText(ConversionUtils.DateTime.formatDate(selectedBill.getCreatedDate()));
            delivery.setText(ConversionUtils.DateTime.formatDate(selectedBill.getDeliverTime()));
            switch (selectedBill.getStatus()) {
                case 'N':
                    statusSpinner.setText(R.string.N);
                    statusSpinner.setTextColor(Color.parseColor("#ff0000"));
                    break;
                case 'G':
                    statusSpinner.setText(R.string.G);
                    statusSpinner.setTextColor(Color.parseColor("#0000ff"));
                    break;
                case 'O':
                    statusSpinner.setText(R.string.O);
                    statusSpinner.setTextColor(Color.parseColor("#00ff00"));
                    break;
                case 'C':
                    statusSpinner.setText(R.string.C);
                    statusSpinner.setTextColor(Color.parseColor("#000000"));
                    break;
            }

            List<BillDetailDto> billDetailList = BillDetailDao.findById(selectedBill.getBillId());
            ListView listBillDetail = (ListView)view.findViewById(R.id.listBillDetail);
            BillManagementFragment.BillListAdapter billAdapter = new BillManagementFragment.BillListAdapter(getContext(), R.layout.bill_detail_list_item , billDetailList);
            listBillDetail.setAdapter(billAdapter);
        }
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billDialog.dismiss();
            }
        });
        billDialog.show();
    }
    public class CustomListAdapter extends ArrayAdapter<UserDto> {
        Context myContext;
        int myLayout;
        List<UserDto> myList;
        public CustomListAdapter(Context context, int layout, List<UserDto> list) {
            super(context, layout, list);
            myContext = context;
            myLayout = layout;
            myList = list;
        }
        @Override
        public int getCount() {
            if (myList!= null)
                return myList.size();
            else
                return 0;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        @NonNull
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public View getView(int i, View view, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(myLayout, null);
            TextView cusName = (TextView)view.findViewById(R.id.textView_Name_customer);
            TextView cusSpent = (TextView)view.findViewById(R.id.textView_customer_amount_item_list);
            ImageView cusImage = (ImageView)view.findViewById(R.id.customer_list_item_avatar);
            UserDto dto = myList.get(i);
            if (dto == null) {
                Toast.makeText(myContext,"User does not exist",Toast.LENGTH_SHORT).show();
                return view;
            }
            List<BillDto> listBill = BillDao.FindByCustomer(dto.getUserId());
            String name = (dto.getLastName() + ", " + dto.getFirstName()).trim();
            long amount = 0;
            for (BillDto bill : listBill) {
                amount += bill.getTotalPrice() + bill.getShipCharge();
            }

            String spentString = String.format(Locale.getDefault(),"%,d",amount) + " VNĐ";
            cusName.setText(name);
            cusSpent.setText(spentString);
            return view;
        }
    }
}