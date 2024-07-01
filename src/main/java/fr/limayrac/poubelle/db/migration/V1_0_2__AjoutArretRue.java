package fr.limayrac.poubelle.db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;

public class V1_0_2__AjoutArretRue extends BaseJavaMigration implements SpringJDBCTemplateProvider{

    @Override
    public void migrate(Context context) throws Exception {
        JdbcTemplate jdbcTemplate = jdbcTemplate(context);
        creationTableArret(jdbcTemplate);
        creationTableRue(jdbcTemplate);
        createArretVoisin(jdbcTemplate);

        ajoutArret(jdbcTemplate);
        ajoutRues(jdbcTemplate);
        ajoutArretVoisin(jdbcTemplate);
    }

    public void creationTableArret(final JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("CREATE TABLE arret(" +
                "id BIGINT NOT NULL PRIMARY KEY auto_increment, " +
                "libelle VARCHAR(255) UNIQUE, " +
                "ramasse BOOLEAN NOT NULL DEFAULT false" +
                ")");
    }

    public void creationTableRue(final JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("CREATE TABLE rue(" +
                "id BIGINT NOT NULL PRIMARY KEY auto_increment, " +
                "libelle VARCHAR(255) UNIQUE" +
                ")");
    }

    public void ajoutArret(JdbcTemplate jdbcTemplate) {
        // Rue Croix-Baragnon
        jdbcTemplate.execute("INSERT INTO arret (libelle) " +
                "VALUES ('La Défense'), ('Esplanade de la Défense'), ('Pont de Neuilly'), ('Les Sablons'), ('Porte Maillot'), ('Argentine'), ('Charles de Gaulle-Étoile'), ('George V'), ('Franklin D. Roosevelt'), ('Champs-Élysées-Clemenceau'), ('Concorde'), ('Tuileries'), ('Palais Royal-Musée du Louvre'), ('Louvre-Rivoli'), ('Châtelet'), ('Hôtel de ville'), ('Saint-Paul'), ('Bastille'), ('Gare de Lyon'), ('Reuilly-Diderot'), ('Nation'), ('Porte de Vincennes'), ('Saint-Mandé'), ('Bérault'), ('Château de Vincennes')");
        // Rue des arts
        jdbcTemplate.execute("INSERT INTO arret (libelle) " +
                "VALUES ('Porte Dauphine'), ('Victor Hugo'), ('Ternes'), ('Courcelles'), ('Monceau'), ('Villiers'), ('Rome'), ('Place de Clichy'), ('Blanche'), ('Pigalle'), ('Anvers'), ('Barbès-Rochechouart'), ('La Chapelle'), ('Stalingrad'), ('Jaurès'), ('Colonel Fabien'), ('Belleville'), ('Couronnes'), ('Ménilmontant'), ('Père Lachaise'), ('Philippe Auguste'), ('Alexandre Dumas'), ('Avron')");
        // Rue Pargaminières
        jdbcTemplate.execute("INSERT INTO arret(libelle)" +
                "VALUES ('Pont de Levallois-Bécon'), ('Anatole France'), ('Louise Michel'), ('Porte de Champerret'), ('Pereire'), ('Wagram'), ('Malesherbes'), ('Europe'), ('Saint-Lazare'), ('Havre-Caumartin'), ('Opéra'), ('Quatre-Septembre'), ('Bourse'), ('Sentier'), ('Réaumur-Sébastopol'), ('Arts et Métiers'), ('Temple'), ('République'), ('Parmentier'), ('Rue Saint-Maur'), ('Gambetta'), ('Porte de Bagnolet'), ('Gallieni')");
        // Rue Saint-Rome
        jdbcTemplate.execute("INSERT INTO arret(libelle)" +
                "VALUES ('Pelleport'), ('Saint-Fargeau'), ('Porte des Lilas')");
        // Rue Saint-Antoine du T
        jdbcTemplate.execute("INSERT INTO arret(libelle)" +
                "VALUES ('Porte de Clignancourt'), ('Simplon'), ('Marcadet-Poissonniers'), ('Château Rouge'), ('Gare du Nord'), (\"Gare de l'Est\"), (\"Château d'Eau\"), ('Strasbourg-Saint-Denis'), ('Étienne Marcel'), ('Les Halles'), ('Cité'), ('Saint-Michel-Notre-Dame'), ('Odéon'), ('Saint-Germain-des-Prés'), ('Saint-Sulpice'), ('Saint-Placide'), ('Montparnasse-Bienvenüe'), ('Vavin'), ('Raspail'), ('Denfert-Rochereau'), ('Mouton-Duvernet'), ('Alésia'), (\"Porte d'Orléans\")");
        // Rue de la Fonderie
        jdbcTemplate.execute("INSERT INTO arret(libelle) " +
                "VALUES ('Bobigny-Pablo Picasso'), ('Bobigny-Pantin-Raymond Queneau'), ('Église de Pantin'), ('Hoche'), ('Porte de Pantin'), ('Ourcq'), ('Laumière'), ('Jacques Bonsergent'), ('Oberkampf'), ('Richard-Lenoir'), ('Bréguet-Sabin'), ('Quai de la Rapée'), (\"Gare d'Austerlitz\"), ('Saint-Marcel'), ('Campo-Formio'), (\"Place d'Italie\")");
        // Rue Peyrolières
        jdbcTemplate.execute("INSERT INTO arret(libelle)" +
                "VALUES ('Kléber'), ('Boissière'), ('Trocadéro'), ('Passy'), ('Champ de Mars-Tour Eiffel'), ('Dupleix'), ('La Motte-Picquet-Grenelle'), ('Cambronne'), ('Sèvres-Lecourbe'), ('Pasteur'), ('Edgar Quinet'), ('Saint-Jacques'), ('Glacière'), ('Corvisart'), ('Nationale'), ('Chevaleret'), ('Quai de la Gare'), ('Bercy'), ('Dugommier'), ('Daumesnil'), ('Bel-Air'), ('Picpus')");
        // Rue Genty-Magre
        jdbcTemplate.execute("INSERT INTO arret(libelle)" +
                "VALUES ('La Courneuve-8 Mai 1945'), (\"Fort d'Aubervilliers\"), ('Aubervilliers-Pantin-Quatre Chemins'), ('Porte de la Villette'), ('Corentin Cariou'), ('Crimée'), ('Riquet'), ('Louis Blanc'), ('Château-Landon'), ('Poissonnière'), ('Cadet'), ('Le Peletier'), (\"Chaussée d'Antin-La Fayette\"), ('Pyramides'), ('Pont Neuf'), ('Pont Marie'), ('Sully-Morland'), ('Jussieu'), ('Place Monge'), ('Censier-Daubenton'), ('Les Gobelins'), ('Tolbiac'), ('Maison Blanche'), (\"Porte d'Italie\"), ('Porte de Choisy'), (\"Porte d'Ivry\"), ('Pierre et Marie Curie'), (\"Mairie d'Ivry\")");
        // Rue d'Alsace-Lorraine
        jdbcTemplate.execute("INSERT INTO arret(libelle) " +
                "VALUES ('Bolivar'), ('Buttes Chaumont'), ('Botzaris'), ('Place des Fêtes'), ('Pré Saint-Gervais')");
        // Rue Peyras
        jdbcTemplate.execute("INSERT INTO arret(libelle)" +
                "VALUES ('Balard'), ('Lourmel'), ('Boucicaut'), ('Félix Faure'), ('Commerce'), ('École Militaire'), ('La Tour-Maubourg'), ('Invalides'), ('Madeleine'), ('Richelieu-Drouot'), ('Grands Boulevards'), ('Bonne Nouvelle'), ('Filles du Calvaire'), ('Saint-Sébastien-Froissart'), ('Chemin Vert'), ('Ledru-Rollin'), ('Faidherbe-Chaligny'), ('Montgallet'), ('Michel Bizot'), ('Porte Dorée'), ('Porte de Charenton'), ('Liberté'), ('Charenton-Écoles'), ('École Vétérinaire de Maisons-Alfort'), ('Maisons-Alfort-Stade'), ('Maisons-Alfort-Les Juilliottes'), (\"Créteil-L'Échat\"), ('Créteil-Université'), ('Créteil-Préfecture')");
        // Rue du Taur
        jdbcTemplate.execute("INSERT INTO arret(libelle)" +
                "VALUES ('Pont de Sèvres'), ('Billancourt'), ('Marcel Sembat'), ('Porte de Saint-Cloud'), ('Exelmans'), ('Michel-Ange-Molitor'), ('Michel-Ange-Auteuil'), ('Jasmin'), ('Ranelagh'), ('La Muette'), ('Rue de la Pompe'), ('Iéna'), ('Alma-Marceau'), ('Saint-Philippe du Roule'), ('Miromesnil'), ('Saint-Augustin'), ('Saint-Ambroise'), ('Voltaire'), ('Charonne'), ('Rue des Boulets'), ('Buzenval'), ('Maraîchers'), ('Porte de Montreuil'), ('Robespierre'), ('Croix de Chavaux'), ('Mairie de Montreuil')");
        // Allée de Jean Jaurès
        jdbcTemplate.execute("INSERT INTO arret(libelle)" +
                "VALUES ('Boulogne-Pont de Saint-Cloud'), ('Boulogne-Jean Jaurès'), (\"Porte d'Auteuil\"), (\"Église d'Auteuil\"), ('Javel-André Citroën'), ('Charles Michels'), ('Avenue Émile Zola'), ('Ségur'), ('Duroc'), ('Vaneau'), ('Sèvres-Babylone'), ('Mabillon'), ('Cluny-La Sorbonne'), ('Maubert-Mutualité'), ('Cardinal Lemoine')");
        // Rue du May
        jdbcTemplate.execute("INSERT INTO arret(libelle)" +
                "VALUES ('Rambuteau'), ('Goncourt'), ('Pyrénées'), ('Jourdain'), ('Télégraphe'), ('Mairie des Lilas')");
        // Rue des Filatiers
        jdbcTemplate.execute("INSERT INTO arret(libelle)" +
                "VALUES ('Porte de la Chapelle'), ('Marx Dormoy'), ('Jules Joffrin'), ('Lamarck-Caulaincourt'), ('Abbesses'), ('Saint-Georges'), ('Notre-Dame-de-Lorette'), (\"Trinité-d'Estienne d'Orves\"), ('Assemblée nationale'), ('Solférino'), ('Rue du Bac'), ('Rennes'), ('Notre-Dame-des-Champs'), ('Falguière'), ('Volontaires'), ('Vaugirard'), ('Convention'), ('Porte de Versailles'), ('Corentin Celton'), (\"Mairie d'Issy\")");
        // Rue Mage
        jdbcTemplate.execute("INSERT INTO arret(libelle)" +
                "VALUES ('Saint-Denis-Université'), ('Basilique de Saint-Denis'), ('Saint-Denis-Porte de Paris'), ('Carrefour Pleyel'), ('Mairie de Saint-Ouen'), ('Garibaldi'), ('Porte de Saint-Ouen'), ('Guy Môquet'), ('La Fourche'), ('Liège'), ('Varenne'), ('Saint-François-Xavier'), ('Gaîté'), ('Pernety'), ('Plaisance'), ('Porte de Vanves'), ('Malakoff-Plateau de Vanves'), ('Malakoff-Rue Etienne Dolet'), ('Châtillon-Montrouge')");
        // Rue d'Espinasse
        jdbcTemplate.execute("INSERT INTO arret(libelle)" +
                "VALUES ('Cour Saint-Émilion'), ('Bibliothèque François Mitterand'), ('Olympiades')");
        // Rue des Gestes
        jdbcTemplate.execute("INSERT INTO arret(libelle)" +
                "VALUES ('Gare Saint-Denis'), ('Théâtre Gérard Philipe'), ('Marché de Saint-Denis'), ('Cimetière de Saint-Denis'), ('Hôpital Delafontaine'), ('Cosmonautes'), ('La Courneuve-Six Routes'), ('Hôtel de Ville de la Courneuve'), ('Stade Géo André'), ('Danton'), ('Maurice Lachâtre'), ('Drancy-Avenir'), ('Hôpital Avicenne'), ('Gaston Roulaud'), ('Escadrille Normandie-Niémen'), ('La Ferme'), ('Libération'), ('Hôtel de Ville de Bobigny'), ('Jean Rostand'), ('Auguste Delaune'), ('Pont de Bondy'), ('Petit Noisy'), ('Noisy-le-Sec')");
        // Quai de la Daurade
        jdbcTemplate.execute("INSERT INTO arret(libelle)" +
                "VALUES ('Puteaux'), ('Belvédère'), ('Suresnes-Longchamp'), ('Les Coteaux'), ('Les Milons'), ('Parc de Saint-Cloud'), ('Musée de Sèvres'), ('Brimborion'), ('Meudon-sur-Seine'), ('Les Moulineaux'), ('Jacques-Henri Lartigue'), ('Issy-Val de Seine')");
        // Rue Bédelières
        jdbcTemplate.execute("INSERT INTO arret(libelle)" +
                "VALUES ('Pont du Garigliano'), ('Desnouettes'), ('Georges Brassens'), ('Brancion'), ('Didot'), ('Jean Moulin'), ('Montsouris'), ('Cité universitaire'), ('Stade Charléty'), ('Poterne des Peupliers'), ('Montempoivre')");
        // Rue Merlane
        jdbcTemplate.execute("INSERT INTO arret(libelle)" +
                "VALUES ('Auber'), ('Châtelet-Les Halles')");
        // Rue Vélane
        jdbcTemplate.execute("INSERT INTO arret(libelle)" +
                "VALUES ('Luxembourg'), ('Port-Royal')");
        // Rue Etroite
        jdbcTemplate.execute("INSERT INTO arret(libelle)" +
                "VALUES ('Porte de Clichy'), ('Avenue Foch'), ('Avenue Henri Martin'), ('Avenue du Président Kennedy'), (\"Pont de l'Alma\"), (\"Musée d'Orsay\")");
        // Rue des Tourneurs
        // Rue de la Trinité
    }

