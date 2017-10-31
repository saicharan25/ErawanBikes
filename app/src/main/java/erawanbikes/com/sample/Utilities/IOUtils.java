package erawanbikes.com.sample.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import erawanbikes.com.sample.Login.Helper;

/**
 * Created by acer on 10/27/2017.
 */

public class IOUtils {

    public static String Font(int id, Context context) {

        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/open-sans.regular.ttf");

        SpannableStringBuilder fontStyle = new SpannableStringBuilder(context.getString(id));

      //  fontStyle.setSpan(new CustomTypefaceSpan("", font), 0, 0, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        return fontStyle.toString();
    }

    public static Bitmap decodeFile(Context context, File f) {

        // decode image size

        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        o.inDither = false; // Disable Dithering mode

        o.inPurgeable = true; // Tell to gc that whether it needs free memory,
        // the Bitmap can be cleared

        o.inInputShareable = true; // Which kind of reference will be used to
        // recover the Bitmap data after being
        // clear, when it will be used in the future
        try {
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
           // FireToast.makeToast(context, IOUtils.Font(R.string.image_not_added, context));
        }

        // Find the correct scale value. It should be the power of 2.
        final int REQUIRED_SIZE = 300;
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 1.5 < REQUIRED_SIZE && height_tmp / 1.5 < REQUIRED_SIZE)
                break;
            width_tmp /= 1.5;
            height_tmp /= 1.5;
            scale *= 1.5;
        }

        // decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        // o2.inSampleSize=scale;
        o.inDither = false; // Disable Dithering mode

        o.inPurgeable = true; // Tell to gc that whether it needs free memory,
        // the Bitmap can be cleared

        o.inInputShareable = true; // Which kind of reference will be used to

        try {

//          return BitmapFactory.decodeStream(new FileInputStream(f), null,
//                  null);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, null);
            System.out.println(" IW " + width_tmp);
            System.out.println("IHH " + height_tmp);
            int iW = width_tmp;
            int iH = height_tmp;
            return Bitmap.createScaledBitmap(bitmap, iW, iH, true);

        } catch (OutOfMemoryError e) {
            // TODO: handle exception
            e.printStackTrace();
         //   FireToast.makeToast(context, IOUtils.Font(R.string.image_not_added, context));
            System.gc();
            return null;
            // System.runFinalization();
            // Runtime.getRuntime().gc();
            // System.gc();
            // decodeFile(f);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          //  FireToast.makeToast(context, IOUtils.Font(R.string.image_not_added, context));

            return null;
        }

    }
    public static Bitmap rotatedBitmap(File file, Context context) {

//        File file = new File(fileUri.getPath());

        try {

            ExifInterface exif = new ExifInterface(file.toString());

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

            //    Log.i("orientation", String.valueOf(orientation));

            if (orientation == 8) {
                Matrix matrix = new Matrix();
                matrix.postRotate(-90);
                Bitmap bt = IOUtils.decodeFile(context, file);
                Bitmap rotatedBitmap = Bitmap.createBitmap(bt, 0, 0, bt.getWidth(), bt.getHeight(), matrix, true);

                return rotatedBitmap;
//                userImage.setImageBitmap(rotatedBitmap);
            } else if (orientation == 6) {
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap bt = IOUtils.decodeFile(context, file);
                Bitmap rotatedBitmap = Bitmap.createBitmap(bt, 0, 0, bt.getWidth(), bt.getHeight(), matrix, true);

                return rotatedBitmap;
            } else {
                Bitmap bt = IOUtils.decodeFile(context, file);

                return bt;
//                userImage.setImageBitmap(bt);
            }

        } catch (IOException io) {
            return null;
        }

    }

}
