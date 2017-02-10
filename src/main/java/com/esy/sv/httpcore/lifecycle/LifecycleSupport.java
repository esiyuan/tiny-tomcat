package com.esy.sv.httpcore.lifecycle;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;



/**
 * Support class to assist in firing LifecycleEvent notifications to
 * registered LifecycleListeners.
 *
 * @author Craig R. McClanahan
 * @version $Id: LifecycleSupport.java,v 1.3 2001/11/09 19:40:54 remm Exp $
 */

public final class LifecycleSupport {


    // ----------------------------------------------------------- Constructors


    /**
     * Construct a new LifecycleSupport object associated with the specified
     * Lifecycle component.
     *
     * @param lifecycle The Lifecycle component that will be the source
     *  of events that we fire
     */
    public LifecycleSupport(Lifecycle lifecycle) {

        super();
        this.lifecycle = lifecycle;

    }

    // ----------------------------------------------------- Instance Variables


    /**
     * The source component for lifecycle events that we will fire.
     */
    private Lifecycle lifecycle = null;


    /**
     * The set of registered LifecycleListeners for event notifications.
     * 因为监听器的添加是在程序整体启动的时刻，写操作只有有限的几次，更多的是读，没必要进行锁控制
     * <p>
     * 那么会不会由于CopyOnWriteArrayList数据弱一致性而产生的监听失效，
     * 此项目中不会，因为都是先添加监听器，然后启动容器
     */
    private List<LifecycleListener> listeners = new CopyOnWriteArrayList<>();


    // --------------------------------------------------------- Public Methods


    /**
     * Add a lifecycle event listener to this component.
     *
     * @param listener The listener to add
     */
    public void addLifecycleListener(LifecycleListener listener) {
    	listeners.add(listener);
    }


    /**
     * Get the lifecycle listeners associated with this lifecycle. If this 
     * Lifecycle has no listeners registered, a zero-length array is returned.
     */
    public LifecycleListener[] findLifecycleListeners() {
        return listeners.toArray(new LifecycleListener[listeners.size()]);

    }


    /**
     * Notify all lifecycle event listeners that a particular event has
     * occurred for this Container.  The default implementation performs
     * this notification synchronously using the calling thread.
     *
     * @param type Event type
     * @param data Event data
     */
    public void fireLifecycleEvent(LifecycleEnum type, Object data) {
        LifecycleEvent event = new LifecycleEvent(lifecycle, type, data);
        for (LifecycleListener each : listeners) {
			each.lifecycleEvent(event);
		}
    }


    /**
     * Remove a lifecycle event listener from this component.
     *
     * @param listener The listener to remove
     */
    public void removeLifecycleListener(LifecycleListener listener) {
    	listeners.remove(listeners);
    }


}

