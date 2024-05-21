package com.example.motormaniacs.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.motormaniacs.Fragments.MenuFragment;
import com.example.motormaniacs.Fragments.ResultadoCarrerasFragment;
import com.example.motormaniacs.R;

public class AdminActivity extends AppCompatActivity {
    FragmentManager fm = getSupportFragmentManager();
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        fragment = fm.findFragmentById(R.id.fragmentContainerAdmins);
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        Fragment fragment_nuevo= new MenuFragment(fm);
        fragmentTransaction.replace(R.id.fragmentContainerAdmins, fragment_nuevo);
        fragmentTransaction.commit();
    }
}