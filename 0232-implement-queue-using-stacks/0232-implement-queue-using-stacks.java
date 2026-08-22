class MyQueue {
    Stack<Integer> st = new Stack<>();
    Stack<Integer> h = new Stack<>();

    public MyQueue() {
        
    }
    
    public void push(int x){
    st.push(x);
        
    }
    
    public int pop() {
         while(st.size()>1){
            h.push(st.pop());
         }
         int x = st.pop();
         while(h.size() > 0){
            st.push(h.pop());
         }
         return x;
    }
    
    public int peek() {
            while(st.size()>1){
            h.push(st.pop());
         }
         int x = st.peek();
         while(h.size() > 0){
            st.push(h.pop());
         }
         return x;
    }
    
    public boolean empty() {
        if(st.size()==0) return true;
        else return false; 
    }
}

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */