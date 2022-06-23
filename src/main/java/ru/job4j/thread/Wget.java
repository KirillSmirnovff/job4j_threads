package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        String[] urlSplit = url.split("/");
        String fileName = urlSplit[urlSplit.length - 1];
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[1024];
            int downloadData = 0;
            long timeStart = System.currentTimeMillis();
            int bytesRead = in.read(dataBuffer, 0, 1024);
            while (bytesRead != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                downloadData += bytesRead;
                if (downloadData >= speed) {
                    long timeDiff = System.currentTimeMillis() - timeStart;
                    if (timeDiff < 1000) {
                        Thread.sleep(1000 - timeDiff);
                    }
                    downloadData = 0;
                    timeStart = System.currentTimeMillis();
                }
                bytesRead = in.read(dataBuffer, 0, 1024);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void validate(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Wrong number of arguments");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}