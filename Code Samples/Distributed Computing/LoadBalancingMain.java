import java.util.Arrays;
import java.io.*;

class LoadBalancingMain {
    public static void main(String args[]) {
        LoadBalancing lb = new LoadBalancing();
    }
}

class LoadBalancing {
    LoadBalancing() {
        int M = -1;
        int N = -1;
        Job[] jobs = null;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter number of machines ");
            M = Integer.parseInt(br.readLine());
            System.out.println("Enter number of processes");
            N = Integer.parseInt(br.readLine());
            jobs = new Job[N];
            System.out.println("Enter Burst Time of Each Process");
            for (int i = 0; i < N; i++) {

                String name = "P" + i;
                System.out.print(name + " : ");
                double time = Double.parseDouble(br.readLine());
                jobs[i] = new Job(name, time);
            }
        } catch (Exception e) {
        }

        Arrays.sort(jobs);
        MinPQ<Processor> pq = new MinPQ<Processor>(M);
        for (int i = 0; i < M; i++)
            pq.insert(new Processor());
        for (int j = N - 1; j >= 0; j--) {
            Processor min = pq.delMin();
            min.add(jobs[j]);
            pq.insert(min);
        }
        System.out.println("***** Machine assignments *****");
        int count = 0;
        while (!pq.isEmpty())
            System.out.println("Machine" + count++ + " ---> Total Load: " + pq.delMin());
    }


}

class Processor implements Comparable<Processor> {
    private double load = 0;
    private Queue<Job> list = new Queue<Job>();

    public void add(Job job) {
        list.enqueue(job);
        load += job.time();
    }

    public int compareTo(Processor that) {
        if (this.load < that.load) return -1;
        if (this.load > that.load) return +1;
        return 0;
    }

    public String toString() {
        String s = load + ":: ";
        for (Job x : list)
            s += x + " ";
        return s;
    }

}

class Job implements Comparable<Job> {
    private final String name;
    private final double time;

    public Job(String name, double time) {
        if (time < 0) throw new RuntimeException("Can't have negative processing time");
        this.name = name;
        this.time = time;
    }

    public double time() {
        return time;
    }

    public int compareTo(Job that) {
        if (this.time < that.time) return -1;
        else if (this.time > that.time) return +1;
        else return 0;
    }

    public String toString() {
        return String.format("%s %.1f", name, time);
    }
}
