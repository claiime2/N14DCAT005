package com.example.zheng.sqlite;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnChonAnh, btnThem, btnXoa, btnSua, btnDanhsach;
    EditText edtTen;

    ListView listView;
    ArrayList<TuSach> list;
    AdapterTuSach adapter;
    final String DATABASE_NAME = "Sach.sqlite";
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tusach);

        addControls();
        readData();
    }

    private void addControls() {
        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new AdapterTuSach(this,list);
        listView.setAdapter(adapter);
    }

    private void readData(){
        try {
            database = Database.initDatabase(this, DATABASE_NAME);
            Cursor cursor = database.rawQuery("SELECT * FROM TuSach", null);
            list.clear();
            for (int i = 0; i<cursor.getCount(); i++){
                cursor.moveToPosition(i);
                int id = cursor.getInt(0);
                String ten = cursor.getString(1);
                byte[] anh = cursor.getBlob(2);
                list.add(new TuSach(anh, id, ten));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }
}