package fr.limayrac.poubelle.db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;

public class V1_0_3__AjoutVelo extends BaseJavaMigration implements SpringJDBCTemplateProvider{

    @Override
    public void migrate(Context context) throws Exception {
        JdbcTemplate jdbcTemplate = jdbcTemplate(context);
        creationTable(jdbcTemplate);
        ajoutVelo(jdbcTemplate);
    }

    public void creationTable(final JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("CREATE TABLE velo(" +
                "id BIGINT NOT NULL PRIMARY KEY auto_increment, " +
                "autonomie INTEGER," +
                "autonomie_max INTEGER," +
                "charge INTEGER, " +
                "charge_max INTEGER, " +
                "mode_ramassage BOOLEAN, " +
                "statut_velo INTEGER " +
                ")");
    }

    public void ajoutVelo(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("INSERT INTO velo(autonomie, autonomie_max, charge, charge_max, statut_velo, mode_ramassage) VALUES " +
                "(50, 50, 0, 200, 0, true)," +
                "(50, 50, 0, 200, 0, true)," +
                "(50, 50, 0, 200, 0, true)," +
                "(50, 50, 0, 200, 0, true)," +
                "(50, 50, 0, 200, 0, true)," +
                "(50, 50, 0, 200, 0, true)," +
                "(50, 50, 0, 200, 0, true)," +
                "(50, 50, 0, 200, 0, true)," +
                "(50, 50, 0, 200, 0, true)," +
                "(50, 50, 0, 200, 0, true)," +
                "(50, 50, 0, 200, 0, true)," +
                "(50, 50, 0, 200, 0, true)," +
                "(50, 50, 0, 200, 0, true)," +
                "(50, 50, 0, 200, 0, true)," +
                "(50, 50, 0, 200, 0, true)," +
                "(50, 50, 0, 200, 0, true)," +
                "(50, 50, 0, 200, 0, true)," +
                "(50, 50, 0, 200, 0, true)," +
                "(50, 50, 0, 200, 0, true)," +
                "(50, 50, 0, 200, 0, true)," +
                "(50, 50, 0, 200, 0, true)," +
                "(50, 50, 0, 200, 0, true)," +
                "(50, 50, 0, 200, 0, true)," +
                "(50, 50, 0, 200, 0, true)," +
                "(50, 50, 0, 200, 0, true)," +
                "(50, 50, 0, 200, 0, true)");
    }


}