    public void ajoutRues(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("INSERT INTO rue(libelle) " +
                "VALUES ('Rue Croix-Baragnon'), " +
                "('Rue des Arts')," +
                "('Rue Pargaminières')," +
                "('Rue Saint-Rome')," +
                "('Rue Saint-Antoine du T')," +
                "('Rue de la Fonderie')," +
                "('Rue Peyrolières')," +
                "('Rue Genty-Magre')," +
                "(\"Rue d'Alsace-Lorraine\")," +
                "('Rue Peyras')," +
                "('Rue du Taur')," +
                "('Allée Jean Jaurès')," +
                "('Rue du May')," +
                "('Rue des Filatiers')," +
                "('Rue Mage')," +
                "(\"Rue d'Espinasse\")," +
                "('Rue des Gestes')," +
                "('Quai de la Daurade')," +
                "('Rue Bédelières')," +
                "('Rue Merlane')," +
                "('Rue Vélane')," +
                "('Rue Etroite')," +
                "('Rue des Tourneurs')," +
                "('Rue de la Trinité')");
    }

    public void createArretVoisin(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("CREATE TABLE arret_voisin(" +
                "id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                "arret BIGINT NOT NULL, " +
                "FOREIGN KEY (arret) REFERENCES arret(id)," +
                "rue BIGINT NOT NULL," +
                "FOREIGN KEY (rue) REFERENCES rue(id)," +
                "arret_suivant BIGINT," +
                "FOREIGN KEY (arret_suivant) REFERENCES arret(id)," +
                "ordre INTEGER" +
                ")");
    }

