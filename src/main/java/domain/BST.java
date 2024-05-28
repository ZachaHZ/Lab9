package domain;

import util.Utility;

public class BST implements Tree {
    public BTreeNode root; //unica entrada al arbol

    public BST(){
        this.root = null;
    }

    @Override
    public int size() throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Search Tree is empty");
        }
        return size(root);
    }

    private int size(BTreeNode node){
        if(node==null)
            return 0;
        else
            return 1+size(node.left)+size(node.right);
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean contains(Object element) throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Search Tree is empty");
        }
        return binarySearch(root, element);
    }

    //método interno
    private boolean binarySearch(BTreeNode node, Object element){
        if(node==null)
            return false;
        else
        if(Utility.compare(node.data, element)==0)
            return true; //ya lo encontro
        else if (util.Utility.compare(element,node.data)<0)
            return binarySearch(node.left,element);
        else if (util.Utility.compare(element,node.data)>0)
            return binarySearch(node.right,element);
        return false;
    }

    @Override
    public void add(Object element) {
        this.root = add(root, element);

    }

    /**
     * else if(node.left==null){
     *    node.left = add(node.left, element);
     * }else if(node.right==null){
     *     node.right = add(node.right, element);
     * }
     * */
    private BTreeNode add(BTreeNode node, Object element){
        if(node==null){ //si el arbol esta vacio
            node = new BTreeNode(element);
        }else{
            //debemos establecer algun criterio de insercion
            if(util.Utility.compare(element,node.data)<0) //si es par inserte por la izq
                node.left = add(node.left, element);
            else if (util.Utility.compare(element,node.data)>0)//si es impart inserte por la der
                node.right = add(node.right, element);
        }
        return node;
    }

    @Override
    public void remove(Object element) throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Search Tree is empty");

        root = remove(root,element);
    }

    private BTreeNode remove(BTreeNode node, Object element){
        if(node!=null) {
            if (util.Utility.compare(element,node.data)<0)
                node.left = remove(node.left,element);
            else if (util.Utility.compare(element,node.data)>0)
                node.right = remove(node.right, element);
            else if (Utility.compare(node.data, element) == 0) {


                //caso 1 el nodo es una hoja
                if (node.left == null && node.right == null) {
                    return null;

                }
                //caso 2 el nodo solo tiene un hijo
                else if (node.left == null && node.right != null) {
                    //node.right = new Path(node.right, node.path);
                    return node.right;

                }else if(node.left!= null && node .right==null){
                    //node.left = new Path(node.left, node.path);
                    return node.left;
                }
                //Caso 3 el nodo tiene dos hijos
                if (node.left!= null && node.right!= null){
                    Object value = min(node.right);
                    node.data=value;
                    node.right= remove(node.right, value);
                }
            }

        }
        return node;
    }


    @Override
    public int height(Object element) throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Search Tree is empty");
        }

        return height(root, element, 0);
    }

    //si no fucnina se pone un -1
    private int height(BTreeNode node, Object element, int counter) {
        if(node == null) return 0; //significa q el elemento no existe
        else if(Utility.compare(node.data, element)==0) return counter;
        else if (util.Utility.compare(element,node.data)<0)
            return height(node.left,element,++counter);
        else return height(node.right,element,++counter);

        //devuelve altura de   en el nodo correspondiente
    }
    @Override
    public Object min() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Search Tree is empty");
        return height(root);

    }
    private Object min(BTreeNode node){
        if (node.left !=null)
            return min(node.left);
        return node.data;
    }

    @Override
    public Object max() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Search Tree is empty");
        return max(root);

    }
    private Object max(BTreeNode node){
        if (node.right !=null)
            return max(node.right);
        return node.data;
    }


    @Override
    public int height() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Search Tree is empty");
        return height(root)-1;

    }

    private int height(BTreeNode node){
        if (node ==null) return 0;
        else return Math.max(height(node.left), height(node.right))+1; //suma 1 porque baja un nivel
    }


    @Override
    public String preOrder() throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Search Tree is empty");
        }
        return preOrder(root)+"\n";
    }

    //node-left-right
    private String preOrder(BTreeNode node){
        String result="";
        if(node!=null){
            //   result =  node.data+"("+node.path+") ";
            result =  node.data + ", ";
            result += preOrder(node.left);
            result += preOrder(node.right);
        }
        return result;
    }

    @Override
    public String inOrder() throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Search Tree is empty");
        }
        return inOrder(root)+"\n";
    }

    //left-node-right
    private String inOrder(BTreeNode node){
        String result=" ";
        if(node!=null){
            result  = inOrder(node.left);
            result += node.data+" ";
            result += inOrder(node.right);
        }
        return result;
    }

    @Override
    public String postOrder() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Search Tree is empty");
        }
        return postOrder(root) + "\n";
    }



    //left-right-node
    private String postOrder(BTreeNode node){
        String result=" ";
        if(node!=null){
            result = postOrder(node.left);
            result += postOrder(node.right);
            result += node.data+", ";

        }
        return result;
    }


    //preOrder: recorre el árbol de la forma: nodo-izq-der
    //inOrder: recorre el árbol de la forma: izq-nodo-der
    //postOrder: recorre el árbol de la forma: izq-der-nodo
    @Override
    public String toString() {
        if(isEmpty())
            return "Binary Search Tree is empty";
        String result = "BINARY SEARCH TREE TOUR...\n";
        result+="PreOrder: "+preOrder(root)+"\n";
        result+="InOrder: "+inOrder(root)+"\n";
        result+="PostOrder: "+postOrder(root)+"\n";
        return result;
    }



}