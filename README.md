# Intention
The TMS Routing service is responsible to route incoming messages to the appropriate
workflow process that finally executes the message. The workflow is a BMPN 2.0 compliant
workflow, with extensions of the used Workflow Engine.

# Resources
Documentation at [GitHub](https://github.com/openwms/org.openwms.tms.routing/wiki)

[![Build status](https://travis-ci.com/openwms/org.openwms.tms.routing.svg?style=flat-square)](https://travis-ci.com/openwms/org.openwms.tms.routing)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
[![Quality](https://sonarcloud.io/api/project_badges/measure?project=org.openwms:org.openwms.tms.routing&metric=alert_status)](https://sonarcloud.io/dashboard?id=org.openwms:org.openwms.tms.routing)
[![Join the chat at https://gitter.im/openwms/org.openwms](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/openwms/org.openwms?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

# Details

A typical and simple workflow to handle incoming OSIP REQ_ messages could look like this:

![Workflow][1]
 
(1) Whenever a REQ_ message arrives, the `TransportUnit` with the given `Barcode` is
booked onto the `Location` provided in the message.

(2) For the current `TransportUnit` a `TransportOrder` is created with the target 
`LocationGroup` equals to `FGSTOCK`.

(3) In the final step the system triggers a service to send an OSIP RES_ message to the
underlying subsystem (i.e. PLC or Raspberry Pi) with the next `Location` coordinate to
move the `TransportUnit` to.

The shown BPMN snippet looks in XML like this:

```
    <serviceTask id="Task1" name="Book to current Location" activiti:expression="#{transportUnitApi.moveTU(in.msg.barcode, in.msg.actualLocationId)}" />
    <serviceTask id="Task2" name="Create TransportOrder to Stock" activiti:expression="#{transportOrderApi.createTO(&quot;FGSTOCK&quot;)}"></serviceTask>
    <serviceTask id="Task3" name="Send OSIP RES telegram" activiti:expression="#{responder.sendTo(in.msg.flexField1)}"></serviceTask>
```

Notice that the flow and the expressions are modelled in Activiti Model Explorer and there
are extensions to the BPMN. Other supported Workflow Engines like Flowable or Camunda have
their own XML namespace, but the XML Element names stay mostly the same.

## Benefits

Why did we choose BPMN for the transport management system layer? From the experience we
have of projects and systems in the past we know that the physical layout of a system and
the material flow is something that always needs to be adopted to the particular customer
project. Changing the material flow must happen rapidly without any compilation nor
deployment interruption.

The second huge benefit of using BPMN for the material flow handling is the advantage of 
having the same notation for business people as well as software engineers, so both can
define and communicate on the same model. Isn't this awesome?

Usually after doing a couple of projects an Engineer or a project team has a library of
well known business functions and can just plug them together to build new flows for new
customer projects. Not only the microservice functions can be reused across projects but
also BPMN flows can be reused.
In some kind we misuse the concept of BPMN for a good purpose :-)

## Features

The current implementation allows to reference standard Spring managed beans within BPMN
workflows. Remote http accessible microservices can be accessed with support of `Feign`.
Therefor the TMS Routing service needs to have the Feign client interface definitions on
the classpath at startup, by simple extending the classpath with the JAR file when the 
process is started.

### Static Routing

Requests from PLC for a next target can be answered statically by using a routing table.
For this reason a `Route` may have `RouteDetails` entries in a well defined order (ordered
by the `pos` attribute). The actual location from the telegram is used to determine the
next target. In the selected workflow just call `#{routing.sendToNextLocation()}` to
choose the next routing location.

### Hard Coded Routing

The workflow that calls the microservice to send the request telegram could pass the next
Location hard-coded within the workflow. This is more or less the simplest solution but
has the drawback that the workflow may not be reused, because the target is hard-coded.
 
### Dynamic Routing

An approach between Static and Hard-Coded Routing is the mechanism of Dynamic Routing. The
workflow can be customized with so called Flex Attributes. This means a workflow can be
called with parameters and these parameters come from a defined Action. The Action may be
specific to the project but this actually doesn't matter. BPMN workflows should be reused
across projects - Actions may not.

# Configuration

The following Spring profiles are supported:                       
`ASYNCHRONOUS` Run with AMQP communication                      
`CAMUNDA` Enable Camunda as Workflow Engine                    
`FLOWABLE` Use Flowable as Workflow Engine |

# Development

## Build

The service includes an UI that is part of the built project aside the actual service
code.build. To build a deliverable JAR file that contains the backend and the frontend call:
```
$ mvn package -Pfrontend
```

To skip the frontend part just remove the -P parameter.

## Deploy

After the deliverable has been built it can be started with:
```
$ java -jar target/openwms-tms-routing-exec.jar --spring.profiles.active=ASYNCHRONOUS
```

To open the frontend application go to [http://localhost:8130/ui/index.html](http://localhost:8130/ui/index.html)
 

# Appendix

## Open Issues

ID   | Description
---- | -----------
I001 | The BPMN process definitions are currently loaded from the JAR file. We should externalize that configuration

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

