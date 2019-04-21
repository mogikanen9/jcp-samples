package org.mogikabebsoftware.play.bq;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;

public class FileCrawler implements Runnable {
	private final BlockingQueue<File> fileQueue;
	private final FileFilter fileFilter;
	private final File root;

	public FileCrawler(BlockingQueue<File> fileQueue, FileFilter filter, File root) {
		super();
		this.fileQueue = fileQueue;
		this.fileFilter = filter;
		this.root = root;
	}

	public void run() {
		try {
			crawl(root);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void crawl(File root) throws InterruptedException {
		System.out.println(String.format("Start crawling root->%s",root.getName()));
		File[] entries = root.listFiles(fileFilter);
		if (entries != null) {
			for (File entry : entries) {
				if (entry.isDirectory()) {
					crawl(entry);
				} else if (!alreadyIndexed(entry)) {
					fileQueue.put(entry);
				}
			}
		}
		System.out.println(String.format("Finished crawling root->%s",root.getName()));
	}

	private boolean alreadyIndexed(File entry) {
		return fileQueue.contains(entry);
	}
}
