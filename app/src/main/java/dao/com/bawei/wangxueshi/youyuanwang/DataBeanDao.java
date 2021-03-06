package com.bawei.wangxueshi.youyuanwang;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.bawei.wangxueshi.youyuanwang.bean.findbean.DataBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "loadbean".
*/
public class DataBeanDao extends AbstractDao<DataBean, Long> {

    public static final String TABLENAME = "loadbean";

    /**
     * Properties of entity DataBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Area = new Property(1, String.class, "area", false, "AREA");
        public final static Property Relation = new Property(2, int.class, "relation", false, "RELATION");
        public final static Property PicWidth = new Property(3, int.class, "picWidth", false, "PIC_WIDTH");
        public final static Property Createtime = new Property(4, long.class, "createtime", false, "CREATETIME");
        public final static Property PicHeight = new Property(5, int.class, "picHeight", false, "PIC_HEIGHT");
        public final static Property Gender = new Property(6, String.class, "gender", false, "GENDER");
        public final static Property Lng = new Property(7, double.class, "lng", false, "LNG");
        public final static Property Introduce = new Property(8, String.class, "introduce", false, "INTRODUCE");
        public final static Property ImagePath = new Property(9, String.class, "imagePath", false, "IMAGE_PATH");
        public final static Property UserId = new Property(10, int.class, "userId", false, "USER_ID");
        public final static Property Yxpassword = new Property(11, String.class, "yxpassword", false, "YXPASSWORD");
        public final static Property Password = new Property(12, String.class, "password", false, "PASSWORD");
        public final static Property Lasttime = new Property(13, long.class, "lasttime", false, "LASTTIME");
        public final static Property Phone = new Property(14, String.class, "phone", false, "PHONE");
        public final static Property Nickname = new Property(15, String.class, "nickname", false, "NICKNAME");
        public final static Property Age = new Property(16, String.class, "age", false, "AGE");
        public final static Property Lat = new Property(17, double.class, "lat", false, "LAT");
    }


    public DataBeanDao(DaoConfig config) {
        super(config);
    }
    
    public DataBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"loadbean\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"AREA\" TEXT NOT NULL ," + // 1: area
                "\"RELATION\" INTEGER NOT NULL ," + // 2: relation
                "\"PIC_WIDTH\" INTEGER NOT NULL ," + // 3: picWidth
                "\"CREATETIME\" INTEGER NOT NULL ," + // 4: createtime
                "\"PIC_HEIGHT\" INTEGER NOT NULL ," + // 5: picHeight
                "\"GENDER\" TEXT NOT NULL ," + // 6: gender
                "\"LNG\" REAL NOT NULL ," + // 7: lng
                "\"INTRODUCE\" TEXT NOT NULL ," + // 8: introduce
                "\"IMAGE_PATH\" TEXT NOT NULL ," + // 9: imagePath
                "\"USER_ID\" INTEGER NOT NULL ," + // 10: userId
                "\"YXPASSWORD\" TEXT NOT NULL ," + // 11: yxpassword
                "\"PASSWORD\" TEXT NOT NULL ," + // 12: password
                "\"LASTTIME\" INTEGER NOT NULL ," + // 13: lasttime
                "\"PHONE\" TEXT NOT NULL ," + // 14: phone
                "\"NICKNAME\" TEXT NOT NULL ," + // 15: nickname
                "\"AGE\" TEXT NOT NULL ," + // 16: age
                "\"LAT\" REAL NOT NULL );"); // 17: lat
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"loadbean\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DataBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getArea());
        stmt.bindLong(3, entity.getRelation());
        stmt.bindLong(4, entity.getPicWidth());
        stmt.bindLong(5, entity.getCreatetime());
        stmt.bindLong(6, entity.getPicHeight());
        stmt.bindString(7, entity.getGender());
        stmt.bindDouble(8, entity.getLng());
        stmt.bindString(9, entity.getIntroduce());
        stmt.bindString(10, entity.getImagePath());
        stmt.bindLong(11, entity.getUserId());
        stmt.bindString(12, entity.getYxpassword());
        stmt.bindString(13, entity.getPassword());
        stmt.bindLong(14, entity.getLasttime());
        stmt.bindString(15, entity.getPhone());
        stmt.bindString(16, entity.getNickname());
        stmt.bindString(17, entity.getAge());
        stmt.bindDouble(18, entity.getLat());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DataBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getArea());
        stmt.bindLong(3, entity.getRelation());
        stmt.bindLong(4, entity.getPicWidth());
        stmt.bindLong(5, entity.getCreatetime());
        stmt.bindLong(6, entity.getPicHeight());
        stmt.bindString(7, entity.getGender());
        stmt.bindDouble(8, entity.getLng());
        stmt.bindString(9, entity.getIntroduce());
        stmt.bindString(10, entity.getImagePath());
        stmt.bindLong(11, entity.getUserId());
        stmt.bindString(12, entity.getYxpassword());
        stmt.bindString(13, entity.getPassword());
        stmt.bindLong(14, entity.getLasttime());
        stmt.bindString(15, entity.getPhone());
        stmt.bindString(16, entity.getNickname());
        stmt.bindString(17, entity.getAge());
        stmt.bindDouble(18, entity.getLat());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public DataBean readEntity(Cursor cursor, int offset) {
        DataBean entity = new DataBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // area
            cursor.getInt(offset + 2), // relation
            cursor.getInt(offset + 3), // picWidth
            cursor.getLong(offset + 4), // createtime
            cursor.getInt(offset + 5), // picHeight
            cursor.getString(offset + 6), // gender
            cursor.getDouble(offset + 7), // lng
            cursor.getString(offset + 8), // introduce
            cursor.getString(offset + 9), // imagePath
            cursor.getInt(offset + 10), // userId
            cursor.getString(offset + 11), // yxpassword
            cursor.getString(offset + 12), // password
            cursor.getLong(offset + 13), // lasttime
            cursor.getString(offset + 14), // phone
            cursor.getString(offset + 15), // nickname
            cursor.getString(offset + 16), // age
            cursor.getDouble(offset + 17) // lat
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DataBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setArea(cursor.getString(offset + 1));
        entity.setRelation(cursor.getInt(offset + 2));
        entity.setPicWidth(cursor.getInt(offset + 3));
        entity.setCreatetime(cursor.getLong(offset + 4));
        entity.setPicHeight(cursor.getInt(offset + 5));
        entity.setGender(cursor.getString(offset + 6));
        entity.setLng(cursor.getDouble(offset + 7));
        entity.setIntroduce(cursor.getString(offset + 8));
        entity.setImagePath(cursor.getString(offset + 9));
        entity.setUserId(cursor.getInt(offset + 10));
        entity.setYxpassword(cursor.getString(offset + 11));
        entity.setPassword(cursor.getString(offset + 12));
        entity.setLasttime(cursor.getLong(offset + 13));
        entity.setPhone(cursor.getString(offset + 14));
        entity.setNickname(cursor.getString(offset + 15));
        entity.setAge(cursor.getString(offset + 16));
        entity.setLat(cursor.getDouble(offset + 17));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(DataBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(DataBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(DataBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
