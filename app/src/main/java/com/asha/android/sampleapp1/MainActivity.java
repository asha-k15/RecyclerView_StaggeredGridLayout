package com.asha.android.sampleapp1;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.asha.android.sampleapp1.data.DogBreed;
import com.asha.android.sampleapp1.utils.DividerItemDecoration;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;


import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements DogBreedAdapter.Callback {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    DogBreedAdapter mDogBreedAdapter;
    ProgressDialog progress;
    StaggeredGridLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUp();
    }

    private void setUp() {
        mLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider_drawable);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        mRecyclerView.setItemAnimator(itemAnimator);
        mDogBreedAdapter = new DogBreedAdapter(new ArrayList<>());

        prepareDemoContent();
    }

    private void prepareDemoContent() {
        progress=new ProgressDialog(this);
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
        new Handler().postDelayed(() -> {
            //prepare data and show loading
            //CommonUtils.hideLoading();
            progress.hide();
            ArrayList<DogBreed> mDogBreed = new ArrayList<DogBreed>();
            String[] dogsList = getResources().getStringArray(R.array.dogs_titles);
            String[] dogsInfo = getResources().getStringArray(R.array.dogs_info);
            String[] dogsImage = getResources().getStringArray(R.array.dogs_images);
            for (int i = 0; i < dogsList.length; i++) {
                mDogBreed.add(new DogBreed(dogsImage[i], dogsInfo[i], "News", dogsList[i]));
            }
            mDogBreedAdapter.addItems(mDogBreed);
            mRecyclerView.setAdapter(mDogBreedAdapter);
        }, 2000);


    }

    @Override
    public void onEmptyViewRetryClick() {
        prepareDemoContent();
    }
}