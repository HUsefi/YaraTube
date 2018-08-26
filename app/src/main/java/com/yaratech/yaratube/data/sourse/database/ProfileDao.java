package com.yaratech.yaratube.data.sourse.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.yaratech.yaratube.data.model.DBModel.Profile;

import java.util.List;

/**
 * Created by Vah on 8/24/2018.
 */

@Dao
public interface ProfileDao {

    @Insert
    void insertProfile(Profile... profile);

    @Update
    void updateProfile(Profile profile);

    @Query("select mobile " +
            "from profile " +
            "where id = 0")
    String getMobile();

    @Query("select count(*) " +
            "from profile")
    int getProfile();
}