package hello.autoconfigure;

import static org.assertj.core.api.Assertions.assertThat;

import hello.ConsoleHelloService;
import hello.HelloService;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.context.annotation.Bean;

public class HelloAutoConfigurationTest {

    @Rule
    public OutputCapture output = new OutputCapture();

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(HelloAutoConfiguration.class));

    @Test
    public void defaultServiceIsAutoConfigured() {
        this.contextRunner
                .withPropertyValues("hello.prefix=Test")
                .run(context -> {
            assertThat(context).hasSingleBean(HelloService.class);
            context.getBean(HelloService.class).sayHello("World");
            assertThat(this.output.toString()).contains("Test World!");
        });
    }

    @Test
    public void defaultServiceBacksOff() {
        this.contextRunner
                .withUserConfiguration(UserConfiguration.class)
                .run(context -> {
                    assertThat(context).hasSingleBean(HelloService.class);
                    context.getBean(HelloService.class).sayHello("Works");
                    assertThat(this.output.toString()).contains("Mine Works**");
                });
    }

    @TestConfiguration
    static class UserConfiguration {
        @Bean
        public HelloService myHelloService() {
            return new ConsoleHelloService("Mine", "**");
        }
    }
}