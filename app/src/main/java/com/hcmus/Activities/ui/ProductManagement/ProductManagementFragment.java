
package com.hcmus.Activities.ui.ProductManagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hcmus.DAO.CategoryDao;
import com.hcmus.DAO.ItemDao;
import com.hcmus.DTO.CategoryDto;
import com.hcmus.DTO.ItemDto;
import com.hcmus.shipe.InsertItems;
import com.hcmus.shipe.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Long.valueOf;

public class ProductManagementFragment extends Fragment {
    private List<String>categoryName=new ArrayList<>();

    private ListView categoryListview;
    private CategoryDto selectedCategory;
    private String selectedCategoryName;
    Button btnAddItem;
    Button btnAddCategory;

    private ProductManagementViewModel productManagementViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //  customerManagementViewModel =
        //           ViewModelProviders.of(this).get(CustomerManagementViewModel.class);

        View root = inflater.inflate(R.layout.fragment_product_management, container, false);



        categoryListview = root.findViewById(R.id.list_category);
        btnAddItem=root.findViewById(R.id.btnAddItem);
        btnAddCategory=root.findViewById(R.id.btnAddCategory);

        categoryName=CategoryDao.ListCategoryName();

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, categoryName);
        categoryListview.setAdapter(adapter);
        categoryListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCategoryName = categoryName.get(position);
                selectedCategory=CategoryDao.findByName(selectedCategoryName);
                showListCategoryItem();
            }
        });

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),InsertItems.class);
                startActivity(intent);
            }
        });
        return root;
    }




    private ItemDto selectedItem;
    private ArrayList<ItemDto> listCategoryItem=new ArrayList<>();
    ListView categoryListItem;
    public void showListCategoryItem() {
        if (selectedCategory == null)
            return;

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.list_item, null);

        categoryListItem=alertLayout.findViewById(R.id.category_list_item);
        listCategoryItem=ItemDao.ArrayListfindByCategory(selectedCategory.getCategoryId());

        CategoryListItem.CustomListAdapter listCategoryItemAdapter=new CategoryListItem.CustomListAdapter(getContext(),R.layout.category_list_item,listCategoryItem);
        categoryListItem.setAdapter(listCategoryItemAdapter);


        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("List "+selectedCategory.getName());
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        categoryListItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = listCategoryItem.get(position);
                ShowItemInfo();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
    private void ShowItemInfo() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.item_info, null);

        final EditText nameItem = alertLayout.findViewById(R.id.edtNameItem);
        final EditText priceItem = alertLayout.findViewById(R.id.edtPriceItem);
        final EditText descriptionItem = alertLayout.findViewById(R.id.edtDescriptionItem);
        final Spinner statusItem = alertLayout.findViewById(R.id.spnStatusItem);
        final Spinner categoryItem = alertLayout.findViewById(R.id.spnCategoryItem);
        final EditText thumbnailItem=alertLayout.findViewById(R.id.edtThumbnailItem);
        ImageView imgItem = alertLayout.findViewById(R.id.imgItem);



        if (selectedItem != null) {
            nameItem.setText(selectedItem.getName());
            priceItem.setText(Long.toString(valueOf(selectedItem.getPrice())));
            descriptionItem.setText(selectedItem.getDescription());
            thumbnailItem.setText(selectedItem.getThumbnail());

            char itemStatus = selectedItem.getStatus();
            int selectedStatus = 1;
            if (itemStatus == 'N')
                selectedStatus = 1;
            if (itemStatus == 'S')
                selectedStatus = 2;
            if (itemStatus == 'X')
                selectedStatus = 3;

            //Status spinner
            List<String> listStatus = new ArrayList<>();
            listStatus.add("New");
            listStatus.add("Sold out");
            listStatus.add("Stop selling");

            ArrayAdapter<String> adapterStatus = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listStatus);
            adapterStatus.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

            statusItem.setAdapter(adapterStatus);
            statusItem.setSelection(selectedStatus - 1);

            statusItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            //Lay category ra tu database
            int currentCategory = selectedItem.getCategory();

            List<String> listCategory;

            listCategory = CategoryDao.SelectAllName();

            ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listCategory);

            adapterCategory.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

            categoryItem.setAdapter(adapterCategory);
            categoryItem.setSelection(currentCategory - 1);
            categoryItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            //con hinh anh chua biet lam sao
            loadImageFromUrl(selectedItem.getThumbnail(),imgItem);


        }

        androidx.appcompat.app.AlertDialog.Builder alert = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        alert.setTitle("Item: " + selectedItem.getName());
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = nameItem.getText().toString();
                String description = descriptionItem.getText().toString();
                // String thumbnail=thumbnailItem.getText().toString();
                String priceString = priceItem.getText().toString();
                long price = Long.parseLong(priceString);
                //Category
                String categoryName = categoryItem.getSelectedItem().toString();
                int category = CategoryDao.findIDByName(categoryName);
                //status
                char status = 'N';
                String statusName = statusItem.getSelectedItem().toString();
                if (statusName == "New")
                    status = 'N';
                if (statusName == "Sold out")
                    status = 'S';
                if (statusName == "Stop selling")
                    status = 'X';


                //Lay ngay hien tai la ngay update
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String currentDateTime = simpleDateFormat.format(new Date());


                //Tao ItemDto
                ItemDto item = new ItemDto();
                item.setId(selectedItem.getId());
                item.setName(name);
                item.setDescription(description);
                //item.setThumbnail(thumbnail);
                item.setPrice(price);
                item.setCategory(category);
                item.setStatus(status);
                item.setUpdatedDate(currentDateTime);

                //Dua ItemDto vao database
                ItemDao.UpdateItem(item);
                Toast.makeText(getContext(), "Update item successfully", Toast.LENGTH_SHORT).show();
            }
        });
        androidx.appcompat.app.AlertDialog dialog = alert.create();
        dialog.show();
    }
    public void loadImageFromUrl(String url,ImageView img)
    {
        Picasso.with(getContext()).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(img,new com.squareup.picasso.Callback(){
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });

    }
}