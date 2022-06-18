package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    private final String[] process = {"-", "\\", "|", "/"};
    private int count = 0;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (count < 4) {
                    System.out.print("\rLoading... " + process[count++]);
                    Thread.sleep(500);
                    continue;
                }
                count = 0;
                System.out.print("\rLoading... " + process[count++]);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(3000);
        progress.interrupt();
    }
}
