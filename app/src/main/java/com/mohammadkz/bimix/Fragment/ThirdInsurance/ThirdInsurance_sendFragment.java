package com.mohammadkz.bimix.Fragment.ThirdInsurance;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.mohammadkz.bimix.Activity.ThirdInsuranceActivity;
import com.mohammadkz.bimix.Model.ThirdInsurance;
import com.mohammadkz.bimix.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class ThirdInsurance_sendFragment extends Fragment {

    View view;
    private ThirdInsurance thirdInsurance;
    Button insurancePic, onCarCardPic, backCarCardPic, onCertificatePic, backCertificatePic;
    Button send;
    ProgressDialog progressDialog;
    String Base = null, mediaPath;
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
        progressDialog.setMessage("Uploading...");

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
                    Log.e("test", " " + thirdInsurance.getInsurancePic());

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
                    thirdInsurance.setInsurancePic(base64);
                } else if (n == 2) {
                    thirdInsurance.setOnCarCardPic(base64);
                } else if (n == 3) {
                    thirdInsurance.setBackCarCardPic(base64);
                } else if (n == 4) {
                    thirdInsurance.setOnCertificatePic(base64);
                } else if (n == 5) {
                    thirdInsurance.setBackCertificatePic(base64);
                } else {
                    Log.e("error ", " to save (number 1)");
                }
                cursor.close();

            } else if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

                if (n == 1) {
                    thirdInsurance.setInsurancePic(encoded);
                } else if (n == 2) {
                    thirdInsurance.setOnCarCardPic(encoded);
                } else if (n == 3) {
                    thirdInsurance.setBackCarCardPic(encoded);
                } else if (n == 4) {
                    thirdInsurance.setOnCertificatePic(encoded);
                } else if (n == 5) {
                    thirdInsurance.setBackCertificatePic(encoded);
                } else {

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

    private String getBase64(Uri uri) {
        Bitmap bm = BitmapFactory.decodeFile(uri.toString());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos); // bm is the bitmap object
        byte[] b = baos.toByteArray();

        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
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

    private boolean checkValue() {
        if (thirdInsurance.getInsurancePic() == null || thirdInsurance.getBackCarCardPic() == null || thirdInsurance.getBackCertificatePic() == null ||
                thirdInsurance.getOnCarCardPic() == null || thirdInsurance.getOnCertificatePic() == null) {
            return false;
        } else
            return true;
    }

}