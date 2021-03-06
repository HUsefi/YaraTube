package com.yaratech.yaratube.ui.profile;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.yaratech.yaratube.BuildConfig;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.ui.MenuActivity;
import com.yaratech.yaratube.ui.imagepicker.ImagePickerDialog;
import com.yaratech.yaratube.utils.Permissions;
import com.yaratech.yaratube.utils.Utils;

import java.io.File;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static com.yaratech.yaratube.ui.imagepicker.ImagePickerDialog.IMAGE_PICKER_TAG;

public class ProfileFragment extends Fragment
        implements ImagePickerDialog.ImagePickerListener,
                    ProfileContract.View{
    ProfileContract.Presenter presenter;
    String img;

    @BindView(R.id.name_family)
    EditText name_family;
    @BindView(R.id.birth_date)
    EditText birth_date;
    @BindView(R.id.gender)
    Spinner gender;
    @BindView(R.id.profile_picture)
    ImageView profilePicture;

    @OnClick(R.id.save)
    public void saveProfile() {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            date = format.parse(birth_date.getText().toString());
            Log.e("Tag",date+" "+img);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.e("Tag",date+" "+img);
        presenter.sendProfileData(
                img,
                name_family.getText().toString(),
                gender.getSelectedItem().toString(),
                date);
    }

    @OnClick(R.id.cancel)
    public void cancel() {
        getChildFragmentManager().popBackStack();
    }


    @OnClick(R.id.profile_picture)
    public void onImageClick() {
        ImagePickerDialog imagePickerDialog = ImagePickerDialog.newInstance(this);
        FragmentManager fragmentManager = getFragmentManager();
        imagePickerDialog.show(fragmentManager, IMAGE_PICKER_TAG);
    }

    public static ProfileFragment newInstance() {

        Bundle args = new Bundle();

        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        presenter = new ProfilePresenter(getContext(), this);
    }

    @Override
    public void onCamera() {
        if (!Permissions.checkCameraPermissions(getContext())) {
            requestCameraPermission(MEDIA_TYPE_IMAGE);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp1.jpg");
            Uri mImageCaptureUri = Uri.fromFile(f);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
            getActivity().startActivityForResult(intent, 0);
        }
    }

    @Override
    public void onGalery() {
        Log.e("galery", "camera");
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select picture"), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Log.e("Tag", "ok");
            Bitmap bitmap = null;
            if (requestCode == 0) {
                Log.e("Tag", "camera");
                bitmap = (Bitmap) data.getExtras().get("data");
                Glide.with(getContext()).load(bitmap).into(profilePicture);
            } else if (requestCode == 1) {
                Log.e("Tag", "galery");
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(getContext()).load(bitmap).into(profilePicture);
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] imgByte = byteArrayOutputStream.toByteArray();
            img = Base64.encodeToString(imgByte, Base64.DEFAULT);
//            File destination = new File(Environment.getExternalStorageDirectory(),
//                    System.currentTimeMillis() + ".jpg");
//            FileOutputStream fileOutputStream = null;
//
//            try {
//                destination.createNewFile();
//                fileOutputStream = new FileOutputStream(destination);
//                fileOutputStream.write(byteArrayOutputStream.toByteArray());
//                fileOutputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

//            Log.e("tag", destination.toString());


        }
    }

    private void requestCameraPermission(final int type) {
        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            if (type == MEDIA_TYPE_IMAGE) {
                                // capture picture
                                onCamera();
                            }

                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            Log.e("Tag ", "ajab!");
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    public void updateImage(Uri uri) {

    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
