package io.ruck.maven.gocd.plugin;

import com.thoughtworks.go.plugin.api.annotation.Extension;
import com.thoughtworks.go.plugin.api.response.validation.ValidationError;
import com.thoughtworks.go.plugin.api.response.validation.ValidationResult;
import com.thoughtworks.go.plugin.api.task.Task;
import com.thoughtworks.go.plugin.api.task.TaskConfig;
import com.thoughtworks.go.plugin.api.task.TaskExecutor;
import com.thoughtworks.go.plugin.api.task.TaskView;

/**
 *
 * @author ruckc
 */
@Extension
public class MavenTask implements Task {

    public static final String ARGUMENTS_KEY = "Arguments";
    public static final String PROFILES_KEY = "Profiles";
    public static final String OFFLINE_KEY = "Offline";
    public static final String QUIET_KEY = "Quiet";
    public static final String DEBUG_KEY = "Debug";
    public static final String BATCH_KEY = "Batch";
    public static final String FILE_KEY = "File";

    @Override
    public TaskConfig config() {
        TaskConfig config = new TaskConfig();
        config.addProperty(ARGUMENTS_KEY);
        config.addProperty(PROFILES_KEY);
        config.addProperty(OFFLINE_KEY);
        config.addProperty(QUIET_KEY);
        config.addProperty(DEBUG_KEY);
        config.addProperty(FILE_KEY);
        config.addProperty(BATCH_KEY).withDefault("true");
        return config;
    }

    @Override
    public TaskExecutor executor() {
        return new MavenTaskExecutor();
    }

    @Override
    public TaskView view() {
        return new MavenTaskView();
    }

    @Override
    public ValidationResult validate(TaskConfig tc) {
        ValidationResult result = new ValidationResult();
        if (StringUtils.isBlank(tc.getValue(ARGUMENTS_KEY))) {
            result.addError(new ValidationError(ARGUMENTS_KEY, "Arguments are required"));
        }
        return result;
    }

}
