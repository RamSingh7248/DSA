class Solution {

    class Node {
        int leftLen, rightLen, maxLen, len;
        char leftChar, rightChar;
    }

    Node[] tree;
    char[] arr;

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        int n = s.length();
        arr = s.toCharArray();
        tree = new Node[4 * n];

        build(1, 0, n - 1);

        int[] ans = new int[queryIndices.length];

        for (int i = 0; i < queryIndices.length; i++) {
            update(1, 0, n - 1, queryIndices[i], queryCharacters.charAt(i));
            ans[i] = tree[1].maxLen;
        }

        return ans;
    }

    private void build(int idx, int l, int r) {
        tree[idx] = new Node();

        if (l == r) {
            tree[idx].len = 1;
            tree[idx].leftLen = 1;
            tree[idx].rightLen = 1;
            tree[idx].maxLen = 1;
            tree[idx].leftChar = arr[l];
            tree[idx].rightChar = arr[l];
            return;
        }

        int mid = (l + r) / 2;
        build(idx * 2, l, mid);
        build(idx * 2 + 1, mid + 1, r);

        tree[idx] = merge(tree[idx * 2], tree[idx * 2 + 1]);
    }

    private void update(int idx, int l, int r, int pos, char ch) {
        if (l == r) {
            arr[pos] = ch;
            tree[idx].leftChar = ch;
            tree[idx].rightChar = ch;
            return;
        }

        int mid = (l + r) / 2;

        if (pos <= mid)
            update(idx * 2, l, mid, pos, ch);
        else
            update(idx * 2 + 1, mid + 1, r, pos, ch);

        tree[idx] = merge(tree[idx * 2], tree[idx * 2 + 1]);
    }

    private Node merge(Node a, Node b) {
        Node res = new Node();

        res.len = a.len + b.len;
        res.leftChar = a.leftChar;
        res.rightChar = b.rightChar;

        res.leftLen = a.leftLen;
        if (a.leftLen == a.len && a.rightChar == b.leftChar)
            res.leftLen = a.len + b.leftLen;

        res.rightLen = b.rightLen;
        if (b.rightLen == b.len && a.rightChar == b.leftChar)
            res.rightLen = b.len + a.rightLen;

        res.maxLen = Math.max(a.maxLen, b.maxLen);
        if (a.rightChar == b.leftChar)
            res.maxLen = Math.max(res.maxLen, a.rightLen + b.leftLen);

        return res;
    }
}