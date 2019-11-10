package com.hcmus.Activities.ui.ItemManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hcmus.Activities.ui.Category.CustomerCategory;
import com.hcmus.Activities.ui.ShoppingCartManagement.ShoppingCartManagement;
import com.hcmus.DAO.CategoryDao;
import com.hcmus.DAO.ItemDao;
import com.hcmus.DTO.BillDetailDto;
import com.hcmus.DTO.CategoryDto;
import com.hcmus.DTO.ItemDto;
import com.hcmus.shipe.R;
import com.hcmus.shipe.Register;

import java.util.ArrayList;
import java.util.List;

public class CustomerHome extends AppCompatActivity{
    TextView textCartItemCount;
    TextView txtvCate;
    TextView txtvHis;
    TextView title1;
    TextView title2;
    TextView title3;
    AnimatorSet set;
    ImageView imageView;
    ImageView icon;
    TextView caption;
    ImageView icon2;
    TextView caption2;
    ImageView icon3;
    TextView caption3;
    ViewGroup scrollViewGroup;
    ViewGroup scrollViewGroup2;
    ViewGroup scrollViewGroup3;
    ItemDto selectedItem;



    List<ItemDto> listItem;
    int [] imageResource={R.drawable.slide_show_1,R.drawable.slide_show_2,R.drawable.slide_show_3};
    int index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        txtvCate=(TextView)findViewById(R.id.txtvCategory);
        txtvHis=(TextView)findViewById(R.id.txtvHistory);
        imageView=(ImageView)findViewById(R.id.slideshow);
        scrollViewGroup=(ViewGroup)findViewById(R.id.viewgroup_1);
        scrollViewGroup2=(ViewGroup)findViewById(R.id.viewgroup_2);
        scrollViewGroup3=(ViewGroup)findViewById(R.id.viewgroup_3);
        title1=(TextView)findViewById(R.id.title1);
        title2=(TextView)findViewById(R.id.title2);
        title3=(TextView)findViewById(R.id.title3);

        title1.setText("TOP 10 TRENDING PRODUCT");
        title2.setText("TOP 10 BEST-SELLING PRODUCT ");
        title3.setText("TOP 10 BEST-SELLING PRODUCT 2019");

        set=(AnimatorSet) AnimatorInflater.loadAnimator(CustomerHome.this,R.animator.slideshow);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }
            public void onAnimationEnd(Animator animator){
                if(index < imageResource.length){
                    imageView.setImageResource(imageResource[index]);
                    index++;
                    set.start();
                }
                else{
                    index=0;
                    set.start();
                }
            }
        });
        set.start();

        txtvCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CustomerCategory.class));
            }
        });
        txtvHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Customer_History.class));
            }
        });
        listItem= ItemDao.findByCategory(2);
        for(int i=0;i<listItem.size();i++){
            final View singleFrame=getLayoutInflater().inflate(R.layout.customlayout_item_home,null);
            final View singleFrame2=getLayoutInflater().inflate(R.layout.customlayout_item_home,null);
            final View singleFrame3=getLayoutInflater().inflate(R.layout.customlayout_item_home,null);
            singleFrame.setId(i);
            caption=(TextView)singleFrame.findViewById(R.id.caption);
            icon=(ImageView)singleFrame.findViewById(R.id.icon_1);
            singleFrame2.setId(i);
            caption2=(TextView)singleFrame2.findViewById(R.id.caption);
            icon2=(ImageView)singleFrame2.findViewById(R.id.icon_1);
            caption3=(TextView)singleFrame3.findViewById(R.id.caption);
            icon3=(ImageView)singleFrame3.findViewById(R.id.icon_1);

            selectedItem=listItem.get(i);

            caption.setText(selectedItem.getName());
            caption2.setText(selectedItem.getName());
            caption3.setText(selectedItem.getName());

            scrollViewGroup.addView(singleFrame);
            scrollViewGroup2.addView(singleFrame2);
            scrollViewGroup3.addView(singleFrame3);
            singleFrame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedItem=listItem.get(singleFrame.getId());
                    dialogShoppingCart();
                }
            });
            singleFrame2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedItem=listItem.get(singleFrame2.getId());
                    dialogShoppingCart();
                }
            });
            singleFrame3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedItem=listItem.get(singleFrame3.getId());
                    dialogShoppingCart();
                }
            });
        }

    }

    private void dialogShoppingCart(){
        final Dialog dialog=new Dialog(CustomerHome.this);
        dialog.setContentView(R.layout.item_info_popup);
        TextView name=(TextView)dialog.findViewById(R.id.Name_item);
        TextView price=(TextView)dialog.findViewById(R.id.price_item);
        TextView des=(TextView)dialog.findViewById(R.id.description);
        ImageView closeBtn=(ImageView)dialog.findViewById(R.id.close);
        ImageView thumbnailItem=(ImageView)dialog.findViewById(R.id.thumbnail_item);
        Button addBtn=(Button)dialog.findViewById(R.id.button_add);

        name.setText("Product: "+selectedItem.getName());
        price.setText("Price: "+Long.toString(selectedItem.getPrice())+" VND");
        des.setText("Description: "+selectedItem.getDescription());

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //add item
                BillDetailDto bill = new BillDetailDto();
                bill.setItemId(selectedItem.getId());
                bill.setAmount((int)selectedItem.getPrice());
                CartDomain.ListItemInCart.add(bill);
                setupBadge();
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        setupBadge();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar_customer,menu);
        final MenuItem menuItem = menu.findItem(R.id.action_cart);
        View actionView = menuItem.getActionView();
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);
        setupBadge();
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        return true;
    }
    @Override
    public void onBackPressed() {
        //Login.Logout(getApplicationContext());
        //finishAndRemoveTask();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_search){
            return true;
        }else if(id==R.id.action_cart){
            Intent intent = new Intent(CustomerHome.this, ShoppingCartManagement.class);
            startActivity(intent);
            return true;
        }
        return false;
    }
    private void setupBadge() {
        if (textCartItemCount != null) {
            int mCartItemCount = CartDomain.ListItemInCart.size();
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
