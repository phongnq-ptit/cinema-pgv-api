/*
 * This file is generated by jOOQ.
 */
package org.cinema.jooq.tables.records;


import java.time.LocalDateTime;

import org.cinema.jooq.tables.Users;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record11;
import org.jooq.Row11;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UsersRecord extends UpdatableRecordImpl<UsersRecord> implements Record11<Long, byte[], String, String, String, String, String, byte[], Integer, LocalDateTime, LocalDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>cinema.users.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>cinema.users.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>cinema.users.uuid</code>.
     */
    public void setUuid(byte[] value) {
        set(1, value);
    }

    /**
     * Getter for <code>cinema.users.uuid</code>.
     */
    public byte[] getUuid() {
        return (byte[]) get(1);
    }

    /**
     * Setter for <code>cinema.users.role</code>.
     */
    public void setRole(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>cinema.users.role</code>.
     */
    public String getRole() {
        return (String) get(2);
    }

    /**
     * Setter for <code>cinema.users.user_name</code>.
     */
    public void setUserName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>cinema.users.user_name</code>.
     */
    public String getUserName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>cinema.users.email</code>.
     */
    public void setEmail(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>cinema.users.email</code>.
     */
    public String getEmail() {
        return (String) get(4);
    }

    /**
     * Setter for <code>cinema.users.password</code>.
     */
    public void setPassword(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>cinema.users.password</code>.
     */
    public String getPassword() {
        return (String) get(5);
    }

    /**
     * Setter for <code>cinema.users.address</code>.
     */
    public void setAddress(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>cinema.users.address</code>.
     */
    public String getAddress() {
        return (String) get(6);
    }

    /**
     * Setter for <code>cinema.users.cinema_id</code>.
     */
    public void setCinemaId(byte[] value) {
        set(7, value);
    }

    /**
     * Getter for <code>cinema.users.cinema_id</code>.
     */
    public byte[] getCinemaId() {
        return (byte[]) get(7);
    }

    /**
     * Setter for <code>cinema.users.active</code>.
     */
    public void setActive(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>cinema.users.active</code>.
     */
    public Integer getActive() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>cinema.users.created_at</code>.
     */
    public void setCreatedAt(LocalDateTime value) {
        set(9, value);
    }

    /**
     * Getter for <code>cinema.users.created_at</code>.
     */
    public LocalDateTime getCreatedAt() {
        return (LocalDateTime) get(9);
    }

    /**
     * Setter for <code>cinema.users.updated_at</code>.
     */
    public void setUpdatedAt(LocalDateTime value) {
        set(10, value);
    }

    /**
     * Getter for <code>cinema.users.updated_at</code>.
     */
    public LocalDateTime getUpdatedAt() {
        return (LocalDateTime) get(10);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record11 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row11<Long, byte[], String, String, String, String, String, byte[], Integer, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row11) super.fieldsRow();
    }

    @Override
    public Row11<Long, byte[], String, String, String, String, String, byte[], Integer, LocalDateTime, LocalDateTime> valuesRow() {
        return (Row11) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Users.USERS.ID;
    }

    @Override
    public Field<byte[]> field2() {
        return Users.USERS.UUID;
    }

    @Override
    public Field<String> field3() {
        return Users.USERS.ROLE;
    }

    @Override
    public Field<String> field4() {
        return Users.USERS.USER_NAME;
    }

    @Override
    public Field<String> field5() {
        return Users.USERS.EMAIL;
    }

    @Override
    public Field<String> field6() {
        return Users.USERS.PASSWORD;
    }

    @Override
    public Field<String> field7() {
        return Users.USERS.ADDRESS;
    }

    @Override
    public Field<byte[]> field8() {
        return Users.USERS.CINEMA_ID;
    }

    @Override
    public Field<Integer> field9() {
        return Users.USERS.ACTIVE;
    }

    @Override
    public Field<LocalDateTime> field10() {
        return Users.USERS.CREATED_AT;
    }

    @Override
    public Field<LocalDateTime> field11() {
        return Users.USERS.UPDATED_AT;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public byte[] component2() {
        return getUuid();
    }

    @Override
    public String component3() {
        return getRole();
    }

    @Override
    public String component4() {
        return getUserName();
    }

    @Override
    public String component5() {
        return getEmail();
    }

    @Override
    public String component6() {
        return getPassword();
    }

    @Override
    public String component7() {
        return getAddress();
    }

    @Override
    public byte[] component8() {
        return getCinemaId();
    }

    @Override
    public Integer component9() {
        return getActive();
    }

    @Override
    public LocalDateTime component10() {
        return getCreatedAt();
    }

    @Override
    public LocalDateTime component11() {
        return getUpdatedAt();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public byte[] value2() {
        return getUuid();
    }

    @Override
    public String value3() {
        return getRole();
    }

    @Override
    public String value4() {
        return getUserName();
    }

    @Override
    public String value5() {
        return getEmail();
    }

    @Override
    public String value6() {
        return getPassword();
    }

    @Override
    public String value7() {
        return getAddress();
    }

    @Override
    public byte[] value8() {
        return getCinemaId();
    }

    @Override
    public Integer value9() {
        return getActive();
    }

    @Override
    public LocalDateTime value10() {
        return getCreatedAt();
    }

    @Override
    public LocalDateTime value11() {
        return getUpdatedAt();
    }

    @Override
    public UsersRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public UsersRecord value2(byte[] value) {
        setUuid(value);
        return this;
    }

    @Override
    public UsersRecord value3(String value) {
        setRole(value);
        return this;
    }

    @Override
    public UsersRecord value4(String value) {
        setUserName(value);
        return this;
    }

    @Override
    public UsersRecord value5(String value) {
        setEmail(value);
        return this;
    }

    @Override
    public UsersRecord value6(String value) {
        setPassword(value);
        return this;
    }

    @Override
    public UsersRecord value7(String value) {
        setAddress(value);
        return this;
    }

    @Override
    public UsersRecord value8(byte[] value) {
        setCinemaId(value);
        return this;
    }

    @Override
    public UsersRecord value9(Integer value) {
        setActive(value);
        return this;
    }

    @Override
    public UsersRecord value10(LocalDateTime value) {
        setCreatedAt(value);
        return this;
    }

    @Override
    public UsersRecord value11(LocalDateTime value) {
        setUpdatedAt(value);
        return this;
    }

    @Override
    public UsersRecord values(Long value1, byte[] value2, String value3, String value4, String value5, String value6, String value7, byte[] value8, Integer value9, LocalDateTime value10, LocalDateTime value11) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UsersRecord
     */
    public UsersRecord() {
        super(Users.USERS);
    }

    /**
     * Create a detached, initialised UsersRecord
     */
    public UsersRecord(Long id, byte[] uuid, String role, String userName, String email, String password, String address, byte[] cinemaId, Integer active, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(Users.USERS);

        setId(id);
        setUuid(uuid);
        setRole(role);
        setUserName(userName);
        setEmail(email);
        setPassword(password);
        setAddress(address);
        setCinemaId(cinemaId);
        setActive(active);
        setCreatedAt(createdAt);
        setUpdatedAt(updatedAt);
    }
}
