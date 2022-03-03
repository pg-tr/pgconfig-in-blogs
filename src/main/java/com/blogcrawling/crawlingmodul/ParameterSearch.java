package com.blogcrawling.crawlingmodul;

import java.util.HashSet;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParameterSearch {

	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	private HashSet<String> poolList;

	public ParameterSearch() {
		poolList = new HashSet<String>();
	}

	public void search(String url, String postgresParam) {

		try {
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
			Document htmlDocument = connection.get();

			Elements linksOnPage = htmlDocument.select("a[href]");

			for (Element link : linksOnPage) {
				String absUrl = link.absUrl("href");
				String[] parts = absUrl.split("/");
				String output = parts[0] + "//" + parts[1] + parts[2];
				poolList.add(output);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		for (String link : poolList) {
			System.out.println(">> Started searching: " + " [" + link + "]");

		}

		System.out.println(poolList.size());

//		for (String link : poolList) {
//			System.out.println(">> Started searching: " + " [" + link + "]");
//			Crawler crawler = new Crawler();
//			crawler.crawl(link, link, postgresParam);
//			System.out.println(">> Finished searching: " + " [" + link + "]");
//		}
	}
}
