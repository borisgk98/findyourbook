package space.borisgk.findyourbook;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import space.borisgk.findyourbook.tool.CookieTool;
import space.borisgk.findyourbook.tool.UserValTool;

@Configuration
public class AppContext {

    @Bean
    public UserValTool userValTool() {
        return new CookieTool();
    }
}
