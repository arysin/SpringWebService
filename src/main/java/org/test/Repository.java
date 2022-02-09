package org.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

@Component
public class Repository {
    @Autowired
    private JdbcOperations jdbcTemplate;
    @Autowired
    private NamedParameterJdbcOperations namedParameterJdbcTemplate;
    
    public List<Entity> findAll() {
        return jdbcTemplate.query("select * from entity", new EntityMapper());
    }

    public Entity findById(String name) {
        return jdbcTemplate.queryForObject("select * from entity where name=?", new EntityMapper(), name);
    }

    public void deleteById(String name) {
        jdbcTemplate.query("delete from entity where name=?", new EntityMapper(), name);
    }

    public Entity create(Entity newEntity) {
        namedParameterJdbcTemplate.update("insert into entity(name, label) values(:name, :label)", 
                new BeanPropertySqlParameterSource(newEntity));
        return newEntity;
    }

    public Entity update(Entity newEntity) {
        namedParameterJdbcTemplate.update("upset entity set name=:name, label=:label where name=:name", 
                new BeanPropertySqlParameterSource(newEntity));
        return newEntity;
    }

}
