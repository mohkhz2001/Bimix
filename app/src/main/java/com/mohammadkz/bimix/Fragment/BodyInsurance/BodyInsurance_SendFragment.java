package com.mohammadkz.bimix.Fragment.BodyInsurance;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.mohammadkz.bimix.Activity.BodyInsuranceActivity;
import com.mohammadkz.bimix.Model.BodyInsurance;
import com.mohammadkz.bimix.Model.User;
import com.mohammadkz.bimix.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class BodyInsurance_SendFragment extends Fragment {

    View view;
    BodyInsurance bodyInsurance;
    Button next;
    ImageView on, back;
    String mediaPath;
    ProgressDialog progressDialog;
    String Base = null;
    int n = 0;
    Bitmap pic1, pic2;


    public BodyInsurance_SendFragment(BodyInsurance bodyInsurance) {
        // Required empty public constructor
        this.bodyInsurance = bodyInsurance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_body_insurance_send, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Uploading...");

        initViews();
        controllerViews();


        return view;
    }

    private void initViews() {
        on = view.findViewById(R.id.on);
        back = view.findViewById(R.id.back);
        next = view.findViewById(R.id.next);
    }

    private void controllerViews() {
        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permission();
                n = 1;
                bottomSheetChooser();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permission();
                n = 2;
                bottomSheetChooser();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base64(); // convert the pics to base64

                ((BodyInsuranceActivity) getActivity()).setSeekBar(6);
                BodyInsurance_ConfirmFragment bodyInsurance_confirmFragment = new BodyInsurance_ConfirmFragment(bodyInsurance);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, bodyInsurance_confirmFragment).commit();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
                progressDialog.show();
                // Get the Image from data
                Uri selectedImage = data.getData();

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);

                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(mediaPath, bmOptions);


                if (n == 1) {
                    on.setImageBitmap(bitmap);
                    pic1 = bitmap;
                } else {
                    back.setImageBitmap(bitmap);
                    pic2 = bitmap;
                }

                cursor.close();

            } else if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                if (n == 1) {
                    on.setImageBitmap(bitmap);
                    pic1 = bitmap;
                } else {
                    back.setImageBitmap(bitmap);
                    pic2 = bitmap;
                }

            } else {
                Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.getMessage();
            Log.e("error", " " + e.getMessage());
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
        }

        progressDialog.dismiss();

    }

    // convert to base64 ( after press next )
    private void base64() {
        progressDialog.show();

        DownloadImagesTask downloadImagesTask = new DownloadImagesTask();
        bodyInsurance.setOnCarCard(downloadImagesTask.doInBackground(pic1));
        bodyInsurance.setBackOnCarCard(downloadImagesTask.doInBackground(pic2));

        progressDialog.dismiss();
    }

    // bottom choose => camera or file(gallery)
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

    // run in the background
    private class DownloadImagesTask extends AsyncTask<Bitmap, Void, String> {
        ProgressDialog progressDialog = new ProgressDialog(getContext());

        @Override
        protected String doInBackground(Bitmap... bitmaps) {
            progressDialog.setMessage("a");
            progressDialog.show();
            bitmaps[0] = Bitmap.createScaledBitmap(bitmaps[0], 700, 700, false);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmaps[0].compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

            Log.e("tr", " " + encoded);
            progressDialog.dismiss();
            return encoded;
        }


    }

}