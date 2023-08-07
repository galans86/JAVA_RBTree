public class RBTree<T extends Comparable<T>> {
    Node root;

    public boolean add(T value){
        if (root == null){
            root = new Node( value);
            root.color = Color.black;
            return true;
        }
        return addNode(root,value) != null;
    }

    private Node addNode(Node node, T value ){
        if(node.value.compareTo(value)==0){
            return null;
        }

        if(node.value.compareTo(value) > 0){
            if( node.left == null ){
                node.left = new Node(value);
                return node;
            }else{
                Node result = addNode(node.left,value);
                node.left = rebalance(node.left);
                return result;
            }
        }
        if(node.value.compareTo(value) < 0){
            if (node.right == null ){
                node.right = new Node(value);
                return node;
            }else {
                Node result = addNode(node.right,value);
                node.right = rebalance(node.right);
                return result;
            }
        }
        return null;
    }

    private void colorSwap(Node node){
        //Оба потомка каждого красного узла — чёрные
        node.right.color = Color.black;
        node.left.color = Color.black;
        node.color = Color.red;
    }

    private Node leftSwap(Node node){
        Node left = node.left;
        Node between = left.right;
        left.right = node;
        node.left = between;
        left.color = node.color;
        node.color = Color.red;
        return  left;
    }

    private Node rightSwap(Node node){
        Node right = node.right;
        Node between = right.left;
        right.left = node;
        node.right = between;
        right.color = node.color;
        node.color = Color.red;
        return right;
    }

    private Node rebalance(Node node){
        Node result = node;
        boolean needRebalance;
        do{
            needRebalance = false;
            //Красные ноды могут быть только левым ребенком
            if (result.right != null && result.right.color == Color.red
                && (result.left == null || result.left.color == Color.black)){
                needRebalance = true;
                result = rightSwap(result); //в приоритете перед colorSwap
            }
            //У краcной ноды все дети черного цвета
            if(result.left!= null && result.left.color == Color.red
               && result.left.left != null && result.left.left.color == Color.red ){
                needRebalance = true;
                result = leftSwap(result);
            }
            //Красные ноды могут быть только левым ребенком
            if(result.left != null && result.left.color == Color.red &&
               result.right != null && result.right.color == Color.red ){
                needRebalance = true;
                colorSwap(result);
            }
        }while(needRebalance);
        return result;
    }
    private class Node {
        T value;
        Node left;
        Node right;
        Color color;

        Node() { this.color = Color.red; }
        Node(T value){
            this.value = value;
            this.color = Color.red;
        }

    }

    enum Color {red, black};
}
