package Class.ExpressionTree;

import java.util.Stack;

class TreeNode {
    String value;
    TreeNode left, right;

    TreeNode(String value) {
        this.value = value;
        left = right = null;
    }
}

public class ExpressionTree {

    // 후위 연산식에서 트리로 변환
    public static TreeNode postfixToTree(String expression) {
        Stack<TreeNode> stack = new Stack<>();
        expression = expression.replaceAll("\\s", "");  // 공백 제거

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isLetterOrDigit(c)) {
                stack.push(new TreeNode(String.valueOf(c)));
            } else if (isOperator(c)) {
                TreeNode right = stack.pop();
                TreeNode left = stack.pop();
                TreeNode node = new TreeNode(String.valueOf(c));
                node.left = left;
                node.right = right;
                stack.push(node);
            }
        }

        return stack.pop();
    }

    // 전위 연산식에서 트리로 변환
    public static TreeNode prefixToTree(String expression) {
        Stack<TreeNode> stack = new Stack<>();
        expression = expression.replaceAll("\\s", "");  // 공백 제거

        // 뒤에서부터 순회
        for (int i = expression.length() - 1; i >= 0; i--) {
            char c = expression.charAt(i);

            if (Character.isLetterOrDigit(c)) {
                stack.push(new TreeNode(String.valueOf(c)));
            } else if (isOperator(c)) {
                TreeNode left = stack.pop();
                TreeNode right = stack.pop();
                TreeNode node = new TreeNode(String.valueOf(c));
                node.left = left;
                node.right = right;
                stack.push(node);
            }
        }

        return stack.pop();
    }

    // 중위 연산식에서 트리로 변환
    public static TreeNode infixToTree(String expression) {
        Stack<TreeNode> operandStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();
        expression = expression.replaceAll("\\s", ""); // 공백 제거

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isLetterOrDigit(c)) {
                operandStack.push(new TreeNode(String.valueOf(c)));
            } else if (c == '(') {
                operatorStack.push(c);
            } else if (c == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    char operator = operatorStack.pop();
                    TreeNode right = operandStack.pop();
                    TreeNode left = operandStack.pop();
                    TreeNode node = new TreeNode(String.valueOf(operator));
                    node.left = left;
                    node.right = right;
                    operandStack.push(node);
                }
                operatorStack.pop();  // '(' 제거
            } else { // 연산자 처리
                while (!operatorStack.isEmpty() && precedence(c) <= precedence(operatorStack.peek())) {
                    char operator = operatorStack.pop();
                    TreeNode right = operandStack.pop();
                    TreeNode left = operandStack.pop();
                    TreeNode node = new TreeNode(String.valueOf(operator));
                    node.left = left;
                    node.right = right;
                    operandStack.push(node);
                }
                operatorStack.push(c);
            }
        }

        while (!operatorStack.isEmpty()) {
            char operator = operatorStack.pop();
            TreeNode right = operandStack.pop();
            TreeNode left = operandStack.pop();
            TreeNode node = new TreeNode(String.valueOf(operator));
            node.left = left;
            node.right = right;
            operandStack.push(node);
        }

        return operandStack.pop();
    }

    private static int precedence(char c) {
        if (c == '+' || c == '-') return 1;
        if (c == '*' || c == '/') return 2;
        if (c == '^') return 3;
        return -1;
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    public static void inorderTraversal(TreeNode root) {
        if (root != null) {
            inorderTraversal(root.left);
            System.out.print(root.value + " ");
            inorderTraversal(root.right);
        }
    }

    public static void preorderTraversal(TreeNode root) {
        if (root != null) {
            System.out.print(root.value + " ");
            preorderTraversal(root.left);
            preorderTraversal(root.right);
        }
    }

    public static void postorderTraversal(TreeNode root) {
        if (root != null) {
            postorderTraversal(root.left);
            postorderTraversal(root.right);
            System.out.print(root.value + " ");
        }
    }

    public static void main(String[] args) {
        // 예시 입력 연산식
        String infixExpr = "(A * B + C / D) / (E + F - G / H)";
        String postfixExpr = "AB*CD/+EF+GH/-/";
        String prefixExpr = "/+*AB/CD-E+FGH";

        // 연산식 타입에 맞는 트리 생성
        System.out.println("중위 연산식 트리 생성:");
        TreeNode infixRoot = infixToTree(infixExpr);
        inorderTraversal(infixRoot);
        System.out.println("\n전위 순회:");
        preorderTraversal(infixRoot);
        System.out.println("\n후위 순회:");
        postorderTraversal(infixRoot);

        System.out.println("\n\n후위 연산식 트리 생성:");
        TreeNode postfixRoot = postfixToTree(postfixExpr);
        inorderTraversal(postfixRoot);
        System.out.println("\n전위 순회:");
        preorderTraversal(postfixRoot);
        System.out.println("\n후위 순회:");
        postorderTraversal(postfixRoot);

        System.out.println("\n\n전위 연산식 트리 생성:");
        TreeNode prefixRoot = prefixToTree(prefixExpr);
        inorderTraversal(prefixRoot);
        System.out.println("\n전위 순회:");
        preorderTraversal(prefixRoot);
        System.out.println("\n후위 순회:");
        postorderTraversal(prefixRoot);
    }
}
