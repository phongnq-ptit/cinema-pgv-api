/*
 * This file is generated by jOOQ.
 */
package org.cinema.jooq;


import java.util.Arrays;
import java.util.List;

import org.cinema.jooq.tables.Categories;
import org.cinema.jooq.tables.Files;
import org.cinema.jooq.tables.FlywaySchemaHistory;
import org.cinema.jooq.tables.MovieCategory;
import org.cinema.jooq.tables.MovieFile;
import org.cinema.jooq.tables.MoviePublic;
import org.cinema.jooq.tables.Movies;
import org.cinema.jooq.tables.Users;
import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Cinema extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>cinema</code>
     */
    public static final Cinema CINEMA = new Cinema();

    /**
     * The table <code>cinema.categories</code>.
     */
    public final Categories CATEGORIES = Categories.CATEGORIES;

    /**
     * The table <code>cinema.files</code>.
     */
    public final Files FILES = Files.FILES;

    /**
     * The table <code>cinema.flyway_schema_history</code>.
     */
    public final FlywaySchemaHistory FLYWAY_SCHEMA_HISTORY = FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY;

    /**
     * The table <code>cinema.movie_category</code>.
     */
    public final MovieCategory MOVIE_CATEGORY = MovieCategory.MOVIE_CATEGORY;

    /**
     * The table <code>cinema.movie_file</code>.
     */
    public final MovieFile MOVIE_FILE = MovieFile.MOVIE_FILE;

    /**
     * The table <code>cinema.movie_public</code>.
     */
    public final MoviePublic MOVIE_PUBLIC = MoviePublic.MOVIE_PUBLIC;

    /**
     * The table <code>cinema.movies</code>.
     */
    public final Movies MOVIES = Movies.MOVIES;

    /**
     * The table <code>cinema.users</code>.
     */
    public final Users USERS = Users.USERS;

    /**
     * No further instances allowed
     */
    private Cinema() {
        super("cinema", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.asList(
            Categories.CATEGORIES,
            Files.FILES,
            FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY,
            MovieCategory.MOVIE_CATEGORY,
            MovieFile.MOVIE_FILE,
            MoviePublic.MOVIE_PUBLIC,
            Movies.MOVIES,
            Users.USERS
        );
    }
}
