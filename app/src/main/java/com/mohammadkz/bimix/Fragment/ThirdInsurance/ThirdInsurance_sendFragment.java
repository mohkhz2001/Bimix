package com.mohammadkz.bimix.Fragment.ThirdInsurance;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.mohammadkz.bimix.Activity.ThirdInsuranceActivity;
import com.mohammadkz.bimix.Model.ThirdInsurance;
import com.mohammadkz.bimix.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class ThirdInsurance_sendFragment extends Fragment {

    View view;
    private ThirdInsurance thirdInsurance;
    ImageButton insurancePic, onCarCardPic, backCarCardPic, onCertificatePic, backCertificatePic;
    Button send;
    ProgressDialog progressDialog;
    String Base = null, mediaPath;
    Bitmap insurancePic_bitmap, onCarCardPic_bitmap, backCarCardPic_bitmap, onCertificatePic_bitmap, backCertificatePic_bitmap;
    String currentPhotoPath;

    int n = 0;

    public ThirdInsurance_sendFragment(ThirdInsurance thirdInsurance) {
        // Required empty public constructor
        this.thirdInsurance = thirdInsurance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_third_insurance_send, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("لطفا منتظر باشید...");

        permission();
        initViews();
        controllerViews();

        return view;
    }

    private void initViews() {
        insurancePic = view.findViewById(R.id.insurancePic);
        onCarCardPic = view.findViewById(R.id.onCarCard);
        backCarCardPic = view.findViewById(R.id.backCarCard);
        onCertificatePic = view.findViewById(R.id.onCertificatePic);
        backCertificatePic = view.findViewById(R.id.backCertificatePic);
        send = view.findViewById(R.id.send);

    }

    private void controllerViews() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValue()) {
                    base64(); // convert the pics to base64

                    ((ThirdInsuranceActivity) getActivity()).setSeekBar(3);

                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    ThirdInsurance_ConfirmFragment thirdInsurance_confirmFragment = new ThirdInsurance_ConfirmFragment(thirdInsurance);
                    fragmentTransaction.replace(R.id.frameLayout, thirdInsurance_confirmFragment);
                    fragmentTransaction.commit();
                }
            }
        });

        insurancePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n = 1;
                bottomSheetChooser();
            }
        });

        onCarCardPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n = 2;
                bottomSheetChooser();
            }
        });

        backCarCardPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n = 3;
                bottomSheetChooser();
            }
        });

        onCertificatePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n = 4;
                bottomSheetChooser();
            }
        });

        backCertificatePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n = 5;
                bottomSheetChooser();
            }
        });
    }

    // bottom choose => camera or file(gallery)
    public void bottomSheetChooser() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);
        View bottomSheetView = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_choose_send, (LinearLayout) view.findViewById(R.id.bottomSheetContainer));

        bottomSheetView.findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dispatchTakePictureIntent();

//                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(takePicture, 0);//zero can be replaced with any action code (called requestCode)

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

    // open camera to take photo ==>  save full size
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            } catch (Exception e) {
                e.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 0);
            }
        }
    }

    // create the file directory
    private File createImageFile() throws Exception {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    // convert uri to bitmap
    private Bitmap setPic() {
        // Get the dimensions of the View
        int targetW = 700;
        int targetH = 700;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.max(1, Math.min(photoW / targetW, photoH / targetH));

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        return bitmap;
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

                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(mediaPath, bmOptions);

                if (n == 1) {
                    insurancePic.setImageBitmap(bitmap);
                    insurancePic_bitmap = bitmap;
                } else if (n == 2) {
                    onCarCardPic.setImageBitmap(bitmap);
                    onCarCardPic_bitmap = bitmap;
                } else if (n == 3) {
                    backCarCardPic.setImageBitmap(bitmap);
                    backCarCardPic_bitmap = bitmap;
                } else if (n == 4) {
                    onCertificatePic.setImageBitmap(bitmap);
                    onCertificatePic_bitmap = bitmap;
                } else if (n == 5) {
                    backCertificatePic.setImageBitmap(bitmap);
                    backCertificatePic_bitmap = bitmap;
                } else {
                    Log.e("error ", " to save (number 1)");
                }
                cursor.close();

            } else if (requestCode == 0 && resultCode == RESULT_OK && currentPhotoPath != null) {

//                Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                Bitmap bitmap = setPic();

                if (n == 1) {
                    insurancePic.setImageBitmap(bitmap);
                    insurancePic_bitmap = bitmap;
                } else if (n == 2) {
                    onCarCardPic.setImageBitmap(bitmap);
                    onCarCardPic_bitmap = bitmap;
                } else if (n == 3) {
                    backCarCardPic.setImageBitmap(bitmap);
                    backCarCardPic_bitmap = bitmap;
                } else if (n == 4) {
                    onCertificatePic.setImageBitmap(bitmap);
                    onCertificatePic_bitmap = bitmap;
                } else if (n == 5) {
                    backCertificatePic.setImageBitmap(bitmap);
                    backCertificatePic_bitmap = bitmap;
                } else {
                    Log.e("error ", " to save (number 1)");
                }

            } else {
                Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.getMessage();
            Log.e("error", " " + e.getMessage());
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
        }

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

    // check all image btn
    private boolean checkValue() {
        if (insurancePic_bitmap == null || onCarCardPic == null || backCarCardPic == null ||
                onCertificatePic_bitmap == null || backCertificatePic_bitmap == null) {
            return false;
        } else
            return true;
    }

    // convert to base64 ( after press next )
    private void base64() {
        progressDialog.show();

        DownloadImagesTask downloadImagesTask = new DownloadImagesTask();

        thirdInsurance.setInsurancePic(downloadImagesTask.doInBackground(insurancePic_bitmap));
        thirdInsurance.setOnCarCardPic(downloadImagesTask.doInBackground(onCarCardPic_bitmap));
        thirdInsurance.setBackCarCardPic(downloadImagesTask.doInBackground(backCarCardPic_bitmap));
        thirdInsurance.setOnCertificatePic(downloadImagesTask.doInBackground(onCertificatePic_bitmap));
        thirdInsurance.setBackCertificatePic(downloadImagesTask.doInBackground(backCertificatePic_bitmap));

        progressDialog.dismiss();
    }

    // run in the background
    // convert to base64
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