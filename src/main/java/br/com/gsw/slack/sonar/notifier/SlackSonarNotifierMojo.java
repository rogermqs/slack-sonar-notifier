package br.com.gsw.slack.sonar.notifier;

import br.com.gsw.slack.sonar.notifier.plugin.factory.LogFactory;
import br.com.gsw.slack.sonar.notifier.plugin.factory.NotifierFactory;
import br.com.gsw.slack.sonar.notifier.plugin.factory.PluginLoadPropertiesFactory;
import br.com.gsw.slack.sonar.notifier.plugin.factory.PluginValidatorFactory;
import br.com.gsw.slack.sonar.notifier.slack.model.Slack;
import br.com.gsw.slack.sonar.notifier.sonar.model.Sonar;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "sonar-notifier",
        requiresOnline = true,
        requiresProject = true,
        threadSafe = false)
public class SlackSonarNotifierMojo extends AbstractMojo {
    private static Log LOGGER;

    @Parameter
    private Sonar sonar;

    @Parameter
    private Slack slack;

    public SlackSonarNotifierMojo() {
        LogFactory.init(getLog());
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        LOGGER = LogFactory.getInstance();

        LOGGER.info("------------------------------------------------------------------------");
        LOGGER.info("Slack Sonar Notifier Plugin");
        LOGGER.info("------------------------------------------------------------------------");
        sonar = PluginLoadPropertiesFactory.getInstance().sonar(sonar);
        slack = PluginLoadPropertiesFactory.getInstance().slack(slack);

        PluginValidatorFactory.getInstance().sonar(sonar);
        PluginValidatorFactory.getInstance().slack(slack);

        NotifierFactory.getInstance().start(sonar, slack);
    }

}
