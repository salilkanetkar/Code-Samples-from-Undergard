import java.io.*;

class PageReplacement {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    int n, page[], f, frames[], faults, count;
    double rate;

    public PageReplacement() throws IOException {
        System.out.println("Enter number of pages");
        n = Integer.parseInt(input.readLine());
        page = new int[n];
        System.out.println("Enter number of page frames");
        f = Integer.parseInt(input.readLine());
        frames = new int[f];
        count = 1;
    }

    void reset() {
        int j;
        for (j = 0; j < f; j++)
            frames[j] = 0;
        faults = 0;
        count = 1;
    }

    void read() throws IOException {
        int i;
        System.out.println("Enter the pages");
        for (i = 0; i < n; i++) {
            System.out.println("Enter page number " + (i + 1));
            page[i] = Integer.parseInt(input.readLine());
        }
        for (i = 0; i < f; i++)
            frames[i] = -1;
    }


    void fifo() {
        int i, j, k = 0;
        reset();
        boolean found = false;
        for (i = 0; i < n; i++) {
            for (j = 0; j < f; j++) {
                if (page[i] == frames[j])
                    found = true;
            }
            if (found == false) {
                frames[k] = page[i];
                if (k == f - 1)
                    k = 0;
                else
                    k++;
                faults++;

            }
            display();
            found = false;
        }
        System.out.println("Number of page faults = " + faults);
        System.out.println("Fault rate = " + (faults * 1.0 / n));
    }


    void lru() {
        int i, j, duration[], max;
        reset();
        duration = new int[f];
        boolean found = false;
        for (i = 0; i < n; i++) {
            for (j = 0; j < f; j++)
                duration[j]++;
            for (j = 0; j < f; j++) {
                if (page[i] == frames[j]) {
                    found = true;
                    duration[j] = 0;
                }
            }
            if (found == false) {
                max = 0;
                for (j = 0; j < f; j++) {
                    if (duration[j] > duration[max])
                        max = j;
                }
                frames[max] = page[i];
                duration[max] = 0;
                faults++;
            }

            display();
            found = false;

        }
        System.out.println("Number of page faults = " + faults);
        System.out.println("Fault rate = " + (faults * 1.0 / n));
    }


    void lfu() {
        int i, j, freq[], max, min, duration[];
        reset();
        freq = new int[f];
        duration = new int[f];
        boolean found = false;
        for (i = 0; i < n; i++) {
            for (j = 0; j < f; j++)
                duration[j]++;
            for (j = 0; j < f; j++) {
                if (page[i] == frames[j]) {
                    found = true;
                    freq[j]++;
                    duration[j] = 0;
                }
            }


            if (found == false) {
                min = 0;
                for (int k = 0; k < f; k++) {
                    if (freq[k] < freq[min])
                        min = k;
                }
                for (int k = 0; k < f; k++) {
                    if (freq[k] == min) {
                        max = 0;
                        for (j = 0; j < f; j++) {
                            if (duration[j] > duration[max])
                                max = j;
                        }
                        frames[max] = page[i];
                        duration[max] = 0;
                        freq[max]++;
                        faults++;
                        break;
                    }
                }

            }
            display();
            found = false;
        }
        System.out.println("Number of page faults = " + faults);
        System.out.println("Fault rate = " + (faults * 1.0 / n));
    }

    void opt() {
        int i, j = 0, k, duration[], max, flag[];
        reset();
        duration = new int[f];
        flag = new int[f];
        boolean found = false;

        for (i = 0; i < n; i++) {
            for (j = 0; j < f; j++) {
                flag[j] = 0;
                duration[j] = n;
            }

            for (k = i + 1; k < n; k++) {
                for (j = 0; j < f; j++)
                    if (page[k] == frames[j] && flag[j] == 0) {
                        duration[j] = k;
                        flag[j] = 1;
                    }
            }

            for (j = 0; j < f; j++)
                if (page[i] == frames[j])
                    found = true;
            if (found == false) {
                max = 0;
                for (j = 0; j < f; j++) {
                    if (duration[j] > duration[max])
                        max = j;
                    if (frames[j] < 0) {
                        max = j;
                        break;
                    }
                }
                frames[max] = page[i];
                faults++;
            }

            display();
            found = false;

        }
        System.out.println("Number of page faults = " + faults);
        System.out.println("Fault rate = " + (faults * 1.0 / n));
    }

    void display() {
        int i;
        System.out.print("Page frame " + count + " :");
        for (i = 0; i < f; i++) {
            if (frames[i] == -1)
                System.out.print(" -");
            else
                System.out.print(" " + frames[i]);
        }
        System.out.print("\n");
        count++;
    }

    public static void main(String[] args) throws IOException {
        int option;
        String choice;
        PageReplacement p = new PageReplacement();
        p.read();
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        do {
            System.out.println("Menu");
            System.out.println("1. FIFO");
            System.out.println("2. LRU");
            System.out.println("3. LFU");
            System.out.println("4. OPT");
            System.out.println("Enter option");
            option = Integer.parseInt(input.readLine());
            switch (option) {
                case 1:
                    p.fifo();
                    break;
                case 2:
                    p.lru();
                    break;
                case 3:
                    p.lfu();
                    break;
                case 4:
                    p.opt();
                    break;
                default:
                    System.out.println("Invalid input");
            }
            System.out.println("Press c to continue");
            choice = input.readLine();
        } while (choice.compareToIgnoreCase("c") == 0);
    }
}
