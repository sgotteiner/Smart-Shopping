package com.sagi.smartshopping.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.sagi.smartshopping.R;
import com.sagi.smartshopping.entities.User;
import com.sagi.smartshopping.interfaces.IWaitingProgressBar;
import com.sagi.smartshopping.utilities.ImageUtils;
import com.sagi.smartshopping.utilities.Utils;

import java.util.Calendar;

public class RegisterFragment extends Fragment implements IWaitingProgressBar {


    private static final int REQUEST_CODE_STORAGE_PERMISSION = 20;
    private EditText mEdtEmail, mEdtPass, mEdtFirstName, mEdtLastName;
    private CheckBox mCheckBoxRememberMe;
    private Button mBtnRegister, mBtnCancel, mBtnDate;
    private boolean mIsRememberMe = false;
    private long mDateBirthDay = -1;
    private ImageView mImgProfile;
    private final int PICK_IMAGE = 1;
    private Bitmap mNewProfilePic = null;
    private User mUser;
    private ProgressDialog mProgressDialog;


    private OnFragmentInteractionListener mListener;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION) {
            boolean isOk = true;
            for (int i = 0; i < grantResults.length; i++) {
                boolean isAccept = grantResults[i] == PackageManager.PERMISSION_GRANTED;
                if (!isAccept)
                    isOk = false;
            }
            if (isOk) {
                getImageFromGallery();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProgressDialog = new ProgressDialog(getContext());
        loadViews(view);
        loadListeners();
    }

    private void loadViews(View view) {
        mEdtEmail = view.findViewById(R.id.edtEmailRegister);
        mEdtPass = view.findViewById(R.id.edtPassRgister);
        mEdtFirstName = view.findViewById(R.id.edtFirstName);
        mEdtLastName = view.findViewById(R.id.edtLastName);
        mCheckBoxRememberMe = view.findViewById(R.id.checkBoxRememberMeRegister);
        mBtnRegister = view.findViewById(R.id.btnRegister);
        mBtnCancel = view.findViewById(R.id.btnCancel);
        mBtnDate = view.findViewById(R.id.btnDate);
        mImgProfile = view.findViewById(R.id.imgProfile);

    }

    private void loadListeners() {
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEdtEmail.getText().toString().trim();
                String pass = mEdtPass.getText().toString().trim();
                String fName = mEdtFirstName.getText().toString().trim().toLowerCase();
                String lName = mEdtLastName.getText().toString().trim();

                if (!Utils.isValid(email, pass, fName, lName, mDateBirthDay, getContext()))
                    return;
                mUser = new User(mEdtFirstName.getText().toString(), mEdtLastName.getText().toString(), mEdtEmail.getText().toString(), mDateBirthDay, System.currentTimeMillis());
                showProgressDialod();
                mListener.createUser(mUser, mEdtPass.getText().toString(), mIsRememberMe, mNewProfilePic);
            }
        });
        mCheckBoxRememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                mIsRememberMe = isCheck;
            }
        });
        mBtnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        mDateBirthDay = Utils.getTimeStampFromDate(year, month, day);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        mImgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                        || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    getImageFromGallery();
                } else {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION);
                }

            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.showLoginFragment();
            }
        });
    }

    private void showProgressDialod() {
        mProgressDialog.setMessage("try login your profile");
        mProgressDialog.setTitle("Waiting");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIcon(R.drawable.save);
        mProgressDialog.show();
    }


    private void getImageFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri uriImageGallery = data.getData();

            mImgProfile.setBackgroundResource(0);
            mNewProfilePic = ImageUtils.handleImageGallery(uriImageGallery, getContext());
            mImgProfile.setImageBitmap(mNewProfilePic);
            mNewProfilePic = ImageUtils.scaleDown(mNewProfilePic, 200, false);

        }
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

    @Override
    public void stopProgressBar() {
        mProgressDialog.dismiss();
    }

    public interface OnFragmentInteractionListener {
        void createUser(User user, String password, boolean isRememberMe, Bitmap newProfilePic);

        void showLoginFragment();

        void registerEventFromRegisterLogin(IWaitingProgressBar iWaitingProgressBar);
    }

}
