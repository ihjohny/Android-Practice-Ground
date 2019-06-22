package com.example.undefined.gsonex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Gson gson = new Gson();
      //  Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create(); //gson for expose keyword;
        //Serialization

        List<FamilyMember> family=new ArrayList<>();
        family.add(new FamilyMember("Wife",30));
        family.add(new FamilyMember("Daughter",18));
        family.add(new FamilyMember("Husband",35));


        Address address = new Address("Germany", "berline");
        Employee employee = new Employee("John", 30, "john@gmail.com", address,family,"this1is2test");
        String json = gson.toJson(employee);

//        String json=gson.toJson(family); //Serialize ArrayList

        //Deserialization
/*           String jsonDe="{\"address\":{\"city\":\"berline\",\"country\":\"Germany\"},\"age\":30,\"mFamily\":[{\"age\":30,\"role\":\"Wife\"},{\"age\":18,\"role\":\"Daughter\"},{\"age\":35,\"role\":\"Husband\"}],\"firstName\":\"John\",\"mail\":\"john@gmail.com\"}";
           Employee employeeDe=gson.fromJson(jsonDe,Employee.class);*/

/*
        String jsonDe = "[{\"age\":30,\"role\":\"Wife\"},{\"age\":18,\"role\":\"Daughter\"},{\"age\":35,\"role\":\"Husband\"}]";
        Type familyType=new TypeToken<ArrayList<FamilyMember>>(){}.getType();
        ArrayList<FamilyMember> family = gson.fromJson(jsonDe, familyType);
*/

    }
}
