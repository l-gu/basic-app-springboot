package org.demo.myapp.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyApplication {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
    public void run(String[] args) {
    	log.info("Starting application...");
    	log.info("args.length = " + args.length);

    }
}
