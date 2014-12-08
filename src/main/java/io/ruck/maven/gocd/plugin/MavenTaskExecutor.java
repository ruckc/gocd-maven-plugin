package io.ruck.maven.gocd.plugin;

import com.thoughtworks.go.plugin.api.logging.Logger;
import com.thoughtworks.go.plugin.api.response.execution.ExecutionResult;
import com.thoughtworks.go.plugin.api.task.Console;
import com.thoughtworks.go.plugin.api.task.TaskConfig;
import com.thoughtworks.go.plugin.api.task.TaskExecutionContext;
import com.thoughtworks.go.plugin.api.task.TaskExecutor;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author ruckc
 */
public class MavenTaskExecutor implements TaskExecutor {
    private static final Logger LOGGER = Logger.getLoggerFor(MavenTaskExecutor.class);

    @Override
    public ExecutionResult execute(TaskConfig tc, TaskExecutionContext tec) {
        Console console = tec.console();
        ProcessBuilder mvn = createMavenCommand(tc, tec);

        try {
            Process process = mvn.start(); 
            
            console.readErrorOf(process.getErrorStream());
            console.readOutputOf(process.getInputStream());
            
            int exitCode = process.waitFor();
            process.destroy();
            if(exitCode != 0) {
                return ExecutionResult.failure("Build failure");
            }
        } catch(Exception t) {
            console.printLine(t.getMessage());
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            console.printLine(sw.toString());
            return ExecutionResult.failure("Build failure: "+t.getMessage(), t);
        }
        
        return ExecutionResult.success("Build success");
    }

    private ProcessBuilder createMavenCommand(TaskConfig tc, TaskExecutionContext tec) {
        List<String> command = new ArrayList<String>();

        command.add("mvn");
        if (StringUtils.equals(tc.getValue(MavenTask.DEBUG_KEY),"true")) {
            command.add("-X");
        }
        if (StringUtils.equals(tc.getValue(MavenTask.QUIET_KEY),"true")) {
            command.add("-q");
        }
        if (StringUtils.equals(tc.getValue(MavenTask.OFFLINE_KEY),"true")) {
            command.add("-o");
        }
        if (StringUtils.equals(tc.getValue(MavenTask.BATCH_KEY),"true")) {
            command.add("-B");
        }
        if (!StringUtils.isBlank(tc.getValue(MavenTask.PROFILES_KEY))) {
            command.add("-p");
            command.add(tc.getValue(MavenTask.PROFILES_KEY));
        }
        command.addAll(Arrays.asList(tc.getValue(MavenTask.ARGUMENTS_KEY).split("\\s+")));

        LOGGER.debug("Building command: "+command);
        
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.directory(new File(tec.workingDir()));
        return builder;
    }
}
