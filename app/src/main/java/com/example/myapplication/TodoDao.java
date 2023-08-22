package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao {
    @Query("SELECT * FROM kangwon_apt")
    List<Todo> getAll();

    @Insert
    void insert(Todo todo);

    @Update
    void update(Todo todo);

    @Delete
    void delete(Todo todo);

    @Query("SELECT * FROM kangwon_apt WHERE type LIKE '%' || :type || '%' AND si LIKE '%' || :si || '%' AND gu LIKE '%' || :gu || '%'AND maxdealprice <= :maxDealPrice AND mindealprice >= :minDealPrice AND maxlprice <= :maxlPrice AND minlPrice >= :minlPrice ORDER BY lrate ASC")
    List<Todo> getByName(String type,String si,String gu,int maxDealPrice, int minDealPrice ,int maxlPrice, int minlPrice );

    @Query("SELECT * FROM kangwon_apt WHERE type LIKE '%' || :type || '%' AND name LIKE '%' || :name || '%'AND si LIKE '%' || :si || '%' AND gu LIKE '%' || :gu || '%'AND maxdealprice <= :maxDealPrice AND mindealprice >= :minDealPrice AND maxlprice <= :maxlPrice AND minlPrice >= :minlPrice ORDER BY lrate ASC")
    List<Todo> getByName1(String type,String name,String si,String gu,int maxDealPrice, int minDealPrice ,int maxlPrice, int minlPrice );

    @Query("SELECT * FROM kangwon_apt WHERE type LIKE '%' || :type || '%' AND si LIKE '%' || :si || '%' AND gu LIKE '%' || :gu || '%' ORDER BY lrate ASC")
    List<Todo> getBYsigu(String type,String si,String gu);


    @Query("SELECT * FROM kangwon_apt WHERE maxdealprice <= :maxDealPrice AND mindealprice >= :minDealPrice ORDER BY lrate ASC")
    List<Todo> getByDealPriceRange(int maxDealPrice , int minDealPrice);

    @Query("SELECT * FROM kangwon_apt WHERE maxlprice <= :maxlPrice AND minlPrice >= :minlPrice ORDER BY lrate ASC")
    List<Todo> getBylPriceRange(int minlPrice, int maxlPrice);

    //getBYALlcon
    @Query("SELECT * FROM kangwon_apt WHERE type LIKE '%' || :type || '%' AND name LIKE '%' || :name || '%' AND si LIKE '%' || :si || '%' AND gu LIKE '%' || :gu || '%'AND maxdealprice <= :maxDealPrice AND mindealprice >= :minDealPrice AND maxlprice <= :maxlPrice AND minlPrice >= :minlPrice ORDER BY lrate ASC")
    List<Todo> getBYALlcon(String type,String name,String si,String gu,int maxDealPrice, int minDealPrice ,int maxlPrice, int minlPrice );
}
