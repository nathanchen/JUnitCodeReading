package org.junit.runner;

import org.junit.runner.manipulation.Filter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * User: Nathanchen Date: 31/10/13 Time: 5:23 PM Description:
 */
public class MethodNameFilter extends Filter
{
    private final Set<String> excludedMethods = new HashSet<String>();

    public MethodNameFilter(String... excludedMethods)
    {
        Collections.addAll(this.excludedMethods, excludedMethods);
    }

    @Override
    public boolean shouldRun (Description description)
    {
        String methodName = description.getMethodName();
        return !excludedMethods.contains(methodName);
    }

    @Override
    public String describe ()
    {
        return this.getClass().getSimpleName() + "-excluded methods: " + excludedMethods;
    }
}
