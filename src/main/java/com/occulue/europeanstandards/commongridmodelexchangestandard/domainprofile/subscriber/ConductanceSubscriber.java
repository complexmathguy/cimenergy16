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
package com.occulue.europeanstandards.commongridmodelexchangestandard.domainprofile.subscriber;

import com.occulue.api.*;
import com.occulue.entity.*;
import com.occulue.exception.*;
import com.occulue.subscriber.*;
import java.util.*;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.stereotype.Component;

/**
 * Subscriber for Conductance related events. .
 *
 * @author your_name_here
 */
@Component("conductance-subscriber")
public class ConductanceSubscriber extends BaseSubscriber {

  public ConductanceSubscriber() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
  }

  public SubscriptionQueryResult<List<Conductance>, Conductance> conductanceSubscribe() {
    return queryGateway.subscriptionQuery(
        new FindAllConductanceQuery(),
        ResponseTypes.multipleInstancesOf(Conductance.class),
        ResponseTypes.instanceOf(Conductance.class));
  }

  public SubscriptionQueryResult<Conductance, Conductance> conductanceSubscribe(
      @DestinationVariable UUID conductanceId) {
    return queryGateway.subscriptionQuery(
        new FindConductanceQuery(new LoadConductanceFilter(conductanceId)),
        ResponseTypes.instanceOf(Conductance.class),
        ResponseTypes.instanceOf(Conductance.class));
  }

  // -------------------------------------------------
  // attributes
  // -------------------------------------------------
  @Autowired private final QueryGateway queryGateway;
}
