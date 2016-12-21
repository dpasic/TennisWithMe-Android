package hr.vspr.dpasic.tenniswithme.model;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by edjapas on 20.12.2016..
 */

@Database(name = LocalDb.NAME, version = LocalDb.VERSION)
public class LocalDb {

    public static final String NAME = "TennisWithMe";
    public static final int VERSION = 1;
}
