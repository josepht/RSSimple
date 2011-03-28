package com.xenno.rssparser;

import java.io.FileReader;
import org.xml.sax.InputSource;
import com.xenno.rssparser.RSSParser;

public class RSSParserTest {

	public static void main(String args[]) throws Exception {

		RSSParser parser = new RSSParser();

		for (int i = 0; i < args.length; i++) {
			//System.out.println("Parsing " + args[i]);
			FileReader r = new FileReader(args[i]);
			parser.parse(new InputSource(r));
		}
	}
}
