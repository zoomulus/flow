# Flow

Flow is a simple Java component for creating state machines to manage data.  Flows are useful for things like protocol stacks or parsing data.  A Flow is basically a series of ordered steps, called Nodes, through which data moves.  Special types of Nodes include Sources and Sinks.

## How a Flow Works

In a Flow, everything begins with a Source and a Receiver.  A class is written that implements the Receiver interface to handle the receipt of data.  A Source knows how to consume the received data and get it started through the Flow.

The opposite of a Source is a Sink.  Data arrives to a Flow via the Source, and it leaves the Flow via a Sink.  And, just like a Source has a companion class called a Receiver, a Sink has a companion class called a Sender.  The Sink uses the Sender to send the data out of the Flow, and the implementer writes a class that implements the Sender interface to handle the actual sending of the data to whereever it needs to go.

In between the Source and the Sink are one or more Nodes.  The companion of a Node is a Processor, which performs some action on the data while it is at the Node.  Once processing of the data is finished, a Processor can either tell the Node to relay (send data on to the next Node in the Flow) or respond (send data to the previous Node in the Flow).

A Flow may be defined such that there are multiple Nodes downstream from a single Node, thus creating a branch.  When a Node is instructed to relay, by default it will send data to every downstream Node.  Alternatively, the downstream Nodes can be configured with a relay condition, which specifies a condition that should be true in order for data to be relayed to that Node.

## Example

Here's a rough example of how a Flow might be used in a crude hypothetical HTTP server.  In this example, the server only supports the `GET` method.

```
  +-----------------+ +--------+                                             +---------------+
  | Socket Receiver | |        |                           +=>relay if GET==>|     Node      |
  |    (port 80)    |=| Source |          +--------------+ | +===response<===| (File Reader) |
  |                 | |        |==relay==>|     Node     |=+ |               +---------------+
  +-----------------+ +--------+          | (HTTP Method |<==+               +---------------+
  +-----------------+ +--------+          |   Examiner)  |=relay if not GET=>|     Node      |
  |  Socket Sender  | |        |<=respond=|              |<=====response<====|  (Error 405)  |
  |    (port 80)    |=|  Sink  |          +--------------+                   +---------------+
  |                 | |        |
  +-----------------+ +--------+
```

On the left we have a Receiver and a Sender, both within the code handling the request on port 80.  The Receiver is used by the Source which receives request data over the port.  The Source relays the request data on to the next Node, here labeled "HTTP Method Examiner".  The Processor in this Node would look at the data and pull out the request method, e.g. GET, POST, DELETE, PUT, etc.  Since this Node has two downstream Nodes to send to, it would try to relay to both, but we would assume in this example each downstream node has a relay condition.  The "File Reader" Node would have a relay condition something like `request.method == GET`, and conversely, the "Error 405" Node would have the opposite relay condition, something like `request.method != GET`.

Notice how both of the Nodes at the right generate a response.  In truth a response can be initiated anywhere along the flow, which would terminate relaying of the message forward and instead begin sending data backward.  In this example it is the Nodes on the right which generate a response.  The responses automatically pass backwards through the Flow until they get to the Sink, which uses it's corresponding Sender to send the response message.

