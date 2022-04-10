package com.blogcrawling.crawlingmodul;

import java.util.HashSet;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.blogcrawling.api.domain.Blog;

@Component
public class ParameterSearch {

	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	private HashSet<String> poolList;

	public ParameterSearch() {
		poolList = new HashSet<String>();
	}

	public HashSet<Blog> search(String url) {
		try {
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
			Document htmlDocument = connection.get();

			Elements linksOnPage = htmlDocument.select("a[href]");

			for (Element link : linksOnPage) {
				String absUrl = link.absUrl("href");
				poolList.add(absUrl);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(">> Pool list size = " + poolList.size());

		HashSet<Blog> blogAndParams = new HashSet<Blog>();

		String[] pollArray = new String[poolList.size()];
		poolList.toArray(pollArray);

		for (String link : poolList) {
			System.out.println(">> Started searching: " + " [" + link + "]");
			Crawler crawler = new Crawler();
			crawler.crawl(link, link);
			HashSet<Blog> tmp = crawler.getBlogs();
			blogAndParams.addAll(tmp);
			System.out.println(">> Finished searching: " + " [" + link + "]");
		}

		return blogAndParams;

	}
}
