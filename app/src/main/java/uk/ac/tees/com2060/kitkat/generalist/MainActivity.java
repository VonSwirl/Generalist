package uk.ac.tees.com2060.kitkat.generalist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.appindexing.Thing;
//import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    // private GoogleApiClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ///////////////////////////////////////////////////////////////////////////
        //Uncomment this to clean out your database then re-comment to avoid repeat

        /*DatabaseHandler deleteEverything = new DatabaseHandler(this);
        deleteEverything.removeAll();*/
        ///////////////////////////////////////////////////////////////////////////
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Adds a Toolbar to this page and gives it a title
        Toolbar homeBar = (Toolbar) findViewById(R.id.homeBar);
        setSupportActionBar(homeBar);
        homeBar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle(R.string.home);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        //Get a reference to the Button object in the layout (XML) file (the button that is linked on the screen)
        final Context context = this;
        Button addBtn = (Button) findViewById(R.id.addButton);

        //Add event listener to button
        addBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, Add.class); //Links the class to the intended place to go
                        startActivity(intent); //Starts that activity
                    }
                }

        );

        //Get a reference to the Button object in the layout (XML) file (the button that is linked on the screen)
        final Context contxt = this;
        Button viewBtn = (Button) findViewById(R.id.view_Button);

        //Add event listener to button
        viewBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(contxt, ViewListActivity.class); //Links the class to the intended place to go
                        startActivity(intent); //Starts that activity
                    }
                }
        );

        class CalendarActivity extends AppCompatActivity {

            CalendarView calendar;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                final calendar compactCalendarView = (calendar) findViewById(R.id.calendar);

                compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);

                Event ev1 new Event(Color.GREEN, 1433701251000L, "Some extra data that I want to store.");
                compactCalendar.addEvent(ev1);

                // Added event 2 GMT: Sun, 07 Jun 2015 19:10:51 GMT
                Event ev2 = new Event(Color.GREEN, 1433704251000L);
                compactCalendar.addEvent(ev2);


                List<Event> events = compactCalendar.getEvents(1433701251000L);

                Log.d(TAG, "Events: " + events);

                compactCalendarView.setListener(new calendar.calendarListener() {
                    @Override
                    public void onDayClick(Date dateClicked) {
                        List<Event> events = compactCalendarView.getEvents(dateClicked);
                        Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);
                    }

                    @Override
                    public void onMonthScroll(Date firstDayOfNewMonth) {
                        Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
                    }
                });
            }
        }
        //Creating calendar for dashboard


        //Intent intent2 = new Intent(context, ViewList.class); //Links the class to the indened place to go
        //startActivity(intent2); //Starts that activity

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.


        //  client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.

     public Action getIndexApiAction() {
     Thing object = new Thing.Builder()
     .setName("Home Page") // TODO: Define a title for the content shown.
     // TODO: Make sure this auto-generated URL is correct.
     .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
     .build();
     return new Action.Builder(Action.TYPE_VIEW)
     .setObject(object)
     .setActionStatus(Action.STATUS_TYPE_COMPLETED)
     .build();
     }

     @Override public void onStart() {
     super.onStart();

     // ATTENTION: This was auto-generated to implement the App Indexing API.
     // See https://g.co/AppIndexing/AndroidStudio for more information.
     client.connect();
     AppIndex.AppIndexApi.start(client, getIndexApiAction());
     }

     @Override public void onStop() {
     super.onStop();

     // ATTENTION: This was auto-generated to implement the App Indexing API.
     // See https://g.co/AppIndexing/AndroidStudio for more information.
     AppIndex.AppIndexApi.end(client, getIndexApiAction());
     client.disconnect();
     }*/
}
