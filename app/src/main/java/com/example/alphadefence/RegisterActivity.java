 package com.example.alphadefence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.FirebaseDatabase;

 public class RegisterActivity extends AppCompatActivity {

    EditText ufirstname, ulastname, uemail ,upassword, uconfpassword, ucontactno;
    Button   btnRegister;
    Spinner   ucountry;
    TextInputLayout userFirstNameWrapper, userLastNameWrapper,userEmailWrapper,userPasswordWrapper,userConfPasswordWrapper,userContactNoWrapper,userCountryWrapper;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
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


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAuth.getCurrentUser()!=null) {
                    //user is logged in and can redirect to another activity
                }else{
                    final String firstname = ufirstname.getText().toString().trim();
                    final String lastname = ulastname.getText().toString().trim();
                    final String email = uemail.getText().toString().trim();
                    String password = upassword.getText().toString().trim();
                    String confpassword = uconfpassword.getText().toString().trim();
                    final String contactno = ucontactno.getText().toString().trim();
                    final String country = ucountry.getSelectedItem().toString().trim();
                    if (firstname.isEmpty()) {
                        userFirstNameWrapper.setError("Enter FirstName");
                        userFirstNameWrapper.requestFocus();
                        return;
                    }
                    if (lastname.isEmpty()) {
                        userLastNameWrapper.setError("Enter LastName");
                        userLastNameWrapper.requestFocus();
                        return;
                    }
                    if (email.isEmpty()) {
                        userEmailWrapper.setError("Enter your Email");
                        userEmailWrapper.requestFocus();
                        return;
                    }
                    if (password.isEmpty()) {
                        userPasswordWrapper.setError("Enter your password");
                        userPasswordWrapper.requestFocus();
                        return;
                    }
                    if (confpassword.isEmpty()) {
                        userConfPasswordWrapper.setError("confirm your password");
                        userConfPasswordWrapper.requestFocus();
                        return;
                    }
                    if (!password.equals(confpassword)) {
                        userPasswordWrapper.setError("Password didn't match!");
                        userPasswordWrapper.requestFocus();
                        return;
                    }
                    if (contactno.isEmpty()) {
                        userContactNoWrapper.setError("enter your digits");
                        userContactNoWrapper.requestFocus();
                        return;
                    }
                    if (country.isEmpty()) {
                        userCountryWrapper.setError("choose any country");
                        userCountryWrapper.requestFocus();
                        return;
                    }
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                //we need to add additional info in DB
                                User user=new User(firstname,lastname,email,contactno,country);
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                           Toast.makeText(RegisterActivity.this,  "User created Successfully." , Toast.LENGTH_LONG ).show();
                                        }else{
                                            Toast.makeText(RegisterActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });


                            }else{
                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });
    }
}
