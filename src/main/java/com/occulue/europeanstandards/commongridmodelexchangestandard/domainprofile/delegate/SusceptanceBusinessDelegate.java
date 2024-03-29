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
package com.occulue.europeanstandards.commongridmodelexchangestandard.domainprofile.delegate;

import com.occulue.api.*;
import com.occulue.delegate.*;
import com.occulue.entity.*;
import com.occulue.europeanstandards.commongridmodelexchangestandard.domainprofile.validator.*;
import com.occulue.exception.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryUpdateEmitter;

/**
 * Susceptance business delegate class.
 *
 * <p>This class implements the Business Delegate design pattern for the purpose of:
 *
 * <ol>
 *   <li>Reducing coupling between the business tier and a client of the business tier by hiding all
 *       business-tier implementation details
 *   <li>Improving the available of Susceptance related services in the case of a Susceptance
 *       business related service failing.
 *   <li>Exposes a simpler, uniform Susceptance interface to the business tier, making it easy for
 *       clients to consume a simple Java object.
 *   <li>Hides the communication protocol that may be required to fulfill Susceptance business
 *       related services.
 * </ol>
 *
 * <p>
 *
 * @author your_name_here
 */
public class SusceptanceBusinessDelegate extends BaseBusinessDelegate {
  // ************************************************************************
  // Public Methods
  // ************************************************************************
  /** Default Constructor */
  public SusceptanceBusinessDelegate() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
    commandGateway = applicationContext.getBean(CommandGateway.class);
    queryUpdateEmitter = applicationContext.getBean(QueryUpdateEmitter.class);
  }

  /**
   * Susceptance Business Delegate Factory Method
   *
   * <p>All methods are expected to be self-sufficient.
   *
   * @return SusceptanceBusinessDelegate
   */
  public static SusceptanceBusinessDelegate getSusceptanceInstance() {
    return (new SusceptanceBusinessDelegate());
  }

  /**
   * Creates the provided command.
   *
   * @param command ${class.getCreateCommandAlias()}
   * @exception ProcessingException
   * @exception IllegalArgumentException
   * @return CompletableFuture<UUID>
   */
  public CompletableFuture<UUID> createSusceptance(CreateSusceptanceCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<UUID> completableFuture = null;

    try {
      // --------------------------------------
      // assign identity now if none
      // --------------------------------------
      if (command.getSusceptanceId() == null) command.setSusceptanceId(UUID.randomUUID());

      // --------------------------------------
      // validate the command
      // --------------------------------------
      SusceptanceValidator.getInstance().validate(command);

      // ---------------------------------------
      // issue the CreateSusceptanceCommand - by convention the future return value for a create
      // command
      // that is handled by the constructor of an aggregate will return the UUID
      // ---------------------------------------
      completableFuture = commandGateway.send(command);

      LOGGER.log(
          Level.INFO,
          "return from Command Gateway for CreateSusceptanceCommand of Susceptance is " + command);

    } catch (Exception exc) {
      final String errMsg = "Unable to create Susceptance - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Update the provided command.
   *
   * @param command UpdateSusceptanceCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   * @exception IllegalArgumentException
   */
  public CompletableFuture<Void> updateSusceptance(UpdateSusceptanceCommand command)
      throws ProcessingException, IllegalArgumentException {
    CompletableFuture<Void> completableFuture = null;

    try {

      // --------------------------------------
      // validate
      // --------------------------------------
      SusceptanceValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the UpdateSusceptanceCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg = "Unable to save Susceptance - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    }

    return completableFuture;
  }

  /**
   * Deletes the associatied value object
   *
   * @param command DeleteSusceptanceCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   */
  public CompletableFuture<Void> delete(DeleteSusceptanceCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<Void> completableFuture = null;

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      SusceptanceValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the DeleteSusceptanceCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg = "Unable to delete Susceptance using Id = " + command.getSusceptanceId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Method to retrieve the Susceptance via SusceptanceFetchOneSummary
   *
   * @param summary SusceptanceFetchOneSummary
   * @return SusceptanceFetchOneResponse
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  public Susceptance getSusceptance(SusceptanceFetchOneSummary summary)
      throws ProcessingException, IllegalArgumentException {

    if (summary == null)
      throw new IllegalArgumentException("SusceptanceFetchOneSummary arg cannot be null");

    Susceptance entity = null;

    try {
      // --------------------------------------
      // validate the fetch one summary
      // --------------------------------------
      SusceptanceValidator.getInstance().validate(summary);

      // --------------------------------------
      // use queryGateway to send request to Find a Susceptance
      // --------------------------------------
      CompletableFuture<Susceptance> futureEntity =
          queryGateway.query(
              new FindSusceptanceQuery(new LoadSusceptanceFilter(summary.getSusceptanceId())),
              ResponseTypes.instanceOf(Susceptance.class));

      entity = futureEntity.get();
    } catch (Exception exc) {
      final String errMsg = "Unable to locate Susceptance with id " + summary.getSusceptanceId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return entity;
  }

  /**
   * Method to retrieve a collection of all Susceptances
   *
   * @return List<Susceptance>
   * @exception ProcessingException Thrown if any problems
   */
  public List<Susceptance> getAllSusceptance() throws ProcessingException {
    List<Susceptance> list = null;

    try {
      CompletableFuture<List<Susceptance>> futureList =
          queryGateway.query(
              new FindAllSusceptanceQuery(), ResponseTypes.multipleInstancesOf(Susceptance.class));

      list = futureList.get();
    } catch (Exception exc) {
      String errMsg = "Failed to get all Susceptance";
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return list;
  }

  /**
   * assign Value on Susceptance
   *
   * @param command AssignValueToSusceptanceCommand
   * @exception ProcessingException
   */
  public void assignValue(AssignValueToSusceptanceCommand command) throws ProcessingException {

    // --------------------------------------------
    // load the parent
    // --------------------------------------------
    load(command.getSusceptanceId());

    com.occulue.europeanstandards.commongridmodelexchangestandard.domainprofile.delegate
            .FloatProxyBusinessDelegate
        childDelegate =
            com.occulue.europeanstandards.commongridmodelexchangestandard.domainprofile.delegate
                .FloatProxyBusinessDelegate.getFloatProxyInstance();
    SusceptanceBusinessDelegate parentDelegate =
        SusceptanceBusinessDelegate.getSusceptanceInstance();
    UUID childId = command.getAssignment().getFloatProxyId();
    FloatProxy child = null;

    try {
      // --------------------------------------
      // best to validate the command now
      // --------------------------------------
      SusceptanceValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);

    } catch (Throwable exc) {
      final String msg = "Failed to get FloatProxy using id " + childId;
      LOGGER.log(Level.WARNING, msg);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * unAssign Value on Susceptance
   *
   * @param command UnAssignValueFromSusceptanceCommand
   * @exception ProcessingException
   */
  public void unAssignValue(UnAssignValueFromSusceptanceCommand command)
      throws ProcessingException {

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      SusceptanceValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to unassign Value on Susceptance";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * Internal helper method to load the root
   *
   * @param id UUID
   * @return Susceptance
   */
  private Susceptance load(UUID id) throws ProcessingException {
    susceptance =
        SusceptanceBusinessDelegate.getSusceptanceInstance()
            .getSusceptance(new SusceptanceFetchOneSummary(id));
    return susceptance;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  private final QueryGateway queryGateway;
  private final CommandGateway commandGateway;
  private final QueryUpdateEmitter queryUpdateEmitter;
  private Susceptance susceptance = null;
  private static final Logger LOGGER =
      Logger.getLogger(SusceptanceBusinessDelegate.class.getName());
}
