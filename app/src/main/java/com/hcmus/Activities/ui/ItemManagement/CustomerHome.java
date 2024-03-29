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
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hcmus.Activities.ui.Category.CustomerCategory;
import com.hcmus.Activities.ui.ShoppingCartManagement.ShoppingCartManagement;
import com.hcmus.DAO.BillDao;
import com.hcmus.DAO.CategoryDao;
import com.hcmus.DAO.ItemDao;
import com.hcmus.DTO.BillDetailDto;
import com.hcmus.DTO.CategoryDto;
import com.hcmus.DTO.ItemDto;
import com.hcmus.Models.Task;
import com.hcmus.Utils.MyCallback;
import com.hcmus.shipe.R;
import com.hcmus.shipe.Register;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.zip.Inflater;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CustomerHome extends AppCompatActivity{
    TextView textCartItemCount;
    TextView txtvCate;
    TextView txtvHis;
    TextView txtvAcc;
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
    ItemDto selectedItem2;
    ItemDto selectedItem3;
    List<ItemDto> listItem;
    List<ItemDto> listItem2;
    List<ItemDto> listItem3;
    private LayoutInflater inflater;
    private Context context;
    ProgressBar progress;

    int [] imageResource={R.drawable.slide_show_1,R.drawable.slide_show_2,R.drawable.slide_show_3};
    int index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        createListItem();

        context=getApplicationContext();
        inflater=LayoutInflater.from(context);

        txtvCate = (TextView) findViewById(R.id.txtvCategory);
        txtvHis = (TextView) findViewById(R.id.txtvHistory);
        txtvAcc = (TextView) findViewById(R.id.txtvAccount);
        imageView = (ImageView) findViewById(R.id.slideshow);
        scrollViewGroup = (ViewGroup) findViewById(R.id.viewgroup_1);
        scrollViewGroup2 = (ViewGroup) findViewById(R.id.viewgroup_2);
        scrollViewGroup3 = (ViewGroup) findViewById(R.id.viewgroup_3);
        title1 = (TextView) findViewById(R.id.title1);
        title2 = (TextView) findViewById(R.id.title2);
        title3 = (TextView) findViewById(R.id.title3);
        progress=(ProgressBar)findViewById(R.id.load_progress);

        title1.setText("TOP 6 TRENDING PRODUCT");
        title2.setText("TOP 6 BEST-SELLING PRODUCT ");
        title3.setText("TOP 6 BEST-SELLING PRODUCT 2019");

        set = (AnimatorSet) AnimatorInflater.loadAnimator(CustomerHome.this, R.animator.slideshow);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            public void onAnimationEnd(Animator animator) {
                if (index < imageResource.length) {
                    imageView.setImageResource(imageResource[index]);
                    index++;
                    set.start();
                } else {
                    index = 0;
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
        txtvAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CustomerInfo.class));
            }
        });
}

    public void createListItem(){
        showProgress();
        createListItemObservable()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(createListItemObserver());
    }
    private Observable<Boolean> createListItemObservable(){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try {
                    listItem = ItemDao.findByCategory(1);
                    listItem2 = ItemDao.findByCategory(2);
                    listItem3 = ItemDao.findByCategory(3);
                } catch (Exception e){
                    Log.e("Task Create", "Error");
                    e.printStackTrace();
                }
                return true;
            }
        });
    }
    private Observer<Boolean> createListItemObserver(){
        return new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(final Boolean checks) {

                for (int i = 0; i < listItem.size(); i++) {
                    final View singleFrame = inflater.inflate(R.layout.customlayout_item_home, null);
                    singleFrame.setId(i);
                    caption = (TextView) singleFrame.findViewById(R.id.caption);
                    icon = (ImageView) singleFrame.findViewById(R.id.icon_1);

                    selectedItem = listItem.get(i);

                    if (selectedItem.getName().length() > 20) {
                        caption.setText(selectedItem.getName().substring(0, 20) + "...");
                    } else {
                        caption.setText(selectedItem.getName().substring(0, selectedItem.getName().length()) + "...");
                    }

                    String res = selectedItem.getThumbnail();
                    Bitmap_Image bmp=new Bitmap_Image(res);
                    icon.setImageBitmap(bmp.bmp);

                    scrollViewGroup.addView(singleFrame);
                    singleFrame.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selectedItem = listItem.get(singleFrame.getId());
                            dialogShoppingCart();
                        }
                    });
                }
                for (int i = 0; i < listItem2.size(); i++) {
                    final View singleFrame2 =inflater.inflate(R.layout.customlayout_item_home, null);
                    singleFrame2.setId(i);
                    caption2 = (TextView) singleFrame2.findViewById(R.id.caption);
                    icon2 = (ImageView) singleFrame2.findViewById(R.id.icon_1);
                    selectedItem2 = listItem2.get(i);

                    if (selectedItem2.getName().length() > 20) {
                        caption2.setText(selectedItem2.getName().substring(0, 20) + "...");
                    } else {
                        caption2.setText(selectedItem2.getName().substring(0, selectedItem2.getName().length()) + "...");
                    }
                    String res2 = selectedItem2.getThumbnail();
                    Bitmap_Image bmp2=new Bitmap_Image(res2);
                    icon2.setImageBitmap(bmp2.bmp);
                    scrollViewGroup2.addView(singleFrame2);
                    singleFrame2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selectedItem2 = listItem2.get(singleFrame2.getId());
                            selectedItem=selectedItem2;
                            dialogShoppingCart();

                        }
                    });
                }
                for (int i = 0; i < listItem3.size(); i++) {
                    final View singleFrame3 =inflater.inflate(R.layout.customlayout_item_home, null);
                    singleFrame3.setId(i);
                    caption3 = (TextView) singleFrame3.findViewById(R.id.caption);
                    icon3 = (ImageView) singleFrame3.findViewById(R.id.icon_1);
                    selectedItem3 = listItem3.get(i);

                    if (selectedItem3.getName().length() > 20) {
                        caption3.setText(selectedItem3.getName().substring(0, 20) + "...");
                    } else {
                        caption3.setText(selectedItem3.getName().substring(0, selectedItem3.getName().length()) + "...");
                    }
                    String res3 = selectedItem3.getThumbnail();
                    Bitmap_Image bmp3=new Bitmap_Image(res3);
                    icon3.setImageBitmap(bmp3.bmp);
                    scrollViewGroup3.addView(singleFrame3);
                    singleFrame3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selectedItem3 = listItem3.get(singleFrame3.getId());
                            selectedItem=selectedItem3;
                            dialogShoppingCart();
                        }
                    });
                }
                dismissProgress();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
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

        String res=selectedItem.getThumbnail();
        Bitmap_Image bmp4=new Bitmap_Image(res);
        thumbnailItem.setImageBitmap(bmp4.bmp);

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

    private void showProgress(){
        if (progress != null)
            progress.setVisibility(View.VISIBLE);
    }

    private void dismissProgress(){
        if (progress != null)
            progress.setVisibility(View.GONE);
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
            finish();
            startActivity(new Intent(getApplicationContext(),ShoppingCartManagement.class));
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
