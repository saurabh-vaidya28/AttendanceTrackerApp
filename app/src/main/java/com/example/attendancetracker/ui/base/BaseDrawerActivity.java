package com.example.attendancetracker.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;

public abstract class BaseDrawerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected abstract @NonNull NavController getNavController();
    protected abstract @NonNull DrawerLayout getDrawerLayout();
    protected abstract @NonNull ActionBarDrawerToggle getToggle();


    public static void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public boolean displaySelectedScreen(@NonNull int menuItem) {
        //creating fragment object
        getNavController().navigate(menuItem);
        return true;
    }


    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        getDrawerLayout().setDrawerLockMode(lockMode);
        getToggle().setDrawerIndicatorEnabled(enabled);
    }


}
