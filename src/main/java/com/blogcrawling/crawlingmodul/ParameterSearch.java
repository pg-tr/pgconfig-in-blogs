package com.blogcrawling.crawlingmodul;

import java.util.LinkedList;
import java.util.List;

public class ParameterSearch {

	public void search(String url, String postgresParam) {
		Crawler crawler = new Crawler();
		List<String> findUrls = new LinkedList<String>();

		findUrls = crawler.searchParam(url, postgresParam);

		for (String str : findUrls) {
			System.out.println(str);
		}
	}

}
