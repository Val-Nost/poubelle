package fr.limayrac.poubelle.db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;

public class V1_0_4__CreationTableRueArret extends BaseJavaMigration implements SpringJDBCTemplateProvider {

    @Override
    public void migrate(Context context) throws Exception {
        JdbcTemplate jdbcTemplate = jdbcTemplate(context);
        creationTable(jdbcTemplate);
        ajoutDonnees(jdbcTemplate);
    }

    public void creationTable(final JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("CREATE TABLE rue_arret(" +
                "id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "rue_id BIGINT NOT NULL, " +
                "arret_id BIGINT NOT NULL, " +
                "FOREIGN KEY (rue_id) REFERENCES rue(id), " +
                "FOREIGN KEY (arret_id) REFERENCES arret(id)" +
                ")");
    }

    public void ajoutDonnees(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("INSERT INTO rue_arret (rue_id, arret_id) VALUES " +
                "(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), " +
                "(1, 6), (1, 7), (1, 8), (1, 9), (1, 10), " +
                "(1, 11), (1, 12), (1, 13), (1, 14), (1, 15), " +
                "(1, 16), (1, 17), (1, 18), (1, 19), (1, 20), " +
                "(1, 21), (1, 22), (1, 23), (1, 24), (1, 25), " +
                "(2, 26), (2, 27), (2, 7), (2, 28), (2, 29), " +
                "(2, 30), (2, 31), (2, 32), (2, 33), (2, 34), " +
                "(2, 35), (2, 36), (2, 37), (2, 38), (2, 39), " +
                "(2, 40), (2, 41), (2, 42), (2, 43), (2, 44), " +
                "(2, 45), (2, 46), (2, 47), (2, 48), (2, 21), " +
                "(3, 49), (3, 50), (3, 51), (3, 52), (3, 53), " +
                "(3, 54), (3, 55), (3, 31), (3, 56), (3, 57), " +
                "(3, 58), (3, 59), (3, 60), (3, 61), (3, 62), " +
                "(3, 63), (3, 64), (3, 65), (3, 66), (3, 67), " +
                "(3, 68), (3, 45), (3, 69), (3, 70), (3, 71), " +
                "(4, 69), (4, 72), (4, 73), (4, 74), (5, 75), " +
                "(5, 76), (5, 77), (5, 78), (5, 37), (5, 79), " +
                "(5, 80), (5, 81), (5, 82), (5, 63), (5, 83), " +
                "(5, 84), (5, 15), (5, 85), (5, 86), (5, 87), " +
                "(5, 88), (5, 89), (5, 90), (5, 91), (5, 92), " +
                "(5, 93), (5, 94), (5, 95), (5, 96), (5, 97), " +
                "(6, 98), (6, 99), (6, 100), (6, 101), (6, 102), " +
                "(6, 103), (6, 104), (6, 40), (6, 39), (6, 79), " +
                "(6, 80), (6, 105), (6, 66), (6, 106), (6, 107), " +
                "(6, 108), (6, 18), (6, 109), (6, 110), (6, 111), " +
                "(6, 112), (6, 113), (7, 7), (7, 114), (7, 115), " +
                "(7, 116), (7, 117), (7, 118), (7, 119), (7, 120), " +
                "(7, 121), (7, 122), (7, 123), (7, 91), (7, 124), " +
                "(7, 93), (7, 94), (7, 125), (7, 126), (7, 127), " +
                "(7, 113), (7, 128), (7, 129), (7, 130), (7, 131), " +
                "(7, 132), (7, 133), (7, 134), (7, 135), (7, 21), " +
                "(8, 136), (8, 137), (8, 138), (8, 139), (8, 140), " +
                "(8, 141), (8, 142), (8, 39), (8, 143), (8, 144), " +
                "(8, 80), (8, 145), (8, 146), (8, 147), (8, 148), " +
                "(8, 59), (8, 149), (8, 13), (8, 150), (8, 15), " +
                "(8, 151), (8, 152), (8, 153), (8, 154), (8, 155), " +
                "(8, 156), (8, 113), (8, 157), (8, 158), (8, 159), " +
                "(8, 160), (8, 161), (8, 162), (8, 163), (9, 143), " +
                "(9, 40), (9, 164), (9, 165), (9, 166), (9, 167), " +
                "(9, 168), (10, 169), (10, 170), (10, 171), (10, 172), " +
                "(10, 173), (10, 120), (10, 174), (10, 175), (10, 176), " +
                "(10, 11), (10, 177), (10, 59), (10, 178), (10, 179), " +
                "(10, 180), (10, 82), (10, 66), (10, 181), (10, 182), " +
                "(10, 183), (10, 18), (10, 184), (10, 185), (10, 20), " +
                "(10, 186), (10, 133), (10, 187), (10, 188), (10, 189), " +
                "(10, 190), (10, 191), (10, 192), (10, 193), (10, 194), " +
                "(10, 195), (10, 196), (10, 197), (11, 198), (11, 199), " +
                "(11, 200), (11, 201), (11, 202), (11, 203), (11, 204), " +
                "(11, 205), (11, 206), (11, 207), (11, 208), (11, 116), " +
                "(11, 209), (11, 210), (11, 9), (11, 211), (11, 212), " +
                "(11, 213), (11, 58), (11, 148), (11, 178), (11, 179), " +
                "(11, 180), (11, 82), (11, 66), (11, 106), (11, 214), " +
                "(11, 215), (11, 216), (11, 217), (11, 21), (11, 218), " +
                "(11, 219), (11, 220), (11, 221), (11, 222), (11, 223), " +
                "(12, 224), (12, 225), (12, 226), (12, 204), (12, 227), " +
                "(12, 228), (12, 229), (12, 230), (12, 120), (12, 231), " +
                "(12, 232), (12, 233), (12, 234), (12, 235), (12, 87), " +
                "(12, 236), (12, 237), (12, 238), (12, 153), (12, 110), " +
                "(13, 15), (13, 16), (13, 239), (13, 64), (13, 66), " +
                "(13, 240), (13, 42), (13, 241), (13, 242), (13, 167), " +
                "(13, 243), (13, 74), (13, 244), (14, 245), (14, 246), " +
                "(14, 77), (14, 247), (14, 248), (14, 249), (14, 35), " +
                "(14, 250), (14, 251), (14, 252), (14, 57), (14, 177), " +
                "(14, 11), (14, 253), (14, 254), (14, 255), (14, 234), " +
                "(14, 256), (14, 257), (14, 91), (14, 258), (14, 123), " +
                "(14, 259), (14, 260), (14, 261), (14, 262), (14, 263), " +
                "(14, 264), (15, 265), (15, 266), (15, 267), (15, 268), " +
                "(15, 269), (15, 270), (15, 271), (15, 272), (15, 273), " +
                "(15, 33), (15, 274), (15, 57), (15, 212), (15, 10), " +
                "(15, 176), (15, 275), (15, 276), (15, 232), (15, 91), " +
                "(15, 277), (15, 278), (15, 279), (15, 280), (15, 281), " +
                "(15, 282), (15, 283), (16, 57), (16, 177), (16, 149), " +
                "(16, 15), (16, 19), (16, 131), (16, 284), (16, 285), " +
                "(16, 286), (17, 287), (17, 288), (17, 289), (17, 266), " +
                "(17, 290), (17, 291), (17, 292), (17, 293), (17, 295), " +
                "(17, 296), (17, 136), (17, 297), (17, 298), (17, 299), " +
                "(17, 300), (17, 301), (17, 302), (17, 303), (17, 304), " +
                "(17, 98), (17, 305), (17, 306), (17, 307), (17, 308), " +
                "(17, 309), (18, 1), (18, 310), (18, 311), (18, 312), " +
                "(18, 313), (18, 314), (18, 315), (18, 316), (18, 317), " +
                "(18, 318), (18, 319), (18, 320), (18, 321), (18, 169), " +
                "(18, 262), (19, 322), (19, 169), (19, 323), (19, 262), " +
                "(19, 324), (19, 325), (19, 280), (19, 326), (19, 327), " +
                "(19, 97), (19, 328), (19, 330), (19, 331), (19, 159), " +
                "(19, 160), (19, 161), (19, 285), (19, 189), (19, 188), " +
                "(19, 332), (19, 22), (20, 1), (20, 7), (20, 333), " +
                "(20, 334), (20, 19), (20, 21), (21, 79), (21, 334), " +
                "(21, 86), (21, 335), (21, 336), (21, 94), (22, 337), " +
                "(22, 53), (22, 5), (22, 338), (22, 339), (22, 207), " +
                "(22, 340), (22, 118), (22, 341), (22, 176), (22, 342), " +
                "(22, 86), (22, 110), (22, 285), (23, 79), (23, 334), " +
                "(23, 19), (24, 57), (24, 79)"
        );
    }
}
