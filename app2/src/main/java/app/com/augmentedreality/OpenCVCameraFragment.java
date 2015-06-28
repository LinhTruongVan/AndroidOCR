
package app.com.augmentedreality;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.core.Size;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import app.com.augmentedreality.core.AugmentedCamera;


public class OpenCVCameraFragment extends Fragment implements
        CameraBridgeViewBase.CvCameraViewListener2, View.OnTouchListener {
    private static final String TAG = "OCVSample::Activity";
    public static AugmentedCamera mOpenCvCameraView;
    private List<Size> mResolutionList;
    private MenuItem[] mEffectMenuItems;
    private SubMenu mColorEffectsMenu;
    private MenuItem[] mResolutionMenuItems;
    private SubMenu mResolutionMenu;
    private View rootView;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(getActivity()) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
//                    mOpenCvCameraView.setOnTouchListener(OpenCVCameraFragment.this);
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.opencv_camera_tab, container, false);

        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);

        mOpenCvCameraView = (AugmentedCamera) rootView.findViewById(R.id.augmented_activity_java_surface_view);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
        mOpenCvCameraView.setOnTouchListener(OpenCVCameraFragment.this);

        return rootView;
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        return inputFrame.rgba();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.i(TAG, "onTouch event");
        String iconsStoragePath = Environment.getExternalStorageDirectory() + "/AndroidOCR";
        File sdIconStorageDir = new File(iconsStoragePath);
        //create storage directories, if they don't exist
        sdIconStorageDir.mkdirs();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateAndTime = sdf.format(new Date());
        String fileName = iconsStoragePath + "/" + currentDateAndTime + ".png";
        mOpenCvCameraView.takePicture(fileName);
        Toast.makeText(getActivity(), fileName + " saved", Toast.LENGTH_SHORT).show();

        MainActivity.imagePaths.add(fileName);

        return false;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume() {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, getActivity(), mLoaderCallback);
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

}