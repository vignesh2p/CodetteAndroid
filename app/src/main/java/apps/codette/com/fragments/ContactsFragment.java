package apps.codette.com.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import apps.codette.com.pingme.R;


/**
 * Created by user on 02-02-2018.
 */

public class ContactsFragment extends Fragment {

    private CursorAdapter mAdapter;
    ListView listView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contacts_layout, container, false);

        listView = (ListView) view.findViewById(R.id.contacts_list);
        //listView.setAdapter(favefoodadapter);

        getContactList();
        return view;
    }

    private void getView(View view) {
        Cursor cursor = this.getActivity().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

    }

    private Cursor getCursor() {
        Cursor cursor = this.getActivity().getContentResolver().query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        return cursor;
    }


    public void bindView(View view, Context context, Cursor cursor) {
        TextView userName = (TextView)view.findViewById(R.id.textView1);
        userName.setText(cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME)));

        //TextView phoneNumber = (TextView)view.findViewById(R.id.textView2);
        //phoneNumber.setText(cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)));
    }

    private void getContactList() {
        ContentResolver cr = this.getActivity().getContentResolver();// getContentResolver();
        Cursor cur = getCursor();

      /*  String[] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone._ID};

        int[] to  = {R.id.textView1, R.id.textView2};
        SimpleCursorAdapter simpleCursorAdapter;
        simpleCursorAdapter = new SimpleCursorAdapter(this.getActivity().getApplication().getApplicationContext(),
                R.layout.contacts_layout, cur,from,to);
        bindView(listView,this.getContext(),cur);
        listView.setAdapter(simpleCursorAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);*/

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                Log.i("Contacts", "Name: " + name);
                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);

                 /*   String[] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER,
                            ContactsContract.CommonDataKinds.Phone._ID};*/

                //    String[] to = {R.id.text1, R.id.text2};

                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i("Contacts", "Name: " + name);
                        Log.i("Contacts", "Phone Number: " + phoneNo);
                    }
                    pCur.close();
                }


            }
        }
    }
}