package erawanbikes.com.sample.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TimePicker;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import erawanbikes.com.sample.Login.Helper;
import erawanbikes.com.sample.Models.Bikes;
import erawanbikes.com.sample.R;

/**
 * Created by acer on 10/16/2017.
 */

public class HomeFragment extends Fragment {
    private AppCompatEditText from_time, to_time;
    private Button search;
    private AutoCompleteTextView pickup_location;
    private Date date_selectTo;
    private boolean mEdtFromDateStatus = false;
    private boolean mEdtToDateStatus = false;
    private Date date_selectFrom;
    private int mYear, mMonth, mDay, mHour, mMinute,mpmam,mHour1,mMinute1,mpmam1;
    Geocoder geocoder;
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String API_KEY = "AIzaSyB7d4V2YC81xga-uWl17a9tNIxRjIwc6Ak";

    List<Bikes> bikes_list= new ArrayList<Bikes>();
    public String response;
    String token,oldDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.pickup_location = (AutoCompleteTextView) view.findViewById(R.id.pickup_location);
        this.from_time = (AppCompatEditText) view.findViewById(R.id.fromtime);
        this.to_time = (AppCompatEditText) view.findViewById(R.id.totime);
        this.search = (Button) view.findViewById(R.id.search_bike);

        this.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Helper.checkNow(getActivity())){

                if(pickup_location.length() == 0 ){
                    Helper.showSnackBar(HomeFragment.this.getView(), "Enter Pickup Location");
                }else if(mEdtFromDateStatus == false ){
                    Helper.showSnackBar(HomeFragment.this.getView(), "Enter From Date");
                }else if(mEdtToDateStatus == false){
                    Helper.showSnackBar(HomeFragment.this.getView(), "Enter To Date");
                }
                else {
                    Bundle bundle = new Bundle();
                    bundle.putString("location", pickup_location.getText().toString());
                    bundle.putString("from_date", from_time.getText().toString());
                    bundle.putString("to_date", to_time.getText().toString());

                    Helper.storeLocally("location", pickup_location.getText().toString(), getActivity());
                    Log.d("location local", pickup_location.getText().toString());
                    Helper.storeLocally("from_date", from_time.getText().toString(), getActivity());
                    Helper.storeLocally("to_date", to_time.getText().toString(), getActivity());

                    Intent intent = new Intent(getActivity(), AllBikesListActivity.class);
                    Log.d("values", "vales: " + bundle);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                }else{
                    Helper.showSnackBar(HomeFragment.this.getView(), "No internet connection");
                }
            }
        });
        this.from_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();

                int y = c.get(Calendar.YEAR);
                int m = c.get(Calendar.MONTH);
                int d = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog DPF_startDate = new DatePickerDialog(getActivity(), myDateListener1, y, m, d);
                DPF_startDate.show();
            }
        });

        this.to_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c1 = Calendar.getInstance();

                int y1 = c1.get(Calendar.YEAR);
                int m1 = c1.get(Calendar.MONTH);
                int d1 = c1.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog DPF_endDate = new DatePickerDialog(getActivity(), myDateListener2, y1, m1, d1);
                DPF_endDate.show();
            }
        });
        geocoder = new Geocoder(getActivity());
        this.pickup_location.setAdapter(new GooglePlacesAutocompleteAdapter(getActivity(), R.layout.list_item));
        this.pickup_location.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                return false;
            }
        });
    }

    private DatePickerDialog.OnDateSetListener myDateListener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub

            Date today = new Date();
            date_selectFrom = new Date(arg1 - 1900, arg2, arg3);
            String currentDate = (String) android.text.format.DateFormat.format("dd-MM-yyyy", today);
             oldDate = (String) android.text.format.DateFormat.format("dd-MM-yyyy", date_selectFrom);

            if (date_selectFrom.before(today) && !oldDate.equals(currentDate)) {
                // FireToast.customSnackbar(getApplicationContext(), IOUtils.Font(R.string.validated_date, getApplicationContext()), IOUtils.Font(R.string.swipe, getApplicationContext()));
                from_time.setText("");
                mEdtFromDateStatus = false;
                Helper.showSnackBar(getView(), "Date has to be more than current date");
            } else {
                from_time.setText(oldDate);
                to_time.setText("");
                mEdtToDateStatus = false;
                mEdtFromDateStatus = true;
            }

            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
            mpmam = c.get(Calendar.AM_PM);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            from_time.setText(oldDate  +hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    };

    private DatePickerDialog.OnDateSetListener myDateListener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day

            Date today = new Date();
            date_selectTo = new Date(arg1 - 1900, arg2, arg3);
            String currentDate = (String) android.text.format.DateFormat.format("dd-MM-yyyy", today);
             oldDate = (String) android.text.format.DateFormat.format("dd-MM-yyyy", date_selectTo);
            String From_date = from_time.getText().toString().trim();

            if (mEdtFromDateStatus) {
                if (date_selectTo.before(today) && !oldDate.equals(currentDate) || date_selectTo.before(date_selectFrom)) {
                    // FireToast.customSnackbar(getApplicationContext(), IOUtils.Font(R.string.validated_date, getApplicationContext()), IOUtils.Font(R.string.swipe, getApplicationContext()));
                    to_time.setText("");
                    mEdtToDateStatus = false;
                    Helper.showSnackBar(getView(), "End date should more than start date");
                } else {
                    to_time.setText(oldDate);
                    mEdtToDateStatus = true;
                }
            } else {
                Toast.makeText(getActivity(), "Select start Date", Toast.LENGTH_SHORT).show();
            }

            final Calendar c1 = Calendar.getInstance();
            mHour1 = c1.get(Calendar.HOUR_OF_DAY);
            mMinute1 = c1.get(Calendar.MINUTE);
            mpmam1 = c1.get(Calendar.AM_PM);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog1 = new TimePickerDialog(getActivity(),
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay1,
                                              int minute1) {

                            to_time.setText(oldDate  +hourOfDay1 + ":" + minute1);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog1.show();
        }
    };


    public static ArrayList autocomplete(String input) {
        ArrayList resultList = null;
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append("&components=");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {

            return resultList;
        } catch (IOException e) {

            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {

        }

        return resultList;
    }
    class GooglePlacesAutocompleteAdapter extends ArrayAdapter implements Filterable {
        private ArrayList resultList;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public Object getItem(int index) {
            return resultList.get(index);
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());
                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }
}
