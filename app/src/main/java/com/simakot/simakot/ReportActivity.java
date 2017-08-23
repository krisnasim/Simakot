package com.simakot.simakot;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportActivity extends AppCompatActivity {

    @BindView(R.id.report_location_label) TextView report_location_label;
    @BindView(R.id.report_desc_label) TextView report_desc_label;
    @BindView(R.id.report_image) ImageView report_image;
    @BindView(R.id.location_label) TextView location_label;
    @BindView(R.id.report_desc_input) TextView report_desc_input;

    private int imageWidth;
    private int imageHeight;
    private String mCurrentPhotoPath;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @OnClick(R.id.report_button)
    public void sendReport() {
        Intent intent = new Intent(ReportActivity.this, SuccessActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);

        report_image.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imageWidth = report_image.getWidth();
                imageHeight = report_image.getHeight(); //height is ready
                Log.d("imageWidth", String.valueOf(imageWidth));
                Log.d("imageHeight", String.valueOf(imageHeight));
                report_image.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                dispatchTakePictureIntent();
            }
        });

        //dispatchTakePictureIntent();

        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/swis.ttf");
        report_location_label.setTypeface(myFont);
        report_desc_label.setTypeface(myFont);
        location_label.setTypeface(myFont);
        report_desc_input.setTypeface(myFont);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //new method for image
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //DO SOMETHING HERE
            setPic();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ReportActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            //startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                //add file to gallery
                galleryAddPic();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.d("PhotoFile", "Error creating file!");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.simakot.simakot.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    private void setPic() {
        // Get the dimensions of the View
        //int targetW = visiblity_picture.getWidth();
        //int targetH = visiblity_picture.getHeight();
        int targetW = imageWidth;
        int targetH = imageHeight;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        File file = new File(mCurrentPhotoPath);
        Log.d("Not Compressor", String.format("Size : %s", getReadableFileSize(file.length())));
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        Log.d("targetW", String.valueOf(targetW));
        Log.d("targetH", String.valueOf(targetH));

        Log.d("photoW", String.valueOf(photoW));
        Log.d("photoH", String.valueOf(photoH));

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        //bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        report_image.setImageBitmap(bitmap);

//        FileInputStream fis = null;
//        InputStream newIS = null;
//
//        try {
//            fis = new FileInputStream(file);
//            newIS = new BufferedInputStream(fis);
//            //imageByte = new byte[(int) file.length()];
//            //fis.read(imageByte); //read file into bytes[]
//            //fis.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //upload the image
//        //uploadImageNX(testPhoto);
//        uploadImageNX();
    }
}
