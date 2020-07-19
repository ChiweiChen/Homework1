package com.chiwei.homework1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.opencv.android.InstallCallbackInterface;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class MainActivity extends AppCompatActivity {
    //Chi-Wei Chen 陳頎崴
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Check if OpenCV is available
        if(!OpenCVLoader.initDebug()){
            Log.d("test","No OpenCV");
        }
        else{
            Log.d("test", "OpenCV Ok");
        }

        //Find the ImageView object that's going to be processed
        final ImageView imageView = findViewById(R.id.imageView);

        //Useless TextBox object
        TextView textView = findViewById(R.id.textView);

        //Find the Button object that's going to be used
        Button btn = findViewById(R.id.button);

        //Set the OnClickListener object that's going to detect clicks
        btn.setOnClickListener(new View.OnClickListener() {

            //Get the original image from imageView
            Bitmap orig = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

            //The coordinates of the four points where lines will be drawn from
            Point pointA = new Point(orig.getWidth() / 4, orig.getHeight() / 4);
            Point pointB = new Point(orig.getWidth() / 4, orig.getHeight() * 3 / 4);
            Point pointC = new Point(orig.getWidth() * 3 / 4, orig.getHeight() * 3 / 4);
            Point pointD = new Point(orig.getWidth() * 3 / 4, orig.getHeight() / 4);

            //The line color of the box that's going to be drawn
            Scalar lineColor = new Scalar(255, 0, 0, 255);

            //The width of the box's lines
            int lineWidth = 18;

            //Variable that keeps track of how many times the image was processed.
            int count = 0;

            @Override
            public void onClick(View view) {

                //Do stuff if the image has been processed less than 4 times
                if(count<4) {
                    //Increment the count variable
                    count += 1;

                    //Get the current image from imageView
                    orig = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                    //Create a new Matrix object from the original bitmap
                    Mat img = new Mat(orig.getWidth(), orig.getHeight(), CvType.CV_8UC4);
                    Utils.bitmapToMat(orig, img);

                    //Decide which line should be drawn
                    if (count == 1) {
                        Imgproc.line(img, pointA, pointB, lineColor, lineWidth);

                    } else if (count == 2) {
                        Imgproc.line(img, pointB, pointC, lineColor, lineWidth);

                    } else if (count == 3) {
                        Imgproc.line(img, pointC, pointD, lineColor, lineWidth);

                    } else if (count == 4) {
                        Imgproc.line(img, pointD, pointA, lineColor, lineWidth);

                    }

                    //Create a new Bitmap object from the processed Matrix object
                    Bitmap bitmap = Bitmap.createBitmap(img.width(), img.height(), Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(img, bitmap);

                    //Set the image in imageView to the new bitmap
                    imageView.setImageBitmap(bitmap);
                }

            }
        });
    }
}
