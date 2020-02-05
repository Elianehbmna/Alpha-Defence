package com.example.alphadefence;

public class Profile {
    public String first;
    public String last;
    public String email;
    public String pass;
    public String confpass;
    public String contact;
    public String country;


    public Profile() {


    }

    public Profile(String first, String last, String email, String pass, String confpass, String contact, String country) {
        this.first = first;
        this.last = last;
        this.email = email;
        this.pass = pass;
        this.confpass = confpass;
        this.contact = contact;
        this.country = country;


    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getConfpass() {
        return confpass;
    }

    public void setConfpass(String confpass) {
        this.confpass = confpass;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}


