// Copyright (C) 2003-2009 by Object Mentor, Inc. All rights reserved.
// Released under the terms of the CPL Common Public License version 1.0.
package util;

import com.google.inject.Module;
import org.junit.Test;
import org.movealong.junit.BaseInjectedTestCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static util.ClassPathHelper.classPath;
import static util.RegexAssertions.assertHasRegexp;

public class CommandRunnerTest extends BaseInjectedTestCase {

    @Override
    protected Module[] getBaseModules() {
        return new Module[]{
                new UtilModule()
        };
    }

    @Test
    public void testBasics() throws Exception {
        String command = "java -cp " + classPath() + " fitnesse.testutil.Echo";
        CommandRunner runner = new CommandRunner(command, "echo this!");
        runner.run();
        assertHasRegexp("echo this!", runner.getOutput());
        assertEquals("", runner.getError());
        assertEquals(false, runner.hasExceptions());
        assertEquals(0, runner.getExitCode());
    }

    @Test
    public void testClassNotFound() throws Exception {
        CommandRunner runner = new CommandRunner("java BadClass", null);
        runner.run();
        assertHasRegexp("java.lang.NoClassDefFoundError", runner.getError());
        assertEquals("", runner.getOutput());
        assertTrue(0 != runner.getExitCode());
    }
}
