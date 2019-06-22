package com.example.undefined.gsonex;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Employee {

 //   @Expose(serialize = false) //it will not serrialize; default value is false;
    @SerializedName("firstName")
    private String mFirstName;
    @SerializedName("age")
    private int mAge;
    @SerializedName("mail")
    private String mMail;
    @SerializedName("address")
    private Address mAddress;
    List<FamilyMember> mFamily;
    @SerializedName("password")
    private transient String mPassword;

    public Employee(String firstName, int age, String mail,Address address,List<FamilyMember>family,String password) {
        mFirstName = firstName;
        mAge=age;
        mMail=mail;
        mAddress=address;
        mFamily=family;
        mPassword=password;
    }
}
