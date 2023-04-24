package com.coursera.aad.capstoneapp.screens;

import static com.coursera.aad.capstoneapp.utils.Utils.isValidSignUpData;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.coursera.aad.capstoneapp.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import timber.log.Timber;

public class SignUpFragment extends Fragment {

    private View root;
    private String email, pass;
    private OnSignUpFragmentListener listener;
    private TextInputLayout emailField;
    private TextInputLayout passwordField;
    private Button btnSignUp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_sign_up, container, false);

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
            if (context instanceof OnSignUpFragmentListener) {
                listener = (OnSignUpFragmentListener) context;
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

    /**
     * Initialized the views used in this fragment
     */
    private void initView() {
        try {
            // Get view by ID
            emailField = root.findViewById(R.id.fieldEmail);
            passwordField = root.findViewById(R.id.fieldPassword);
            btnSignUp = root.findViewById(R.id.btnSignUp);
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    /**
     * Initialize the interaction listener on the views used in this fragment
     */
    private void initListener() {
        try {
            btnSignUp.setOnClickListener(view -> {
                String email = Objects.requireNonNull(emailField.getEditText()).getText().toString().trim();
                String pass = Objects.requireNonNull(passwordField.getEditText()).getText().toString().trim();

                if (isValidSignUpData(requireContext(), email, pass)) {
                    // Notify activity, and load nest fragment
                    listener.onSignUp(email, pass);
                }
            });
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    public interface OnSignUpFragmentListener {
        void onSignUp(String email, String password);
    }
}