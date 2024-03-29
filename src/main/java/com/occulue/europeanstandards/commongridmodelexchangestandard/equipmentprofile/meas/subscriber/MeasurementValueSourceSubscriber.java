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
package com.occulue.europeanstandards.commongridmodelexchangestandard.equipmentprofile.meas.subscriber;

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
 * Subscriber for MeasurementValueSource related events. .
 *
 * @author your_name_here
 */
@Component("measurementValueSource-subscriber")
public class MeasurementValueSourceSubscriber extends BaseSubscriber {

  public MeasurementValueSourceSubscriber() {
    queryGateway = applicationContext.getBean(QueryGateway.class);
  }

  public SubscriptionQueryResult<List<MeasurementValueSource>, MeasurementValueSource>
      measurementValueSourceSubscribe() {
    return queryGateway.subscriptionQuery(
        new FindAllMeasurementValueSourceQuery(),
        ResponseTypes.multipleInstancesOf(MeasurementValueSource.class),
        ResponseTypes.instanceOf(MeasurementValueSource.class));
  }

  public SubscriptionQueryResult<MeasurementValueSource, MeasurementValueSource>
      measurementValueSourceSubscribe(@DestinationVariable UUID measurementValueSourceId) {
    return queryGateway.subscriptionQuery(
        new FindMeasurementValueSourceQuery(
            new LoadMeasurementValueSourceFilter(measurementValueSourceId)),
        ResponseTypes.instanceOf(MeasurementValueSource.class),
        ResponseTypes.instanceOf(MeasurementValueSource.class));
  }

  // -------------------------------------------------
  // attributes
  // -------------------------------------------------
  @Autowired private final QueryGateway queryGateway;
}
