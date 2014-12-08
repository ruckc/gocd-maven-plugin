package io.ruck.maven.gocd.plugin;

import com.thoughtworks.go.plugin.api.annotation.Extension;
import com.thoughtworks.go.plugin.api.response.validation.ValidationError;
import com.thoughtworks.go.plugin.api.response.validation.ValidationResult;
import com.thoughtworks.go.plugin.api.task.Task;
import com.thoughtworks.go.plugin.api.task.TaskConfig;
import com.thoughtworks.go.plugin.api.task.TaskExecutor;
import com.thoughtworks.go.plugin.api.task.TaskView;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

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

    @Override
    public TaskConfig config() {
        TaskConfig config = new TaskConfig();
        config.addProperty(ARGUMENTS_KEY);
        config.addProperty(PROFILES_KEY);
        config.addProperty(OFFLINE_KEY);
        config.addProperty(QUIET_KEY);
        config.addProperty(DEBUG_KEY);
        return config;
    }

    @Override
    public TaskExecutor executor() {
        return new MavenTaskExecutor();
    }

    @Override
    public TaskView view() {
        return new TaskView() {

            @Override
            public String displayValue() {
                return "Maven";
            }

            @Override
            public String template() {
                try {
                    return IOUtils.toString(getClass().getResourceAsStream("/views/maventask.template.html"));
                } catch (IOException ex) {
                    return "Failed to find template: " + ex.getMessage();
                }
            }
        };
    }

    @Override
    public ValidationResult validate(TaskConfig tc) {
        ValidationResult result = new ValidationResult();
        if (StringUtils.isBlank(tc.getValue(ARGUMENTS_KEY))) {
            result.addError(new ValidationError(ARGUMENTS_KEY, "Arguments arer equired"));
        }
        return result;
    }

}
