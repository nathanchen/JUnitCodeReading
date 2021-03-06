package org.junit.runner;

import org.junit.runner.manipulation.Filter;

/**
 * Extend this class to create a factory that creates {@link Filter}.
 */
public interface FilterFactory {
    /**
     * Creates a {@link Filter} given a {@link FilterFactoryParams} argument.
     *
     * @param params Parameters needed to create the {@link Filter}
     * @throws FilterNotCreatedException
     */
    Filter createFilter(FilterFactoryParams params) throws FilterNotCreatedException;

    /**
     * Exception thrown if the {@link Filter} cannot be created.
     * define a custom exception
     */
    public static class FilterNotCreatedException extends Exception {
        public FilterNotCreatedException(Exception exception) {
            super(exception.getMessage(), exception);
        }
    }
}
