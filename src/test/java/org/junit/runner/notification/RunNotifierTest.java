package org.junit.runner.notification;

import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Result;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RunNotifierTest {
    private final RunNotifier fNotifier = new RunNotifier();

    @Test
    public void notifiesSecondListenerIfFirstThrowsException() {
        FailureListener failureListener = new FailureListener();
        // add two listeners to fNotifer
        fNotifier.addListener(new CountingListener());
        fNotifier.addListener(failureListener);
        // add a Failure object to fNotifier
        // the Failure should be notified to all listeners
        fNotifier.fireTestFailure(new Failure(null, null));
        assertNotNull("The FailureListener registered no failure.",
                failureListener.failure);
    }

    @Test
    public void hasNoProblemsWithFailingListeners() { // see issues 209 and 395
        fNotifier.addListener(new CorruptListener());
        fNotifier.addListener(new FailureListener());
        fNotifier.addListener(new CorruptListener());
        fNotifier.fireTestRunFinished(new Result());
    }

    private static class CorruptListener extends RunListener {
        @Override
        public void testRunFinished(Result result) throws Exception {
            throw new RuntimeException();
        }

        @Override
        public void testFailure(Failure failure) throws Exception {
            throw new RuntimeException();
        }
    }
    
    @Test
    public void addAndRemoveWithNonThreadSafeListener() {
        CountingListener listener = new CountingListener();
        assertThat(listener.fTestStarted.get(), is(0));
        fNotifier.addListener(listener);
        fNotifier.fireTestStarted(null);
        assertThat(listener.fTestStarted.get(), is(1));
        fNotifier.removeListener(listener);
        fNotifier.fireTestStarted(null);
        assertThat(listener.fTestStarted.get(), is(1));
    }

    @Test
    public void addFirstAndRemoveWithNonThreadSafeListener() {
        CountingListener listener = new CountingListener();
        assertThat(listener.fTestStarted.get(), is(0));
        fNotifier.addFirstListener(listener);
        fNotifier.fireTestStarted(null);
        assertThat(listener.fTestStarted.get(), is(1));
        fNotifier.removeListener(listener);
        fNotifier.fireTestStarted(null);
        assertThat(listener.fTestStarted.get(), is(1));
    }
    
    @Test
    public void addAndRemoveWithThreadSafeListener() {
        ThreadSafeListener listener = new ThreadSafeListener();
        assertThat(listener.fTestStarted.get(), is(0));
        fNotifier.addListener(listener);
        fNotifier.fireTestStarted(null);
        assertThat(listener.fTestStarted.get(), is(1));
        fNotifier.removeListener(listener);
        fNotifier.fireTestStarted(null);
        assertThat(listener.fTestStarted.get(), is(1));
    }

    @Test
    public void addFirstAndRemoveWithThreadSafeListener() {
        ThreadSafeListener listener = new ThreadSafeListener();
        assertThat(listener.fTestStarted.get(), is(0));
        fNotifier.addFirstListener(listener);
        fNotifier.fireTestStarted(null);
        assertThat(listener.fTestStarted.get(), is(1));
        fNotifier.removeListener(listener);
        fNotifier.fireTestStarted(null);
        assertThat(listener.fTestStarted.get(), is(1));
    }

    @Test
    public void wrapIfNotThreadSafeShouldNotWrapThreadSafeListeners() {
        ThreadSafeListener listener = new ThreadSafeListener();;
        assertSame(listener, new RunNotifier().wrapIfNotThreadSafe(listener));
    }

    @Test
    public void wrapIfNotThreadSafeShouldWrapNonThreadSafeListeners() {
        CountingListener listener = new CountingListener();
        RunListener wrappedListener = new RunNotifier().wrapIfNotThreadSafe(listener);
        assertThat(wrappedListener, instanceOf(SynchronizedRunListener.class));
    }

    private static class FailureListener extends RunListener {
        private Failure failure;

        @Override
        public void testFailure(Failure failure) throws Exception {
            this.failure = failure;
        }
    }
    
    private static class CountingListener extends RunListener {
        final AtomicInteger fTestStarted = new AtomicInteger(0);

        @Override
        public void testStarted(Description description) throws Exception {
            fTestStarted.incrementAndGet();
        }
    }
    
    @RunListener.ThreadSafe
    private static class ThreadSafeListener extends CountingListener {
    }

}
