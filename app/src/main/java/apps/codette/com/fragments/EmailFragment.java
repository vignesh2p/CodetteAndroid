package apps.codette.com.fragments;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

import apps.codette.com.pingme.R;


/**
 * Created by user on 02-02-2018.
 */

public class EmailFragment extends Fragment  {

    List<String> contacts ;
    MultiAutoCompleteTextView toListView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.email_layout, container, false);

        toListView = view.findViewById(R.id.email_to_list);
        contacts = getContactList();
        String arr[] = {"Apple", "Orange"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this.getContext(), R.layout.email_layout, contacts);
        toListView.setAdapter(arrayAdapter);
        toListView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        return view;
    }

    private List<String> getContactList() {
        contacts = new ArrayList<String>();
        ContentResolver cr = this.getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                Log.i("Contacts", "Name: " + name);
                contacts.add(name);
                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i("Contacts", "Phone Number: " + phoneNo);
                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
        return  contacts;
    }
}
