package com.blogcrawling.crawlingmodul;

import java.util.HashSet;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import lombok.Data;

@Data
public class Crawler {

	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	private HashSet<String> urls;

	private Integer count = 0;

	public Crawler() {
		urls = new HashSet<String>();
	}

	public void crawl(String baseUrl, String url, String postgresParam) {
		if (!urls.contains(url) && url.startsWith(baseUrl)) {

			// System.out.println(">> count: " + count + " [" + url + "]");
			urls.add(url);

			try {
				Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
				Document htmlDocument = connection.get();

				Elements linksOnPage = htmlDocument.select("a[href]");

//				String bodyText = htmlDocument.body().text();
//
//				if (bodyText.toLowerCase().contains(postgresParam.toLowerCase())) {
//					System.out.println(">>>>>> Found: " + " [" + postgresParam + "]" + " [" + url + "]");
//				}

				count++;

				for (Element link : linksOnPage) {
					crawl(baseUrl, link.absUrl("href"), postgresParam);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
