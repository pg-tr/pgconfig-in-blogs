package com.blogcrawling.crawlingmodul;

import java.util.LinkedList;
import java.util.List;

public class ParameterSearch {

	public void search(String url, String postgresParam) {
		// SearchModule searchModule = new SearchModule();

		Crawler crawler = new Crawler();
		crawler.findAllUrls(url, url, 0, postgresParam);

		System.out.println(">> Finished searching: " + " [" + url + "]");

		// searchModule.seach(url, postgresParam);
	}
}
