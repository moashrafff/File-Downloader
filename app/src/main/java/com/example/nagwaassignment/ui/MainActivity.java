package com.example.nagwaassignment.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.nagwaassignment.Data.FileInterface;
import com.example.nagwaassignment.Di.AppComponent;
import com.example.nagwaassignment.Di.AppModule;
import com.example.nagwaassignment.Di.DaggerAppComponent;
import com.example.nagwaassignment.Fragments.DownloadedFiles;
import com.example.nagwaassignment.Fragments.FilesFragment;
import com.example.nagwaassignment.Adapters.PagerAdapter;
import com.example.nagwaassignment.RecyclerViewOnClickInterface;
import com.example.nagwaassignment.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    FileInterface fileInterface ;
    ActivityMainBinding binding;
    private static final int permissionReqCode = 5 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},permissionReqCode);
        }

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(FilesFragment.newInstance("Files"));
        fragments.add(DownloadedFiles.newInstance("Downloaded Files"));

        PagerAdapter pagerAdapter = new PagerAdapter(this,fragments);
        binding.viewpager.setAdapter(pagerAdapter);

        new TabLayoutMediator(binding.tabLayout,
                binding.viewpager
                , new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0){
                    tab.setText("Files");
                }else {
                    tab.setText("Downloaded Files");
                }
            }
        }).attach();


        AppComponent build = DaggerAppComponent.builder().appModule(new AppModule()).build();
        build.inject(this);
        fileInterface.getFiles();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case permissionReqCode:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                }
                else {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},permissionReqCode);
                }
        }

    }


}