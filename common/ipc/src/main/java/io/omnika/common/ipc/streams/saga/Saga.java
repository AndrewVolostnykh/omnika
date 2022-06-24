package io.omnika.common.ipc.streams.saga;

/** This interface used to wrap common
 * functionality of sending something to cloud streams
 * using {@link io.omnika.common.ipc.streams.SendToStream} aspect
 * from places haven't spring context, it means a places where
 * method with annotation {@link io.omnika.common.ipc.streams.SendToStream}
 * cannot be called according to impossibility of
 * building proxy around the method.
 * For instance methods of cloud streams which calls method annotated with
 * {@link io.omnika.common.ipc.streams.SendToStream} will not send data
 * to stream because it is impossible to call proxy, just common method */
public interface Saga<T, R> {

    R send(T data);

}
