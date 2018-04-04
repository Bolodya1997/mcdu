package bolodya.mcdu.view;

import lombok.Cleanup;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.*;
import java.io.IOException;

@Configuration
public class ViewConfig {

    @Bean
    public Font getFont() throws IOException, FontFormatException {
        @Cleanup
        val fontStream = ViewConfig.class
                .getClassLoader()
                .getResourceAsStream("font/ClassCoder.ttf");

        return Font.createFont(Font.TRUETYPE_FONT, fontStream);
    }
}
