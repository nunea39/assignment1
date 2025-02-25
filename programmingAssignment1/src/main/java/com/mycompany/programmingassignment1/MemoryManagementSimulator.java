package com.mycompany.programmingassignment1;
/*Andry Nunez*/
/*CSC343 Programming Assignment 1*/
import java.util.ArrayList;
import java.util.Random;

class Process {
    int processId;
    int startAddress;
    int processSize;
    int unusedSpace;

    public Process(int processId, int startAddress, int processSize, int unusedSpace) {
        this.processId = processId;
        this.startAddress = startAddress;
        this.processSize = processSize;
        this.unusedSpace = unusedSpace;
    }
}

public class MemoryManagementSimulator {
    static final int MEMORY_SIZE_MB = 16000; 
    static final int PAGE_SIZE_MB = 160; 
    static final int TOTAL_PAGES = MEMORY_SIZE_MB / PAGE_SIZE_MB; 
    static final int START_ADDRESS = 2000;

    static int[] memory = new int[TOTAL_PAGES]; 
    static ArrayList<Process> processList = new ArrayList<>();

    public static void userMemoryAllocation() {
        Random random = new Random();
        int processId = 1;
        int currentAddress = START_ADDRESS;
        int currentPageIndex = 0;

        while (currentPageIndex < TOTAL_PAGES) {
            int pagesNeeded = (random.nextInt(30) + 1); 
            int processSize = pagesNeeded * 80; 
            int pagesRequired = (int) Math.ceil((double) processSize / PAGE_SIZE_MB);
            
            if (currentPageIndex + pagesRequired > TOTAL_PAGES) {
                break; 
            }
            
            int totalAllocatedSize = pagesRequired * PAGE_SIZE_MB;
            int unusedSpace = totalAllocatedSize - processSize;
            
            processList.add(new Process(processId, currentAddress, processSize, unusedSpace));
            
            for (int i = 0; i < pagesRequired; i++) {
                memory[currentPageIndex + i] = processId;
            }
            
            currentAddress += totalAllocatedSize;
            currentPageIndex += pagesRequired;
            processId++;
        }
    }

    public static void printMemoryReport() {
        System.out.println("Process Id\tStarting Memory Address\tSize of the Process (MB)\tUnused Space (MB)");
        for (Process p : processList) {
            System.out.printf("%10d\t%24d\t%22d\t%18d\n", p.processId, p.startAddress, p.processSize, p.unusedSpace);
        }
    }

    public static void main(String[] args) {
        userMemoryAllocation();
        printMemoryReport();
    }
}
