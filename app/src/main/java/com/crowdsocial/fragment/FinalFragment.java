package com.crowdsocial.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crowdsocial.R;

public class FinalFragment extends Fragment {

    OnContactSelectedListener listener;

    public interface OnContactSelectedListener {
        public void onContactSelected(String email, String name);
    }

    private SimpleCursorAdapter contactsAdapter;
    public static final int CONTACT_LOADER_ID = 78;

    private LoaderManager.LoaderCallbacks<Cursor> contactsLoader =
        new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                String[] projectionFields = new String[] {
                        ContactsContract.CommonDataKinds.Email._ID,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID,
                        ContactsContract.CommonDataKinds.Email.ADDRESS,
                        ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.Contacts.PHOTO_URI
                };

                return new CursorLoader(FinalFragment.this.getContext(),
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI, // URI
                        projectionFields, // projection fields
                        null, // the selection criteria
                        null, // the selection args
                        null // the sort order
                );
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                contactsAdapter.swapCursor(cursor);
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                contactsAdapter.swapCursor(null);

            }
        };

    public static FinalFragment newInstance() {
        return new FinalFragment();
    }

    public FinalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupCursorAdapter();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnContactSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " " +
                    "must implement OnContactSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_final, container, false);

        ListView lvContacts = (ListView) v.findViewById(R.id.lvContacts);
        lvContacts.setAdapter(contactsAdapter);

        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor c = ((SimpleCursorAdapter) parent.getAdapter()).getCursor();
                c.moveToPosition(position);
                c.getString(c.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
                listener.onContactSelected(
                        c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)),
                        c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
            }
        });

        getActivity().getSupportLoaderManager().initLoader(CONTACT_LOADER_ID,
                new Bundle(), contactsLoader);

        return v;
    }

    private void setupCursorAdapter() {
        // Column data from cursor to bind views from
        String[] uiBindFrom = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_URI
        };
        // View IDs which will have the respective column data inserted
        int[] uiBindTo = { R.id.tvEmail, R.id.tvName, R.id.ivImage };
        // Create the simple cursor adapter to use for our list
        // specifying the template to inflate (item_contact),
        contactsAdapter = new SimpleCursorAdapter(
                this.getContext(), R.layout.item_contact,
                null, uiBindFrom, uiBindTo,
                0);
    }
}
