package com.finra.boot;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileReadController {

    @RequestMapping("/fileAttr")
    public Attributes getFileAttributes(@RequestParam(value="name", defaultValue="World") String name) throws IOException {
    	ClassLoader classLoader = getClass().getClassLoader();
    	Path file = Paths.get(classLoader.getResource("reservation.pdf").getFile());
    	BasicFileAttributes attr = null;
		try {
			attr = Files.readAttributes(file, BasicFileAttributes.class);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
           return new Attributes(attr.creationTime().toString(),attr.lastModifiedTime().toString(),attr.isOther(),attr.isRegularFile());
	
    }
    
    @RequestMapping(value="download", method=RequestMethod.GET)
    public void getDownload(HttpServletResponse response) throws IOException {
    	ClassLoader classLoader = getClass().getClassLoader();
    	// Get your file stream from wherever.
    	InputStream myStream = new FileInputStream(classLoader.getResource("reservation.pdf").getFile());

    	// Set the content type and attachment header.
    	response.addHeader("Content-disposition", "attachment;filename=reservation.pdf");
    	response.setContentType("txt/plain");

    	// Copy the stream to the response's output stream.
    	IOUtils.copy(myStream, response.getOutputStream());
    	response.flushBuffer();
    }
		
}
