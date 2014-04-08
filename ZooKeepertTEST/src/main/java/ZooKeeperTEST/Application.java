package ZooKeeperTEST;

import org.apache.zookeeper.KeeperException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

	private static Polaczenie polaczenie;
	
	@RequestMapping(value = "/dodaj/{nazwa}", method = RequestMethod.POST)
	@ResponseBody
	public String index(@PathVariable String nazwa,
			@RequestParam("tekst") String info) throws KeeperException,
			InterruptedException {

		polaczenie.createZNode(nazwa, info);
		System.out.println("Utworzono");
		return "Utworzono";
	}

	
    public static void main(String[] args) throws KeeperException, InterruptedException {
        SpringApplication.run(Application.class, args);
        polaczenie = new Polaczenie();
        
    }
}
