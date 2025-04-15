package org.example;

import java.util.concurrent.locks.ReentrantLock;

public class Main {
    static ReentrantLock fork0 = new ReentrantLock();
    static ReentrantLock fork1 = new ReentrantLock();

    static class Philosopher extends Thread {
        private int id;

        public Philosopher(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            ReentrantLock firstFork, secondFork;

            if (id == 0) {
                firstFork = fork0;
                secondFork = fork1;
            } else {
                firstFork = fork1;
                secondFork = fork0;
            }

            for (int i = 0; i < 5; i++) {
                try {
                    System.out.println("Philosopher " + id + " is thinking.");
                    Thread.sleep((int)(Math.random() * 1000));

                    firstFork.lock();
                    System.out.println("Philosopher " + id + " picked up first fork.");

                    secondFork.lock();
                    System.out.println("Philosopher " + id + " picked up second fork. Eating...");

                    Thread.sleep((int)(Math.random() * 1000));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    secondFork.unlock();
                    firstFork.unlock();
                    System.out.println("Philosopher " + id + " put down forks.");
                }
            }
        }
    }

    public static void main(String[] args) {
        Philosopher p0 = new Philosopher(0);
        Philosopher p1 = new Philosopher(1);

        p0.start();
        p1.start();

        try {
            p0.join();
            p1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Both philosophers are done eating.");
    }
}
