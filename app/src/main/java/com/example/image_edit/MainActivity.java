package com.example.image_edit;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Random;

public class MainActivity extends ActionBarActivity {
    Button b1, b2, b3;
    ImageView im;
    EditText editText;
    SQLiteDatabase db;
    TextView text;
    String TAG = "hello";

    private Bitmap bmp;
    private Bitmap operation;
    private float x1, x2, y1, y2;
    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        im = (ImageView) findViewById(R.id.imageView);

        BitmapDrawable abmp = (BitmapDrawable) im.getDrawable();
        bmp = abmp.getBitmap();
        db = this.openOrCreateDatabase("test.db", Context.MODE_PRIVATE, null);
        db.execSQL("Create table if not exists tb (a blob) ");
    }

    public void gray(View view) {
        operation = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        final double red = 0.299;
        final double green = 0.587;
        final double blue = 0.114;


        for (int i = 0; i < bmp.getWidth(); i++) {
            for (int j = 0; j < bmp.getHeight(); j++) {
                int p = bmp.getPixel(i, j);
                int r = Color.red(p);
                int g = Color.green(p);
                int b = Color.blue(p);
                int alpha = Color.alpha(p);

                r = g = b = (int)(red * r + green * g + blue * b);

                operation.setPixel(i, j, Color.argb(alpha, r, g, b));
            }
        }
        im.setImageBitmap(operation);
    }

    public void bright(View view) {
        operation = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        for (int i = 0; i < bmp.getWidth(); i++) {
            for (int j = 0; j < bmp.getHeight(); j++) {
                int p = bmp.getPixel(i, j);
                int r = Color.red(p);
                int g = Color.green(p);
                int b = Color.blue(p);
                int alpha = Color.alpha(p);

                r = 100 + r;
                g = 100 + g;
                b = 100 + b;
                alpha = 100 + alpha;
                operation.setPixel(i, j, Color.argb(alpha, r, g, b));
            }
        }
        im.setImageBitmap(operation);
    }

    public void dark(View view) {
        operation = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        for (int i = 0; i < bmp.getWidth(); i++) {
            for (int j = 0; j < bmp.getHeight(); j++) {
                int p = bmp.getPixel(i, j);
                int r = Color.red(p);
                int g = Color.green(p);
                int b = Color.blue(p);
                int alpha = Color.alpha(p);

                r = r - 50;
                g = g - 50;
                b = b - 50;
                alpha = alpha - 50;
                operation.setPixel(i, j, Color.argb(Color.alpha(p), r, g, b));
            }
        }
        im.setImageBitmap(operation);
    }

    public void red(View view) {
        operation = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        for (int i = 0; i < bmp.getWidth(); i++) {
            for (int j = 0; j < bmp.getHeight(); j++) {
                int p = bmp.getPixel(i, j);
                int r = Color.red(p);
                int g = Color.green(p);
                int b = Color.blue(p);
                int alpha = Color.alpha(p);

                r = r + 150;
                g = 0;
                b = 0;
                alpha = 0;
                operation.setPixel(i, j, Color.argb(Color.alpha(p), r, g, b));
            }
        }
        im.setImageBitmap(operation);
    }

    public void green(View view) {
        operation = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        for (int i = 0; i < bmp.getWidth(); i++) {
            for (int j = 0; j < bmp.getHeight(); j++) {
                int p = bmp.getPixel(i, j);
                int r = Color.red(p);
                int g = Color.green(p);
                int b = Color.blue(p);
                int alpha = Color.alpha(p);

                r = 0;
                g = g + 150;
                b = 0;
                alpha = 0;
                operation.setPixel(i, j, Color.argb(Color.alpha(p), r, g, b));
            }
        }
        im.setImageBitmap(operation);
    }

    public void blue(View view) {
        operation = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        for (int i = 0; i < bmp.getWidth(); i++) {
            for (int j = 0; j < bmp.getHeight(); j++) {
                int p = bmp.getPixel(i, j);
                int r = Color.red(p);
                int g = Color.green(p);
                int b = Color.blue(p);
                int alpha = Color.alpha(p);

                r = 0;
                g = 0;
                b = b + 150;
                alpha = 0;
                operation.setPixel(i, j, Color.argb(Color.alpha(p), r, g, b));
            }
        }
        im.setImageBitmap(operation);
    }
    public void Save(View view){

        /*try {
            FileInputStream fileInputStream = new FileInputStream("/Storage/SDCARD/image.jpg");
            byte[] = im = new.byte(fileInputStream.available());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
        final String path = Environment.getExternalStorageDirectory() + "/DCIM/";

        //getGalleryPath();
        File myDir = new File(path);
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        File file = new File (myDir, fname);

        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            operation.compress(Bitmap.CompressFormat.JPEG, 90, out);
            Toast.makeText(this, "Saved to DCIM", Toast.LENGTH_LONG).show();
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        File root = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File file1 = new File(root.getAbsolutePath()+"/Gallery/img.jpg");
        try
        {
            file1.createNewFile();
            FileOutputStream ostream = new FileOutputStream(file1);
            operation.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
            Toast.makeText(this, "Saved to Gallery", Toast.LENGTH_LONG).show();
            ostream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        /*Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
       //mCurrentPhotoPath = "file:" + im.getAbsolutePath();
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
        Toast.makeText(this, "Saved to Gallery", Toast.LENGTH_LONG).show();*/


    }
    //private static String getGalleryPath() {
        //return path = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/";
    //}
    /*public void crop(View view){

        Paint p = new Paint();
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        Canvas canvas = new Canvas(operation);
        canvas.drawBitmap(operation, 0, 0, null);
        canvas.drawRect(0, 0, 20, 20, null);

        Point a = new Point(0, 20);
        Point b = new Point(20, 20);
        Point c = new Point(0, 40);

        Path path = new Path();
        //path.setFillType(FillType.EVEN_ODD);
        path.lineTo(b.x, b.y);
        path.lineTo(c.x, c.y);
        path.lineTo(a.x, a.y);
        path.close();

        canvas.drawPath(path, p);

        /*a = new Point(0, 40);
        b = new Point(0, 60);
        c = new Point(20, 60);

        path = new Path();
        //path.setFillType(FillType.EVEN_ODD);
        path.lineTo(b.x, b.y);
        path.lineTo(c.x, c.y);
        path.lineTo(a.x, a.y);
        path.close();

        canvas.drawPath(path, p);*/

        //canvas.drawRect(0, 60, 20, operation.getHeight(), p);

    //}

    //public  void doodle(View view){
        /*int widthRatio = 2;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = widthRatio;
        im.setImageBitmap(operation);*/
        //im.setImageBitmap(operation);
        //text.setText("" + R.id.edit);

        //Bitmap imageBitmap = Bitmap.createBitmap(im.getWidth(),
               // im.getHeight(), Bitmap.Config.ARGB_8888);
        /*Canvas canvas = new Canvas(bmp);
        float scale = getResources().getDisplayMetrics().density;
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        p.setTextSize(24 * scale);
        canvas.drawText(string, im.getWidth() / 2, im.getHeight() / 2, p);


        /*@Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x1 = event.getX();
                    y1 = event.getY();
                    Log.d(TAG, "Action was UP");
                    break;

                case MotionEvent.ACTION_UP:
                    x2 = event.getX();
                    y2 = event.getY();
                    float Y = y2 - y1;
                    float X = x2 - x1;
                    if (Math.abs(X) > MIN_DISTANCE) {
                        if (x2 > x1) {

                            //SnakeView.cur_dirn = 1;
                            SnakeView.count = 1;
                            //SnakeView.transition();
                            //Toast.makeText(getContext(), "right swipe", Toast.LENGTH_SHORT).show();
                            //swipe=1;
                        } else {
                            //SnakeView.cur_dirn = 2;
                            SnakeView.count = 2;
                            //SnakeView.transition();
                            //Toast.makeText(getContext(), "left swipe", Toast.LENGTH_SHORT).show();
                            //swipe=2;
                        }
                    }

                    else if (Math.abs(Y) > MIN_DISTANCE) {
                        if (y2 > y1) {
                            //SnakeView.cur_dirn = 4;
                            SnakeView.count = 4;
                            //SnakeView.transition();
                            //Toast.makeText(getContext(), "top to bottom", Toast.LENGTH_SHORT).show();
                            //swipe=3;
                        } else {
                            //SnakeView.cur_dirn = 3;
                            SnakeView.count = 3;
                            //SnakeView.transition();
                            //Toast.makeText(getContext(), "bottom to top", Toast.LENGTH_SHORT).show();
                            //swipe=4;
                        }
                    }

                    break;
            }

            return super.onTouchEvent(event);*/
        //}

        //canvas.setBitmap(imageBitmap);




    public void resize(View view){

        int widthRatio = 2;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = widthRatio;
        Bitmap operation =  BitmapFactory.decodeResource(getResources(),R.drawable.download, options);
        im.setImageBitmap(operation);
    }

    public void Undo(View view){
        im.setImageBitmap(bmp);
    }

    public void Redo(View view){

        im.setImageBitmap(operation);
    }

    /*public void doodle(View view){


        Canvas canvas = new Canvas();
        //canvas.drawBitmap();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(10);
        canvas.drawText("Some Text here", x, y, paint);
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                Log.d(TAG, "Action was UP");
                break;

            case MotionEvent.ACTION_UP:
                x2 = event.getX();
               y2 = event.getY();
                float Y = y2 - y1;
                float X = x2 - x1;
                if(x1 < im.getWidth() && x2 < im.getHeight()) {
                    if (X < im.getWidth() && Y < im.getHeight()) {
                        //editText = (EditText)findViewById(R.id.edit);
                        //String string = editText.toString();
                        Canvas canvas = new Canvas();
                        float scale = getResources().getDisplayMetrics().density;
                        Paint p = new Paint();
                        p.setColor(Color.BLACK);
                        //p.setTextSize(24 * scale);
                        //canvas.drawText(string, im.getWidth() / 2, im.getHeight() / 2, p);
                        canvas.drawLine(x1, y1, x2, y2, p);
                    }
                }
                break;

        }

        return super.onTouchEvent(event);
    }

}