import java.util.*;

class Node {
    String c;
    int value;
    String code;
    Node left, right, next, parent;

    Node(String ch, int f) {

        c = ch;
        value = f;
        left = right = next = parent = null;
    }

    Node(Node one, Node two) {
        c = one.c + "," + two.c;
        one.code = "0";
        two.code = "1";
        value = one.value + two.value;
        left = one;
        right = two;
        one.parent = two.parent = this;
    }
}

class Queue {
    public static Node front, rear;
    public static Node arr[] = new Node[30];

    Queue() {
        front = rear = null;
    }

    public static boolean empty() {
        if (front == rear)
            return true;
        else
            return false;
    }

    public static void insert(Node n) {
        if (rear == null || front == null) {

            rear = n;
            front = n;
        } else {
            Node current = front;
            Node prev = null;
            while (current != rear.next) {
                if (n.value < current.value)
                    if (current == front) {
                        n.next = current;
                        front = n;
                        break;
                    } else {
                        n.next = current;
                        prev.next = n;
                        break;
                    }

                prev = current;
                current = current.next;

            }
            if (current == rear.next) {
                rear.next = n;
                rear = n;
            }
        }
    }

    public static Node remove() {
        Node re = front;
        front = front.next;
        return re;
    }
    /* public static void display()
	{
	Node current=front;
	while(current!=rear.next)
	{
	System.out.print(current.c+" "+"("+current.value+")"+current.code+"\t");
	current=current.next;
	}
        System.out.println();
	}*/
}

class Huffman {
    public static Queue q = new Queue();
    public static int frequency[] = new int['Z' - 'A' + 1];

    public static void calcfreq(String s) {
        for (int i = 0; i < s.length(); i++) {
            char ch = Character.toUpperCase(s.charAt(i));
            if ((ch >= 'A') && (ch <= 'Z'))
                ++frequency[ch - 'A'];
        }

    }

    public static void create() {

        for (int i = 0; i < 'Z' - 'A' + 1; i++) {
            if (frequency[i] > 0) {
                char s = (char) ('A' + i);
                String st = s + "";
                Node n = new Node(st, frequency[i]);
                Queue.insert(n);
            }
        }
        //Queue.display();
    }

    public static void huffman() {
        int count = 0;

        while (!q.empty()) {
            Node one = q.remove();
            Node two = q.remove();
            Node n = new Node(one, two);

            q.insert(n);
            ++count;
            //System.out.println("Step "+count+"--");
            //q.display();
            //System.out.println();
        }
    }

    public static void inorder(Node root) {

        if (root == null)
            return;
        inorder(root.left);
        if (root.left == null && root.right == null) {
            String s = " ";
            Node n = root;
            while (n.parent != null) {
                s = n.code + s;
                n = n.parent;
            }
            System.out.println(root.c + "\t" + s);
        }
        inorder(root.right);

    }

    public static void displaycode() {
        System.out.println("Letter\tCode");
        inorder(Queue.rear);
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        String i;
        System.out.print("Enter the string=");
        i = sc.nextLine();
        calcfreq(i);
        create();
        huffman();
        displaycode();
    }
}
