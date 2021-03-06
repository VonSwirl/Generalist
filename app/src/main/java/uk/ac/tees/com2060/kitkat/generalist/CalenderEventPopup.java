package uk.ac.tees.com2060.kitkat.generalist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeepThought2 on 09/05/17.
 */

public class CalenderEventPopup extends AppCompatActivity {

    private PopListAdapter adapter;
    DatabaseHandler dh;
    public ArrayList<ListInfo> entries = new ArrayList<>();

    //Default Constructor
    public CalenderEventPopup() {
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Database handling, getting all the items adding them to an array list
        dh = new DatabaseHandler(this);

        setContentView(R.layout.activity_main_popup);

        Intent i = getIntent();
        Bundle args = i.getBundleExtra("BUNDLE");
        entries = (ArrayList<ListInfo>) args.getSerializable("ARRAYLIST");
        //entries = i.getStringArrayListExtra("name_of_extra");
        System.out.println("THE ENTRIES " + entries);

        //Creating the list view
        ListView lv = (ListView) findViewById(R.id.listthis);
        adapter = new PopListAdapter(this, R.layout.view_row, entries);
        lv.setAdapter(adapter);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .85), (int) (height * .60)); //Sets the size of the popup window
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //Stops the background clipping white
        // SORT BY DATE UNCOMMENT
        //Collections.sort(value, new ChangeComparator());
    }

    private class PopListAdapter extends ArrayAdapter<ListInfo> {

        private int layout;
        private List<ListInfo> mObjects;

        //Default constructor
        public PopListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ListInfo> objects) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
        }

        //Used for getting the view and creating the buttons etc
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewListActivity.ViewHolder mainViewholder = null;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewListActivity.ViewHolder viewHolder = new ViewListActivity.ViewHolder();
                viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.list_item_tickbox);
                viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_text);
                viewHolder.editBtn = (ImageButton) convertView.findViewById(R.id.list_item_editBtn);
                viewHolder.delBtn = (ImageButton) convertView.findViewById(R.id.list_item_delBtn);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewListActivity.ViewHolder) convertView.getTag();

            mainViewholder.editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), Editing.class); //Links the class to the intended place to go
                    intent.putExtra("id", mObjects.get(position).getID()); //Passes in the position to be used
                    intent.putExtra("arrayIndex", position);
                    startActivityForResult(intent, 1); //Start the activity and pass 1 as a resultCode
                }
            });

            mainViewholder.delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), " Item Deleted " + position, Toast.LENGTH_SHORT).show();
                    ListInfo toRemove = mObjects.get(position);

                    dh.deleteItem(toRemove.getID());
                    mObjects.remove(position);

                    Log.d("test", "delete at " + position);
                    adapter.notifyDataSetChanged();
                    //recreate();
                }
            });

            //Check box listener
            mainViewholder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton Checkbutton, boolean checked) {
                    if (Checkbutton.isChecked()) {
                        System.out.println("CheckBox ticked at " + position);
                        dh.updateChecked(mObjects.get(position).getID(), 1); //Updated the checked box in the data base when the tickbox at position is pressed
                    } else if (!Checkbutton.isChecked()) {
                        System.out.println("CheckBox unticked at " + position);
                        dh.updateChecked(mObjects.get(position).getID(), 0); //Updated the checked box in the data base when the tickbox at position is pressed
                    }
                }
            });
            //Check if the database value and set the checkbox accordingly
            if (mObjects.get(position).getChecked() == 1) {
                mainViewholder.checkBox.setChecked(true);
            } else if (mObjects.get(position).getChecked() == 0) {
                mainViewholder.checkBox.setChecked(false);
            }

            mainViewholder.title.setText(getItem(position).toString());
            return convertView;
        }
    }

    //Gets the result from a returned activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String updatedName = data.getStringExtra("updatedName"); //Get the updated name from the edit class
            String updatedContents = data.getStringExtra("updatedContents"); //Get the updated contents from the edit class
            int id = data.getIntExtra("id", 0); //Get the current position it was editing
            int arrayIndex = data.getIntExtra("arrayIndex", 0);

            adapter.mObjects.get(arrayIndex).setName(updatedName); //Set the new name where the current position is
            adapter.mObjects.get(arrayIndex).setContents(updatedContents);

            adapter.notifyDataSetChanged(); //update the viewlist
        }
    }
}//ClassEnd