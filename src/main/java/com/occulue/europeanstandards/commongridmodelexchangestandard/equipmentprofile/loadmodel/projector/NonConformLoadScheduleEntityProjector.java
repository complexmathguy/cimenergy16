/**
 * ***************************************************************************** Turnstone Biologics
 * Confidential
 *
 * <p>2018 Turnstone Biologics All Rights Reserved.
 *
 * <p>This file is subject to the terms and conditions defined in file 'license.txt', which is part
 * of this source code package.
 *
 * <p>Contributors : Turnstone Biologics - General Release
 * ****************************************************************************
 */
package com.occulue.europeanstandards.commongridmodelexchangestandard.equipmentprofile.loadmodel.projector;

import com.occulue.api.*;
import com.occulue.entity.*;
import com.occulue.europeanstandards.commongridmodelexchangestandard.equipmentprofile.loadmodel.repository.*;
import com.occulue.exception.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Projector for NonConformLoadSchedule as outlined for the CQRS pattern.
 *
 * <p>Commands are handled by NonConformLoadScheduleAggregate
 *
 * @author your_name_here
 */
@Component("nonConformLoadSchedule-entity-projector")
public class NonConformLoadScheduleEntityProjector {

  // core constructor
  public NonConformLoadScheduleEntityProjector(NonConformLoadScheduleRepository repository) {
    this.repository = repository;
  }

  /*
   * Insert a NonConformLoadSchedule
   *
   * @param	entity NonConformLoadSchedule
   */
  public NonConformLoadSchedule create(NonConformLoadSchedule entity) {
    LOGGER.info("creating " + entity.toString());

    // ------------------------------------------
    // persist a new one
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Update a NonConformLoadSchedule
   *
   * @param	entity NonConformLoadSchedule
   */
  public NonConformLoadSchedule update(NonConformLoadSchedule entity) {
    LOGGER.info("updating " + entity.toString());

    // ------------------------------------------
    // save with an existing instance
    // ------------------------------------------
    return repository.save(entity);
  }

  /*
   * Delete a NonConformLoadSchedule
   *
   * @param	id		UUID
   */
  public NonConformLoadSchedule delete(UUID id) {
    LOGGER.info("deleting " + id.toString());

    // ------------------------------------------
    // locate the entity by the provided id
    // ------------------------------------------
    NonConformLoadSchedule entity = repository.findById(id).get();

    // ------------------------------------------
    // delete what is discovered
    // ------------------------------------------
    repository.delete(entity);

    return entity;
  }

  /*
   * Add to the NonConformLoadSchedules
   *
   * @param	parentID	UUID
   * @param	addTo		childType
   * @return	NonConformLoadSchedule
   */
  public NonConformLoadSchedule addToNonConformLoadSchedules(
      UUID parentId, NonConformLoadSchedule addTo) {
    LOGGER.info("handling AssignNonConformLoadSchedulesToNonConformLoadScheduleEvent - ");

    NonConformLoadSchedule parentEntity = repository.findById(parentId).get();
    NonConformLoadSchedule child =
        NonConformLoadScheduleProjector.find(addTo.getNonConformLoadScheduleId());

    parentEntity.getNonConformLoadSchedules().add(child);

    // ------------------------------------------
    // save
    // ------------------------------------------
    repository.save(parentEntity);

    return parentEntity;
  }

  /*
   * Remove from the NonConformLoadSchedules
   *
   * @param	parentID	UUID
   * @param	removeFrom	childType
   * @return	NonConformLoadSchedule
   */
  public NonConformLoadSchedule removeFromNonConformLoadSchedules(
      UUID parentId, NonConformLoadSchedule removeFrom) {
    LOGGER.info("handling RemoveNonConformLoadSchedulesFromNonConformLoadScheduleEvent ");

    NonConformLoadSchedule parentEntity = repository.findById(parentId).get();
    NonConformLoadSchedule child =
        NonConformLoadScheduleProjector.find(removeFrom.getNonConformLoadScheduleId());

    parentEntity.getNonConformLoadSchedules().remove(child);

    // ------------------------------------------
    // save
    // ------------------------------------------
    update(parentEntity);

    return parentEntity;
  }

  /**
   * Method to retrieve the NonConformLoadSchedule via an FindNonConformLoadScheduleQuery
   *
   * @return query FindNonConformLoadScheduleQuery
   */
  @SuppressWarnings("unused")
  public NonConformLoadSchedule find(UUID id) {
    LOGGER.info("handling find using " + id.toString());
    try {
      return repository.findById(id).get();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(Level.WARNING, "Failed to find a NonConformLoadSchedule - {0}", exc.getMessage());
    }
    return null;
  }

  /**
   * Method to retrieve a collection of all NonConformLoadSchedules
   *
   * @param query FindAllNonConformLoadScheduleQuery
   * @return List<NonConformLoadSchedule>
   */
  @SuppressWarnings("unused")
  public List<NonConformLoadSchedule> findAll(FindAllNonConformLoadScheduleQuery query) {
    LOGGER.info("handling findAll using " + query.toString());
    try {
      return repository.findAll();
    } catch (IllegalArgumentException exc) {
      LOGGER.log(
          Level.WARNING, "Failed to find all NonConformLoadSchedule - {0}", exc.getMessage());
    }
    return null;
  }

  // --------------------------------------------------
  // attributes
  // --------------------------------------------------
  @Autowired protected final NonConformLoadScheduleRepository repository;

  @Autowired
  @Qualifier(value = "nonConformLoadSchedule-entity-projector")
  com.occulue.europeanstandards.commongridmodelexchangestandard.equipmentprofile.loadmodel.projector
          .NonConformLoadScheduleEntityProjector
      NonConformLoadScheduleProjector;

  private static final Logger LOGGER =
      Logger.getLogger(NonConformLoadScheduleEntityProjector.class.getName());
}
