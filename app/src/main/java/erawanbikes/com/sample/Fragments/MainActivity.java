package erawanbikes.com.sample.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import erawanbikes.com.sample.Login.Helper;
import erawanbikes.com.sample.R;


public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg;
    private CircleImageView circleView;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;

    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = "http://api.androidhive.info/images/nav-menu-header-bg.jpg";
    private static final String urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOpYvCag1vBARCmZwnVS6rS5t4JLzJ6QgQSBquM0nuTsCpLhYbKljoyS-txg";
    // index to identify current nav menu item
    public static int navItemIndex = 0;
    // tags used to attach the fragments
    private static final String TAG_HOME = "Home";
    private static final String TAG_MYRIDES = "MyRides";
    private static final String TAG_CHANGEPASSWORD = "ChangePassword";
    private static final String TAG_EDITPROFILE = "EditProfile";
    private static final String TAG_LOGOUT = "Logout";
    private static final String TAG_ABOUTUS = "Aboutus";
    private static final String TAG_CONTACTUS = "Contactus";
    private static final String TAG_BLOG = "blog";
    public static String CURRENT_TAG = TAG_HOME;
    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;
    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    private String name,token;
    private Bundle EXTRAS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        ImageView backimage = (ImageView) findViewById(R.id.backimage);

        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        circleView = (CircleImageView) navHeader.findViewById(R.id.circleView);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
        // load nav menu header data
        loadNavHeader();
        setUpNavigationView();

        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // initializing navigation menu
                drawer.openDrawer(GravityCompat.START);

            }
        });
        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

        EXTRAS = getIntent().getExtras();
        try
        {
            this.name = EXTRAS.getString("name");
            this.token = Helper.getAuthenticationHeader(MainActivity.this);


        }catch(NullPointerException npe){

        }catch(NumberFormatException nfe){

        }catch(Exception e){

        }
    }
    private void loadNavHeader() {
        // name, website
        txtName.setText("sample");
    }
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();
        // set toolbar title
        // setToolbarTitle();
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };
        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
        //Closing drawer on item click
        drawer.closeDrawers();
        invalidateOptionsMenu();
    }
    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                // myrides
                MyRidesFragement myridesfragment = new MyRidesFragement();
                return myridesfragment;
            case 2:
                // changepassword fragment
                ChangePasswordFragment changefragment = new ChangePasswordFragment();
                return changefragment;
            case 3:
                // editprofile fragment
                EditProfileFragment editfragment = new EditProfileFragment();
                return editfragment;
            case 4:
                // logout fragment
                finish();
            case 5:
                // aboutfragment fragment
                AboutusFragment aboutfragment = new AboutusFragment();
                return aboutfragment;
            case 6:
                // contactfragment fragment
                ContactusFragment contactfragment = new ContactusFragment();
                return contactfragment;
            case 7:
                // blogfragment fragment
                BlogFragment blogfragment = new BlogFragment();
                return blogfragment;
            default:
                return new HomeFragment();
        }
    }
    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }
    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.myrides:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_MYRIDES;
                        break;
                    case R.id.changepassword:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_CHANGEPASSWORD;
                        break;
                    case R.id.editprofile:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_EDITPROFILE;
                        break;
                    case R.id.logout:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_LOGOUT;
                        break;
                    case R.id.aboutus:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_ABOUTUS;
                        break;
                    case R.id.contactus:
                        navItemIndex = 6;
                        CURRENT_TAG = TAG_CONTACTUS;
                        break;
                    case R.id.blog:
                        navItemIndex = 7;
                        CURRENT_TAG = TAG_BLOG;
                        break;
                    default:
                        navItemIndex = 0;
                }
                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        if (shouldLoadHomeFragOnBackPress) {
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }
        super.onBackPressed();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
