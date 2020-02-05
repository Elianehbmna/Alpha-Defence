 package com.example.alphadefence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

 public class RegisterActivity extends AppCompatActivity {

    private EditText ufirstname, ulastname, uemail ,upassword, uconfpassword, ucontactno;
    private Button   btnRegister;
    Spinner   ucountry;
    TextInputLayout userFirstNameWrapper, userLastNameWrapper,userEmailWrapper,userPasswordWrapper,userConfPasswordWrapper,userContactNoWrapper,userCountryWrapper;
     private FirebaseAuth firebaseAuth;
     private FirebaseStorage firebaseStorage;
     private StorageReference storageReference;

     private String myemail,upass,myfirstname,mylastname,uconfpass,ucontactn,mycountry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ufirstname=findViewById(R.id.userFirstName);
        ulastname=findViewById(R.id.userLastName);
        uemail=findViewById(R.id.userEmail);
        upassword=findViewById(R.id.userPassword);
        uconfpassword=findViewById(R.id.userConfPassword);
        ucontactno=findViewById(R.id.userContactNumber);

        ucountry=findViewById(R.id.userCountry);

        btnRegister=findViewById(R.id.btnRegister);

        userFirstNameWrapper=findViewById(R.id.userFirstNameWrapper);
        userLastNameWrapper=findViewById(R.id.userLastNameWrapper);
        userEmailWrapper=findViewById(R.id.userEmailAddressWrapper);
        userPasswordWrapper=findViewById(R.id.passwordWrapper);
        userConfPasswordWrapper=findViewById(R.id.confPasswordWrapper);
        userContactNoWrapper=findViewById(R.id.contactNoWrapper);
        userCountryWrapper=findViewById(R.id.countryWrapper);
        firebaseAuth =FirebaseAuth.getInstance();
        firebaseStorage =FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String usemail=uemail.getText().toString().trim();
                    String uspass=upassword.getText().toString().trim();
                    String usconfpass=upassword.getText().toString().trim();
                    String usfirst=ufirstname.getText().toString().trim();
                    String uslast=ulastname.getText().toString().trim();
                    String uscontact=ucontactno.getText().toString().trim();
                    String uscountry = ucountry.getSelectedItem().toString().trim();

                    if ( usfirst.isEmpty() || uslast.isEmpty() || usemail.isEmpty() || uspass.isEmpty() || usconfpass.isEmpty() || uscontact.isEmpty() ){

                        Toast.makeText(getBaseContext(),"please fill the blanks",Toast.LENGTH_LONG).show();

                    }else{
                        firebaseAuth.createUserWithEmailAndPassword(usemail,uspass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    sendverification();
                                    userdata();
                                    Toast.makeText(RegisterActivity.this, "successfully registered", Toast.LENGTH_SHORT).show();
                                    firebaseAuth.signOut();
                                    finish();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                }else{
                                    Toast.makeText(RegisterActivity.this, "registration failed", Toast.LENGTH_SHORT).show();


                                }



                            }


                        });

                    }




            }
        });
    }
     public boolean validate() {
         boolean result=false;
         String myfirstname = ufirstname.getText().toString();
         String mylastname = ulastname.getText().toString();
         String myemail = uemail.getText().toString();
         String upass = upassword.getText().toString();
         String uconfpass = uconfpassword.getText().toString();
         String ucontactn = ucontactno.getText().toString();
         String mycountry = ucountry.getSelectedItem().toString();

         return result;
     }
     private void sendverification(){
         FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
         if(firebaseUser!=null) {
             firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {
                     if (task.isSuccessful()) {
                         Toast.makeText(RegisterActivity.this, "successfully sent to your email!", Toast.LENGTH_SHORT).show();
                     }else{
                         Toast.makeText(RegisterActivity.this, "failed to send the verification!", Toast.LENGTH_SHORT).show();

                     }


                 }

             });
         }



     }
     private void userdata(){
         String email=uemail.getText().toString().trim();
         String pass=upassword.getText().toString().trim();
         String confpass=upassword.getText().toString().trim();
         String first=ufirstname.getText().toString().trim();
         String last=ulastname.getText().toString().trim();
         String contact=ucontactno.getText().toString().trim();
         String country = ucountry.getSelectedItem().toString().trim();
         FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
         DatabaseReference databaseReference = firebaseDatabase.getReference();

         Profile profile= new Profile(first,last,email,pass,confpass,contact,country);
         databaseReference.child("users").child(firebaseAuth.getCurrentUser().getUid()).setValue(profile);


     }
 }
