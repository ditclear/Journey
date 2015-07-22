package vienan.app.journey.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import vienan.app.journey.model.CountyBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table county.
*/
public class CountyBeanDao extends AbstractDao<CountyBean, Long> {

    public static final String TABLENAME = "county";

    /**
     * Properties of entity CountyBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property County_name = new Property(1, String.class, "county_name", false, "COUNTY_NAME");
        public final static Property County_code = new Property(2, String.class, "county_code", false, "COUNTY_CODE");
        public final static Property City_id = new Property(3, Integer.class, "city_id", false, "CITY_ID");
    };


    public CountyBeanDao(DaoConfig config) {
        super(config);
    }
    
    public CountyBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'county' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'COUNTY_NAME' TEXT," + // 1: county_name
                "'COUNTY_CODE' TEXT," + // 2: county_code
                "'CITY_ID' INTEGER);"); // 3: city_id
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "IDX_county__id ON county" +
                " (_id);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'county'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, CountyBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String county_name = entity.getCounty_name();
        if (county_name != null) {
            stmt.bindString(2, county_name);
        }
 
        String county_code = entity.getCounty_code();
        if (county_code != null) {
            stmt.bindString(3, county_code);
        }
 
        Integer city_id = entity.getCity_id();
        if (city_id != null) {
            stmt.bindLong(4, city_id);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public CountyBean readEntity(Cursor cursor, int offset) {
        CountyBean entity = new CountyBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // county_name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // county_code
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3) // city_id
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, CountyBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCounty_name(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCounty_code(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCity_id(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(CountyBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(CountyBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
