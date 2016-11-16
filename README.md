# org.openwms.tms.routing

The TMS Routing service is responsible to route incoming messages to the
appropriate workflow and finally handle the request message. The workflow
is defined as a BMPN conform workflow, with Activiti extensions, and
comprises the execution of services in sequential order or in parallel
execution steps.

A typical and simple workflow to handle incoming OSIP REQ_ messages
could look like this:

![Workflow][1]
 
(1) Whenever a REQ_ message arrives, the `TransportUnit` with the given
`Barcode` is booked onto the `Location` provided in the message.

(2) For the current `TransportUnit` a `TransportOrder` is created with
the target `LocationGroup` equals to `FGSTOCK`.

(3) In the final step the system triggers a service to send an OSIP RES_
message to the underlying subsystem (i.e. PLC or Raspberry Pi) with the
next `Location` coordinate to move to.

The actual Activiti BPMN snippet looks like this:

```
    <serviceTask id="sid-...27D" name="Book TransportUnit to current Location" activiti:expression="#{transportUnitApi.updateActualLocation(in.msg.barcode, in.msg.actualLocation)}"></serviceTask>
    <serviceTask id="sid-...B7F" name="Create TransportOrder to Stock" activiti:expression="#{transportOrderApi.createTO(&quot;FGSTOCK&quot;)}"></serviceTask>
    <serviceTask id="sid-...C72" name="Send RES_ to Conveyor location" activiti:expression="#{responder.sendTo(&quot;FGIN/CONV/0001/0000/0000&quot;)}"></serviceTask>
```

Notice that the flow and the expressions are modelled in Activiti
Model Explorer.

## Features

The current implementation allows to reference standard Spring managed
beans within BPMN workflows. Remote http accessible microservices can
be accessed with support of `Feign`. Therefor the TMS Routing service
needs to have the Feign client interface definitions on the classpath at
startup.

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

[1]: src/docs/res/workflow.png

