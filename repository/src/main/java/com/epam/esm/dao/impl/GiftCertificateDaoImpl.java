package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.rowmapper.GiftCertificateRowMapper;
import com.epam.esm.entity.GiftCertificate;
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

import static java.sql.Timestamp.valueOf;
import static java.time.LocalDateTime.now;

@Repository
@PropertySource("classpath:sqlQueries.properties")
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    @Value("${gc.selectAll}")
    private static String SQL_SELECT_ALL_GIFT_CERTIFICATES;
    @Value("${gc.selectById}")
    private static String SQL_SELECT_GIFT_CERTIFICATE_BY_ID;
    @Value("${gc.selectByName}")
    private static String SQL_FIND_GIFT_CERTIFICATE_BY_NAME;
    @Value("${gc.save}")
    private static String SQL_ADD_GIFT_CERTIFICATE;
    @Value("${gc.update}")
    private static String SQL_UPDATE_GIFT_CERTIFICATE;
    @Value("${gc.delete}")
    private static String SQL_DELETE_GIFT_CERTIFICATE;
    @Value("${gc.addTag}")
    private static String SQL_ADD_TAG_TO_GIFT_CERTIFICATE;
    /*private final static String SELECT_TAGS = "SELECT * FROM tags INNER JOIN gift_certificate_tag_link " +
            "ON tag.id = gift_certificate_tag_link.tag_id WHERE gift_certificate_tag_link.gift_certificate_id = ?";*/
    @Value("${gc.selectByTag}")
    private static String SQL_SELECT_GIFT_CERTIFICATE_BY_TAG;

    private final JdbcTemplate jdbcTemplate;
    //private final TagRowMapper tagRowMapper;
    private final GiftCertificateRowMapper giftCertificateRowMapper;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate,
                                  GiftCertificateRowMapper giftCertificateRowMapper){
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateRowMapper = giftCertificateRowMapper;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL_GIFT_CERTIFICATES, giftCertificateRowMapper);
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return jdbcTemplate.query(SQL_SELECT_GIFT_CERTIFICATE_BY_ID, giftCertificateRowMapper).stream().findAny();
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(SQL_DELETE_GIFT_CERTIFICATE, id);
    }

    @Override
    public Optional<GiftCertificate> save(GiftCertificate obj) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Optional<GiftCertificate> giftCertificate = Optional.empty();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(SQL_ADD_GIFT_CERTIFICATE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, obj.getName());
            ps.setString(2, obj.getDescription());
            ps.setBigDecimal(3, obj.getPrice());
            ps.setInt(4, obj.getDuration());
            ps.setTimestamp(5, valueOf(now()));
            ps.setTimestamp(6, valueOf(now()));
            return ps;
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            giftCertificate = findById(keyHolder.getKey().longValue());

        }
        return giftCertificate;
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        return jdbcTemplate.query(SQL_FIND_GIFT_CERTIFICATE_BY_NAME, giftCertificateRowMapper).stream().findAny();
    }

    @Override
    public Optional<GiftCertificate> update(GiftCertificate giftCertificate) {
        jdbcTemplate.update(SQL_UPDATE_GIFT_CERTIFICATE, giftCertificate.getName(), giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getDuration(),
                giftCertificate.getLastUpdateDate(), giftCertificate.getId());
        return findById(giftCertificate.getId());
    }

    @Override
    public void addTagToGiftCertificate(long tagId, long giftCertificateId) {
        jdbcTemplate.update(SQL_ADD_TAG_TO_GIFT_CERTIFICATE, tagId, giftCertificateId);
    }

    @Override
    public List<GiftCertificate> findByTagName(String tagName) {
        return jdbcTemplate.query(SQL_SELECT_GIFT_CERTIFICATE_BY_TAG, giftCertificateRowMapper);
    }
}
