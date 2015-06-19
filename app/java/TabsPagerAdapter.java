package app.com.augmentedreality;

/**
 * Created by HeavyRain on 5/10/2015.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // OpenCvCamera fragment activity
                return new OpenCVCameraFragment();
            case 1:
                // Results fragment activity
                return new ResultsFragment();
            case 2:
                // Google Search fragment activity
                return new GoogleSearchFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

}