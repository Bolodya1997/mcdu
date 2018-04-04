package bolodya.mcdu;

import bolodya.mcdu.view.MainFrame;
import lombok.val;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class McduApplication {

	public static void main(String[] args) {
        val context = new SpringApplicationBuilder(McduApplication.class)
                .headless(false)
                .run(args);

		context.getBean(MainFrame.class);
	}
}
