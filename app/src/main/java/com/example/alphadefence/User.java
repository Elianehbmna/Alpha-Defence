package com.example.alphadefence;

import android.widget.Spinner;

public class User {
    String firstname, lastname ,email, contactno, country;

    public User() {
    }

    public User(String firstname, String lastname, String email, String contactno, String country) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.contactno = contactno;
        this.country = country;
    }
}
