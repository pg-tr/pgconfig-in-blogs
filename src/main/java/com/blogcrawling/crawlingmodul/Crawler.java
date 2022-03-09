package com.blogcrawling.crawlingmodul;

import java.net.SocketTimeoutException;
import java.util.HashSet;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import com.blogcrawling.api.domain.Blog;
import com.blogcrawling.api.domain.BlogIdentity;
import com.blogcrawling.api.repository.BlogRepository;

import lombok.Data;

@Data
public class Crawler {

	@Autowired
	BlogRepository blogRepository;

	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	private HashSet<String> urls;
	private HashSet<Blog> blogs;

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
		blogs = new HashSet<Blog>();
	}

	public void crawl(String baseUrl, String url) {
		if (!urls.contains(url) && url.startsWith(baseUrl)) {

			urls.add(url);

			try {
				Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
				Document htmlDocument = connection.get();

				Elements linksOnPage = htmlDocument.select("a[href]");

				bodyContent = htmlDocument.body().text();
				String title = htmlDocument.title();

				if (connection.response().statusCode() == 200
						&& connection.response().contentType().contains("text/html")) {
					searchParameters(url, title);

					for (Element link : linksOnPage) {
						if (!link.absUrl("href").endsWith("txt") && !link.absUrl("href").endsWith("pdf")
								&& !link.absUrl("href").endsWith("xml"))
							crawl(baseUrl, link.absUrl("href"));
					}

				} else {
					System.out.println("Skipped [" + url + "] because it is not a html template.");
				}

			} catch (UnsupportedMimeTypeException e) {

			} catch (HttpStatusException e) {

			} catch (SocketTimeoutException e) {

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void searchParameters(String URL, String title) {
		for (String param : postgresParamArray) {
			if (bodyContent.toLowerCase().contains(param.toLowerCase())) {

				BlogIdentity blogIdentity = new BlogIdentity();
				blogIdentity.setBlog_url(URL);
				blogIdentity.setParam(param);

				Blog newBlogEntry = new Blog();
				newBlogEntry.setBlogTitle(title);
				newBlogEntry.setIdentity(blogIdentity);

				blogs.add(newBlogEntry);

				System.out.println(">>>>>> Found: " + " [" + param + "]" + " [" + URL + "]" + " [" + title + "]");
			}
		}
	}
}
