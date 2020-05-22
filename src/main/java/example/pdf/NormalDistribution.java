package example.pdf;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NormalDistribution {
	private static Logger log = LoggerFactory.getLogger("random-distribution");
	
	private Random rand = new Random();
	
	public void setSeed(long seed) {
		rand.setSeed(seed);
		log.info("Set SEED to {}", seed);
	}
	
	public double nextStandardNormal() {
		return rand.nextGaussian();
	}
	
	public double nextNormal(double mean, double std_dev) {
		double abs_std_dev = std_dev;
		if(std_dev<0) {
			log.warn("Standard deviation is NEGATIVE = {}, we take absolute value then.", std_dev);
			abs_std_dev = Math.abs(std_dev);
		}
		return mean + nextStandardNormal()*abs_std_dev;
	}
}
