import java.util.LinkedList;
import java.util.Scanner;

public class ProbeBasedAlgo {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int n, i, j, a[][], currentNode, s;
        boolean deadlock = false, visited[];
        System.out.println("Enter no. of nodes");
        n = sc.nextInt();
        a = new int[n + 1][n + 1];
        visited = new boolean[n + 1];
        System.out.println("Enter WFG of the whole system");
        for (i = 1; i < n + 1; i++) {
            for (j = 1; j < n + 1; j++) {
                if (i != j) {
                    System.out.println("Is node " + i + " waiting for node " + j + "(1/0)");
                    a[i][j] = sc.nextInt();
                }
            }
            visited[i] = false;
        }
        System.out.println("Enter the initiator node for detection");
        s = sc.nextInt();
        LinkedList l = new LinkedList();
        l.add(new Integer(s));
        while (!(l.isEmpty()) && !deadlock) {
            Integer temp = (Integer) l.remove();
            currentNode = temp.intValue();
            if (visited[currentNode] == false) {
                visited[currentNode] = true;
                for (i = 1; i < n + 1; i++) {
                    if (a[currentNode][i] == 1) {
                        System.out.println("Message from node " + currentNode + " to node" + i + "(P" + s + ",P" + i + ")");
                        l.add(new Integer(i));
                        if (i == s) {
                            deadlock = true;
                            break;
                        }
                    }
                }
            }
        }
        if (deadlock)
            System.out.println("Deadlock detected");
        else
            System.out.println("No deadlock");
    }
}