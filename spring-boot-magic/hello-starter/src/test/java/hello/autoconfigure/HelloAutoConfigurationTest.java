package hello.autoconfigure;

import hello.HelloService;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.rule.OutputCapture;

public class HelloAutoConfigurationTest {

    @Rule
    public OutputCapture output = new OutputCapture();

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(HelloAutoConfiguration.class));

    @Test
    public void defaultServiceIsAutoConfigured() {
        this.contextRunner.run(context -> {
            Assertions.assertThat(context).hasSingleBean(HelloService.class);
            context.getBean(HelloService.class).sayHello("World");
            Assertions.assertThat(this.output.toString()).contains("Hello World!");
        });
    }
}