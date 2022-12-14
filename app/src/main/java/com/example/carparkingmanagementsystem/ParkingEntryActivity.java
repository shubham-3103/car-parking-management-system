package com.example.carparkingmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParkingEntryActivity extends AppCompatActivity {
    AutoCompleteTextView autoCompleteTextView,parkingLocation;
    EditText etName,etContact,etVehicle;
    // creating a variable for our
    // Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    // creating a variable for
    // our object class
    VehicleInfo vehicleInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_entry);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        parkingLocation=findViewById(R.id.Parking_location);
        etName=findViewById(R.id.et_ParkingName);
        etContact=findViewById(R.id.et_ParkingContact);
        etVehicle=findViewById(R.id.et_ParkingVehicle);
        //We will use this data to inflate the drop-down items
        String[] Subjects = new String[]{"2 Wheeler", "3 Wheeler", "4 wheeler","Heavy Vehicle"};
        String[] Subjects1=new String[]{"A 1","A 2","A 3","A 4","B 1","B 2","B 3","B 4"};
        // create an array adapter and pass the required parameter
        // in our case pass the context, drop down layout , and array.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_item, Subjects);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.dropdown_item, Subjects1);
        autoCompleteTextView.setAdapter(adapter);
        parkingLocation.setAdapter(adapter1);

        //to get selected value add item click listener
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "" + autoCompleteTextView.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        parkingLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "" + parkingLocation.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("VehicleInfo");

        // initializing our object
        // class variable.
        vehicleInfo = new VehicleInfo();
        Button buttonSubmit = findViewById(R.id.id_submit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textName=etName.getText().toString();
                String textContact=etContact.getText().toString();
                String textVehicle=etVehicle.getText().toString();
                String textVehicleType=autoCompleteTextView.getText().toString();
                String textParkingSlot=parkingLocation.getText().toString();
                //Validate Mobile Number
                String mobileRegex="[6-9][0-9]{9}";
                Matcher mobileMatcher;
                Pattern mobilePattern = Pattern.compile(mobileRegex);
                mobileMatcher = mobilePattern.matcher(textContact);
                if (TextUtils.isEmpty(textName)){
                    Toast.makeText(ParkingEntryActivity.this,"Please Enter you full name",Toast.LENGTH_LONG).show();
                    etName.setError("Full Name is Required");
                    etName.requestFocus();
                }
                else if(TextUtils.isEmpty(textContact)){
                Toast.makeText(ParkingEntryActivity.this,"Please Enter contact details",Toast.LENGTH_SHORT).show();
                etContact.setError("Contact number is required");
                etContact.requestFocus();
                }else if (textContact.length()!=10){
                    Toast.makeText(ParkingEntryActivity.this,"Please Re-Enter your Mobile Number",Toast.LENGTH_LONG).show();
                    etContact.setError("Valid Phone No. is Required");
                    etContact.requestFocus();
                } else if(!mobileMatcher.find()){
                    Toast.makeText(ParkingEntryActivity.this,"Please Re-Enter your Mobile Number",Toast.LENGTH_LONG).show();
                    etContact.setError("Valid Phone No. is Required");
                    etContact.requestFocus();
                } else if(TextUtils.isEmpty(textVehicle)){
                    Toast.makeText(ParkingEntryActivity.this,"Please Enter your vehicle details",Toast.LENGTH_SHORT).show();
                    etVehicle.setError("Vehicle number is required");
                    etVehicle.requestFocus();
                }
                else if(TextUtils.isEmpty(textVehicleType)){
                    Toast.makeText(ParkingEntryActivity.this,"Please Enter type of your vehicle",Toast.LENGTH_SHORT).show();
                    autoCompleteTextView.setError("choose a vehicle type");
                    autoCompleteTextView.requestFocus();
                }
                else if(TextUtils.isEmpty(textParkingSlot)){
                    Toast.makeText(ParkingEntryActivity.this,"Please select parking slot",Toast.LENGTH_SHORT).show();
                    parkingLocation.setError("choose a parking slot");
                    parkingLocation.requestFocus();
                }
                else{
                    registerUser(textName,textContact,textVehicle,textVehicleType,textParkingSlot);
                }

            }

            private void registerUser(String textName, String textContact, String textVehicle, String textVehicleType, String textParkingSlot) {
//                vehicleInfo.setName(textName);
//                vehicleInfo.setContactNumber(textContact);
//                vehicleInfo.setVehicleNumber(textVehicle);
//                vehicleInfo.setVehicleType(textVehicleType);
//                vehicleInfo.setParkingslot(textParkingSlot);

//                databaseReference.child("VehicleInfo").child(textContact).child("fullname").setValue(textName);
//                databaseReference.child("VehicleInfo").child(textContact).child("vehicleDetails").setValue(textVehicle);
//                databaseReference.child("VehicleInfo").child(textContact).child("vehicleType").setValue(textVehicleType);
//                databaseReference.child("VehicleInfo").child(textContact).child("parkingSlot").setValue(textParkingSlot);
//                // we are use add value event listener method
//                // which is called with database reference.
//                databaseReference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        // inside the method of on Data change we are setting
//                        // our object class to our database reference.
//                        // data base reference will sends data to firebase.
//                        databaseReference.setValue(vehicleInfo);
//
//                        // after adding this data we are showing toast message.
//                        Toast.makeText(ParkingEntryActivity.this, "data added", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        // if the data is not added or it is cancelled then
//                        // we are displaying a failure toast message.
//                        Toast.makeText(ParkingEntryActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
//                    }
//                });
//                FirebaseAuth auth = FirebaseAuth.getInstance();
//                FirebaseUser firebaseUser = auth.getCurrentUser();

                FirebaseAuth auth = FirebaseAuth.getInstance();
                VehicleInfo vehicleInfo = new VehicleInfo(textName,textContact,textVehicle,textVehicleType,textParkingSlot);
                DatabaseReference vehicleProfile = FirebaseDatabase.getInstance().getReference("VehicleInfo");
                vehicleProfile.child(textVehicle).setValue(vehicleInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ParkingEntryActivity.this,"Data added", Toast.LENGTH_LONG).show();

                        etName.setText("");
                        etContact.setText("");
                        etVehicle.setText("");
                    }
                });
            }
        });
    }
}