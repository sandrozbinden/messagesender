package com.sandrozbinden.messagesender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

public class EternaUserLoader implements Iterator<EternaUser>, Iterable<EternaUser> {
	private static final Logger logger = LoggerFactory.getLogger(EternaUserLoader.class);

	private static final int REQUEST_SLEEP_IN_MS = Setting.getInstance().getEternaPlayersRequestSleepInMS();
	private int skip = Setting.getInstance().getEternaPlayersSkipStart();
	private int size = Setting.getInstance().getEternaPlayersChunkSize();
	private List<EternaUser> chunks = new ArrayList<EternaUser>();
	private Stopwatch stopwatch = Stopwatch.createStarted();

	@Override
	public boolean hasNext() {
		return skip < Setting.getInstance().getEternaPlayersMaxSkip();
	}

	@Override
	public EternaUser next() {
		if (chunks.size() > 0) {
			EternaUser nextEternaUser = chunks.get(0);
			chunks = chunks.subList(1, chunks.size());
			return nextEternaUser;
		} else {
			loadChunk();
			return next();
		}
	}

	private void loadChunk() {
		loadNextChunk();
	}

	private void loadNextChunk() {
		String rankingURL = Eterna.BASE_REST_URL + "?type=users&sort=active&skip=" + skip + "&size=" + size;
		try {
			while (!(stopwatch.elapsed(TimeUnit.MILLISECONDS) > REQUEST_SLEEP_IN_MS)) {
				Thread.sleep(10);
			}
			logger.info("Loading next chunk of players with url: " + rankingURL);
			stopwatch = Stopwatch.createStarted();
			skip = skip+ size;
			HttpClient client = HttpClients.custom().build();
			HttpGet get = new HttpGet(rankingURL);
			HttpResponse response = client.execute(get);
			String entity = EntityUtils.toString(response.getEntity());
			JSONObject root = (JSONObject) JSONValue.parse(entity);
			JSONObject data = (JSONObject) root.get("data");
			JSONArray users = (JSONArray) data.get("users");
			for (Object userObject : users) {
				JSONObject user = (JSONObject) userObject;
				String id = (String) user.get("uid");
				String userName = (String) user.get("name");
				chunks.add(new EternaUser(id, userName));
			}
			
			
		} catch (InterruptedException |  IOException e) {
			throw new IllegalStateException("Can't get next ranking with url: " + rankingURL, e);
		}
	}

	@Override
	public void remove() {
		throw new IllegalStateException("Not allowed at this position");

	}

	@Override
	public Iterator<EternaUser> iterator() {
		return this;
	}

}
