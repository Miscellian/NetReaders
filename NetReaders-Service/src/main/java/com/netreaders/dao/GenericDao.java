package com.netreaders.dao;

import com.netreaders.exception.classes.DataBaseSQLException;
import com.netreaders.exception.classes.DuplicateModelException;
import com.netreaders.exception.classes.NoSuchModelException;

import java.io.Serializable;
import java.util.Collection;

/**
 * The following interface defines the methods for CRUD operations over the tables
 *
 * @param <E>  type of entity
 * @param <PK> primary key of entity
 * @author Andrii Kobyliuk
 */
public interface GenericDao<E, PK extends Serializable> {

    /**
     * Persist the entity object into database and return entity with PK.
     *
     * @throws DataBaseSQLException
     */
    E create(E entity) throws DataBaseSQLException;

    /**
     * Retrieve an object that was previously persisted to the database using
     * the indicated id as primary key.
     *
     * @throws DataBaseSQLException
     * @throws DuplicateModelException
     */
    E getById(PK id) throws DataBaseSQLException, NoSuchModelException;

    /**
     * Save changes made to a persistent object.
     *
     * @throws DataBaseSQLException
     * @throws NoSuchModelException
     */
    void update(E entity) throws DataBaseSQLException;

    /**
     * Remove an object from persistent storage in the database.
     *
     * @throws DataBaseSQLException
     */
    void delete(E entity) throws DataBaseSQLException;

    /**
     * Optional method.
     * Retrieve all objects that were previously persisted to the database.
     *
     * @throws DataBaseSQLException
     */
    Collection<E> getAll() throws DataBaseSQLException;
}
