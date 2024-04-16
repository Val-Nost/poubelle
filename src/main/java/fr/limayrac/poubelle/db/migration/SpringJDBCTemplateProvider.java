package fr.limayrac.poubelle.db.migration;

import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

interface SpringJDBCTemplateProvider {
    default JdbcTemplate jdbcTemplate(Context context) {
        return new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true));
    }
}