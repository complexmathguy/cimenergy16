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
 * Length business delegate class.
 *
 * <p>This class implements the Business Delegate design pattern for the purpose of:
 *
 * <ol>
 *   <li>Reducing coupling between the business tier and a client of the business tier by hiding all
 *       business-tier implementation details
 *   <li>Improving the available of Length related services in the case of a Length business related
 *       service failing.
 *   <li>Exposes a simpler, uniform Length interface to the business tier, making it easy for
 *       clients to consume a simple Java object.
 *   <li>Hides the communication protocol that may be required to fulfill Length business related
 *       services.
 * </ol>
 *
 * <p>
 *
 * @author your_name_here
 */
public class LengthBusinessDelegate extends BaseBusinessDelegate {
  // ************************************************************************
  // Public Methods
  // ************************************************************************
  /** Default Constructor */
  public LengthBusinessDelegate() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
    commandGateway = applicationContext.getBean(CommandGateway.class);
    queryUpdateEmitter = applicationContext.getBean(QueryUpdateEmitter.class);
  }

  /**
   * Length Business Delegate Factory Method
   *
   * <p>All methods are expected to be self-sufficient.
   *
   * @return LengthBusinessDelegate
   */
  public static LengthBusinessDelegate getLengthInstance() {
    return (new LengthBusinessDelegate());
  }

  /**
   * Creates the provided command.
   *
   * @param command ${class.getCreateCommandAlias()}
   * @exception ProcessingException
   * @exception IllegalArgumentException
   * @return CompletableFuture<UUID>
   */
  public CompletableFuture<UUID> createLength(CreateLengthCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<UUID> completableFuture = null;

    try {
      // --------------------------------------
      // assign identity now if none
      // --------------------------------------
      if (command.getLengthId() == null) command.setLengthId(UUID.randomUUID());

      // --------------------------------------
      // validate the command
      // --------------------------------------
      LengthValidator.getInstance().validate(command);

      // ---------------------------------------
      // issue the CreateLengthCommand - by convention the future return value for a create command
      // that is handled by the constructor of an aggregate will return the UUID
      // ---------------------------------------
      completableFuture = commandGateway.send(command);

      LOGGER.log(
          Level.INFO,
          "return from Command Gateway for CreateLengthCommand of Length is " + command);

    } catch (Exception exc) {
      final String errMsg = "Unable to create Length - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Update the provided command.
   *
   * @param command UpdateLengthCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   * @exception IllegalArgumentException
   */
  public CompletableFuture<Void> updateLength(UpdateLengthCommand command)
      throws ProcessingException, IllegalArgumentException {
    CompletableFuture<Void> completableFuture = null;

    try {

      // --------------------------------------
      // validate
      // --------------------------------------
      LengthValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the UpdateLengthCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg = "Unable to save Length - " + exc;
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    }

    return completableFuture;
  }

  /**
   * Deletes the associatied value object
   *
   * @param command DeleteLengthCommand
   * @return CompletableFuture<Void>
   * @exception ProcessingException
   */
  public CompletableFuture<Void> delete(DeleteLengthCommand command)
      throws ProcessingException, IllegalArgumentException {

    CompletableFuture<Void> completableFuture = null;

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      LengthValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the DeleteLengthCommand and return right away
      // --------------------------------------
      completableFuture = commandGateway.send(command);
    } catch (Exception exc) {
      final String errMsg = "Unable to delete Length using Id = " + command.getLengthId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return completableFuture;
  }

  /**
   * Method to retrieve the Length via LengthFetchOneSummary
   *
   * @param summary LengthFetchOneSummary
   * @return LengthFetchOneResponse
   * @exception ProcessingException - Thrown if processing any related problems
   * @exception IllegalArgumentException
   */
  public Length getLength(LengthFetchOneSummary summary)
      throws ProcessingException, IllegalArgumentException {

    if (summary == null)
      throw new IllegalArgumentException("LengthFetchOneSummary arg cannot be null");

    Length entity = null;

    try {
      // --------------------------------------
      // validate the fetch one summary
      // --------------------------------------
      LengthValidator.getInstance().validate(summary);

      // --------------------------------------
      // use queryGateway to send request to Find a Length
      // --------------------------------------
      CompletableFuture<Length> futureEntity =
          queryGateway.query(
              new FindLengthQuery(new LoadLengthFilter(summary.getLengthId())),
              ResponseTypes.instanceOf(Length.class));

      entity = futureEntity.get();
    } catch (Exception exc) {
      final String errMsg = "Unable to locate Length with id " + summary.getLengthId();
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return entity;
  }

  /**
   * Method to retrieve a collection of all Lengths
   *
   * @return List<Length>
   * @exception ProcessingException Thrown if any problems
   */
  public List<Length> getAllLength() throws ProcessingException {
    List<Length> list = null;

    try {
      CompletableFuture<List<Length>> futureList =
          queryGateway.query(
              new FindAllLengthQuery(), ResponseTypes.multipleInstancesOf(Length.class));

      list = futureList.get();
    } catch (Exception exc) {
      String errMsg = "Failed to get all Length";
      LOGGER.log(Level.WARNING, errMsg, exc);
      throw new ProcessingException(errMsg, exc);
    } finally {
    }

    return list;
  }

  /**
   * assign Value on Length
   *
   * @param command AssignValueToLengthCommand
   * @exception ProcessingException
   */
  public void assignValue(AssignValueToLengthCommand command) throws ProcessingException {

    // --------------------------------------------
    // load the parent
    // --------------------------------------------
    load(command.getLengthId());

    com.occulue.europeanstandards.commongridmodelexchangestandard.domainprofile.delegate
            .FloatProxyBusinessDelegate
        childDelegate =
            com.occulue.europeanstandards.commongridmodelexchangestandard.domainprofile.delegate
                .FloatProxyBusinessDelegate.getFloatProxyInstance();
    LengthBusinessDelegate parentDelegate = LengthBusinessDelegate.getLengthInstance();
    UUID childId = command.getAssignment().getFloatProxyId();
    FloatProxy child = null;

    try {
      // --------------------------------------
      // best to validate the command now
      // --------------------------------------
      LengthValidator.getInstance().validate(command);

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
   * unAssign Value on Length
   *
   * @param command UnAssignValueFromLengthCommand
   * @exception ProcessingException
   */
  public void unAssignValue(UnAssignValueFromLengthCommand command) throws ProcessingException {

    try {
      // --------------------------------------
      // validate the command
      // --------------------------------------
      LengthValidator.getInstance().validate(command);

      // --------------------------------------
      // issue the command
      // --------------------------------------
      commandGateway.sendAndWait(command);
    } catch (Exception exc) {
      final String msg = "Failed to unassign Value on Length";
      LOGGER.log(Level.WARNING, msg, exc);
      throw new ProcessingException(msg, exc);
    }
  }

  /**
   * Internal helper method to load the root
   *
   * @param id UUID
   * @return Length
   */
  private Length load(UUID id) throws ProcessingException {
    length = LengthBusinessDelegate.getLengthInstance().getLength(new LengthFetchOneSummary(id));
    return length;
  }

  // ************************************************************************
  // Attributes
  // ************************************************************************
  private final QueryGateway queryGateway;
  private final CommandGateway commandGateway;
  private final QueryUpdateEmitter queryUpdateEmitter;
  private Length length = null;
  private static final Logger LOGGER = Logger.getLogger(LengthBusinessDelegate.class.getName());
}
