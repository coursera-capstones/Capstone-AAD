package com.coursera.aad.capstoneapp.screens;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.coursera.aad.capstoneapp.R;
import com.coursera.aad.capstoneapp.models.User;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import timber.log.Timber;

public class SignInFragment extends Fragment {

    private View root;
    private User user;
    private OnSignInFragmentListener listener;

    private TextInputLayout fullNameField;
    private TextInputLayout emailField;
    private TextInputLayout passwordField;
    private TextInputLayout confirmPasswordField;
    private Button btnSignIn;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_sign_in, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            initView();
            initListener();
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            if (context instanceof OnSignInFragmentListener) {
                listener = (OnSignInFragmentListener) context;
            }
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    private void initView() {
        try {
            // Get view by ID
            fullNameField = root.findViewById(R.id.fieldFullName);
            emailField = root.findViewById(R.id.fieldEmail);
            passwordField = root.findViewById(R.id.fieldPassword);
            confirmPasswordField = root.findViewById(R.id.fieldConfirmPassword);
            btnSignIn = root.findViewById(R.id.btnSignIn);
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    private void initListener() {
        btnSignIn.setOnClickListener(view -> {
           try {
               if (isValidUserInput()) {
                   // Notify activity, and load nest fragment
                   listener.onValidUserInput(user);
               }
           } catch (Exception e) {
               Timber.e(e);
           }
        });
    }

    /**
     * Check user input
     * @return True if user's input are valid. Otherwise, false
     */
    private boolean isValidUserInput() {
        String fullName;
        String password;
        String email;
        try {
            fullName = Objects.requireNonNull(fullNameField.getEditText()).getText().toString();
            email = Objects.requireNonNull(emailField.getEditText()).getText().toString();
            password = Objects.requireNonNull(passwordField.getEditText()).getText().toString();
            String confirmPassword = Objects.requireNonNull(confirmPasswordField.getEditText()).getText().toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(requireContext(),
                        requireContext().getString(R.string.email_field_error_toast),
                        Toast.LENGTH_SHORT).show();

                return false;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(requireContext(),
                        requireContext().getString(R.string.password_field_error_toast),
                        Toast.LENGTH_SHORT).show();

                return false;
            }

            if (TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(requireContext(),
                        requireContext().getString(R.string.confirm_password_field_error_toast),
                        Toast.LENGTH_SHORT).show();

                return false;
            }

            if (!password.trim().equalsIgnoreCase(confirmPassword.trim())) {
                Toast.makeText(requireContext(),
                        requireContext().getString(R.string.password_validation_error_toast),
                        Toast.LENGTH_SHORT).show();

                return false;
            }
        } catch (Exception e) {
            Timber.e(e);

            return false;
        }

        user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);
        user.setSignIn(true);
        user.setStatus(1);

        return true;
    }

    public interface OnSignInFragmentListener {
        void onValidUserInput(User user);
    }
}