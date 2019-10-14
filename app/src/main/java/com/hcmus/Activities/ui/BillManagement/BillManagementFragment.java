package com.hcmus.Activities.ui.BillManagement;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.hcmus.DAO.BillDao;
import com.hcmus.DAO.BillDetailDao;
import com.hcmus.DAO.ItemDao;
import com.hcmus.DAO.UserDao;
import com.hcmus.DTO.BillDetailDto;
import com.hcmus.DTO.BillDto;
import com.hcmus.DTO.ItemDto;
import com.hcmus.DTO.UserDto;
import com.hcmus.shipe.R;

import java.util.List;

public class BillManagementFragment extends Fragment {

    private List<BillDto> listBill;
    private ListView billListView;

    CustomListAdapter adapter;
    Dialog myDialog;
    List<BillDetailDto> billDetailList;
    private ListView listBillDetail;
    BillListAdapter billAdapter;
    private BillDto selectedBill;
    private int selectedType = 0;
    private boolean recordChanged = false;
    private final String[] BILL_TYPE = {"New", "Getting", "On-Going", "Completed", "All"};
    private final String[] BILL_ALL_STATUS = {"New", "Getting", "On-Going", "Completed"};
    private final String[] BILL_STATUS = {"Active", "Inactive"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bill_management, container, false);
        Spinner billTypeSpinner = root.findViewById(R.id.bill_type_spinner);
        billTypeSpinner.setAdapter(new ArrayAdapter<String>(root.getContext(), R.layout.support_simple_spinner_dropdown_item, BILL_TYPE));
        billTypeSpinner.setSelection(4);
        billListView = root.findViewById(R.id.list_bill_view);
        billTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        billListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedBill = listBill.get(position);
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
                listBill = BillDao.GetNew();
                SetListViewBill();
                break;
            case 1:
                listBill = BillDao.GetGetting();
                SetListViewBill();
                break;
            case 2:
                listBill = BillDao.GetOnGoing();
                SetListViewBill();
                break;
            case 3:
                listBill = BillDao.GetCompleted();
                SetListViewBill();
                break;
            default:
                listBill = BillDao.SelectAll();
                SetListViewBill();
                break;
        }
    }

    private void ShowPopup(){
        Dialog view = myDialog;
        view.setCancelable(false);
        view.setContentView(R.layout.bill_info_popup);
        TextView customer = (TextView)view.findViewById(R.id.bill_info_customer);
        TextView shipper= (TextView)view.findViewById(R.id.bill_info_shipper);
        TextView description = (TextView)view.findViewById(R.id.bill_info_description);
        TextView created = (TextView)view.findViewById(R.id.bill_info_created);
        TextView delivery = (TextView)view.findViewById(R.id.bill_info_delivery);
        Spinner statusSpinner = (Spinner)view.findViewById(R.id.bill_info_status_spinner);
        ImageView closeBtn = (ImageView)view.findViewById(R.id.bill_info_close);
        if (selectedBill!=null) {
            statusSpinner.setAdapter(new ArrayAdapter<String>(view.getContext(),R.layout.support_simple_spinner_dropdown_item, BILL_ALL_STATUS));
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
                nameshipper = lshipper + " " + fshipper;
            }
            customer.setText(namecustomer);
            shipper.setText(nameshipper);
            description.setText(selectedBill.getDescription());
            created.setText(selectedBill.getCreatedDate());
            delivery.setText(selectedBill.getDeliverTime());

            switch (selectedBill.getStatus()) {
                case 'N':
                    statusSpinner.setSelection(0);
                    break;
                case 'G':
                    statusSpinner.setSelection(1);
                    break;
                case 'O':
                    statusSpinner.setSelection(2);
                    break;
                case 'C':
                    statusSpinner.setSelection(3);
                    break;
            }

            billDetailList = BillDetailDao.findById(selectedBill.getBillId());
            listBillDetail = (ListView)view.findViewById(R.id.listBillDetail);
            billAdapter = new BillListAdapter(getContext(), R.layout.bill_detail_list_item , billDetailList);
            listBillDetail.setAdapter(billAdapter);
        }
        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (selectedBill==null)
                    return;
                switch (i) {
                    case 0:
                        selectedBill.setStatus('N');
                        break;
                    case 1:
                        selectedBill.setStatus('G');
                        break;
                    case 2:
                        selectedBill.setStatus('O');
                        break;
                    case 3:
                        selectedBill.setStatus('C');
                        break;
                }
                BillDao.Update(selectedBill);
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
    private void SetListViewBill() {
        adapter = new CustomListAdapter(getContext(), R.layout.bill_list_item, listBill);
        billListView.setAdapter(adapter);
    }

    public class CustomListAdapter extends ArrayAdapter<BillDto> {
        Context myContext;
        int myLayout;
        List<BillDto> myList;

        public CustomListAdapter(Context context, int layout, List<BillDto> list) {
            super(context, layout, list);
            myContext = context;
            myLayout = layout;
            myList = list;
        }

        @Override
        public int getCount() {
            if (listBill != null)
                return listBill.size();
            else
                return 0;
        }

        /*@Override
        public Object getItem(int position) {
            return listBill.get(position);
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
            view = inflater.inflate(R.layout.bill_list_item, null);
            //RatingBar ratingBar = (RatingBar)view.findViewById(R.id.ratingBar_Bill);
            TextView customerName = (TextView)view.findViewById(R.id.textView_Customer_Bill);
            TextView shipperName = (TextView)view.findViewById(R.id.textView_Shipper_Bill);
            TextView billPrice = (TextView)view.findViewById(R.id.textView_Price_Bill);
            TextView billStatus = (TextView)view.findViewById(R.id.textView_Status_Bill);
            BillDto billDto = myList.get(i);
            UserDto billCustomer = UserDao.findById(billDto.getCustomerId());
            UserDto billShipper = UserDao.findById(billDto.getShipperId());
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
                nameshipper = lshipper + " " + fshipper;
            }



            customerName.setText(namecustomer);
            shipperName.setText(nameshipper);
            Long totalPrice =  billDto.getTotalPrice();
            String price = totalPrice.toString() + "VNĐ";
            billPrice.setText(price);
            switch (billDto.getStatus()) {
                case 'N':
                    billStatus.setText(R.string.N);
                    billStatus.setTextColor(Color.parseColor("#ff0000"));
                    break;
                case 'G':
                    billStatus.setText(R.string.G);
                    billStatus.setTextColor(Color.parseColor("#0000ff"));
                    break;
                case 'O':
                    billStatus.setText(R.string.O);
                    billStatus.setTextColor(Color.parseColor("#00ff00"));
                    break;
                case 'C':
                    billStatus.setText(R.string.C);
                    billStatus.setTextColor(Color.parseColor("#000000"));
                    break;
            }
            return view;
        }
    }
    public class BillListAdapter extends ArrayAdapter<BillDetailDto> {
        Context myContext;
        int myLayout;
        List<BillDetailDto> myList;

        public BillListAdapter(Context context, int layout, List<BillDetailDto> list) {
            super(context, layout, list);
            myContext = context;
            myLayout = layout;
            myList = list;
        }

        @Override
        public int getCount() {
            if (billDetailList != null)
                return billDetailList.size();
            else
                return 0;
        }

        /*@Override
        public Object getItem(int position) {
            return listBill.get(position);
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
            view = inflater.inflate(R.layout.bill_detail_list_item, null);
            TextView itemName = (TextView)view.findViewById(R.id.textView_Name_Item);
            TextView itemPrice = (TextView)view.findViewById(R.id.textView_Price_Item);
            TextView amountItem = (TextView)view.findViewById(R.id.textView_Amount);
            TextView total_price = (TextView)view.findViewById(R.id.textView_Total);
            BillDetailDto billDetail = myList.get(i);
            ItemDto item = ItemDao.findById(billDetail.getItemId());
            String name = item.getName().trim();
            Long price = item.getPrice();
            int amount = billDetail.getAmount();
            Long total = price * amount;

            itemName.setText(name);
            itemPrice.setText(price.toString());
            amountItem.setText(String.valueOf(amount));
            total_price.setText(total.toString());
            return view;
        }
    }
}