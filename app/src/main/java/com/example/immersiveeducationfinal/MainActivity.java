package com.example.immersiveeducationfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.immersiveeducationfinal.Adapter.CategoryAdapter;
import com.example.immersiveeducationfinal.Common.Common;
import com.example.immersiveeducationfinal.Common.SpaceDecoration;
import com.example.immersiveeducationfinal.DBHelper.DBHelper;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recycler_category;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.category_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_settings)
        {
            showSettings();
        }
        return true;
    }

    private void showSettings() {
        View setting_layout = LayoutInflater.from(this)
                .inflate(R.layout.settings_layout,null);
        final Button btnLogout =  (Button)setting_layout.findViewById(R.id.logout);

        new MaterialStyledDialog.Builder(MainActivity.this)
                .setIcon(R.drawable.ic_baseline_settings_24)
                .setTitle("Settings")
                .setDescription("Please choose action")
                .setCustomView(setting_layout)
                .setNegativeText("DISMISS")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveText("Logout")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intToMain = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intToMain);
                    }
                }).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Paper.init(this);

        Common.isOnlineMode = Paper.book().read(Common.KEY_SAVE_ONLINE_MODE,false); // Default false

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Immersive Education");
        setSupportActionBar(toolbar);

        recycler_category = (RecyclerView)findViewById(R.id.recycler_category);
        recycler_category.setHasFixedSize(true);
        recycler_category.setLayoutManager(new GridLayoutManager(this,2));


        CategoryAdapter adapter = new CategoryAdapter(MainActivity.this,DBHelper.getInstance(this).getAllCategories());
        int spaceInPixel = 4;
        recycler_category.addItemDecoration(new SpaceDecoration(spaceInPixel));
        recycler_category.setAdapter(adapter);


    }
}