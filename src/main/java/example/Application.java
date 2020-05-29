package example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import example.pdf.NormalDistribution;

@SpringBootApplication
@RestController
public class Application {
	private static Logger log = LoggerFactory.getLogger("application");

	@Autowired
	private NormalDistribution normalPdf;
	
	@GetMapping("/")
	public String main() {
		log.info("Called main path");
		return "Choose {/gauss/,/standard} or {gauss/{mean}/{std_dev}, /normal/{mean}/{std_dev}}";
	}

	@GetMapping({"/gauss", "/standard"})
	public String standardNormal() {
		double normal = normalPdf.nextStandardNormal();
		log.info("Generated random value x={} with Standard Normal pdf.", normal);
		return "Generated random value " + normal + " with Standard Normal pdf.";
	}
	
	@GetMapping({"/gauss/{mean}/{std_dev}", "/normal/{mean}/{std_dev}"})
	public String normal(@PathVariable double mean, @PathVariable double std_dev) {
		double normal = normalPdf.nextNormal(mean, std_dev);
		
		log.info("Generated random value x={} with Normal(mean={}, sigma={}) pdf.", normal, mean, std_dev);
		return "Generated random value " + normal + " with Normal distribution (mean=" + mean + ", sigma=" + std_dev +").";
	}
	

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
