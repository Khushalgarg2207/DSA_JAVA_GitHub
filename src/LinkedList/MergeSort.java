package LinkedList;

class Node {
    int data;
    Node next;

    public Node(int d) {
        this.data = d;
        next = null;
    }
}
public class MergeSort {
    public static void main(String[] args) {

    }
    static Node mergeSort(Node head) {
        if(head == null || head.next == null) return head;

        Node middle = findMid(head);
        Node leftHead = head;
        Node rightHead = middle.next;
        middle.next = null;

        leftHead = mergeSort(leftHead);
        rightHead = mergeSort(rightHead);
        return merge(leftHead,rightHead);
    }
    static Node findMid(Node head) {
        Node slow = head;
        Node fast = head.next;

        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }
    static Node merge(Node h1,Node h2) {
        Node dummyList = new Node(-1);
        Node temp = dummyList;

        while(h1 != null && h2 != null) {
            if(h1.data < h2.data) {
                temp.next = h1;
                temp = h1;
                h1 = h1.next;
            }
            else {
                temp.next = h2;
                temp = h2;
                h2 = h2.next;
            }
        }

        if(h1 != null) temp.next = h1;
        if(h2 != null) temp.next = h2;

        return dummyList.next;
    }
}