package com.devilyang.musicstation.cache;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @title 缓存管理器
 * @author 杨福海（michael） www.yangfuhai.com
 * @version 1.0
 */
public class ACacheManager {
	private final AtomicLong cacheSize;
	private final AtomicInteger cacheCount;
	private final long sizeLimit;
	private final int countLimit;
	private final Map<File, Long> lastUsageDates = Collections.synchronizedMap(new HashMap<File, Long>());
	protected File cacheDir;

	public ACacheManager(File cacheDir, long sizeLimit, int countLimit) {
		this.cacheDir = cacheDir;
		this.sizeLimit = sizeLimit;
		this.countLimit = countLimit;
		cacheSize = new AtomicLong();
		cacheCount = new AtomicInteger();
		calculateCacheSizeAndCacheCount();
	}

	/**
	 * 计算 cacheSize和cacheCount
	 */
	public void calculateCacheSizeAndCacheCount() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				int size = 0;
				int count = 0;
				File[] cachedFiles = cacheDir.listFiles();
				if (cachedFiles != null) {
					for (File cachedFile : cachedFiles) {
						size += calculateSize(cachedFile);
						count += 1;
						lastUsageDates.put(cachedFile, cachedFile.lastModified());
					}
					cacheSize.set(size);
					cacheCount.set(count);
				}
			}
		}).start();
	}

	public void put(File file) {
		int curCacheCount = cacheCount.get();
		while (curCacheCount + 1 > countLimit) {
			long freedSize = removeNext();
			cacheSize.addAndGet(-freedSize);

			curCacheCount = cacheCount.addAndGet(-1);
		}
		cacheCount.addAndGet(1);

		long valueSize = calculateSize(file);
		long curCacheSize = cacheSize.get();
		while (curCacheSize + valueSize > sizeLimit) {
			long freedSize = removeNext();
			curCacheSize = cacheSize.addAndGet(-freedSize);
		}
		cacheSize.addAndGet(valueSize);

		Long currentTime = System.currentTimeMillis();
		file.setLastModified(currentTime);
		lastUsageDates.put(file, currentTime);
	}

	public File get(String key) {
		File file = newFile(key);
		Long currentTime = System.currentTimeMillis();
		file.setLastModified(currentTime);
		lastUsageDates.put(file, currentTime);

		return file;
	}

	public File newFile(String key) {
		return new File(cacheDir, getFilenameForKey(key));
	}
	/**
     * Creates a pseudo-unique filename for the specified cache key.
     * @param key The key to generate a file name for.
     * @return A pseudo-unique filename.
     */
    private String getFilenameForKey(String key) {
        int firstHalfLength = key.length() / 2;
        String localFilename = String.valueOf(key.substring(0, firstHalfLength).hashCode());
        localFilename += String.valueOf(key.substring(firstHalfLength).hashCode());
        return localFilename;
    }
	public boolean remove(String key) {
		File image = get(key);
		return image.delete();
	}

	public void clear() {
		lastUsageDates.clear();
		cacheSize.set(0);
		File[] files = cacheDir.listFiles();
		if (files != null) {
			for (File f : files) {
				f.delete();
			}
		}
	}

	/**
	 * 移除旧的文件
	 * 
	 * @return
	 */
	public long removeNext() {
		if (lastUsageDates.isEmpty()) {
			return 0;
		}

		Long oldestUsage = null;
		File mostLongUsedFile = null;
		Set<Entry<File, Long>> entries = lastUsageDates.entrySet();
		synchronized (lastUsageDates) {
			for (Entry<File, Long> entry : entries) {
				if (mostLongUsedFile == null) {
					mostLongUsedFile = entry.getKey();
					oldestUsage = entry.getValue();
				} else {
					Long lastValueUsage = entry.getValue();
					if (lastValueUsage < oldestUsage) {
						oldestUsage = lastValueUsage;
						mostLongUsedFile = entry.getKey();
					}
				}
			}
		}

		long fileSize = calculateSize(mostLongUsedFile);
		if (mostLongUsedFile.delete()) {
			lastUsageDates.remove(mostLongUsedFile);
		}
		return fileSize;
	}

	public long calculateSize(File file) {
		return file.length();
	}
}