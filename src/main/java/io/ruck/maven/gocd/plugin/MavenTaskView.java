package io.ruck.maven.gocd.plugin;

import com.thoughtworks.go.plugin.api.task.TaskView;
import java.io.IOException;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author ruckc
 */
public class MavenTaskView implements TaskView {

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

}
