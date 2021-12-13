package com.coursera.aad.capstoneapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.coursera.aad.capstoneapp.database.CountriesDbHelper;
import com.coursera.aad.capstoneapp.database.UserDbHelper;
import com.coursera.aad.capstoneapp.database.dao.CountryDao;
import com.coursera.aad.capstoneapp.database.dao.UserDao;
import com.coursera.aad.capstoneapp.models.User;
import com.coursera.aad.capstoneapp.screens.CovidNewsFragment;
import com.coursera.aad.capstoneapp.screens.SignInFragment;
import com.coursera.aad.capstoneapp.screens.SignUpFragment;

import timber.log.Timber;

import static com.coursera.aad.capstoneapp.utils.Utils.loadFragmentToFrameLayout;

public class MainActivity extends AppCompatActivity implements
        SignInFragment.OnSignInFragmentListener,
        SignUpFragment.OnSignUpFragmentListener {

    private UserDao userDao;
    public static CountryDao countryDao;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            // Initialize dbHelper instance
            userDao = new UserDao(new UserDbHelper(this));
            countryDao = new CountryDao(new CountriesDbHelper(this));
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            initView();
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @Override
    public void onValidUserInput(User user) {
        try {
            MainActivity.user = user;
            if (userDao.insert(user)) {
                // Load news fragment
                loadFragmentToFrameLayout(
                        this,
                        R.id.layoutContainer,
                        new CovidNewsFragment());
            } else {
                Toast.makeText(this,
                        getString(R.string.save_user_data_error_toast),
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @Override
    public void onSignUp(String email, String password) {
        try {
            if (userDao.authUser(email, password) != null) {
                // Load news fragment
                loadFragmentToFrameLayout(
                        this,
                        R.id.layoutContainer,
                        new CovidNewsFragment());
            } else {
                Toast.makeText(this,
                        getString(R.string.auth_user_data_error_toast),
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    private void initView() {
       try {
           // Check if the user is connected or not
           user = userDao.read(1);

           // Database is empty
           if (user == null) {
               // Load sign in fragment
               loadFragmentToFrameLayout(
                       this,
                       R.id.layoutContainer,
                       new SignInFragment());
           } else { // Database is not empty
               if (user.getStatus() == 0) { // User is not connected
                   // Load sign up fragment
                   loadFragmentToFrameLayout(
                           this,
                           R.id.layoutContainer,
                           new SignUpFragment());
               } else { // User has already connected
                   // Load news fragment
                   loadFragmentToFrameLayout(
                           this,
                           R.id.layoutContainer,
                           new CovidNewsFragment());
               }
           }
       } catch (Exception e) {
           Timber.e(e);
       }
    }
}