package com.example.undefined.gsonex;

import com.google.gson.annotations.SerializedName;

public class FamilyMember {
    @SerializedName("role")
    private String mRole;
    @SerializedName("age")
    private int mAge;

    public FamilyMember(String role, int age) {
        this.mRole = role;
        this.mAge = age;
    }
}
