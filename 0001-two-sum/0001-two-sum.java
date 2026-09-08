class Solution {
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> mp = new HashMap<>();

        for(int i=0; i<nums.length; i++){
            
            int sum = target - nums[i];

            if(mp.containsKey(sum)){
                return new int[]{mp.get(sum), i};
            }
            mp.put(nums[i], i);
        }
        return new int[]{};
    }
}