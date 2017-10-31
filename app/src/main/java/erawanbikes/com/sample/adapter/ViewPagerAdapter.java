package erawanbikes.com.sample.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import erawanbikes.com.sample.Fragments.AboutusFragment;
import erawanbikes.com.sample.Fragments.BlogFragment;
import erawanbikes.com.sample.Fragments.ChangePasswordFragment;
import erawanbikes.com.sample.Fragments.ContactusFragment;
import erawanbikes.com.sample.Fragments.EditProfileFragment;
import erawanbikes.com.sample.Fragments.HomeFragment;
import erawanbikes.com.sample.Fragments.MyRidesFragement;


/**
 * Created by acer on 9/10/2017.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 1) {
            return new MyRidesFragement();
        } else if (position == 2) {
            return new ChangePasswordFragment();
        } else if (position == 3) {
            return new EditProfileFragment();
        }  else if (position == 5) {
            return new AboutusFragment();
        } else if (position == 6) {
            return new ContactusFragment();
        }else if (position == 7) {
            return new BlogFragment();
            }
        else return new HomeFragment();
    }

    @Override
    public int getCount() {
        return 6;
    }

}
