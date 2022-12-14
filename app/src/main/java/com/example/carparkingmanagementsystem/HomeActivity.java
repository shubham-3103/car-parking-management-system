package com.example.carparkingmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth authProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("Home");
        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        ImageButton addVehicle = (ImageButton)findViewById(R.id.new_entry_button);
        addVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this,"New Entry",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(HomeActivity.this,ParkingEntryActivity.class);
                startActivity(intent);
            }
        });

        ImageButton currentVehicles = (ImageButton)findViewById(R.id.current_entry_button);
        currentVehicles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this,"Current Entries",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(HomeActivity.this,ShowParkedVehicle.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.menu_refresh){
            //Refresh
            startActivity(getIntent());
            finish();
            overridePendingTransition(0,0);
        } else if (id==R.id.menu_profile){
            Intent intent = new Intent(HomeActivity.this,UserProfileActivity.class);
            startActivity(intent);
        } else if (id==R.id.menu_home){
            startActivity(getIntent());
            finish();
            overridePendingTransition(0,0);
        } else if(id==R.id.menu_logout){
            authProfile.signOut();
            Toast.makeText(HomeActivity.this,"Logged Out",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(HomeActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else{
            Toast.makeText(HomeActivity.this,"Something Went Wrong!",Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
}