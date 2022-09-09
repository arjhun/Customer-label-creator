package com.arjenklaverstijn.android.customerlabelcreator;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.arjenklaverstijn.android.customerlabelcreator.models.ProgressModel;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.arjenklaverstijn.android.customerlabelcreator.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


        final ProgressModel progressModel = new ViewModelProvider(this).get(ProgressModel.class);

        binding.contentMain.seekBar2.setOnTouchListener((v, event) -> true);

        MutableLiveData<Integer> progressData = progressModel.getCurrentProgress();

        if(progressData.getValue() != null) {
            binding.contentMain.seekBar2.setProgress(progressData.getValue());
        }

         progressModel.getCurrentProgress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer newProgress) {
                if(newProgress != null)
                    binding.contentMain.seekBar2.setProgress(newProgress);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here;. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}