    public void createArretAdjacent(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("CREATE TABLE arret_adjacent(" +
                "id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                "arret BIGINT NOT NULL, " +
                "FOREIGN KEY (arret) REFERENCES arret(id)," +
                "rue BIGINT NOT NULL," +
                "FOREIGN KEY (rue) REFERENCES rue(id)," +
                "arret_adjacent BIGINT," +
                "FOREIGN KEY (arret_adjacent) REFERENCES arret(id)," +
                ")");
    }

    public void ajoutArretVoisin(JdbcTemplate jdbcTemplate) {
        // Rue Croix-Baragnon
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant,  ordre) VALUES " +
//                "(1, 1, 2, 1), (1, 2, 3, 2), (1, 3, 4, 3), (1, 4, 5, 4), (1, 5, 6, 5), (1, 6, 7, 6), (1, 7, 8, 7), (1, 8, 9, 8), (1, 9, 10, 9), (1, 10, 11, 10), (1, 11, 12, 11), (1, 12, 13, 12), (1, 13, 14, 13), (1, 14, 15, 14), (1, 15, 16, 15), (1, 16, 17, 16), (1, 17, 18, 17), (1, 18, 19, 18), (1, 19, 20, 19), (1, 20, 21, 20), (1, 21, 22, 21), (1, 22, 23, 22), (1, 23, 24, 23), (1, 24, 25, 24)");
//        // Rue des Arts
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(2, 26, 27, 1), (2, 27, 7, 2), (2, 7, 28, 3), (2, 28, 29, 4), (2, 29, 30, 5), (2, 30, 31, 6), (2, 31, 32, 7), (2, 32, 33, 8), (2, 33, 34, 9), (2, 34, 35, 10), (2, 35, 36, 11), (2, 36, 37, 12), (2, 37, 38, 13), (2, 38, 39, 14), (2, 39, 40, 15), (2, 40, 41, 16), (2, 41, 42, 17), (2, 42, 43, 18), (2, 43, 44, 19), (2, 44, 45, 20), (2, 45, 46, 21), (2, 46, 47, 22), (2, 47, 48, 23), (2, 48, 21, 24)");
//        // Rue Pargaminières
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(3, 49, 50, 1), (3, 50, 51, 2), (3, 51, 52, 3), (3, 52, 53, 4), (3, 53, 54, 5), (3, 54, 55, 6), (3, 55, 31, 7), (3, 31, 56, 8), (3, 56, 57, 9), (3, 57, 58, 10), (3, 58, 59, 11), (3, 59, 60, 12), (3, 60, 61, 13), (3, 61, 62, 14), (3, 62, 63, 15), (3, 63, 64, 16), (3, 64, 65, 17), (3, 65, 66, 18), (3, 66, 67, 19), (3, 67, 68, 20), (3, 68, 45, 21), (3, 45, 69, 22), (3, 69, 70, 23), (3, 70, 71, 24)");
//        // Rue Saint-Rome
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(4, 69, 72, 1), (4, 72, 73, 2), (4, 73, 74, 3)");
        // Rue Saint-Antoine du T
        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
                "(5, 75, 76, 1), (5, 76, 77, 2), (5, 77, 78, 3), (5, 78, 37, 4), (5, 37, 79, 5), (5, 79, 80, 6), (5, 80, 81, 7), (5, 81, 82, 8), (5, 82, 63, 9), (5, 63, 83, 10), (5, 83, 84, 11), (5, 84, 15, 12), (5, 15, 85, 13), (5, 85, 86, 14), (5, 86, 87, 15), (5, 87, 88, 16), (5, 88, 89, 17), (5, 89, 90, 18), (5, 90, 91, 19), (5, 91, 92, 20), (5, 92, 93, 21), (5, 93, 94, 22), (5, 94, 95, 23), (5, 95, 96, 24), (5, 96, 97, 25)");
        // Rue de la Fonderie
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(6, 98, 99, 1), (6, 99, 100, 2), (6, 100, 101, 3), (6, 101, 102, 4), (6, 102, 103, 5), (6, 103, 104, 6), (6, 104, 40, 7), (6, 40, 39, 8), (6, 39, 79, 9), (6, 79, 80, 10), (6, 80, 105, 11), (6, 105, 66, 12), (6, 66, 106, 13), (6, 106, 107, 14), (6, 107, 108, 15), (6, 108, 18, 16), (6, 18, 109, 17), (6, 109, 110, 18), (6, 110, 111, 19), (6, 111, 112, 20), (6, 112, 113, 21)");
//        // Rue Peyrolières
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(7, 7, 114, 1), (7, 114, 115, 2), (7, 115, 116, 3), (7, 116, 117, 4), (7, 117, 118, 5), (7, 118, 119, 6), (7, 119, 120, 7), (7, 120, 121, 8), (7, 121, 122, 9), (7, 122, 123, 10), (7, 123, 91, 11), (7, 91, 124, 12), (7, 124, 93, 13), (7, 93, 94, 14), (7, 94, 125, 15), (7, 125, 126, 16), (7, 126, 127, 17), (7, 127, 113, 18), (7, 113, 128, 19), (7, 128, 129, 20), (7, 129, 130, 21), (7, 130, 131, 22), (7, 131, 132, 23), (7, 132, 133, 24), (7, 133, 134, 25), (7, 134, 135, 26), (7, 135, 21, 27)");
//        // Rue Genty-Magre
        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
                "(8, 136, 137, 1), (8, 137, 138, 2), (8, 138, 139, 3), (8, 139, 140, 4), (8, 140, 141, 5), (8, 141, 142, 6), (8, 142, 39, 7), (8, 39, 143, 8), (8, 143, 144, 9), (8, 144, 80, 10), (8, 80, 145, 11), (8, 145, 146, 12), (8, 146, 147, 13), (8, 147, 148, 14), (8, 148, 59, 15), (8, 59, 149, 16), (8, 149, 13, 17), (8, 13, 150, 18), (8, 150, 15, 19), (8, 15, 151, 20), (8, 151, 152, 21), (8, 152, 153, 22), (8, 153, 154, 23), (8, 154, 155, 24), (8, 155, 156, 25), (8, 156, 113, 26), (8, 113, 157, 27), (8, 157, 158, 28), (8, 158, 159, 29), (8, 159, 160, 30), (8, 160, 161, 31), (8, 161, 162, 32), (8, 162, 163, 33)");
        // Rue d'Alsace-Lorraine
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(9, 143, 40, 1), (9, 40, 164, 2), (9, 164, 165, 3), (9, 165, 166, 4), (9, 166, 167, 5), (9, 167, 168, 6)");
//        // Rue Peyras
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(10, 169, 170, 1), (10, 170, 171, 2), (10, 171, 172, 3), (10, 172, 173, 4), (10, 173, 120, 5), (10, 120, 174, 6), (10, 174, 175, 7), (10, 175, 176, 8), (10, 176, 11, 9), (10, 11, 177, 10), (10, 177, 59, 11), (10, 59, 178, 12), (10, 178, 179, 13), (10, 179, 180, 14), (10, 180, 82, 15), (10, 82, 66, 16), (10, 66, 181, 17), (10, 181, 182, 18), (10, 182, 183, 19), (10, 183, 18, 20), (10, 18, 184, 21), (10, 184, 185, 22), (10, 185, 20, 23), (10, 20, 186, 24), (10, 186, 133, 25), (10, 133, 187, 26), (10, 187, 188, 27), (10, 188, 189, 28), (10, 189, 190, 29), (10, 190, 191, 30), (10, 191, 192, 31), (10, 192, 193, 32), (10, 193, 194, 33), (10, 194, 195, 34), (10, 195, 196, 35), (10, 196, 197, 36)");
//        // Rue du Taur
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(11, 198, 199, 1), (11, 199, 200, 2), (11, 200, 201, 3), (11, 201, 202, 4), (11, 202, 203, 5), (11, 203, 204, 6), (11, 204, 205, 7), (11, 205, 206, 8), (11, 206, 207, 9), (11, 207, 208, 10), (11, 208, 116, 11), (11, 116, 209, 12), (11, 209, 210, 13), (11, 210, 9, 14), (11, 9, 211, 15), (11, 211, 212, 16), (11, 212, 213, 17), (11, 213, 58, 18), (11, 58, 148, 19), (11, 148, 178, 20), (11, 178, 179, 21), (11, 179, 180, 22), (11, 180, 82, 23), (11, 82, 66, 24), (11, 66, 106, 25), (11, 106, 214, 26), (11, 214, 215, 27), (11, 215, 216, 28), (11, 216, 217, 29), (11, 217, 21, 30), (11, 21, 218, 31), (11, 218, 219, 32), (11, 219, 220, 33), (11, 220, 221, 34), (11, 221, 22, 35), (11, 222, 223, 36)");
//        // Allée Jean Jaurès
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(12, 224, 225, 1), (12, 225, 226, 2), (12, 226, 204, 3), (12, 204, 227, 4), (12, 227, 228, 5), (12, 228, 229, 6), (12, 229, 230, 7), (12, 230, 231, 8), (12, 231, 232, 9), (12, 232, 233, 10), (12, 233, 234, 11), (12, 234, 235, 12), (12, 235, 87, 13), (12, 87, 236, 14), (12, 236, 237, 15), (12, 237, 238, 16), (12, 238, 153, 17), (12, 153, 110, 18)");
//        // Rue du May
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(13, 15, 16, 1), (13, 16, 239, 2), (13, 239, 64, 3), (13, 64, 66, 4), (13, 66, 240, 5), (13, 240, 42, 6), (13, 42, 241, 7), (13, 241, 242, 8), (13, 242, 167, 9), (13, 167, 243, 10), (13, 243, 74, 11), (13, 74, 244, 12)");
//        // Rue des Filatiers
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(14, 245, 246, 1), (14, 246, 77, 2), (14, 77, 247, 3), (14, 247, 248, 4), (14, 248, 249, 5), (14, 249, 35, 6), (14, 35, 250, 7), (14, 250, 251, 8), (14, 251, 252, 9), (14, 252, 57, 10), (14, 57, 177, 11), (14, 177, 11, 12), (14, 11, 253, 13), (14, 253, 254, 14), (14, 254, 255, 15), (14, 255, 234, 16), (14, 234, 256, 17), (14, 256, 257, 18), (14, 257, 91, 19), (14, 91, 258, 20), (14, 258, 123, 21), (14, 123, 259, 22), (14, 259, 260, 23), (14, 260, 261, 24), (14, 261, 262, 25), (14, 262, 263, 26), (14, 263, 264, 27)");
//        // Rue Mage
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(15, 265, 266, 1), (15, 266, 267, 2), (15, 267, 268, 3), (15, 268, 269, 4), (15, 269, 270, 5), (15, 270, 271, 6), (15, 271, 272, 7), (15, 272, 273, 8), (15, 273, 33, 9), (15, 33, 274, 10), (15, 274, 57, 11), (15, 57, 212, 12), (15, 212, 10, 13), (15, 10, 176, 14), (15, 176, 275, 15), (15, 275, 276, 16), (15, 276, 232, 17), (15, 232, 91, 18), (15, 91, 277, 19), (15, 277, 278, 20), (15, 278, 279, 21), (15, 279, 280, 22), (15, 280, 281, 23), (15, 281, 282, 24), (15, 282, 283, 25)");
//        // Rue d'Espinasse
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(16, 57, 177, 1), (16, 177, 149, 2), (16, 149, 15, 3), (16, 15, 19, 4), (16, 19, 131, 5), (16, 131, 284, 6), (16, 284, 285, 7), (16, 285, 286, 8)");
//        // Rue des Gestes
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(17, 287, 288, 1), (17, 288, 289, 2), (17, 289, 266, 3), (17, 266, 290, 4), (17, 290, 291, 5), (17, 291, 292, 6), (17, 292, 293, 7), (17, 293, 284, 8), (17, 294, 295, 9), (17, 295, 296, 10), (17, 296, 136, 11), (17, 136, 297, 12), (17, 297, 298, 13), (17, 298, 299, 14), (17, 299, 300, 15), (17, 300, 301, 16), (17, 301, 302, 17), (17, 302, 303, 18), (17, 303, 304, 19), (17, 304, 98, 20), (17, 98, 305, 21), (17, 305, 306, 22), (17, 306, 307, 23), (17, 307, 308, 24), (17, 308, 309, 25)");
//        // Quai de la Daurade
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(18, 1, 310, 1), (18, 310, 311, 2), (18, 311, 312, 3), (18, 312, 313, 4), (18, 313, 314, 5), (18, 314, 315, 6), (18, 315, 316, 7), (18, 316, 317, 8), (18, 317, 318, 9), (18, 318, 319, 10), (18, 319, 320, 11), (18, 320, 321, 12), (18, 321, 169, 13), (18, 169, 262, 14)");
        // Rue Bédelières
        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
                "(19, 322, 169, 1), (19, 169, 323, 2), (19, 323, 262, 3), (19, 262, 324, 4), (19, 324, 325, 5), (19, 325, 280, 6), (19, 280, 326, 7), (19, 326, 327, 8), (19, 327, 97, 9), (19, 97, 328, 10), (19, 328, 329, 11), (19, 329, 330, 12), (19, 330, 331, 13), (19, 331, 159, 14), (19, 159, 160, 15), (19, 160, 161, 16), (19, 161, 285, 17), (19, 285, 189, 18), (19, 189, 188, 19), (19, 188, 332, 20), (19, 332, 22, 21)");
        // Rue Merlane
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(20, 1, 7, 1), (20, 7, 333, 2), (20, 333, 334, 3), (20, 334, 19, 4), (20, 19, 94, 5)");
//        // Rue Vélane
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(21, 79, 334, 1), (21, 334, 86, 2), (21, 86, 335, 3), (21, 335, 336, 4), (21, 336, 94, 5), (21, 94, 329, 6)");
//        // Rue Etroite
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(22, 337, 53, 1), (22, 53, 5, 2), (22, 5, 338, 3), (22, 338, 339, 4), (22, 339, 207, 5), (22, 207, 340, 6), (22, 340, 118, 7), (22, 118, 341, 8), (22, 341, 176, 9), (22, 176, 342, 10), (22, 342, 86, 11), (22, 86, 110, 12), (22, 110, 285, 13)");
//        // Rue des Tourneurs
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(23, 79, 334, 1), (23, 334, 19, 2)");
//        // Rue des Tourneurs
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(24, 57, 79, 1)");
    }

    public void ajoutArretAdjacent(JdbcTemplate jdbcTemplate) {
        // Rue Croix-Baragnon
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(1, 1, 2), (1, 2, 3), (1, 3, 4), (1, 4, 5), (1, 5, 6), (1, 6, 7), (1, 7, 8), (1, 8, 9), (1, 9, 10), (1, 10, 11), (1, 11, 12), (1, 12, 13), (1, 13, 14), (1, 14, 15), (1, 15, 16), (1, 16, 17), (1, 17, 18), (1, 18, 19), (1, 19, 20), (1, 20, 21), (1, 21, 22), (1, 22, 23), (1, 23, 24), (1, 24, 25)");
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(1, 2, 1), (1, 3, 2), (1, 4, 3), (1, 5, 4), (1, 6, 5), (1, 7, 6), (1, 8, 7), (1, 9, 8), (1, 10, 9), (1, 11, 10), (1, 12, 11), (1, 13, 12), (1, 14, 13), (1, 15, 14), (1, 16, 15), (1, 17, 16), (1, 18, 17), (1, 19, 18), (1, 20, 19), (1, 21, 20), (1, 22, 21), (1, 23, 22), (1, 24, 23), (1, 25, 24)");
        // Rue des Arts
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(2, 26, 27), (2, 27, 7), (2, 7, 28), (2, 28, 29), (2, 29, 30), (2, 30, 31), (2, 31, 32), (2, 32, 33), (2, 33, 34), (2, 34, 35), (2, 35, 36), (2, 36, 37), (2, 37, 38), (2, 38, 39), (2, 39, 40), (2, 40, 41), (2, 41, 42), (2, 42, 43), (2, 43, 44), (2, 44, 45), (2, 45, 46), (2, 46, 47), (2, 47, 48), (2, 48, 21)");
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(2, 27, 26), (2, 7, 27), (2, 28, 7), (2, 29, 28), (2, 30, 29), (2, 31, 30), (2, 32, 31), (2, 33, 32), (2, 34, 33), (2, 35, 34), (2, 36, 35), (2, 37, 36), (2, 38, 37), (2, 39, 38), (2, 40, 39), (2, 41, 40), (2, 42, 41), (2, 43, 42), (2, 44, 43), (2, 45, 44), (2, 45, 46), (2, 47, 46), (2, 48, 47), (2, 21, 48)");
        // Rue Pargaminières
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(3, 49, 50), (3, 50, 51), (3, 51, 52), (3, 52, 53), (3, 53, 54), (3, 54, 55), (3, 55, 31), (3, 31, 56), (3, 56, 57), (3, 57, 58), (3, 58, 59), (3, 59, 60), (3, 60, 61), (3, 61, 62), (3, 62, 63), (3, 63, 64), (3, 64, 65), (3, 65, 66), (3, 66, 67), (3, 67, 68), (3, 68, 45), (3, 45, 69), (3, 69, 70), (3, 70, 71)");
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(3, 50, 49), (3, 51, 50), (3, 52, 51), (3, 53, 52), (3, 54, 53), (3, 55, 54), (3, 31, 55), (3, 56, 31), (3, 57, 56), (3, 58, 57), (3, 59, 58), (3, 60, 59), (3, 61, 60), (3, 62, 61), (3, 63, 62), (3, 64, 63), (3, 65, 64), (3, 66, 65), (3, 67, 66), (3, 68, 67), (3, 45, 68), (3, 69, 45), (3, 70, 69), (3, 71, 70)");
        // Rue Saint-Rome
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(4, 69, 72), (4, 72, 73), (4, 73, 74)");
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(4, 72, 69), (4, 73, 72), (4, 74, 73)");
        // Rue Saint-Antoine du T
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(5, 75, 76), (5, 76, 77), (5, 77, 78), (5, 78, 37), (5, 37, 79), (5, 79, 80), (5, 80, 81), (5, 81, 82), (5, 82, 63), (5, 63, 83), (5, 83, 84), (5, 84, 15), (5, 15, 85), (5, 85, 86), (5, 86, 87), (5, 87, 88), (5, 88, 89), (5, 89, 90), (5, 90, 91), (5, 91, 92), (5, 92, 93), (5, 93, 94), (5, 94, 95), (5, 95, 96), (5, 96, 97)");
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(5, 76, 75), (5, 77, 76), (5, 78, 77), (5, 37, 78), (5, 79, 37), (5, 80, 79), (5, 81, 80), (5, 82, 81), (5, 63, 82), (5, 83, 63), (5, 84, 83), (5, 15, 84), (5, 85, 15), (5, 86, 85), (5, 87, 86), (5, 88, 87), (5, 89, 88), (5, 90, 89), (5, 91, 90), (5, 92, 91), (5, 93, 92), (5, 94, 93), (5, 95, 94), (5, 96, 95), (5, 97, 96)");
        // Rue de la Fonderie
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(6, 98, 99), (6, 99, 100), (6, 100, 101), (6, 101, 102), (6, 102, 103), (6, 103, 104), (6, 104, 40), (6, 40, 39), (6, 39, 79), (6, 79, 80), (6, 80, 105), (6, 105, 66), (6, 66, 106), (6, 106, 107), (6, 107, 108), (6, 108, 18), (6, 18, 109), (6, 109, 110), (6, 110, 111), (6, 111, 112), (6, 112, 113)");
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(6, 99, 98), (6, 100, 99), (6, 101, 100), (6, 102, 101), (6, 103, 102), (6, 104, 103), (6, 40, 104), (6, 39, 40), (6, 79, 39), (6, 80, 79), (6, 105, 80), (6, 66, 105), (6, 106, 66), (6, 107, 106), (6, 108, 107), (6, 18, 108), (6, 109, 18), (6, 110, 109), (6, 111, 110), (6, 112, 111), (6, 113, 112)");
//        // Rue Peyrolières
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(7, 7, 114), (7, 114, 115), (7, 115, 116), (7, 116, 117), (7, 117, 118), (7, 118, 119), (7, 119, 120), (7, 120, 121), (7, 121, 122), (7, 122, 123), (7, 123, 91), (7, 91, 124), (7, 124, 93), (7, 93, 94), (7, 94, 125), (7, 125, 126), (7, 126, 127), (7, 127, 113), (7, 113, 128), (7, 128, 129), (7, 129, 130), (7, 130, 131), (7, 131, 132), (7, 132, 133), (7, 133, 134), (7, 134, 135), (7, 135, 21)");
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(7, 114, 7), (7, 115, 114), (7, 116, 115), (7, 117, 116), (7, 118, 117), (7, 119, 118), (7, 120, 119), (7, 121, 120), (7, 122, 121), (7, 123, 122), (7, 91, 123), (7, 124, 91), (7, 93, 124), (7, 94, 93), (7, 125, 94), (7, 126, 125), (7, 127, 126), (7, 113, 127), (7, 128, 113), (7, 129, 128), (7, 130, 129), (7, 131, 130), (7, 132, 131), (7, 133, 132), (7, 134, 133), (7, 135, 134), (7, 21, 135)");
//        // Rue Genty-Magre
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(8, 136, 137), (8, 137, 138), (8, 138, 139), (8, 139, 140), (8, 140, 141), (8, 141, 142), (8, 142, 39), (8, 39, 143), (8, 143, 144), (8, 144, 80), (8, 80, 145), (8, 145, 146), (8, 146, 147), (8, 147, 148), (8, 148, 59), (8, 59, 149), (8, 149, 13), (8, 13, 150), (8, 150, 15), (8, 15, 151), (8, 151, 152), (8, 152, 153), (8, 153, 154), (8, 154, 155), (8, 155, 156), (8, 156, 113), (8, 113, 157), (8, 157, 158), (8, 158, 159), (8, 159, 160), (8, 160, 161), (8, 161, 162), (8, 162, 163)");
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(8, 137, 136), (8, 138, 137), (8, 139, 138), (8, 140, 139), (8, 141, 140), (8, 142, 141), (8, 39, 142), (8, 143, 39), (8, 144, 143), (8, 80, 144), (8, 145, 80), (8, 146, 145), (8, 147, 146), (8, 148, 147), (8, 59, 148), (8, 149, 59), (8, 13, 149), (8, 150, 13), (8, 15, 150), (8, 151, 15), (8, 152, 151), (8, 153, 152), (8, 154, 153), (8, 155, 154), (8, 156, 155), (8, 113, 156), (8, 157, 113), (8, 158, 157), (8, 159, 158), (8, 160, 159), (8, 161, 160), (8, 162, 161), (8, 163, 162)");
        // Rue d'Alsace-Lorraine
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(9, 143, 40, 1), (9, 40, 164, 2), (9, 164, 165, 3), (9, 165, 166, 4), (9, 166, 167, 5), (9, 167, 168, 6)");
//        // Rue Peyras
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(10, 169, 170, 1), (10, 170, 171, 2), (10, 171, 172, 3), (10, 172, 173, 4), (10, 173, 120, 5), (10, 120, 174, 6), (10, 174, 175, 7), (10, 175, 176, 8), (10, 176, 11, 9), (10, 11, 177, 10), (10, 177, 59, 11), (10, 59, 178, 12), (10, 178, 179, 13), (10, 179, 180, 14), (10, 180, 82, 15), (10, 82, 66, 16), (10, 66, 181, 17), (10, 181, 182, 18), (10, 182, 183, 19), (10, 183, 18, 20), (10, 18, 184, 21), (10, 184, 185, 22), (10, 185, 20, 23), (10, 20, 186, 24), (10, 186, 133, 25), (10, 133, 187, 26), (10, 187, 188, 27), (10, 188, 189, 28), (10, 189, 190, 29), (10, 190, 191, 30), (10, 191, 192, 31), (10, 192, 193, 32), (10, 193, 194, 33), (10, 194, 195, 34), (10, 195, 196, 35), (10, 196, 197, 36)");
//        // Rue du Taur
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(11, 198, 199, 1), (11, 199, 200, 2), (11, 200, 201, 3), (11, 201, 202, 4), (11, 202, 203, 5), (11, 203, 204, 6), (11, 204, 205, 7), (11, 205, 206, 8), (11, 206, 207, 9), (11, 207, 208, 10), (11, 208, 116, 11), (11, 116, 209, 12), (11, 209, 210, 13), (11, 210, 9, 14), (11, 9, 211, 15), (11, 211, 212, 16), (11, 212, 213, 17), (11, 213, 58, 18), (11, 58, 148, 19), (11, 148, 178, 20), (11, 178, 179, 21), (11, 179, 180, 22), (11, 180, 82, 23), (11, 82, 66, 24), (11, 66, 106, 25), (11, 106, 214, 26), (11, 214, 215, 27), (11, 215, 216, 28), (11, 216, 217, 29), (11, 217, 21, 30), (11, 21, 218, 31), (11, 218, 219, 32), (11, 219, 220, 33), (11, 220, 221, 34), (11, 221, 22, 35), (11, 222, 223, 36)");
//        // Allée Jean Jaurès
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(12, 224, 225, 1), (12, 225, 226, 2), (12, 226, 204, 3), (12, 204, 227, 4), (12, 227, 228, 5), (12, 228, 229, 6), (12, 229, 230, 7), (12, 230, 231, 8), (12, 231, 232, 9), (12, 232, 233, 10), (12, 233, 234, 11), (12, 234, 235, 12), (12, 235, 87, 13), (12, 87, 236, 14), (12, 236, 237, 15), (12, 237, 238, 16), (12, 238, 153, 17), (12, 153, 110, 18)");
//        // Rue du May
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(13, 15, 16, 1), (13, 16, 239, 2), (13, 239, 64, 3), (13, 64, 66, 4), (13, 66, 240, 5), (13, 240, 42, 6), (13, 42, 241, 7), (13, 241, 242, 8), (13, 242, 167, 9), (13, 167, 243, 10), (13, 243, 74, 11), (13, 74, 244, 12)");
//        // Rue des Filatiers
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(14, 245, 246, 1), (14, 246, 77, 2), (14, 77, 247, 3), (14, 247, 248, 4), (14, 248, 249, 5), (14, 249, 35, 6), (14, 35, 250, 7), (14, 250, 251, 8), (14, 251, 252, 9), (14, 252, 57, 10), (14, 57, 177, 11), (14, 177, 11, 12), (14, 11, 253, 13), (14, 253, 254, 14), (14, 254, 255, 15), (14, 255, 234, 16), (14, 234, 256, 17), (14, 256, 257, 18), (14, 257, 91, 19), (14, 91, 258, 20), (14, 258, 123, 21), (14, 123, 259, 22), (14, 259, 260, 23), (14, 260, 261, 24), (14, 261, 262, 25), (14, 262, 263, 26), (14, 263, 264, 27)");
//        // Rue Mage
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(15, 265, 266, 1), (15, 266, 267, 2), (15, 267, 268, 3), (15, 268, 269, 4), (15, 269, 270, 5), (15, 270, 271, 6), (15, 271, 272, 7), (15, 272, 273, 8), (15, 273, 33, 9), (15, 33, 274, 10), (15, 274, 57, 11), (15, 57, 212, 12), (15, 212, 10, 13), (15, 10, 176, 14), (15, 176, 275, 15), (15, 275, 276, 16), (15, 276, 232, 17), (15, 232, 91, 18), (15, 91, 277, 19), (15, 277, 278, 20), (15, 278, 279, 21), (15, 279, 280, 22), (15, 280, 281, 23), (15, 281, 282, 24), (15, 282, 283, 25)");
//        // Rue d'Espinasse
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(16, 57, 177, 1), (16, 177, 149, 2), (16, 149, 15, 3), (16, 15, 19, 4), (16, 19, 131, 5), (16, 131, 284, 6), (16, 284, 285, 7), (16, 285, 286, 8)");
//        // Rue des Gestes
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(17, 287, 288, 1), (17, 288, 289, 2), (17, 289, 266, 3), (17, 266, 290, 4), (17, 290, 291, 5), (17, 291, 292, 6), (17, 292, 293, 7), (17, 293, 284, 8), (17, 294, 295, 9), (17, 295, 296, 10), (17, 296, 136, 11), (17, 136, 297, 12), (17, 297, 298, 13), (17, 298, 299, 14), (17, 299, 300, 15), (17, 300, 301, 16), (17, 301, 302, 17), (17, 302, 303, 18), (17, 303, 304, 19), (17, 304, 98, 20), (17, 98, 305, 21), (17, 305, 306, 22), (17, 306, 307, 23), (17, 307, 308, 24), (17, 308, 309, 25)");
//        // Quai de la Daurade
//        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, arret_suivant, ordre) VALUES " +
//                "(18, 1, 310, 1), (18, 310, 311, 2), (18, 311, 312, 3), (18, 312, 313, 4), (18, 313, 314, 5), (18, 314, 315, 6), (18, 315, 316, 7), (18, 316, 317, 8), (18, 317, 318, 9), (18, 318, 319, 10), (18, 319, 320, 11), (18, 320, 321, 12), (18, 321, 169, 13), (18, 169, 262, 14)");
        // Rue Bédelières
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(19, 322, 169), (19, 169, 323), (19, 323, 262), (19, 262, 324), (19, 324, 325), (19, 325, 280), (19, 280, 326), (19, 326, 327), (19, 327, 97), (19, 97, 328), (19, 328, 329), (19, 329, 330), (19, 330, 331), (19, 331, 159), (19, 159, 160), (19, 160, 161), (19, 161, 285), (19, 285, 189), (19, 189, 188), (19, 188, 332), (19, 332, 22)");
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(19, 169, 322), (19, 323, 169), (19, 262, 323), (19, 324, 262), (19, 325, 324), (19, 280, 325), (19, 326, 280), (19, 327, 326), (19, 97, 327), (19, 328, 97), (19, 329, 328), (19, 330, 329), (19, 331, 330), (19, 159, 331), (19, 160, 159), (19, 161, 160), (19, 285, 161), (19, 189, 285), (19, 188, 189), (19, 332, 188), (19, 22, 332)");
        // Rue Merlane
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(20, 1, 7), (20, 7, 333), (20, 333, 334), (20, 334, 19), (20, 19, 94)");
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(20, 7, 1), (20, 333, 7), (20, 334, 333), (20, 19, 334), (20, 94, 19)");
        // Rue Vélane
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(21, 79, 334), (21, 334, 86), (21, 86, 335), (21, 335, 336), (21, 336, 94), (21, 94, 329)");
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(21, 334, 79), (21, 86, 334), (21, 335, 86), (21, 336, 335), (21, 94, 336), (21, 329, 94)");
//        // Rue Etroite
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(22, 337, 53), (22, 53, 5), (22, 5, 338), (22, 338, 339), (22, 339, 207), (22, 207, 340), (22, 340, 118), (22, 118, 341), (22, 341, 176), (22, 176, 342), (22, 342, 86), (22, 86, 110), (22, 110, 285)");
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(22, 53, 337), (22, 5, 53), (22, 338, 5), (22, 339, 338), (22, 207, 339), (22, 340, 207), (22, 118, 340), (22, 341, 118), (22, 176, 341), (22, 342, 176), (22, 86, 342), (22, 110, 86), (22, 285, 110)");
        // Rue des Tourneurs
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(23, 79, 334), (23, 334, 19), (23, 334, 79), (23, 19, 334)");
        // Rue des Tourneurs
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(24, 57, 79), (24, 79, 57)");
    }

}
