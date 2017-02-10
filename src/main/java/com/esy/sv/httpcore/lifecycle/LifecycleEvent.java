package com.esy.sv.httpcore.lifecycle;

import java.util.EventObject;

/**
 * General event for notifying listeners of significant changes on a component
 * that implements the Lifecycle interface.  In particular, this will be useful
 * on Containers, where these events replace the ContextInterceptor concept in
 * Tomcat 3.x.
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.3 $ $Date: 2001/07/22 20:13:30 $
 */

public final class LifecycleEvent
    extends EventObject {


    // ----------------------------------------------------------- Constructors


    /**
     * Construct a new LifecycleEvent with the specified parameters.
     *
     * @param lifecycle Component on which this event occurred
     * @param type Event type (required)
     */
    public LifecycleEvent(Lifecycle lifecycle, LifecycleEnum type) {

        this(lifecycle, type, null);

    }


    /**
     * Construct a new LifecycleEvent with the specified parameters.
     *
     * @param lifecycle Component on which this event occurred
     * @param type Event type (required)
     * @param data Event data (if any)
     */
    public LifecycleEvent(Lifecycle lifecycle, LifecycleEnum type, Object data) {

        super(lifecycle);
        this.lifecycle = lifecycle;
        this.type = type;
        this.data = data;

    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The event data associated with this event.
     */
    private Object data = null;


    /**
     * The Lifecycle on which this event occurred.
     */
    private Lifecycle lifecycle = null;


    /**
     * The event type this instance represents.
     */
    private LifecycleEnum type = null;


    // ------------------------------------------------------------- Properties


    /**
     * Return the event data of this event.
     */
    public Object getData() {

        return (this.data);

    }


    /**
     * Return the Lifecycle on which this event occurred.
     */
    public Lifecycle getLifecycle() {

        return (this.lifecycle);

    }


    /**
     * Return the event type of this event.
     */
    public LifecycleEnum getType() {

        return (this.type);

    }


}
