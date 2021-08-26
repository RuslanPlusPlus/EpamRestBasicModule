package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {



    private JdbcTemplate jdbcTemplate;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<GiftCertificateDao> findAll() {
        return null;
    }

    @Override
    public Optional<GiftCertificateDao> findById(long id) {
        return Optional.empty();
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public GiftCertificateDao save(GiftCertificateDao obj) {
        return null;
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        return null;
    }
}
