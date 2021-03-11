package tasks.first;

import java.util.ArrayDeque;

public class FirstTaskSolution implements FirstTask {
    ArrayDeque<Integer> way = new ArrayDeque<>();
    boolean[] pickedVertex;
    String outputString = "";


    @Override
    public String breadthFirst(boolean[][] adjacencyMatrix, int startIndex) {
        if (way.isEmpty()) way.addFirst(startIndex);

        addVertex(adjacencyMatrix[startIndex],0);
        outputString += Integer.toString(way.pollFirst());
        pickedVertex[startIndex] = true;
        if (way.size() == 1 && way.peekFirst() != startIndex)
            return Integer.toString(way.pollFirst());
        return (breadthFirst(adjacencyMatrix, way.peekFirst()));
    }

    @Override
    public Boolean validateBrackets(String s) {
        if (s.isEmpty()) return true;
        ArrayDeque<Character> deque = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '[' || s.charAt(i) == '(' || s.charAt(i) == '{') {
                deque.addFirst(s.charAt(i));
                continue;
            }


            if (s.charAt(i)==']' && deque.peekFirst()=='[') deque.pollFirst();
            if (s.charAt(i)==')' && deque.peekFirst()=='(') deque.pollFirst();
            if (s.charAt(i)=='}' && deque.peekFirst()=='{') deque.pollFirst();
        }
        return deque.isEmpty();
    }

    @Override
    public Long polishCalculation(String s) {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        String dig = "";
        int i = 0;
        while (charCheck(s.charAt(i))) {
            if (s.charAt(i) != ' ') dig += s.charAt(i);
            else{
                try {
                    deque.addLast(Integer.parseInt(dig));
                    dig = "";
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            i++;
        }
        for (int j = i; j < s.length(); j++) {
            if (s.charAt(j) != ' ') {
                if (deque.size() >= 2   ){
                    int a = deque.pollLast();
                    int b = deque.pollLast();
                    deque.addLast(compute(a, b, s.charAt(j)));
                }
                else throw new IndexOutOfBoundsException();
            }
        }
        if (deque.size()>1) throw  new IllegalArgumentException();
        else return Long.valueOf(deque.pollLast());
    }

    // ~~~~~~~~~~~~~~~ мои методы ~~~~~~~~~~~~~~~~

    public boolean charCheck(char x) {
        return x != '+' && x != '-' && x != '*' && x != '/';
    }

    public Integer compute(int a, int b, char c) {
        switch (c) {
            case '+': return a + b;
            case '-': return b - a;
            case '*': return a * b;
            case '/': return b / a;
        }return null;
    }

    public void addVertex(boolean[] row, int i) {
        if (row[i] && !pickedVertex[i]) {
            way.addLast(i);
            pickedVertex[i]=true;
        }
        if (i < row.length-1) addVertex(row,++i);
    }

}
