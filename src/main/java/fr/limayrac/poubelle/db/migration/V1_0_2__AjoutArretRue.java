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
                "libelle VARCHAR(255) UNIQUE" +
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
                "ordre INTEGER" +
                ")");
    }

    public void ajoutArretVoisin(JdbcTemplate jdbcTemplate) {
        // Rue Croix-Baragnon
        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, ordre) VALUES " +
                "(1, 1, 1), (1, 2, 2), (1, 3, 3), (1, 4, 4), (1, 5, 5), (1, 6, 6), (1, 7, 7), (1, 8, 8), (1, 9, 9), (1, 10, 10), (1, 11, 11), (1, 12, 12), (1, 13, 13), (1, 14, 14), (1, 15, 15), (1, 16, 16), (1, 17, 17), (1, 18, 18), (1, 19, 19), (1, 20, 20), (1, 21, 21), (1, 22, 22), (1, 23, 23), (1, 24, 24), (1, 25, 25)");
        // Rue des Arts
        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, ordre) VALUES " +
                "(2, 26, 1), (2, 27, 2), (2, 7, 3), (2, 28, 4), (2, 29, 5), (2, 30, 6), (2, 31, 7), (2, 32, 8), (2, 33, 9), (2, 34, 10), (2, 35, 11), (2, 36, 12), (2, 37, 13), (2, 38, 14), (2, 39, 15), (2, 40, 16), (2, 41, 17), (2, 42, 18), (2, 43, 19), (2, 44, 20), (2, 45, 21), (2, 46, 22), (2, 47, 23), (2, 48, 24), (2, 21, 25)");
        // Rue Pargaminières
        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, ordre) VALUES " +
                "(3, 49, 1), (3, 50, 2), (3, 51, 3), (3, 52, 4), (3, 53, 5), (3, 54, 6), (3, 55, 7), (3, 31, 8), (3, 56, 9), (3, 57, 10), (3, 58, 11), (3, 59, 12), (3, 60, 13), (3, 61, 14), (3, 62, 15), (3, 63, 16), (3, 64, 17), (3, 65, 18), (3, 66, 19), (3, 67, 20), (3, 68, 21), (3, 45, 22), (3, 69, 23), (3, 70, 24), (3, 71, 25)");
        // Rue Saint-Rome
        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, ordre) VALUES " +
                "(4, 69, 1), (4, 72, 2), (4, 73, 3), (4, 74, 4)");
        // Rue Saint-Antoine du T
        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, ordre) VALUES " +
                "(5, 75, 1), (5, 76, 2), (5, 77, 3), (5, 78, 4), (5, 37, 5), (5, 79, 6), (5, 80, 7), (5, 81, 8), (5, 82, 9), (5, 63, 10), (5, 83, 11), (5, 84, 12), (5, 15, 13), (5, 85, 14), (5, 86, 15), (5, 87, 16), (5, 88, 17), (5, 89, 18), (5, 90, 19), (5, 91, 20), (5, 92, 21), (5, 93, 22), (5, 94, 23), (5, 95, 24), (5, 96, 25), (5, 97, 26)");
        // Rue de la Fonderie
        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, ordre) VALUES " +
                "(6, 98, 1), (6, 99, 2), (6, 100, 3), (6, 101, 4), (6, 102, 5), (6, 103, 6), (6, 104, 7), (6, 40, 8), (6, 39, 9), (6, 79, 10), (6, 80, 11), (6, 105, 12), (6, 66, 13), (6, 106, 14), (6, 107, 15), (6, 108, 16), (6, 18, 17), (6, 109, 18), (6, 110, 19), (6, 111, 20), (6, 112, 21), (6, 113, 22)");
        // Rue Peyrolières
        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, ordre) VALUES " +
                "(7, 7, 1), (7, 114, 2), (7, 115, 3), (7, 116, 4), (7, 117, 5), (7, 118, 6), (7, 119, 7), (7, 120, 8), (7, 121, 9), (7, 122, 10), (7, 123, 11), (7, 91, 12), (7, 124, 13), (7, 93, 14), (7, 94, 15), (7, 125, 16), (7, 126, 17), (7, 127, 18), (7, 113, 19), (7, 128, 20), (7, 129, 21), (7, 130, 22), (7, 131, 23), (7, 132, 24), (7, 133, 25), (7, 134, 26), (7, 135, 27), (7, 21, 28)");
        // Rue Genty-Magre
        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, ordre) VALUES " +
                "(8, 136, 1), (8, 137, 2), (8, 138, 3), (8, 139, 4), (8, 140, 5), (8, 141, 6), (8, 142, 7), (8, 39, 8), (8, 143, 9), (8, 144, 10), (8, 80, 11), (8, 145, 12), (8, 146, 13), (8, 147, 14), (8, 148, 15), (8, 59, 16), (8, 149, 17), (8, 13, 18), (8, 150, 19), (8, 15, 20), (8, 151, 21), (8, 152, 22), (8, 153, 23), (8, 154, 24), (8, 155, 25), (8, 156, 26), (8, 113, 27), (8, 157, 28), (8, 158, 29), (8, 159, 30), (8, 160, 31), (8, 161, 32), (8, 162, 33), (8, 163, 34)");
        // Rue d'Alsace-Lorraine
        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, ordre) VALUES " +
                "(9, 143, 1), (9, 40, 2), (9, 164, 3), (9, 165, 4), (9, 166, 5), (9, 167, 6), (9, 168, 7)");
        // Rue Peyras
        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, ordre) VALUES " +
                "(10, 169, 1), (10, 170, 2), (10, 171, 3), (10, 172, 4), (10, 173, 5), (10, 120, 6), (10, 174, 7), (10, 175, 8), (10, 176, 9), (10, 11, 10), (10, 177, 11), (10, 59, 12), (10, 178, 13), (10, 179, 14), (10, 180, 15), (10, 82, 16), (10, 66, 17), (10, 181, 18), (10, 182, 19), (10, 183, 20), (10, 18, 21), (10, 184, 22), (10, 185, 23), (10, 20, 24), (10, 186, 25), (10, 133, 26), (10, 187, 27), (10, 188, 28), (10, 189, 29), (10, 190, 30), (10, 191, 31), (10, 192, 32), (10, 193, 33), (10, 194, 34), (10, 195, 35), (10, 196, 36), (10, 197, 37)");
        // Rue du Taur
        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, ordre) VALUES " +
                "(11, 198, 1), (11, 199, 2), (11, 200, 3), (11, 201, 4), (11, 202, 5), (11, 203, 6), (11, 204, 7), (11, 205, 8), (11, 206, 9), (11, 207, 10), (11, 208, 11), (11, 116, 12), (11, 209, 13), (11, 210, 14), (11, 9, 15), (11, 211, 16), (11, 212, 17), (11, 213, 18), (11, 58, 19), (11, 148, 20), (11, 178, 21), (11, 179, 22), (11, 180, 23), (11, 82, 24), (11, 66, 25), (11, 106, 26), (11, 214, 27), (11, 215, 28), (11, 216, 29), (11, 217, 30), (11, 21, 31), (11, 218, 32), (11, 219, 33), (11, 220, 34), (11, 221, 35), (11, 222, 36), (11, 223, 37)");
        // Allée Jean Jaurès
        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, ordre) VALUES " +
                "(12, 224, 1), (12, 225, 2), (12, 226, 3), (12, 204, 4), (12, 227, 5), (12, 228, 6), (12, 229, 7), (12, 230, 8), (12, 231, 9), (12, 232, 10), (12, 233, 11), (12, 234, 12), (12, 235, 13), (12, 87, 14), (12, 236, 15), (12, 237, 16), (12, 238, 17), (12, 153, 18), (12, 110, 19)");
        // Rue du May
        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, ordre) VALUES " +
                "(13, 15, 1), (13, 16, 2), (13, 239, 3), (13, 64, 4), (13, 66, 5), (13, 240, 6), (13, 42, 7), (13, 241, 8), (13, 242, 9), (13, 167, 10), (13, 243, 11), (13, 74, 12), (13, 244, 13)");
        // Rue des Filatiers
        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, ordre) VALUES " +
                "(14, 245, 1), (14, 246, 2), (14, 77, 3), (14, 247, 4), (14, 248, 5), (14, 249, 6), (14, 35, 7), (14, 250, 8), (14, 251, 9), (14, 252, 10), (14, 57, 11), (14, 177, 12), (14, 11, 13), (14, 253, 14), (14, 254, 15), (14, 255, 16), (14, 234, 17), (14, 256, 18), (14, 257, 19), (14, 91, 20), (14, 258, 21), (14, 123, 22), (14, 259, 23), (14, 260, 24), (14, 261, 25), (14, 262, 26), (14, 263, 27), (14, 264, 28)");
        // Rue Mage
        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, ordre) VALUES " +
                "(15, 265, 1), (15, 266, 2), (15, 267, 3), (15, 268, 4), (15, 269, 5), (15, 270, 6), (15, 271, 7), (15, 272, 8), (15, 273, 9), (15, 33, 10), (15, 274, 11), (15, 57, 12), (15, 212, 13), (15, 10, 14), (15, 176, 15), (15, 275, 16), (15, 276, 17), (15, 232, 18), (15, 91, 19), (15, 277, 20), (15, 278, 21), (15, 279, 22), (15, 280, 23), (15, 281, 24), (15, 282, 25), (15, 283, 26)");
        // Rue d'Espinasse
        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, ordre) VALUES " +
                "(16, 57, 1), (16, 177, 2), (16, 149, 3), (16, 15, 4), (16, 19, 5), (16, 131, 6), (16, 284, 7), (16, 285, 8), (16, 286, 9)");
        // Rue des Gestes
        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, ordre) VALUES " +
                "(17, 287, 1), (17, 288, 2), (17, 289, 3), (17, 266, 4), (17, 290, 5), (17, 291, 6), (17, 292, 7), (17, 293, 8), (17, 294, 9), (17, 295, 10), (17, 296, 11), (17, 136, 12), (17, 297, 13), (17, 298, 14), (17, 299, 15), (17, 300, 16), (17, 301, 17), (17, 302, 18), (17, 303, 19), (17, 304, 20), (17, 98, 21), (17, 305, 22), (17, 306, 23), (17, 307, 24), (17, 308, 25), (17, 309, 26)");
        // Quai de la Daurade
        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, ordre) VALUES " +
                "(18, 1, 1), (18, 310, 2), (18, 311, 3), (18, 312, 4), (18, 313, 5), (18, 314, 6), (18, 315, 7), (18, 316, 8), (18, 317, 9), (18, 318, 10), (18, 319, 11), (18, 320, 12), (18, 321, 13), (18, 169, 14), (18, 262, 15)");
        // Rue Bédelières
        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, ordre) VALUES " +
                "(19, 322, 1), (19, 169, 2), (19, 323, 3), (19, 262, 4), (19, 324, 5), (19, 325, 6), (19, 280, 7), (19, 326, 8), (19, 327, 9), (19, 97, 10), (19, 328, 11), (19, 329, 12), (19, 330, 13), (19, 331, 14), (19, 159, 15), (19, 160, 16), (19, 161, 17), (19, 285, 18), (19, 189, 19), (19, 188, 20), (19, 332, 21), (19, 22, 22)");
        // Rue Merlane
        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, ordre) VALUES " +
                "(20, 1, 1), (20, 7, 2), (20, 333, 3), (20, 334, 4), (20, 19, 5), (20, 94, 21)");
        // Rue Vélane
        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, ordre) VALUES " +
                "(21, 79, 1), (21, 334, 2), (21, 86, 3), (21, 335, 4), (21, 336, 5), (21, 94, 6), (21, 329, 7)");
        // Rue Etroite
        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, ordre) VALUES " +
                "(22, 337, 1), (22, 53, 2), (22, 5, 3), (22, 338, 4), (22, 339, 5), (22, 207, 6), (22, 340, 7), (22, 118, 8), (22, 341, 9), (22, 176, 10), (22, 342, 11), (22, 86, 12), (22, 110, 13), (22, 285, 14)");
        // Rue des Tourneurs
        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, ordre) VALUES " +
                "(23, 79, 1), (23, 334, 2), (23, 19, 3)");
        // Rue des Tourneurs
        jdbcTemplate.execute("INSERT INTO arret_voisin(rue, arret, ordre) VALUES " +
                "(24, 57, 1), (24, 79, 2)");
    }

}
