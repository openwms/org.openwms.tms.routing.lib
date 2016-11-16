# org.openwms.tms.routing

The TMS Routing service is responsible to route incoming messages to an
appropriate workflow to finally handle the request message. The workflow
is defined as a BMPN conform workflow and comprises the execution of
services in sequential order or with parallel execution steps.

A typical and simple workflow to handle incoming OSIP REQ_ messages
could look like this:

![Workflow][1]

(1) When the REQ_ message arrives, the `TransportUnit` with the given
`Barcode` is booked onto the `Location` provided in the message.

(2) For the current `TransportUnit` a `TransportOrder` is created with
the target `LocationGroup` equals to `STOCK`.

(3) In the final step the system triggers a service to send an OSIP RES_
message to the underlying subsystem (i.e. PLC or Raspberry Pi) with the
next `Location` coordinate to move to.

[1]: src/docs/res/workflow.png
