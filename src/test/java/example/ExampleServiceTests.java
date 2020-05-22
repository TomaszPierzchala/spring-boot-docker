package example;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import example.pdf.NormalDistribution;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class ExampleServiceTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	NormalDistribution normalDistribution;

	@Test
	public void testServiceStatus() throws Exception {
		ResponseEntity<String> entity = restTemplate
				.getForEntity("http://localhost:" + this.port + "/", String.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());
	}
	
	@Test
	public void testStandardNormalDistributionWithGivenSEED() {
		normalDistribution.setSeed(12l);
		double EXPECTED[] = {0.9495664817512859, -0.9290711821234726, -0.031517213460427594, 0.7257028112994507};
		
		ResponseEntity<String> entity = null;
		for(int i=0;i<4;i++) {
			entity = restTemplate
					.getForEntity("http://localhost:" + this.port + "/standard", String.class);
			String expected = "Generated random value " + EXPECTED[i] + " with Standard Normal pdf.";
			assertEquals(expected, entity.getBody());
		}
		
		normalDistribution.setSeed(12l);
		for(int i=0;i<4;i++) {
			entity = restTemplate
					.getForEntity("http://localhost:" + this.port + "/gauss", String.class);
			String expected = "Generated random value " + EXPECTED[i] + " with Standard Normal pdf.";
			assertEquals(expected, entity.getBody());
		}
	}
	
	@Test
	public void testNormalDistributionWithGivenSEED() {
		normalDistribution.setSeed(12l);
		double EXPECTED[] = {11.899132963502572, 8.141857635753055, 9.936965573079144, 11.451405622598902};
		
		ResponseEntity<String> entity = null;
		for(int i=0;i<4;i++) {
			entity = restTemplate
					.getForEntity("http://localhost:" + this.port + "/normal/10/2", String.class);
			String expected = "Generated random value " + EXPECTED[i] + " with Normal distribution (mean=10.0, sigma=2.0).";
			assertEquals(expected, entity.getBody());
		}
		
		normalDistribution.setSeed(12l);
		for(int i=0;i<4;i++) {
			entity = restTemplate
					.getForEntity("http://localhost:" + this.port + "/gauss/10/2", String.class);
			String expected = "Generated random value " + EXPECTED[i] + " with Normal distribution (mean=10.0, sigma=2.0).";
			assertEquals(expected, entity.getBody());
		}
		}
}
