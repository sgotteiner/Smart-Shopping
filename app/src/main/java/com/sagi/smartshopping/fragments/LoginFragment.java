package com.sagi.smartshopping.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sagi.smartshopping.R;
import com.sagi.smartshopping.interfaces.IWaitingProgressBar;
import com.sagi.smartshopping.reposetories.preferance.SharedPreferencesHelper;

public class LoginFragment extends Fragment implements IWaitingProgressBar {

    private OnFragmentInteractionListener mListener;

    private EditText mEdtEmail, mEdtPass;
    private CheckBox mCheckBoxRememberMe;
    private Button mBtnLogin;
    private TextView mTxtRegister;
    private boolean mIsRememberMe = false;
    private ProgressDialog mProgressDialog;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressDialog = new ProgressDialog(getContext());
        loadViews(view);
        loadListeners();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            mListener.registerEventFromRegisterLogin(this);
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener.registerEventFromRegisterLogin(null);
        mListener = null;
    }

    private void loadListeners() {
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isValid())
                    return;
                showProgressDialog();
                checkIfUserExist();
            }
        });
        mTxtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterScreen();
            }
        });
        mCheckBoxRememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                mIsRememberMe = isCheck;
                SharedPreferencesHelper.getInstance().setIsAlreadyLogin(isCheck);
            }
        });
    }


    private void showRegisterScreen() {
        mListener.showRegisterFragment();
    }

    private boolean isValid() {
        String email = mEdtEmail.getText().toString();
        String pass = mEdtPass.getText().toString();

        if (pass.trim().equals("") || email.equals("")) {
            Toast.makeText(getContext(), "must fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        } else if (pass.length() < 6) {
            Toast.makeText(getContext(), "Pass must have at least 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isEmailValid(email)) {
            Toast.makeText(getContext(), "Email is not valid", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void checkIfUserExist() {
        String email = mEdtEmail.getText().toString();
        String pass = mEdtPass.getText().toString();
        mListener.signIn(email, pass, mIsRememberMe);
    }

    private void showProgressDialog() {
        mProgressDialog.setMessage("Try login your profile");
        mProgressDialog.setTitle("Waiting");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIcon(R.drawable.save);
        mProgressDialog.show();
    }


    private void loadViews(View view) {
        mEdtEmail = view.findViewById(R.id.edtEmailLogin);
        mEdtPass = view.findViewById(R.id.edtPassLogin);
        mCheckBoxRememberMe = view.findViewById(R.id.checkBoxRememberMeLgin);
        mBtnLogin = view.findViewById(R.id.btnLogin);
        mTxtRegister = view.findViewById(R.id.textViewRegister);
    }


    @Override
    public void stopProgressBar() {
        mProgressDialog.dismiss();
    }


    public interface OnFragmentInteractionListener {
        void signIn(String email, String password, boolean isRememberMe);

        void showRegisterFragment();

        void registerEventFromRegisterLogin(IWaitingProgressBar iWaitingProgressBar);
    }
}
