package erawanbikes.com.sample.Utilities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.io.ByteArrayOutputStream;

import erawanbikes.com.sample.R;


public class CropView extends AppCompatActivity implements View.OnClickListener{

    CropImageView cropImageView;
    Button Crop,Cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_view);

        cropImageView = (CropImageView) findViewById(R.id.cropImageView);
        Crop = (Button) findViewById(R.id.Crop);
        Crop.setOnClickListener(this);
        Cancel = (Button) findViewById(R.id.Cancel);
        Cancel.setOnClickListener(this);

        Intent intent = getIntent();

        Uri uri = Uri.parse(intent.getStringExtra("imageUri"));

        cropImageView.setImageUriAsync(uri);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Crop:
                Bitmap cropped = cropImageView.getCroppedImage();
                Uri CroppedUri = getImageUri(CropView.this,cropped);

                Intent returnIntent = getIntent();
                returnIntent.putExtra("cropUri",CroppedUri.toString());
                setResult(RESULT_OK,returnIntent);
                finish();
                break;

            case R.id.Cancel:
                finish();
                break;

        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
            return Uri.parse(path);

    }
}
