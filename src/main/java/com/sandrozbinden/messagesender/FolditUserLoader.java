package com.sandrozbinden.messagesender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

public class FolditUserLoader implements Iterator<FolditUser>, Iterable<FolditUser> {
	
	private static final Logger logger = LoggerFactory.getLogger(FolditUserLoader.class);

	private static final int REQUEST_SLEEP_IN_MS = Setting.getInstance().getFolditPlayersRequestSleepInMS();
	private int page = Setting.getInstance().getFolditPlayersStartPage();
	private List<FolditUser> chunks = new ArrayList<FolditUser>();
	private Stopwatch stopwatch = Stopwatch.createStarted();

	@Override
	public boolean hasNext() {
		return page < Setting.getInstance().getFolditPlayersPageCount();
	}

	@Override
	public FolditUser next() {
		if (chunks.size() > 0) {
			FolditUser nextFolditUser = chunks.get(0);
			chunks = chunks.subList(1, chunks.size());
			return nextFolditUser;
		} else {
			loadChunk();
			return next();
		}
	}

	private void loadChunk() {
		Document document = getNextChunkDocument();
		Elements playerTableRows = document.getElementsByClass("even");
		for (Element element : playerTableRows) {
			Element link = element.select("a").first();
			String rank = element.select(".view-field-global-rank").first()
					.text().replace("#", "");
			String score = element.select(".view-field-global-score").first()
					.text();
			String userName = link.text().substring(0, link.text().indexOf(" "));
			String href = link.attr("href");
			String id = href.substring(href.lastIndexOf("/") +1 , href.length());
			chunks.add(new FolditUser(id, userName, rank, score));
		}
	}

	private Document getNextChunkDocument() {
		String rankingURL = Foldit.RANKING_URL + "?page=" + page;
		try {
			while (!(stopwatch.elapsed(TimeUnit.MILLISECONDS) > REQUEST_SLEEP_IN_MS)) {
				Thread.sleep(10);
			}
			logger.info("Loading next chunk of players with url: " + rankingURL);
			Document document = Jsoup.connect(rankingURL).timeout(Foldit.DEFAULT_TIME_OUT).execute().parse();
			stopwatch = Stopwatch.createStarted();
			page = page + 1;
			return document;
		} catch (IOException | InterruptedException e) {
			throw new IllegalStateException("Can't get next ranking with url: "
					+ rankingURL, e);
		}
	}

	@Override
	public void remove() {
		throw new IllegalStateException("Not allowed at this position");

	}

	@Override
	public Iterator<FolditUser> iterator() {
		return this;
	}

}
