package com.example.zheng.sqlite;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by zheng on 21/04/2018.
 */

public class UpdateActivity extends AppCompatActivity {
    final String DATABASE_NAME = "Sach.sqlite";
    int id = -1;
    Button btnChonHinh, btnLuu, btnHuy;
    EditText edtTen;
    ImageView imgHinhDaiDien;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);

        addControls();
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
        init();
    }



    private void addControls() {
        btnLuu = (Button) findViewById(R.id.btnSave);
        btnHuy = (Button) findViewById(R.id.btnCancel);
        edtTen = (EditText) findViewById(R.id.edtName);
        imgHinhDaiDien = (ImageView) findViewById(R.id.imageBook);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
    }
    private void init() {
        Intent intent = getIntent();
        id = intent.getIntExtra("ID", -1);
        try {
            SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
            Cursor cursor = database.rawQuery("SELECT * FROM Tusach WHERE ID = ? ", new String[]{id+""});
            if( cursor != null && cursor.moveToFirst() ){
                String ten = cursor.getString(1);
                byte[] anh = cursor.getBlob(2);
                Bitmap bitmap = BitmapFactory.decodeByteArray(anh, 0, anh.length);
                imgHinhDaiDien.setImageBitmap(bitmap);
                edtTen.setText(ten);
                cursor.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void update(){
        String ten = edtTen.getText().toString();
        byte[] anh = getByteArrayFromImageView(imgHinhDaiDien);

        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("Ten", ten);
        contentValues.put("HinhAnh", anh);

        try {
            SQLiteDatabase database = Database.initDatabase(this, "Sach.sqlite");
            database.update("TuSach", contentValues, "id = ?",new String[]{id+""});
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void cancel(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private byte[] getByteArrayFromImageView(ImageView imgv){

        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
