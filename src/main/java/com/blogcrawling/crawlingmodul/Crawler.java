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
	private String bodyContent;
	private String[] postgresParamArray = { "wal_level", "fsync", "synchronous_commit", "wal_sync_method",
			"full_page_writes", "wal_log_hints", "wal_compression", "wal_init_zero", "wal_recycle", "wal_buffers",
			"wal_writer_delay", "wal_writer_flush_after", "wal_skip_threshold", "commit_delay", "commit_siblings",
			"checkpoint_timeout", "checkpoint_completion_target", "checkpoint_flush_after", "checkpoint_warning",
			"min_wal_size", "archive_mode", "archive_command", "archive_timeout", "restore_command",
			"archive_cleanup_command", "recovery_end_command", "recovery_target = 'immediate'", "recovery_target_name",
			"recovery_target_time", "recovery_target_lsn", "recovery_target_xid" };

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

				bodyContent = htmlDocument.body().text();
				String title = htmlDocument.title();

				searchParameters(url, title);

				// count++;

				for (Element link : linksOnPage) {
					crawl(baseUrl, link.absUrl("href"), postgresParam);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void searchParameters(String URL, String Title) {
		for (String param : postgresParamArray) {
			if (bodyContent.toLowerCase().contains(param.toLowerCase())) {
				System.out.println(">>>>>> Found: " + " [" + param + "]" + " [" + URL + "]" + " [" + Title + "]");
			}
		}
	}
}
