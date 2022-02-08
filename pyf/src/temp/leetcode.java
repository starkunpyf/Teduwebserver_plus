package temp;

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

public class leetcode {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode headResult = new ListNode(0);
        ListNode result = new ListNode(0);
        result = headResult.next;
        boolean flag = false;
        while (l1 != null || l2 != null) {
            int temp = 0;
            if (l1 != null && l2 != null){
                temp = l1.val + l2.val;
            }else if (l1 != null && l2 == null){
                temp = l1.val;
            }else if (l1 == null && l2 != null){
                temp = l2.val;
            }
            if (temp >= 10){

                result.val = temp - 10;
                if (flag){
                    result.val++;
                    flag = false;
                }
                flag = true;

            }else{
                result.val = temp;
                if (flag){
                    result.val++;
                    flag = false;
                }
                flag = false;
            }
            l2 = l2.next;
            l1 = l1.next;
            System.out.println(result.val);
            result = new ListNode(0,result.next);

        }
        System.out.println(headResult.next.val);
        System.out.println(headResult.next.next.val);
        System.out.println(headResult.next.next.next.val);
        return headResult.next;
    }
}
