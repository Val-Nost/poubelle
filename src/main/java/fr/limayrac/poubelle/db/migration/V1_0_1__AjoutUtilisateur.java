package fr.limayrac.poubelle.db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class V1_0_1__AjoutUtilisateur extends BaseJavaMigration implements SpringJDBCTemplateProvider{

    @Override
    public void migrate(Context context) throws Exception {
        JdbcTemplate jdbcTemplate = jdbcTemplate(context);
        creationTable(jdbcTemplate);
        ajoutUtilisateur(jdbcTemplate);
    }

    public void creationTable(final JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("CREATE TABLE utilisateur(" +
                "id BIGINT NOT NULL PRIMARY KEY auto_increment, " +
                "nom VARCHAR(255)," +
                "prenom VARCHAR(255)," +
                "login VARCHAR(255) UNIQUE NOT NULL, " +
                "password VARCHAR(255) NOT NULL, " +
                "role INTEGER NOT NULL, " +
                "actif BOOLEAN NOT NULL " +
                ")");
    }

    public void ajoutUtilisateur(JdbcTemplate jdbcTemplate) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = "cycliste";
        jdbcTemplate.execute("INSERT INTO utilisateur (actif, login, nom, password, prenom, role) " +
                "VALUES (true, 'cycliste', 'CLAVETTE', '" + bCryptPasswordEncoder.encode(password) +"', 'Tyson', 0)");
        password = "gestionnaire";
        jdbcTemplate.execute("INSERT INTO utilisateur (actif, login, nom, password, prenom, role) " +
                "VALUES (true, 'gestionnaire', 'BONSAINT', '" + bCryptPasswordEncoder.encode(password) +"', 'Antoine', 1)");
        password = "rh";
        jdbcTemplate.execute("INSERT INTO utilisateur (actif, login, nom, password, prenom, role) " +
                "VALUES (true, 'rh', 'TARDIF', '" + bCryptPasswordEncoder.encode(password) +"', 'Nanna', 2)");
        password = "admin";
        jdbcTemplate.execute("INSERT INTO utilisateur (actif, login, nom, password, prenom, role) " +
                "VALUES (true, 'admin', 'COLLIN', '" + bCryptPasswordEncoder.encode(password) +"', 'Virginie', 3)");
    }


}
