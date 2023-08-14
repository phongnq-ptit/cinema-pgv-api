/*
 * This file is generated by jOOQ.
 */
package org.cinema.jooq.tables;


import org.cinema.jooq.Cinema;
import org.cinema.jooq.Keys;
import org.cinema.jooq.tables.records.MovieFileRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MovieFile extends TableImpl<MovieFileRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>cinema.movie_file</code>
     */
    public static final MovieFile MOVIE_FILE = new MovieFile();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MovieFileRecord> getRecordType() {
        return MovieFileRecord.class;
    }

    /**
     * The column <code>cinema.movie_file.id</code>.
     */
    public final TableField<MovieFileRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>cinema.movie_file.uuid</code>.
     */
    public final TableField<MovieFileRecord, byte[]> UUID = createField(DSL.name("uuid"), SQLDataType.BINARY(16).nullable(false), this, "");

    /**
     * The column <code>cinema.movie_file.movie_uuid</code>.
     */
    public final TableField<MovieFileRecord, byte[]> MOVIE_UUID = createField(DSL.name("movie_uuid"), SQLDataType.BINARY(16).nullable(false), this, "");

    /**
     * The column <code>cinema.movie_file.file_uuid</code>.
     */
    public final TableField<MovieFileRecord, byte[]> FILE_UUID = createField(DSL.name("file_uuid"), SQLDataType.BINARY(16).nullable(false), this, "");

    private MovieFile(Name alias, Table<MovieFileRecord> aliased) {
        this(alias, aliased, null);
    }

    private MovieFile(Name alias, Table<MovieFileRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>cinema.movie_file</code> table reference
     */
    public MovieFile(String alias) {
        this(DSL.name(alias), MOVIE_FILE);
    }

    /**
     * Create an aliased <code>cinema.movie_file</code> table reference
     */
    public MovieFile(Name alias) {
        this(alias, MOVIE_FILE);
    }

    /**
     * Create a <code>cinema.movie_file</code> table reference
     */
    public MovieFile() {
        this(DSL.name("movie_file"), null);
    }

    public <O extends Record> MovieFile(Table<O> child, ForeignKey<O, MovieFileRecord> key) {
        super(child, key, MOVIE_FILE);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Cinema.CINEMA;
    }

    @Override
    public Identity<MovieFileRecord, Long> getIdentity() {
        return (Identity<MovieFileRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<MovieFileRecord> getPrimaryKey() {
        return Keys.KEY_MOVIE_FILE_PRIMARY;
    }

    @Override
    public MovieFile as(String alias) {
        return new MovieFile(DSL.name(alias), this);
    }

    @Override
    public MovieFile as(Name alias) {
        return new MovieFile(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public MovieFile rename(String name) {
        return new MovieFile(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public MovieFile rename(Name name) {
        return new MovieFile(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Long, byte[], byte[], byte[]> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
