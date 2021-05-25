package com.example.db_project.dao.permission;

import com.example.db_project.model.permission.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository("PermissionRepo")
public class PermissionDataAccessService implements PermissionDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void initializePermissions() {
        String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema='studty_platform_database' and table_type='BASE TABLE'";
        List<String> tableNames = jdbcTemplate.queryForList(sql, String.class);

        String sql2 = "insert into contenttype (model) value (:tableName)";


        List<ContentType> contentTypes = new ArrayList<>();
        for (String tableName : tableNames) {
            String sql4 = "select exists(select * from contenttype where model = ?)";

            int exists = jdbcTemplate.queryForObject(sql4, Integer.class, tableName);
            if (exists != 1) {
                KeyHolder holder = new GeneratedKeyHolder();
                SqlParameterSource parameters = new MapSqlParameterSource()
                        .addValue("tableName", tableName);
                namedParameterJdbcTemplate.update(sql2, parameters, holder);
                contentTypes.add(new ContentType(Objects.requireNonNull(holder.getKey()).intValue(), tableName));
            }

        }

        List<String> actions = Arrays.asList("create", "read", "update", "delete");

        for (ContentType contentType : contentTypes) {
            for (String action : actions) {
                String sql5 = "select exists(select * from permission where action = ? and content_type_id = ?)";

                int exists = jdbcTemplate.queryForObject(sql5, Integer.class, action, contentType.getId());

                if (exists != 1) {
                    String sql3 = "insert into permission (name, content_type_id, action) VALUES (?, ?, ?)";
                    jdbcTemplate.update(sql3,
                            "Can " + action + " " + contentType.getModel(),
                            contentType.getId(), action);
                }
            }
        }

        setStudentPermissions();
        setProfessorPermissions();
        List<Integer> adminPermissions = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            adminPermissions.add(i);
        }
        setPermissions(adminPermissions, 4);
        List<Integer> exclude = new ArrayList<>(Arrays.asList(
                25, 26, 27, 28, 29, 30, 31, 32, 41, 42, 43, 44
        ));
        for (Integer i : exclude) {
            adminPermissions.remove(i);
        }
        setPermissions(adminPermissions, 3);
    }

    public void setStudentPermissions() {
        List<Integer> studentPermissionIds = new ArrayList<>(Arrays.asList(
                2, 6, 14, 17, 18, 19, 20, 22, 25, 26, 27, 28, 33, 34, 35, 36, 37,
                38, 39, 40, 42, 46, 49, 50, 51, 52, 54, 57, 58, 59, 60, 60, 61, 62, 63, 64, 65, 66, 67, 68,
                69, 70, 71, 72, 74, 78, 82, 85, 86, 87, 88, 90, 94, 98
        ));

        setPermissions(studentPermissionIds, 1);

    }

    public void setProfessorPermissions() {
        List<Integer> professorPermission = new ArrayList<>(Arrays.asList(2, 3, 6, 14, 21, 22, 23, 24,
                38, 39, 40, 45, 54, 58, 61, 62, 63, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 83, 84,
                85, 86, 87, 88, 94, 98));

        setPermissions(professorPermission, 2);
    }

    private void setPermissions(List<Integer> permissionIds, int groupId) {
        for (Integer permissionId : permissionIds) {
            String sql = "select exists(select * from groupPermission where groupId = ? and permissionId = ?)";
            int exists = jdbcTemplate.queryForObject(sql, Integer.class, groupId, permissionId);
            if (exists != 1) {
                String sql2 = "insert into grouppermission (groupId, permissionId) VALUES (?, ?)";
                jdbcTemplate.update(sql2, groupId, permissionId);
            }
        }
    }
}



