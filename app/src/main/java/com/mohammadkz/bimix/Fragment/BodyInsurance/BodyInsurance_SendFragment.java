package com.mohammadkz.bimix.Fragment.BodyInsurance;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mohammadkz.bimix.Activity.BodyInsuranceActivity;
import com.mohammadkz.bimix.Model.BodyInsurance;
import com.mohammadkz.bimix.Model.User;
import com.mohammadkz.bimix.R;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;
import static android.bluetooth.BluetoothGattCharacteristic.PERMISSION_READ;
import static android.bluetooth.BluetoothGattCharacteristic.PERMISSION_WRITE;
import static android.content.Context.CAMERA_SERVICE;
import static android.media.MediaRecorder.VideoSource.CAMERA;


public class BodyInsurance_SendFragment extends Fragment {

    View view;
    BodyInsurance bodyInsurance;
    Button on, back, next;
    String mediaPath;
    ProgressDialog progressDialog;
    String Base = null;
    int n = 0;


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


        checkPermission();
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
                n = 1;
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 1);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n = 2;
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 1);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);

                // Set the Image in ImageView for Previewing the Media
//                imgView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                String base64 = getBase64(Uri.parse(mediaPath));
                Base = base64;
                Log.i("test", " " + base64);

                if (n == 1) {
                    bodyInsurance.setOnCarCard(base64);
                } else {
                    bodyInsurance.setBackOnCarCard(base64);
                }
                cursor.close();

            } else {
                Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }

    private String getBase64(Uri uri) {
        Bitmap bm = BitmapFactory.decodeFile(uri.toString());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos); // bm is the bitmap object
        byte[] b = baos.toByteArray();

        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }

    // Uploading Image/Video
//    private void uploadFile(String base) {
//        progressDialog.show();
//        Log.e("lenght", " " + base.length());
//
//        ApiConfig apiConfig = AppConfig.getRetrofit().create(ApiConfig.class);
//        Call<ServerResponse> request = apiConfig.uploadImage("test", base);
//
//        request.enqueue(new Callback<ServerResponse>() {
//            @Override
//            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
//                Log.e("response", " " + response.body().response);
//                Log.e("response", " " + response);
//                progressDialog.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<ServerResponse> call, Throwable t) {
//                t.getMessage();
//                progressDialog.dismiss();
//            }
//        });
//
////        String url = "http://192.168.1.103/uploadIMG/upload.php?img=" + base;
////        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
////            @Override
////            public void onResponse(String response) {
////                Log.e("response" , " " + response);
////            }
////        }, new Response.ErrorListener() {
////            @Override
////            public void onErrorResponse(VolleyError error) {
////
////            }
////        });
////
////        requestQueue.add(stringRequest);
//
//    }

    // runtime permission
    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if ((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ);
            return false;
        }

        int WRITE_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if ((WRITE_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_READ);
            return false;
        }
        int CAMERA = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
        if ((CAMERA != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 4);
            return false;
        }

        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_READ: {
                if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(getContext(), "Please allow storage permission", Toast.LENGTH_LONG).show();
                    } else {

                    }
                }

            }
            break;
            case PERMISSION_WRITE: {
                if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(getContext(), "Please allow storage permission", Toast.LENGTH_LONG).show();
                    } else {

                    }
                }

            }
            break;
            case 4: {
                if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.CAMERA)) {
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(getContext(), "Please allow storage permission", Toast.LENGTH_LONG).show();
                    } else {

                    }
                }

            }
            break;

        }
    }
}