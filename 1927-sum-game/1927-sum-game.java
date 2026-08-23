class Solution {
    public boolean sumGame(String num) {
        int n = num.length();

        int leftSum = 0, rightSum = 0;
        int leftQ = 0, rightQ = 0;

        for (int i = 0; i < n / 2; i++) {
            char c = num.charAt(i);
            if (c == '?')
                leftQ++;
            else
                leftSum += c - '0';
        }

        for (int i = n / 2; i < n; i++) {
            char c = num.charAt(i);
            if (c == '?')
                rightQ++;
            else
                rightSum += c - '0';
        }

        // Odd number of '?' -> Alice gets one extra move.
        if ((leftQ + rightQ) % 2 == 1)
            return true;

        // Bob wins only if this equation is satisfied.
        return leftSum - rightSum != (rightQ - leftQ) / 2 * 9;
    }
}