/*
 * This file is generated by jOOQ.
 */
package org.cinema.jooq.tables;


import java.time.LocalDateTime;

import org.cinema.jooq.Cinema;
import org.cinema.jooq.Keys;
import org.cinema.jooq.tables.records.MoviesRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row9;
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
public class Movies extends TableImpl<MoviesRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>cinema.movies</code>
     */
    public static final Movies MOVIES = new Movies();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MoviesRecord> getRecordType() {
        return MoviesRecord.class;
    }

    /**
     * The column <code>cinema.movies.id</code>.
     */
    public final TableField<MoviesRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>cinema.movies.uuid</code>.
     */
    public final TableField<MoviesRecord, byte[]> UUID = createField(DSL.name("uuid"), SQLDataType.BINARY(16).nullable(false), this, "");

    /**
     * The column <code>cinema.movies.name</code>.
     */
    public final TableField<MoviesRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(200).nullable(false), this, "");

    /**
     * The column <code>cinema.movies.duration</code>.
     */
    public final TableField<MoviesRecord, Integer> DURATION = createField(DSL.name("duration"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>cinema.movies.author</code>.
     */
    public final TableField<MoviesRecord, String> AUTHOR = createField(DSL.name("author"), SQLDataType.VARCHAR(200).nullable(false), this, "");

    /**
     * The column <code>cinema.movies.release_date</code>.
     */
    public final TableField<MoviesRecord, LocalDateTime> RELEASE_DATE = createField(DSL.name("release_date"), SQLDataType.LOCALDATETIME(0), this, "");

    /**
     * The column <code>cinema.movies.active</code>.
     */
    public final TableField<MoviesRecord, Integer> ACTIVE = createField(DSL.name("active"), SQLDataType.INTEGER.nullable(false).defaultValue(DSL.inline("1", SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>cinema.movies.created_at</code>.
     */
    public final TableField<MoviesRecord, LocalDateTime> CREATED_AT = createField(DSL.name("created_at"), SQLDataType.LOCALDATETIME(0).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>cinema.movies.updated_at</code>.
     */
    public final TableField<MoviesRecord, LocalDateTime> UPDATED_AT = createField(DSL.name("updated_at"), SQLDataType.LOCALDATETIME(0).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.LOCALDATETIME)), this, "");

    private Movies(Name alias, Table<MoviesRecord> aliased) {
        this(alias, aliased, null);
    }

    private Movies(Name alias, Table<MoviesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>cinema.movies</code> table reference
     */
    public Movies(String alias) {
        this(DSL.name(alias), MOVIES);
    }

    /**
     * Create an aliased <code>cinema.movies</code> table reference
     */
    public Movies(Name alias) {
        this(alias, MOVIES);
    }

    /**
     * Create a <code>cinema.movies</code> table reference
     */
    public Movies() {
        this(DSL.name("movies"), null);
    }

    public <O extends Record> Movies(Table<O> child, ForeignKey<O, MoviesRecord> key) {
        super(child, key, MOVIES);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Cinema.CINEMA;
    }

    @Override
    public Identity<MoviesRecord, Long> getIdentity() {
        return (Identity<MoviesRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<MoviesRecord> getPrimaryKey() {
        return Keys.KEY_MOVIES_PRIMARY;
    }

    @Override
    public Movies as(String alias) {
        return new Movies(DSL.name(alias), this);
    }

    @Override
    public Movies as(Name alias) {
        return new Movies(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Movies rename(String name) {
        return new Movies(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Movies rename(Name name) {
        return new Movies(name, null);
    }

    // -------------------------------------------------------------------------
    // Row9 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row9<Long, byte[], String, Integer, String, LocalDateTime, Integer, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row9) super.fieldsRow();
    }
}
