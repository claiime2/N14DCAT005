package com.example.zheng.sqlite;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by zheng on 20/04/2018.
 */

public class AdapterTuSach extends BaseAdapter {
    Activity context;
    ArrayList<TuSach> list;

    public AdapterTuSach(Activity context, ArrayList<TuSach> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_main, null);
        TextView txtTen = (TextView) row.findViewById(R.id.textName);
        ImageView imgAnh = (ImageView) row.findViewById(R.id.imageBook);
        Button btnSua = (Button) row.findViewById(R.id.btnEdit);
        Button btnXoa = (Button) row.findViewById(R.id.btnDelete);

        final TuSach tusach = list.get(position);
        txtTen.setText(tusach.ten);


        Bitmap bmpAnh = BitmapFactory.decodeByteArray(tusach.anh, 0, tusach.anh.length);
        imgAnh.setImageBitmap(bmpAnh);

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("ID", tusach.id);
                context.startActivity(intent);
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setTitle("Delete");
                builder.setMessage("Would you delete this?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete(tusach.id);

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return row;
    }

    private void delete(int idTuSach){
        try {
            SQLiteDatabase database = Database.initDatabase(context, "Sach.sqlite");
            database.delete("TuSach","ID = ?",new String[]{idTuSach+""});
            list.clear();
            Cursor cursor = database.rawQuery("SELECT * FROM TuSach", null);
            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String ten = cursor.getString(1);
                byte[] anh = cursor.getBlob(2);

                list.add(new TuSach(anh, id, ten));
            }
            notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
