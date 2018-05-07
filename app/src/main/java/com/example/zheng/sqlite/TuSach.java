package com.example.zheng.sqlite;

/**
 * Created by zheng on 20/04/2018.
 */

public class TuSach {
    public int id;
    public byte[] anh;
    public String ten;

    public TuSach(byte[] anh, int id, String ten) {
        this.id = id;
        this.anh = anh;
        this.ten = ten;
    }
}
