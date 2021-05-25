package com.example.db_project.mapper.finalTest;

import com.example.db_project.model.course.Activity;
import com.example.db_project.model.course.FinalTest;
import com.example.db_project.model.course.FinalTestType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FinalTestRowMapper implements RowMapper<FinalTest> {
    @Override
    public FinalTest mapRow(ResultSet resultSet, int i) throws SQLException {
        FinalTest finalTest = new FinalTest();
        finalTest.setId(resultSet.getInt("id"));
        finalTest.setDate(resultSet.getTimestamp("date"));
        Activity activity = new Activity();
        activity.setId(resultSet.getInt("activity"));
        finalTest.setActivity(activity);
        finalTest.setType(FinalTestType.valueOf(resultSet.getString("type")));

        return finalTest;
    }
}
