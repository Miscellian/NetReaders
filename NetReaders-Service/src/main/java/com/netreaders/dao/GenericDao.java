package com.netreaders.dao;

import com.netreaders.exception.DataBaseSQLException;

import java.io.Serializable;
import java.util.Collection;

/**
 * The following interface defines the methods for CRUD operations
 *
 * @param <E>  type of entity
 * @param <PK> primary key of entity
 * @author Andrii Kobyliuk
 */
public interface GenericDao<E, PK extends Serializable> {

    /**
     * Persist the newInstance object into database and return newInstance with PK
     */
    E create(E newInstance) throws DataBaseSQLException;

    /**
     * Retrieve an object that was previously persisted to the database using
     * the indicated id as primary key
     */
    E getById(PK id) throws DataBaseSQLException;

    /**
     * Save changes made to a persistent object.
     */
    void update(E persistentObject) throws DataBaseSQLException;

    /**
     * Remove an object from persistent storage in the database
     */
    void delete(E persistentObject) throws DataBaseSQLException;

    /**
     * Retrieve all objects that were previously persisted to the database
     */
    Collection<E> getAll() throws DataBaseSQLException;
}
