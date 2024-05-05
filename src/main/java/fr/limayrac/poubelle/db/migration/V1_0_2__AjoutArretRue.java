package fr.limayrac.poubelle.db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;

public class V1_0_2__AjoutArretRue extends BaseJavaMigration implements SpringJDBCTemplateProvider{

    @Override
    public void migrate(Context context) throws Exception {
        JdbcTemplate jdbcTemplate = jdbcTemplate(context);
        creationTableArret(jdbcTemplate);
        ajoutArret(jdbcTemplate);
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
                "VALUES ('Porte de la Chapelle'), ('Marx Dormoy'), ('Jules Joffrin'), ('Lamarck-Caulaincourt'), ('Abbesses'), ('Saint-Georges'), ('Notre-Dame-de-Lorette'), (\"Trinité-d'Estienne d'Orves\"), ('Assemblée nationale'), ('Solférino'), ('Rue du Bac'), ('Rennes'), ('Notre-DAme-des-Champs'), ('Falguière'), ('Volontaires'), ('Vaugirard'), ('Convention'), ('Porte de Versailles'), ('Corentin Celton'), (\"Mairie d'Issy\")");
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


}
