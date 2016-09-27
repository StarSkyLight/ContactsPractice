package com.example.ziyi.contactspractice;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_search = (Button)findViewById(R.id.button);
        final TextView textView = (TextView)findViewById(R.id.textView);

        button_search.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ContentResolver contentResolver = getContentResolver();
                        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);

                        String result = "";

                        while (cursor.moveToNext()){
                            String massage = "";

                            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                            massage = "ID:"+id + "\n";

                            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                            massage = massage + "Name:" + name + "\n";

                            Cursor phoneNumbers = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+id,null,null);

                            while (phoneNumbers.moveToNext()){
                                String phonenumber = phoneNumbers.getString(phoneNumbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                                massage = massage + "Phone:" + phonenumber + "\n";
                            }

                            Cursor emails = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,null,
                                    ContactsContract.CommonDataKinds.Email.CONTACT_ID+"="+id,null,null);

                            while (emails.moveToNext()){
                                String email = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                                massage = massage + "Email:" + email + "\n";
                            }
                            result = result + massage;

                        }
                        textView.setText(result);
                        Log.v("tag",result);
                    }
                }
        );
    }
}
