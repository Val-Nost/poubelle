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
        createArretRue(jdbcTemplate);
        createArretAdjacent(jdbcTemplate);

        ajoutArret(jdbcTemplate);
        ajoutRues(jdbcTemplate);
        ajoutArretVoisin(jdbcTemplate);
        ajoutArretAdjacent(jdbcTemplate);
    }

    public void creationTableArret(final JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("CREATE TABLE arret(" +
                "id BIGINT NOT NULL PRIMARY KEY auto_increment, " +
                "libelle VARCHAR(255) UNIQUE, " +
                "ramasse BOOLEAN NOT NULL DEFAULT false," +
                "`accessible` BOOLEAN NOT NULL DEFAULT true" +
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

    public void createArretRue(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("CREATE TABLE arret_rue(" +
                "id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                "arret BIGINT NOT NULL, " +
                "FOREIGN KEY (arret) REFERENCES arret(id)," +
                "rue BIGINT NOT NULL," +
                "FOREIGN KEY (rue) REFERENCES rue(id)," +
                "ordre INTEGER" +
                ")");
    }

    public void createArretAdjacent(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("CREATE TABLE arret_adjacent(" +
                "id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                "arret BIGINT, " +
                "FOREIGN KEY (arret) REFERENCES arret(id)," +
                "rue BIGINT NOT NULL," +
                "FOREIGN KEY (rue) REFERENCES rue(id)," +
                "arret_adjacent BIGINT," +
                "FOREIGN KEY (arret_adjacent) REFERENCES arret(id)" +
                ")");
    }

    public void ajoutArretVoisin(JdbcTemplate jdbcTemplate) {
        // Rue Croix-Baragnon
        jdbcTemplate.execute("INSERT INTO arret_rue(rue, arret, ordre) VALUES " +
                "(1, 1, 1), (1, 2, 2), (1, 3, 3), (1, 4, 4), (1, 5, 5), (1, 6, 6), (1, 7, 7), (1, 8, 8), (1, 9, 9), (1, 10, 10), (1, 11, 11), (1, 12, 12), (1, 13, 13), (1, 14, 14), (1, 15, 15), (1, 16, 16), (1, 17, 17), (1, 18, 18), (1, 19, 19), (1, 20, 20), (1, 21, 21), (1, 22, 22), (1, 23, 23), (1, 24, 24), (1, 25, 25)");
        // Rue des Arts
        jdbcTemplate.execute("INSERT INTO arret_rue(rue, arret, ordre) VALUES " +
                "(2, 26, 1), (2, 27, 2), (2, 7, 3), (2, 28, 4), (2, 29, 5), (2, 30, 6), (2, 31, 7), (2, 32, 8), (2, 33, 9), (2, 34, 10), (2, 35, 11), (2, 36, 12), (2, 37, 13), (2, 38, 14), (2, 39, 15), (2, 40, 16), (2, 41, 17), (2, 42, 18), (2, 43, 19), (2, 44, 20), (2, 45, 21), (2, 46, 22), (2, 47, 23), (2, 48, 24), (2, 21, 25)");
        // Rue Pargaminières
        jdbcTemplate.execute("INSERT INTO arret_rue(rue, arret, ordre) VALUES " +
                "(3, 49, 1), (3, 50, 2), (3, 51, 3), (3, 52, 4), (3, 53, 5), (3, 54, 6), (3, 55, 7), (3, 31, 8), (3, 56, 9), (3, 57, 10), (3, 58, 11), (3, 59, 12), (3, 60, 13), (3, 61, 14), (3, 62, 15), (3, 63, 16), (3, 64, 17), (3, 65, 18), (3, 66, 19), (3, 67, 20), (3, 68, 21), (3, 45, 22), (3, 69, 23), (3, 70, 24), (3, 71, 25)");
        // Rue Saint-Rome
        jdbcTemplate.execute("INSERT INTO arret_rue(rue, arret, ordre) VALUES " +
                "(4, 69, 1), (4, 72, 2), (4, 73, 3), (4, 74, 4)");
        // Rue Saint-Antoine du T
        jdbcTemplate.execute("INSERT INTO arret_rue(rue, arret, ordre) VALUES " +
                "(5, 75, 1), (5, 76, 2), (5, 77, 3), (5, 78, 4), (5, 37, 5), (5, 79, 6), (5, 80, 7), (5, 81, 8), (5, 82, 9), (5, 63, 10), (5, 83, 11), (5, 84, 12), (5, 15, 13), (5, 85, 14), (5, 86, 15), (5, 87, 16), (5, 88, 17), (5, 89, 18), (5, 90, 19), (5, 91, 20), (5, 92, 21), (5, 93, 22), (5, 94, 23), (5, 95, 24), (5, 96, 25), (5, 97, 26)");
        // Rue de la Fonderie
        jdbcTemplate.execute("INSERT INTO arret_rue(rue, arret, ordre) VALUES " +
                "(6, 98, 1), (6, 99, 2), (6, 100, 3), (6, 101, 4), (6, 102, 5), (6, 103, 6), (6, 104, 7), (6, 40, 8), (6, 39, 9), (6, 79, 10), (6, 80, 11), (6, 105, 12), (6, 66, 13), (6, 106, 14), (6, 107, 15), (6, 108, 16), (6, 18, 17), (6, 109, 18), (6, 110, 19), (6, 111, 20), (6, 112, 21), (6, 113, 22)");
        // Rue Peyrolières
        jdbcTemplate.execute("INSERT INTO arret_rue(rue, arret, ordre) VALUES " +
                "(7, 7, 1), (7, 114, 2), (7, 115, 3), (7, 116, 4), (7, 117, 5), (7, 118, 6), (7, 119, 7), (7, 120, 8), (7, 121, 9), (7, 122, 10), (7, 123, 11), (7, 91, 12), (7, 124, 13), (7, 93, 14), (7, 94, 15), (7, 125, 16), (7, 126, 17), (7, 127, 18), (7, 113, 19), (7, 128, 20), (7, 129, 21), (7, 130, 22), (7, 131, 23), (7, 132, 24), (7, 133, 25), (7, 134, 26), (7, 135, 27), (7, 21, 28)");
        // Rue Genty-Magre
        jdbcTemplate.execute("INSERT INTO arret_rue(rue, arret, ordre) VALUES " +
                "(8, 136, 1), (8, 137, 2), (8, 138, 3), (8, 139, 4), (8, 140, 5), (8, 141, 6), (8, 142, 7), (8, 39, 8), (8, 143, 9), (8, 144, 10), (8, 80, 11), (8, 145, 12), (8, 146, 13), (8, 147, 14), (8, 148, 15), (8, 59, 16), (8, 149, 17), (8, 13, 18), (8, 150, 19), (8, 15, 20), (8, 151, 21), (8, 152, 22), (8, 153, 23), (8, 154, 24), (8, 155, 25), (8, 156, 26), (8, 113, 27), (8, 157, 28), (8, 158, 29), (8, 159, 30), (8, 160, 31), (8, 161, 32), (8, 162, 33), (8, 163, 34)");
        // Rue d'Alsace-Lorraine
        jdbcTemplate.execute("INSERT INTO arret_rue(rue, arret, ordre) VALUES " +
                "(9, 143, 1), (9, 40, 2), (9, 164, 3), (9, 165, 4), (9, 166, 5), (9, 167, 6), (9, 168, 7)");
        // Rue Peyras
        jdbcTemplate.execute("INSERT INTO arret_rue(rue, arret, ordre) VALUES " +
                "(10, 169, 1), (10, 170, 2), (10, 171, 3), (10, 172, 4), (10, 173, 5), (10, 120, 6), (10, 174, 7), (10, 175, 8), (10, 176, 9), (10, 11, 10), (10, 177, 11), (10, 59, 12), (10, 178, 13), (10, 179, 14), (10, 180, 15), (10, 82, 16), (10, 66, 17), (10, 181, 18), (10, 182, 19), (10, 183, 20), (10, 18, 21), (10, 184, 22), (10, 185, 23), (10, 20, 24), (10, 186, 25), (10, 133, 26), (10, 187, 27), (10, 188, 28), (10, 189, 29), (10, 190, 30), (10, 191, 31), (10, 192, 32), (10, 193, 33), (10, 194, 34), (10, 195, 35), (10, 196, 36), (10, 197, 37)");
        // Rue du Taur
        jdbcTemplate.execute("INSERT INTO arret_rue(rue, arret, ordre) VALUES " +
                "(11, 198, 1), (11, 199, 2), (11, 200, 3), (11, 201, 4), (11, 202, 5), (11, 203, 6), (11, 204, 7), (11, 205, 8), (11, 206, 9), (11, 207, 10), (11, 208, 11), (11, 116, 12), (11, 209, 13), (11, 210, 14), (11, 9, 15), (11, 211, 16), (11, 212, 17), (11, 213, 18), (11, 58, 19), (11, 148, 20), (11, 178, 21), (11, 179, 22), (11, 180, 23), (11, 82, 24), (11, 66, 25), (11, 106, 26), (11, 214, 27), (11, 215, 28), (11, 216, 29), (11, 217, 30), (11, 21, 31), (11, 218, 32), (11, 219, 33), (11, 220, 34), (11, 221, 35), (11, 222, 36), (11, 223, 37)");
        // Allée Jean Jaurès
        jdbcTemplate.execute("INSERT INTO arret_rue(rue, arret, ordre) VALUES " +
                "(12, 224, 1), (12, 225, 2), (12, 226, 3), (12, 204, 4), (12, 227, 5), (12, 228, 6), (12, 229, 7), (12, 230, 8), (12, 231, 9), (12, 232, 10), (12, 233, 11), (12, 234, 12), (12, 235, 13), (12, 87, 14), (12, 236, 15), (12, 237, 16), (12, 238, 17), (12, 153, 18), (12, 110, 19)");
        // Rue du May
        jdbcTemplate.execute("INSERT INTO arret_rue(rue, arret, ordre) VALUES " +
                "(13, 15, 1), (13, 16, 2), (13, 239, 3), (13, 64, 4), (13, 66, 5), (13, 240, 6), (13, 42, 7), (13, 241, 8), (13, 242, 9), (13, 167, 10), (13, 243, 11), (13, 74, 12), (13, 244, 13)");
        // Rue des Filatiers
        jdbcTemplate.execute("INSERT INTO arret_rue(rue, arret, ordre) VALUES " +
                "(14, 245, 1), (14, 246, 2), (14, 77, 3), (14, 247, 4), (14, 248, 5), (14, 249, 6), (14, 35, 7), (14, 250, 8), (14, 251, 9), (14, 252, 10), (14, 57, 11), (14, 177, 12), (14, 11, 13), (14, 253, 14), (14, 254, 15), (14, 255, 16), (14, 234, 17), (14, 256, 18), (14, 257, 19), (14, 91, 20), (14, 258, 21), (14, 123, 22), (14, 259, 23), (14, 260, 24), (14, 261, 25), (14, 262, 26), (14, 263, 27), (14, 264, 28)");
        // Rue Mage
        jdbcTemplate.execute("INSERT INTO arret_rue(rue, arret, ordre) VALUES " +
                "(15, 265, 1), (15, 266, 2), (15, 267, 3), (15, 268, 4), (15, 269, 5), (15, 270, 6), (15, 271, 7), (15, 272, 8), (15, 273, 9), (15, 33, 10), (15, 274, 11), (15, 57, 12), (15, 212, 13), (15, 10, 14), (15, 176, 15), (15, 275, 16), (15, 276, 17), (15, 232, 18), (15, 91, 19), (15, 277, 20), (15, 278, 21), (15, 279, 22), (15, 280, 23), (15, 281, 24), (15, 282, 25), (15, 283, 26)");
        // Rue d'Espinasse
        jdbcTemplate.execute("INSERT INTO arret_rue(rue, arret, ordre) VALUES " +
                "(16, 57, 1), (16, 177, 2), (16, 149, 3), (16, 15, 4), (16, 19, 5), (16, 131, 6), (16, 284, 7), (16, 285, 8), (16, 286, 9)");
        // Rue des Gestes
        jdbcTemplate.execute("INSERT INTO arret_rue(rue, arret, ordre) VALUES " +
                "(17, 287, 1), (17, 288, 2), (17, 289, 3), (17, 266, 4), (17, 290, 5), (17, 291, 6), (17, 292, 7), (17, 293, 8), (17, 294, 9), (17, 295, 10), (17, 296, 11), (17, 136, 12), (17, 297, 13), (17, 298, 14), (17, 299, 15), (17, 300, 16), (17, 301, 17), (17, 302, 18), (17, 303, 19), (17, 304, 20), (17, 98, 21), (17, 305, 22), (17, 306, 23), (17, 307, 24), (17, 308, 25), (17, 309, 26)");
        // Quai de la Daurade
        jdbcTemplate.execute("INSERT INTO arret_rue(rue, arret, ordre) VALUES " +
                "(18, 1, 1), (18, 310, 2), (18, 311, 3), (18, 312, 4), (18, 313, 5), (18, 314, 6), (18, 315, 7), (18, 316, 8), (18, 317, 9), (18, 318, 10), (18, 319, 11), (18, 320, 12), (18, 321, 13), (18, 169, 14), (18, 262, 15)");
        // Rue Bédelières
        jdbcTemplate.execute("INSERT INTO arret_rue(rue, arret, ordre) VALUES " +
                "(19, 322, 1), (19, 169, 2), (19, 323, 3), (19, 262, 4), (19, 324, 5), (19, 325, 6), (19, 280, 7), (19, 326, 8), (19, 327, 9), (19, 97, 10), (19, 328, 11), (19, 329, 12), (19, 330, 13), (19, 331, 14), (19, 159, 15), (19, 160, 16), (19, 161, 17), (19, 285, 18), (19, 189, 19), (19, 188, 20), (19, 332, 21), (19, 22, 22)");
        // Rue Merlane
        jdbcTemplate.execute("INSERT INTO arret_rue(rue, arret, ordre) VALUES " +
                "(20, 1, 1), (20, 7, 2), (20, 333, 3), (20, 334, 4), (20, 19, 5), (20, 94, 21)");
        // Rue Vélane
        jdbcTemplate.execute("INSERT INTO arret_rue(rue, arret, ordre) VALUES " +
                "(21, 79, 1), (21, 334, 2), (21, 86, 3), (21, 335, 4), (21, 336, 5), (21, 94, 6), (21, 329, 7)");
        // Rue Etroite
        jdbcTemplate.execute("INSERT INTO arret_rue(rue, arret, ordre) VALUES " +
                "(22, 337, 1), (22, 53, 2), (22, 5, 3), (22, 338, 4), (22, 339, 5), (22, 207, 6), (22, 340, 7), (22, 118, 8), (22, 341, 9), (22, 176, 10), (22, 342, 11), (22, 86, 12), (22, 110, 13), (22, 285, 14)");
        // Rue des Tourneurs
        jdbcTemplate.execute("INSERT INTO arret_rue(rue, arret, ordre) VALUES " +
                "(23, 79, 1), (23, 334, 2), (23, 19, 3)");
        // Rue des Tourneurs
        jdbcTemplate.execute("INSERT INTO arret_rue(rue, arret, ordre) VALUES " +
                "(24, 57, 1), (24, 79, 2)");
    }

    public void ajoutArretAdjacent(JdbcTemplate jdbcTemplate) {
        // Rue Croix-Baragnon
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(1, 1, 2), (1, 2, 3), (1, 3, 4), (1, 4, 5), (1, 5, 6), (1, 6, 7), (1, 7, 8), (1, 8, 9), (1, 9, 10), (1, 10, 11), (1, 11, 12), (1, 12, 13), (1, 13, 14), (1, 14, 15), (1, 15, 16), (1, 16, 17), (1, 17, 18), (1, 18, 19), (1, 19, 20), (1, 20, 21), (1, 21, 22), (1, 22, 23), (1, 23, 24), (1, 24, 25)");
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(1, 2, 1), (1, 3, 2), (1, 4, 3), (1, 5, 4), (1, 6, 5), (1, 7, 6), (1, 8, 7), (1, 9, 8), (1, 10, 9), (1, 11, 10), (1, 12, 11), (1, 13, 12), (1, 14, 13), (1, 15, 14), (1, 16, 15), (1, 17, 16), (1, 18, 17), (1, 19, 18), (1, 20, 19), (1, 21, 20), (1, 22, 21), (1, 23, 22), (1, 24, 23), (1, 25, 24)");
        // Terminus
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(1, null, 1), (1, 25, null)");
        // Rue des Arts
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(2, 26, 27), (2, 27, 7), (2, 7, 28), (2, 28, 29), (2, 29, 30), (2, 30, 31), (2, 31, 32), (2, 32, 33), (2, 33, 34), (2, 34, 35), (2, 35, 36), (2, 36, 37), (2, 37, 38), (2, 38, 39), (2, 39, 40), (2, 40, 41), (2, 41, 42), (2, 42, 43), (2, 43, 44), (2, 44, 45), (2, 45, 46), (2, 46, 47), (2, 47, 48), (2, 48, 21)");
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(2, 27, 26), (2, 7, 27), (2, 28, 7), (2, 29, 28), (2, 30, 29), (2, 31, 30), (2, 32, 31), (2, 33, 32), (2, 34, 33), (2, 35, 34), (2, 36, 35), (2, 37, 36), (2, 38, 37), (2, 39, 38), (2, 40, 39), (2, 41, 40), (2, 42, 41), (2, 43, 42), (2, 44, 43), (2, 45, 44), (2, 45, 46), (2, 47, 46), (2, 48, 47), (2, 21, 48)");
        // Terminus
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(2, null, 26), (2, 21, null)");
        // Rue Pargaminières
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(3, 49, 50), (3, 50, 51), (3, 51, 52), (3, 52, 53), (3, 53, 54), (3, 54, 55), (3, 55, 31), (3, 31, 56), (3, 56, 57), (3, 57, 58), (3, 58, 59), (3, 59, 60), (3, 60, 61), (3, 61, 62), (3, 62, 63), (3, 63, 64), (3, 64, 65), (3, 65, 66), (3, 66, 67), (3, 67, 68), (3, 68, 45), (3, 45, 69), (3, 69, 70), (3, 70, 71)");
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(3, 50, 49), (3, 51, 50), (3, 52, 51), (3, 53, 52), (3, 54, 53), (3, 55, 54), (3, 31, 55), (3, 56, 31), (3, 57, 56), (3, 58, 57), (3, 59, 58), (3, 60, 59), (3, 61, 60), (3, 62, 61), (3, 63, 62), (3, 64, 63), (3, 65, 64), (3, 66, 65), (3, 67, 66), (3, 68, 67), (3, 45, 68), (3, 69, 45), (3, 70, 69), (3, 71, 70)");
        // Terminus
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(3, null, 49), (3, 71, null)");
        // Rue Saint-Rome
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(4, 69, 72), (4, 72, 73), (4, 73, 74)");
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(4, 72, 69), (4, 73, 72), (4, 74, 73)");
        // Terminus
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(4, null, 69), (4, 74, null)");
        // Rue Saint-Antoine du T
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(5, 75, 76), (5, 76, 77), (5, 77, 78), (5, 78, 37), (5, 37, 79), (5, 79, 80), (5, 80, 81), (5, 81, 82), (5, 82, 63), (5, 63, 83), (5, 83, 84), (5, 84, 15), (5, 15, 85), (5, 85, 86), (5, 86, 87), (5, 87, 88), (5, 88, 89), (5, 89, 90), (5, 90, 91), (5, 91, 92), (5, 92, 93), (5, 93, 94), (5, 94, 95), (5, 95, 96), (5, 96, 97)");
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(5, 76, 75), (5, 77, 76), (5, 78, 77), (5, 37, 78), (5, 79, 37), (5, 80, 79), (5, 81, 80), (5, 82, 81), (5, 63, 82), (5, 83, 63), (5, 84, 83), (5, 15, 84), (5, 85, 15), (5, 86, 85), (5, 87, 86), (5, 88, 87), (5, 89, 88), (5, 90, 89), (5, 91, 90), (5, 92, 91), (5, 93, 92), (5, 94, 93), (5, 95, 94), (5, 96, 95), (5, 97, 96)");
        // Terminus
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(5, null, 75), (1, 97, null)");
        // Rue de la Fonderie
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(6, 98, 99), (6, 99, 100), (6, 100, 101), (6, 101, 102), (6, 102, 103), (6, 103, 104), (6, 104, 40), (6, 40, 39), (6, 39, 79), (6, 79, 80), (6, 80, 105), (6, 105, 66), (6, 66, 106), (6, 106, 107), (6, 107, 108), (6, 108, 18), (6, 18, 109), (6, 109, 110), (6, 110, 111), (6, 111, 112), (6, 112, 113)");
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(6, 99, 98), (6, 100, 99), (6, 101, 100), (6, 102, 101), (6, 103, 102), (6, 104, 103), (6, 40, 104), (6, 39, 40), (6, 79, 39), (6, 80, 79), (6, 105, 80), (6, 66, 105), (6, 106, 66), (6, 107, 106), (6, 108, 107), (6, 18, 108), (6, 109, 18), (6, 110, 109), (6, 111, 110), (6, 112, 111), (6, 113, 112)");
        // Terminus
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(6, null, 98), (6, 113, null)");
        // Rue Peyrolières
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(7, 7, 114), (7, 114, 115), (7, 115, 116), (7, 116, 117), (7, 117, 118), (7, 118, 119), (7, 119, 120), (7, 120, 121), (7, 121, 122), (7, 122, 123), (7, 123, 91), (7, 91, 124), (7, 124, 93), (7, 93, 94), (7, 94, 125), (7, 125, 126), (7, 126, 127), (7, 127, 113), (7, 113, 128), (7, 128, 129), (7, 129, 130), (7, 130, 131), (7, 131, 132), (7, 132, 133), (7, 133, 134), (7, 134, 135), (7, 135, 21)");
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(7, 114, 7), (7, 115, 114), (7, 116, 115), (7, 117, 116), (7, 118, 117), (7, 119, 118), (7, 120, 119), (7, 121, 120), (7, 122, 121), (7, 123, 122), (7, 91, 123), (7, 124, 91), (7, 93, 124), (7, 94, 93), (7, 125, 94), (7, 126, 125), (7, 127, 126), (7, 113, 127), (7, 128, 113), (7, 129, 128), (7, 130, 129), (7, 131, 130), (7, 132, 131), (7, 133, 132), (7, 134, 133), (7, 135, 134), (7, 21, 135)");
        // Terminus
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(7, null, 7), (7, 21, null)");
        // Rue Genty-Magre
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(8, 136, 137), (8, 137, 138), (8, 138, 139), (8, 139, 140), (8, 140, 141), (8, 141, 142), (8, 142, 39), (8, 39, 143), (8, 143, 144), (8, 144, 80), (8, 80, 145), (8, 145, 146), (8, 146, 147), (8, 147, 148), (8, 148, 59), (8, 59, 149), (8, 149, 13), (8, 13, 150), (8, 150, 15), (8, 15, 151), (8, 151, 152), (8, 152, 153), (8, 153, 154), (8, 154, 155), (8, 155, 156), (8, 156, 113), (8, 113, 157), (8, 157, 158), (8, 158, 159), (8, 159, 160), (8, 160, 161), (8, 161, 162), (8, 162, 163)");
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(8, 137, 136), (8, 138, 137), (8, 139, 138), (8, 140, 139), (8, 141, 140), (8, 142, 141), (8, 39, 142), (8, 143, 39), (8, 144, 143), (8, 80, 144), (8, 145, 80), (8, 146, 145), (8, 147, 146), (8, 148, 147), (8, 59, 148), (8, 149, 59), (8, 13, 149), (8, 150, 13), (8, 15, 150), (8, 151, 15), (8, 152, 151), (8, 153, 152), (8, 154, 153), (8, 155, 154), (8, 156, 155), (8, 113, 156), (8, 157, 113), (8, 158, 157), (8, 159, 158), (8, 160, 159), (8, 161, 160), (8, 162, 161), (8, 163, 162)");
        // Terminus
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(8, null, 136), (8, 163, null)");
        // Rue d'Alsace-Lorraine
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(9, 143, 40), (9, 40, 164), (9, 164, 165), (9, 165, 166), (9, 166, 167), (9, 167, 168)");
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(9, 40, 143), (9, 164, 40), (9, 165, 164), (9, 166, 165), (9, 167, 166), (9, 168, 167)");
        // Terminus
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(9, null, 143), (9, 168, null)");
        // Rue Peyras
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(10, 169, 170), (10, 170, 171), (10, 171, 172), (10, 172, 173), (10, 173, 120), (10, 120, 174), (10, 174, 175), (10, 175, 176), (10, 176, 11), (10, 11, 177), (10, 177, 59), (10, 59, 178), (10, 178, 179), (10, 179, 180), (10, 180, 82), (10, 82, 66), (10, 66, 181), (10, 181, 182), (10, 182, 183), (10, 183, 18), (10, 18, 184), (10, 184, 185), (10, 185, 20), (10, 20, 186), (10, 186, 133), (10, 133, 187), (10, 187, 188), (10, 188, 189), (10, 189, 190), (10, 190, 191), (10, 191, 192), (10, 192, 193), (10, 193, 194), (10, 194, 195), (10, 195, 196), (10, 196, 197)");
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(10, 170, 169), (10, 171, 170), (10, 172, 171), (10, 173, 172), (10, 120, 173), (10, 174, 120), (10, 175, 174), (10, 176, 175), (10, 11, 176), (10, 177, 11), (10, 59, 177), (10, 178, 59), (10, 179, 178), (10, 180, 179), (10, 82, 180), (10, 66, 82), (10, 181, 66), (10, 182, 181), (10, 183, 182), (10, 18, 183), (10, 184, 18), (10, 185, 184), (10, 20, 185), (10, 186, 20), (10, 133, 186), (10, 187, 133), (10, 188, 187), (10, 189, 188), (10, 190, 189), (10, 191, 190), (10, 192, 191), (10, 193, 192), (10, 194, 193), (10, 195, 194), (10, 196, 195), (10, 197, 196)");
        // Terminus
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(10, null, 169), (10, 197, null)");
        // Rue du Taur
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(11, 198, 199), (11, 199, 200), (11, 200, 201), (11, 201, 202), (11, 202, 203), (11, 203, 204), (11, 204, 205), (11, 205, 206), (11, 206, 207), (11, 207, 208), (11, 208, 116), (11, 116, 209), (11, 209, 210), (11, 210, 9), (11, 9, 211), (11, 211, 212), (11, 212, 213), (11, 213, 58), (11, 58, 148), (11, 148, 178), (11, 178, 179), (11, 179, 180), (11, 180, 82), (11, 82, 66), (11, 66, 106), (11, 106, 214), (11, 214, 215), (11, 215, 216), (11, 216, 217), (11, 217, 21), (11, 21, 218), (11, 218, 219), (11, 219, 220), (11, 220, 221), (11, 221, 22), (11, 222, 223)");
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(11, 199, 198), (11, 200, 199), (11, 201, 200), (11, 202, 201), (11, 203, 202), (11, 204, 203), (11, 205, 204), (11, 206, 205), (11, 207, 206), (11, 208, 207), (11, 116, 208), (11, 209, 116), (11, 210, 209), (11, 9, 210), (11, 211, 9), (11, 211, 212), (11, 213, 212), (11, 58, 213), (11, 148, 58), (11, 178, 148), (11, 179, 178), (11, 180, 179), (11, 82, 180), (11, 66, 80), (11, 106, 66), (11, 214, 106), (11, 215, 214), (11, 216, 215), (11, 217, 216), (11, 21, 217), (11, 218, 21), (11, 219, 218), (11, 220, 219), (11, 221, 220), (11, 22, 221), (11, 223, 222)");
        // Terminus
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(11, null, 198), (11, 223, null)");
        // Allée Jean Jaurès
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(12, 224, 225), (12, 225, 226), (12, 226, 204), (12, 204, 227), (12, 227, 228), (12, 228, 229), (12, 229, 230), (12, 230, 231), (12, 231, 232), (12, 232, 233), (12, 233, 234), (12, 234, 235), (12, 235, 87), (12, 87, 236), (12, 236, 237), (12, 237, 238), (12, 238, 153), (12, 153, 110)");
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(12, 225, 224), (12, 226, 225), (12, 204, 226), (12, 227, 204), (12, 228, 227), (12, 229, 228), (12, 230, 229), (12, 231, 230), (12, 232, 231), (12, 233, 232), (12, 234, 233), (12, 235, 234), (12, 87, 235), (12, 236, 87), (12, 237, 236), (12, 238, 237), (12, 153, 238), (12, 110, 153)");
        // Terminus
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(12, null, 224), (12, 110, null)");
        // Rue du May
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(13, 15, 16), (13, 16, 239), (13, 239, 64), (13, 64, 66), (13, 66, 240), (13, 240, 42), (13, 42, 241), (13, 241, 242), (13, 242, 167), (13, 167, 243), (13, 243, 74), (13, 74, 244)");
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(13, 16, 15), (13, 239, 16), (13, 64, 239), (13, 66, 64), (13, 240, 66), (13, 42, 240), (13, 241, 42), (13, 242, 241), (13, 167, 242), (13, 243, 167), (13, 74, 243), (13, 244, 74)");
        // Terminus
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(13, null, 15), (13, 244, null)");
        // Rue des Filatiers
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(14, 245, 246), (14, 246, 77), (14, 77, 247), (14, 247, 248), (14, 248, 249), (14, 249, 35), (14, 35, 250), (14, 250, 251), (14, 251, 252), (14, 252, 57), (14, 57, 177), (14, 177, 11), (14, 11, 253), (14, 253, 254), (14, 254, 255), (14, 255, 234), (14, 234, 256), (14, 256, 257), (14, 257, 91), (14, 91, 258), (14, 258, 123), (14, 123, 259), (14, 259, 260), (14, 260, 261), (14, 261, 262), (14, 262, 263), (14, 263, 264)");
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(14, 246, 245), (14, 77, 246), (14, 247, 77), (14, 248, 247), (14, 249, 248), (14, 35, 249), (14, 250, 35), (14, 251, 250), (14, 252, 251), (14, 57, 252), (14, 177, 57), (14, 11, 177), (14, 253, 11), (14, 254, 253), (14, 255, 254), (14, 234, 255), (14, 256, 234), (14, 257, 256), (14, 91, 257), (14, 258, 91), (14, 123, 258), (14, 259, 123), (14, 260, 259), (14, 261, 260), (14, 262, 261), (14, 263, 262), (14, 264, 263)");
        // Terminus
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(14, null, 245), (14, 264, null)");
        // Rue Mage
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(15, 265, 266), (15, 266, 267), (15, 267, 268), (15, 268, 269), (15, 269, 270), (15, 270, 271), (15, 271, 272), (15, 272, 273), (15, 273, 33), (15, 33, 274), (15, 274, 57), (15, 57, 212), (15, 212, 10), (15, 10, 176), (15, 176, 275), (15, 275, 276), (15, 276, 232), (15, 232, 91), (15, 91, 277), (15, 277, 278), (15, 278, 279), (15, 279, 280), (15, 280, 281), (15, 281, 282), (15, 282, 283)");
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(15, 266, 265), (15, 267, 266), (15, 268, 267), (15, 269, 268), (15, 270, 269), (15, 271, 270), (15, 272, 271), (15, 273, 272), (15, 33, 273), (15, 274, 33), (15, 57, 274), (15, 212, 57), (15, 10, 212), (15, 176, 10), (15, 275, 176), (15, 276, 275), (15, 232, 276), (15, 91, 232), (15, 277, 91), (15, 278, 277), (15, 279, 278), (15, 280, 279), (15, 281, 280), (15, 282, 281), (15, 283, 282)");
        // Terminus
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(15, null, 265), (15, 283, null)");
        // Rue d'Espinasse
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(16, 57, 177), (16, 177, 149), (16, 149, 15), (16, 15, 19), (16, 19, 131), (16, 131, 284), (16, 284, 285), (16, 285, 286)");
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(16, 177, 57), (16, 149, 177), (16, 15, 149), (16, 19, 15), (16, 131, 19), (16, 284, 131), (16, 285, 284), (16, 286, 285)");
        // Terminus
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(16, null, 57), (16, 286, null)");
        // Rue des Gestes
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(17, 287, 288), (17, 288, 289), (17, 289, 266), (17, 266, 290), (17, 290, 291), (17, 291, 292), (17, 292, 293), (17, 293, 284), (17, 294, 295), (17, 295, 296), (17, 296, 136), (17, 136, 297), (17, 297, 298), (17, 298, 299), (17, 299, 300), (17, 300, 301), (17, 301, 302), (17, 302, 303), (17, 303, 304), (17, 304, 98), (17, 98, 305), (17, 305, 306), (17, 306, 307), (17, 307, 308), (17, 308, 309)");
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(17, 288, 287), (17, 289, 288), (17, 266, 289), (17, 290, 266), (17, 291, 290), (17, 292, 291), (17, 293, 292), (17, 284, 293), (17, 295, 294), (17, 296, 295), (17, 136, 296), (17, 297, 136), (17, 298, 297), (17, 299, 298), (17, 300, 299), (17, 301, 300), (17, 302, 301), (17, 303, 302), (17, 304, 303), (17, 98, 304), (17, 305, 98), (17, 306, 305), (17, 307, 306), (17, 308, 307), (17, 309, 308)");
        // Terminus
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(17, null, 287), (17, 309, null)");
        // Quai de la Daurade
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(18, 1, 310), (18, 310, 311), (18, 311, 312), (18, 312, 313), (18, 313, 314), (18, 314, 315), (18, 315, 316), (18, 316, 317), (18, 317, 318), (18, 318, 319), (18, 319, 320), (18, 320, 321), (18, 321, 169), (18, 169, 262)");
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(18, 310, 1), (18, 311, 310), (18, 312, 311), (18, 313, 312), (18, 314, 313), (18, 315, 314), (18, 316, 315), (18, 317, 316), (18, 318, 317), (18, 319, 318), (18, 320, 319), (18, 321, 320), (18, 169, 321), (18, 262, 169)");
        // Terminus
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(18, null, 1), (18, 262, null)");
        // Rue Bédelières
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(19, 322, 169), (19, 169, 323), (19, 323, 262), (19, 262, 324), (19, 324, 325), (19, 325, 280), (19, 280, 326), (19, 326, 327), (19, 327, 97), (19, 97, 328), (19, 328, 329), (19, 329, 330), (19, 330, 331), (19, 331, 159), (19, 159, 160), (19, 160, 161), (19, 161, 285), (19, 285, 189), (19, 189, 188), (19, 188, 332), (19, 332, 22)");
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(19, 169, 322), (19, 323, 169), (19, 262, 323), (19, 324, 262), (19, 325, 324), (19, 280, 325), (19, 326, 280), (19, 327, 326), (19, 97, 327), (19, 328, 97), (19, 329, 328), (19, 330, 329), (19, 331, 330), (19, 159, 331), (19, 160, 159), (19, 161, 160), (19, 285, 161), (19, 189, 285), (19, 188, 189), (19, 332, 188), (19, 22, 332)");
        // Terminus
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(19, null, 322), (19, 22, null)");
        // Rue Merlane
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(20, 1, 7), (20, 7, 333), (20, 333, 334), (20, 334, 19), (20, 19, 94)");
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(20, 7, 1), (20, 333, 7), (20, 334, 333), (20, 19, 334), (20, 94, 19)");
        // Terminus
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(20, null, 1), (20, 94, null)");
        // Rue Vélane
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(21, 79, 334), (21, 334, 86), (21, 86, 335), (21, 335, 336), (21, 336, 94), (21, 94, 329)");
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(21, 334, 79), (21, 86, 334), (21, 335, 86), (21, 336, 335), (21, 94, 336), (21, 329, 94)");
        // Terminus
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(21, null, 79), (21, 329, null)");
        // Rue Etroite
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(22, 337, 53), (22, 53, 5), (22, 5, 338), (22, 338, 339), (22, 339, 207), (22, 207, 340), (22, 340, 118), (22, 118, 341), (22, 341, 176), (22, 176, 342), (22, 342, 86), (22, 86, 110), (22, 110, 285)");
        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
                "(22, 53, 337), (22, 5, 53), (22, 338, 5), (22, 339, 338), (22, 207, 339), (22, 340, 207), (22, 118, 340), (22, 341, 118), (22, 176, 341), (22, 342, 176), (22, 86, 342), (22, 110, 86), (22, 285, 110)");
        // Terminus
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(22, null, 337), (22, 285, null)");
        // Rue des Tourneurs
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(23, 79, 334), (23, 334, 19), (23, 334, 79), (23, 19, 334)");
        // Terminus
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(23, null, 79), (23, 334, null)");
        // Rue des Tourneurs
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(24, 57, 79), (24, 79, 57)");
        // Terminus
//        jdbcTemplate.execute("INSERT INTO arret_adjacent(rue, arret, arret_adjacent) VALUES " +
//                "(24, null, 57), (24, 79, null)");
    }

}
