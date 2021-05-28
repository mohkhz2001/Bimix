package com.mohammadkz.bimix.Fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.mohammadkz.bimix.API.ApiConfig;
import com.mohammadkz.bimix.API.AppConfig;
import com.mohammadkz.bimix.Model.LoginResponse;
import com.mohammadkz.bimix.Model.User;
import com.mohammadkz.bimix.R;
import com.mohammadkz.bimix.StaticFun;

import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {


    View view;
    User user;
    Button save;
    TextInputEditText name, birthdayDate, idCard, phoneNumber;
    ApiConfig request;
    ImageView idCardImage;
    String mediaPath;
    TextView text_click, Error;
    ProgressDialog progressDialog;
    TextInputLayout date_layout, idCard_layout;

    public ProfileFragment(String auth) {
        // Required empty public constructor
        user = new User();
        user.setAuth(auth);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        progressDialog = new ProgressDialog(getContext());

        request = AppConfig.getRetrofit().create(ApiConfig.class);

        initViews();
        controllerViews();

        getData();

        return view;
    }

    private void initViews() {
        name = view.findViewById(R.id.name);
        save = view.findViewById(R.id.save);
        birthdayDate = view.findViewById(R.id.birthdayDate);
        idCard = view.findViewById(R.id.idCard);
        phoneNumber = view.findViewById(R.id.phoneNumber);
        text_click = view.findViewById(R.id.text_click);
        idCardImage = view.findViewById(R.id.idCardImage);
        Error = view.findViewById(R.id.Error);
        date_layout = view.findViewById(R.id.date_layout);
        idCard_layout = view.findViewById(R.id.idCard_layout);
    }

    private void controllerViews() {

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                user.setIdCard(idCard.getText().toString());
                user.setBirthdayDate(birthdayDate.getText().toString());

                if (user.getIdCard().equals("-") && user.getIdCardImage().equals("") && user.getBirthdayDate().equals("-")) {
                    progressDialog.dismiss();
                    if (user.getIdCard().equals("-")) {
                        date_layout.setErrorEnabled(true);
                        date_layout.setError("نمیتواند خالی باشد.");
                    } else {
                        date_layout.setErrorEnabled(false);
                    }

                    if (user.getIdCardImage().equals("")) {
                        Error.setVisibility(View.VISIBLE);
                    } else
                        Error.setVisibility(View.GONE);


                    if (user.getBirthdayDate().equals("-")) {
                        idCard_layout.setErrorEnabled(true);
                        idCard_layout.setError("نمیتواند خالی باشد.");
                    } else {
                        idCard_layout.setErrorEnabled(false);
                    }

                } else {
                    sendDate();
                }

            }
        });

        idCardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (user.getIdCardImage() == null) {
                permission();
                bottomSheetChooser();
//                }

            }
        });

    }

    private void getData() {
        progressDialog.setMessage("درحال دریافت...");
        progressDialog.show();
        Call<User> getUser = request.getUser(user.getAuth());

        getUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.body().getName().equals("")) {
                    response.body().setAuth(user.getAuth());
                    user = response.body();
                    setValue(user);

                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println();

                progressDialog.dismiss();
            }
        });
    }

    private void setValue(User user) {
        name.setText(user.getName());
        birthdayDate.setText(user.getBirthdayDate());
        idCard.setText(user.getIdCard());
        phoneNumber.setText(user.getPhoneNumber());
        name.setText(user.getName());
        name.setText(user.getName());

        if (user.getIdCardImage() != null) {
            showImage(user.getIdCardImage());
        }

    }

    private void sendDate() {
        progressDialog.setMessage("در حال ارسال...");
        progressDialog.show();
        Call<LoginResponse> update = request.updateUser(user.getAuth(), user.getIdCardImage(), user.getIdCard(), user.getBirthdayDate());

        update.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body().getCode().equals("0")) {
                    System.out.println();
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), " اطلاعات شما با موفقیت تغییر کرد ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                System.out.println();
                StaticFun.alertDialog_connectionFail(getContext());
            }
        });
    }

    private void showImage(String base64) {
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        idCardImage.setImageBitmap(decodedByte);
    }

    public void bottomSheetChooser() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);
        View bottomSheetView = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_choose_send, (LinearLayout) view.findViewById(R.id.bottomSheetContainer));

        bottomSheetView.findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);//zero can be replaced with any action code (called requestCode)

                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetView.findViewById(R.id.gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);//one can be replaced with any action code

                bottomSheetDialog.dismiss();

            }
        });

        bottomSheetView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    // runtime permission
    private void permission() {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {

            }
        };

        TedPermission.with(getContext())
                .setPermissionListener(permissionListener)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA).check();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 1 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);

                String base64 = getBase64(Uri.parse(mediaPath));
                user.setIdCardImage(base64);
                showImage(base64);


                cursor.close();

            } else if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String base64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

                user.setIdCardImage(base64);
                showImage(base64);

            } else {
                Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.getMessage();
            Log.e("error", " " + e.getMessage());
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }

    private String getBase64(Uri uri) {
        text_click.setVisibility(View.GONE);
        Bitmap bm = BitmapFactory.decodeFile(uri.toString());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos); // bm is the bitmap object
        byte[] b = baos.toByteArray();

        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }
}