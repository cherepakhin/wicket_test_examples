package se.crisp.wicket.examples;

import org.apache.wicket.protocol.http.WebApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see se.crisp.wicket.examples.TestExampleStarter#main(String[])
 */
public class WicketTestExamplesApplication extends WebApplication
{
	private static final Logger log = LoggerFactory.getLogger(WicketTestExamplesApplication.class);

	/**
     * Constructor
     */
	public WicketTestExamplesApplication()
	{
	}
	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	public Class<HomePage> getHomePage() {
//		log.info("ConfigurationType=" + this.getConfigurationType().toString()); // ConfigurationType=DEVELOPMENT
		return HomePage.class;
	}

}
