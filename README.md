# Intention
The TMS Routing service is responsible to route incoming messages to the appropriate
workflow and finally handle the message. The workflow is defined as a BMPN conform
workflow, with [Activiti](https://www.activiti.org) extensions, and comprises the
execution of services in sequential order or in parallel execution steps.

# Resources
Documentation at [GitHub](https://github.com/openwms/org.openwms.tms.routing/wiki)

[![Build status](https://img.shields.io/travis/openwms/org.openwms.tms.routing.svg?style=flat-square)](https://travis-ci.com/openwms/org.openwms.tms.routing)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
[![Quality](https://sonarcloud.io/api/project_badges/measure?project=org.openwms:org.openwms.tms.routing&metric=alert_status)](https://sonarcloud.io/dashboard?id=org.openwms:org.openwms.tms.routing)
[![Join the chat at https://gitter.im/openwms/org.openwms](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/openwms/org.openwms?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

# Details

A typical and simple workflow to handle incoming OSIP REQ_ messages
could look like this:

![Workflow][1]
 
(1) Whenever a REQ_ message arrives, the `TransportUnit` with the given
`Barcode` is booked onto the `Location` provided in the message.

(2) For the current `TransportUnit` a `TransportOrder` is created with
the target `LocationGroup` equals to `FGSTOCK`.

(3) In the final step the system triggers a service to send an OSIP RES_
message to the underlying subsystem (i.e. PLC or Raspberry Pi) with the
next `Location` coordinate to move the `TransportUnit` to.

The actual Activiti BPMN snippet looks like this:

```
    <serviceTask id="sid-...27D" name="Book TransportUnit to current Location" activiti:expression="#{transportUnitApi.updateActualLocation(in.msg.barcode, in.msg.actualLocation)}"></serviceTask>
    <serviceTask id="sid-...B7F" name="Create TransportOrder to Stock" activiti:expression="#{transportOrderApi.createTO(&quot;FGSTOCK&quot;)}"></serviceTask>
    <serviceTask id="sid-...C72" name="Send RES_ to Conveyor location" activiti:expression="#{responder.sendTo(&quot;FGIN/CONV/0001/0000/0000&quot;)}"></serviceTask>
```

Notice that the flow and the expressions are modelled in Activiti
Model Explorer rather than in XML.

## Benefits

Why did we choose BPMN and Activiti? From the project experience we have,
we think that it is important that project consultants as well as
business people should realize the project specific parts in BPMN and not
in code. Usually after doing several projects an engineer has a library
of business functions and can just plug them together in some kind of
flow - we've misused the concept of BPMN for that.

## Features

The current implementation allows to reference standard Spring managed
beans within BPMN workflows. Remote http accessible microservices can
be accessed with support of `Feign`. Therefor the TMS Routing service
needs to have the Feign client interface definitions on the classpath at
startup.

### Static Routing

Requests from PLC for a next target can be answered statically by using a routing table.
For this reason a `Route` may have `RouteDetails` entries in a well defined order (ordered
by the `pos` attribute). The actual location from the telegram is used to determine the
next target. In the selected workflow just call `#{routing.sendToNextLocation()}` to choose
the next routing location.

## Open Issues

ID   | Description
---- | -----------
I001 | FeignClients are instantiated in an isolated child context and accessible by the hosting application only but not from Activiti.

## Outlook

ID   | Description
---- | -----------
R001 | We should have an additional expression language to support microservices. At best with support for HATEOS for better navigation

## Further reading

ID   | Description
---- | -----------
L001 | [Secured Eureka First Microservices](https://github.com/openwms/org.openwms/wiki/Secured-Eureka-First-services-on-Heroku)
L002 | [Development Process](src/site/markdown/development.md)

[1]: src/site/resources/images/workflow.png

