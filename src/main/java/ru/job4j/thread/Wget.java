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
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("file_tmp")) {
            byte[] dataBuffer = new byte[1024];
            long timeStart = System.currentTimeMillis();
            int bytesRead = in.read(dataBuffer, 0, 1024);
            long timeEnd = System.currentTimeMillis();
            long pause = 1024 / speed * 1000 - timeEnd + timeStart;
            while (bytesRead != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                if (pause > 0) {
                    Thread.sleep(pause);
                }
                timeStart = System.currentTimeMillis();
                bytesRead = in.read(dataBuffer, 0, 1024);
                timeEnd = System.currentTimeMillis();
                pause = 1024 / speed * 1000 - timeEnd + timeStart;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}