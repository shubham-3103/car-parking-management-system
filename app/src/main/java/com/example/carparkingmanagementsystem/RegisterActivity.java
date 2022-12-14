package com.example.carparkingmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {

    private EditText editTextRegisterFullName, editTextRegisterEmail,editTextRegisterDoB, editTextRegisterMobile,editTextRegisterPwd, editTextRegisterConfirmPwd;
    private ProgressBar progressBar;
    private RadioGroup radioGroupRegisterGender;
    private RadioButton radioButtonRegisterGenderSelected;
    private DatePickerDialog picker;
    private static final String TAG = "RegisterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        getSupportActionBar().setTitle("Register");
        Toast.makeText(RegisterActivity.this,"You can Register now", Toast.LENGTH_LONG).show();
        progressBar = findViewById(R.id.progressBar);
        editTextRegisterFullName = findViewById(R.id.editText_register_full_name);
        editTextRegisterEmail = findViewById(R.id.editText_register_email);
        editTextRegisterMobile = findViewById(R.id.editText_register_mobile);
        editTextRegisterDoB = findViewById(R.id.editText_register_dob);
        editTextRegisterPwd = findViewById(R.id.editText_register_password);
//        editTextRegisterConfirmPwd = findViewById(R.id.editText_register_confirm_password);

        //Radio Button for Gender
        radioGroupRegisterGender = findViewById(R.id.radio_group_register_gender);
        radioGroupRegisterGender.clearCheck();
        //Setting up DatePicker
        editTextRegisterDoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                //Date Picker
                picker = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {
                        editTextRegisterDoB.setText(dayofMonth+"/"+(month+1)+"/"+year);
                    }
                },year,month,day);
                picker.show();
            }
        });
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        Button buttonRegister = findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedGenderId = radioGroupRegisterGender.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelected = findViewById(selectedGenderId);
                //obtain data
                String textFullName = editTextRegisterFullName.getText().toString();
                String textEmail = editTextRegisterEmail.getText().toString();
                String textDob = editTextRegisterDoB.getText().toString();
                String textMobile = editTextRegisterMobile.getText().toString();
                String textPwd = editTextRegisterPwd.getText().toString();
                String textGender; //Can't obtain the value before verifying if any button was selected or not

                //Validate Mobile Number
                String mobileRegex="[6-9][0-9]{9}";
                Matcher mobileMatcher;
                Pattern mobilePattern = Pattern.compile(mobileRegex);
                mobileMatcher = mobilePattern.matcher(textMobile);

                if (TextUtils.isEmpty(textFullName)){
                    Toast.makeText(RegisterActivity.this,"Please Enter you full name",Toast.LENGTH_LONG).show();
                    editTextRegisterFullName.setError("Full Name is Required");
                    editTextRegisterFullName.requestFocus();
                } else if(TextUtils.isEmpty(textEmail)){
                    Toast.makeText(RegisterActivity.this,"Please Enter your email",Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Email is Required");
                    editTextRegisterEmail.requestFocus();
                } else if(!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                    Toast.makeText(RegisterActivity.this,"Please Re-Enter your email",Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Valid Email is Required");
                    editTextRegisterEmail.requestFocus();
                } else if(TextUtils.isEmpty(textDob)){
                    Toast.makeText(RegisterActivity.this,"Please Enter your DOB",Toast.LENGTH_LONG).show();
                    editTextRegisterDoB.setError("DOB is Required");
                    editTextRegisterDoB.requestFocus();
                } else if(TextUtils.isEmpty(textMobile)){
                    Toast.makeText(RegisterActivity.this,"Please Enter your Mobile Number",Toast.LENGTH_LONG).show();
                    editTextRegisterMobile.setError("Phone No. is Required");
                    editTextRegisterMobile.requestFocus();
                } else if (textMobile.length()!=10){
                    Toast.makeText(RegisterActivity.this,"Please Re-Enter your Mobile Number",Toast.LENGTH_LONG).show();
                    editTextRegisterMobile.setError("Valid Phone No. is Required");
                    editTextRegisterMobile.requestFocus();
                } else if(!mobileMatcher.find()){
                    Toast.makeText(RegisterActivity.this,"Please Re-Enter your Mobile Number",Toast.LENGTH_LONG).show();
                    editTextRegisterMobile.setError("Valid Phone No. is Required");
                    editTextRegisterMobile.requestFocus();
                } else if(radioGroupRegisterGender.getCheckedRadioButtonId()==-1){
                    Toast.makeText(RegisterActivity.this,"Please select your Gender",Toast.LENGTH_LONG).show();
                    radioButtonRegisterGenderSelected.setError("Gender is Required");
                    radioButtonRegisterGenderSelected.requestFocus();
                } else if(TextUtils.isEmpty(textPwd)){
                    Toast.makeText(RegisterActivity.this,"Please enter Password",Toast.LENGTH_LONG).show();
                    editTextRegisterPwd.setError("Gender is Required");
                    editTextRegisterPwd.requestFocus();
                } else if(textPwd.length()<6){
                    Toast.makeText(RegisterActivity.this,"Please Re-enter Password",Toast.LENGTH_LONG).show();
                    editTextRegisterPwd.setError("Weak Password");
                    editTextRegisterPwd.requestFocus();
                } else{
                    textGender = radioButtonRegisterGenderSelected.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(textFullName,textEmail,textDob,textGender,textMobile,textPwd);
                }
            }
        });


    }

    private void registerUser(String textFullName, String textEmail, String textDob, String textGender, String textMobile, String textPwd) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmail,textPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    //Update Display Name of User
                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textFullName).build();
                    firebaseUser.updateProfile(profileChangeRequest);


                    //Enter User Data into Firebase Realtime Database
                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textDob,textGender,textMobile);
                    //Extracting reference from database
                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //send verification email
                            firebaseUser.sendEmailVerification();
                            Toast.makeText(RegisterActivity.this,"User Registered, please verify your email", Toast.LENGTH_LONG).show();
                            if(task.isSuccessful()){
                                //open user profile after registration
                                Intent intent = new Intent(RegisterActivity.this,UserProfileActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(RegisterActivity.this,"Registration Failed, please try again", Toast.LENGTH_LONG).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });


                } else{
                    try{
                        throw task.getException();
                    } catch(FirebaseAuthWeakPasswordException e){
                        editTextRegisterPwd.setError("Your Password is too Weak. Kindly use a Alphanumeric password with special character");
                        editTextRegisterPwd.requestFocus();
                        progressBar.setVisibility(View.GONE);
                    }catch(FirebaseAuthInvalidCredentialsException e){
                        editTextRegisterPwd.setError("Your email is invalid or already in use");
                        editTextRegisterPwd.requestFocus();
                        progressBar.setVisibility(View.GONE);
                    }catch(FirebaseAuthUserCollisionException e){
                        editTextRegisterPwd.setError("User is already registered with this email");
                        editTextRegisterPwd.requestFocus();
                        progressBar.setVisibility(View.GONE);
                    }catch (Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
}