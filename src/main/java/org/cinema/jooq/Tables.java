/*
 * This file is generated by jOOQ.
 */
package org.cinema.jooq;


import org.cinema.jooq.tables.Categories;
import org.cinema.jooq.tables.Files;
import org.cinema.jooq.tables.FlywaySchemaHistory;
import org.cinema.jooq.tables.MovieCategory;
import org.cinema.jooq.tables.MovieFile;
import org.cinema.jooq.tables.MoviePublic;
import org.cinema.jooq.tables.Movies;
import org.cinema.jooq.tables.Purchases;
import org.cinema.jooq.tables.Tickets;
import org.cinema.jooq.tables.Users;


/**
 * Convenience access to all tables in cinema.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>cinema.categories</code>.
     */
    public static final Categories CATEGORIES = Categories.CATEGORIES;

    /**
     * The table <code>cinema.files</code>.
     */
    public static final Files FILES = Files.FILES;

    /**
     * The table <code>cinema.flyway_schema_history</code>.
     */
    public static final FlywaySchemaHistory FLYWAY_SCHEMA_HISTORY = FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY;

    /**
     * The table <code>cinema.movie_category</code>.
     */
    public static final MovieCategory MOVIE_CATEGORY = MovieCategory.MOVIE_CATEGORY;

    /**
     * The table <code>cinema.movie_file</code>.
     */
    public static final MovieFile MOVIE_FILE = MovieFile.MOVIE_FILE;

    /**
     * The table <code>cinema.movie_public</code>.
     */
    public static final MoviePublic MOVIE_PUBLIC = MoviePublic.MOVIE_PUBLIC;

    /**
     * The table <code>cinema.movies</code>.
     */
    public static final Movies MOVIES = Movies.MOVIES;

    /**
     * The table <code>cinema.purchases</code>.
     */
    public static final Purchases PURCHASES = Purchases.PURCHASES;

    /**
     * The table <code>cinema.tickets</code>.
     */
    public static final Tickets TICKETS = Tickets.TICKETS;

    /**
     * The table <code>cinema.users</code>.
     */
    public static final Users USERS = Users.USERS;
}
