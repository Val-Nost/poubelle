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
        // Cycliste
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = "cycliste";
        jdbcTemplate.execute("INSERT INTO utilisateur (actif, login, nom, password, prenom, role) " +
                "VALUES (true, 'cycliste1', 'MARTIN', '" + bCryptPasswordEncoder.encode(password) +"', 'Léa', 0)");
        jdbcTemplate.execute("INSERT INTO utilisateur (actif, login, nom, password, prenom, role) " +
                "VALUES (true, 'cycliste2', 'DUPONT', '" + bCryptPasswordEncoder.encode(password) +"', 'Thomas', 0)");
        jdbcTemplate.execute("INSERT INTO utilisateur (actif, login, nom, password, prenom, role) " +
                "VALUES (true, 'cycliste3', 'PETIT', '" + bCryptPasswordEncoder.encode(password) +"', 'Sophie', 0)");
        jdbcTemplate.execute("INSERT INTO utilisateur (actif, login, nom, password, prenom, role) " +
                "VALUES (true, 'cycliste4', 'DURAND', '" + bCryptPasswordEncoder.encode(password) +"', 'Pierre', 0)");
        jdbcTemplate.execute("INSERT INTO utilisateur (actif, login, nom, password, prenom, role) " +
                "VALUES (true, 'cycliste5', 'MOREAU', '" + bCryptPasswordEncoder.encode(password) +"', 'Julie', 0)");
        jdbcTemplate.execute("INSERT INTO utilisateur (actif, login, nom, password, prenom, role) " +
                "VALUES (true, 'cycliste6', 'SIMON', '" + bCryptPasswordEncoder.encode(password) +"', 'Alexandre', 0)");
        jdbcTemplate.execute("INSERT INTO utilisateur (actif, login, nom, password, prenom, role) " +
                "VALUES (true, 'cycliste7', 'LAURENT', '" + bCryptPasswordEncoder.encode(password) +"', 'Emma', 0)");
        jdbcTemplate.execute("INSERT INTO utilisateur (actif, login, nom, password, prenom, role) " +
                "VALUES (true, 'cycliste8', 'LEFEBVRE', '" + bCryptPasswordEncoder.encode(password) +"', 'Maxime', 0)");
        jdbcTemplate.execute("INSERT INTO utilisateur (actif, login, nom, password, prenom, role) " +
                "VALUES (true, 'cycliste9', 'MICHEL', '" + bCryptPasswordEncoder.encode(password) +"', 'Chloé', 0)");
        jdbcTemplate.execute("INSERT INTO utilisateur (actif, login, nom, password, prenom, role) " +
                "VALUES (true, 'cycliste10', 'ROUX', '" + bCryptPasswordEncoder.encode(password) +"', 'Antoine', 0)");
        jdbcTemplate.execute("INSERT INTO utilisateur (actif, login, nom, password, prenom, role) " +
                "VALUES (true, 'cycliste11', 'DAVID', '" + bCryptPasswordEncoder.encode(password) +"', 'Inès', 0)");
        jdbcTemplate.execute("INSERT INTO utilisateur (actif, login, nom, password, prenom, role) " +
                "VALUES (true, 'cycliste12', 'BERTRAND', '" + bCryptPasswordEncoder.encode(password) +"', 'Lucas', 0)");
        jdbcTemplate.execute("INSERT INTO utilisateur (actif, login, nom, password, prenom, role) " +
                "VALUES (true, 'abazinet', 'BAZINET', '" + bCryptPasswordEncoder.encode(password) +"', 'Amélie', 0)");
        jdbcTemplate.execute("INSERT INTO utilisateur (actif, login, nom, password, prenom, role) " +
                "VALUES (true, 'lturgeon', 'TURGEON', '" + bCryptPasswordEncoder.encode(password) +"', 'Lucas', 0)");
        jdbcTemplate.execute("INSERT INTO utilisateur (actif, login, nom, password, prenom, role) " +
                "VALUES (true, 'mletourneau', 'LETOURNEAU', '" + bCryptPasswordEncoder.encode(password) +"', 'Marcel', 0)");
        jdbcTemplate.execute("INSERT INTO utilisateur (actif, login, nom, password, prenom, role) " +
                "VALUES (true, 'cfrancoeur', 'FRANCOEUR', '" + bCryptPasswordEncoder.encode(password) +"', 'Célie', 0)");
        // Gestionnaire
        password = "gestionnaire";
        jdbcTemplate.execute("INSERT INTO utilisateur (actif, login, nom, password, prenom, role) " +
                "VALUES (true, 'gestionnaire', 'BONSAINT', '" + bCryptPasswordEncoder.encode(password) +"', 'Antoine', 1)");
        // RH
        password = "rh";
        jdbcTemplate.execute("INSERT INTO utilisateur (actif, login, nom, password, prenom, role) " +
                "VALUES (true, 'rh', 'TARDIF', '" + bCryptPasswordEncoder.encode(password) +"', 'Nanna', 2)");
        // Admin
        password = "admin";
        jdbcTemplate.execute("INSERT INTO utilisateur (actif, login, nom, password, prenom, role) " +
                "VALUES (true, 'admin', 'COLLIN', '" + bCryptPasswordEncoder.encode(password) +"', 'Virginie', 3)");
    }


}
