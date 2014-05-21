package ZooKeeperTEST;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

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
public class Application extends JFrame{

	private JButton addNode = new JButton("Add ");
	
	public Application() {
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setSize(new Dimension(90,90));
		setLayout(new FlowLayout());
		
		addNode.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					polaczenie.createZNode("nowyNode", "jakiesDane");
				} catch (KeeperException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		add(addNode);
	}
	
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
