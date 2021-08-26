package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.rowmapper.TagRowMapper;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;


@Repository
@PropertySource("classpath:sqlQueries.properties")
public class TagDaoImpl implements TagDao {

    @Value("${tg.selectAll}")
    private String SQL_SELECT_ALL_SQL_TAGS;
    @Value("${tg.selectById}")
    private String SQL_SELECT_TAG_BY_ID;
    @Value("${tg.delete}")
    private String SQL_DELETE_TAG;
    @Value("${tg.save}")
    private String SQL_ADD_TAG;

    private final JdbcTemplate jdbcTemplate;
    private final TagRowMapper tagRowMapper;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate,
                      TagRowMapper tagRowMapper){
        this.jdbcTemplate = jdbcTemplate;
        this.tagRowMapper = tagRowMapper;
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL_SQL_TAGS, tagRowMapper);
    }

    @Override
    public Optional<Tag> findById(long id) {
        return jdbcTemplate.query(SQL_SELECT_TAG_BY_ID, tagRowMapper).stream().findAny();
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(SQL_DELETE_TAG, id);
    }

    @Override
    public Optional<Tag> save(Tag obj) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Optional<Tag> tag = Optional.empty();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(SQL_ADD_TAG, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, obj.getName());

            return ps;
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            tag = findById(keyHolder.getKey().longValue());
        }
        return tag;
    }
}
