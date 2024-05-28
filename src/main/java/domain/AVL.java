package domain;

import util.Utility;

public class AVL implements Tree {
    public BTreeNode root; //unica entrada al arbol

    public AVL(){
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
        this.root = add(root, element,"root");

    }

    /**
     * else if(node.left==null){
     *    node.left = add(node.left, element);
     * }else if(node.right==null){
     *     node.right = add(node.right, element);
     * }
     * */
    private BTreeNode add(BTreeNode node, Object element, String sequence){
        if(node==null){ //si el arbol esta vacio
            node = new BTreeNode(element,"Added as" + sequence);
        }else{
            //debemos establecer algun criterio de insercion
            if(Utility.compare(element,node.data)<0) //si es par inserte por la izq
                node.left = add(node.left, element, sequence+"/left" );
            else if (Utility.compare(element,node.data)>0)//si es impart inserte por la der
                node.right = add(node.right, element, sequence +"/right");
        }
        //se determina si se requiere rebalanceo
        node = reBalance(node, element);
        return node;
    }

    private BTreeNode reBalance(BTreeNode node, Object element) {
        int balance = getBalanceFactor(node);

        //Left Left Case
        if (balance>1 && util.Utility.compare(element, node.data)<0) {
            node.path += ". Simple Right Rotation";
            return rightRotate(node);
        }


        //Right Right Case
        if (balance<-1 && util.Utility.compare(element, node.data)>0) {
            node.path += ". Simple Left Rotation";
            return leftRotate(node);
        }

        //Left Right Case
        if (balance>1 && util.Utility.compare(element, node.data)>0) {
            node.path += ". Double Left/Right Rotation";
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        //Right Left Case
        if (balance<-1 && util.Utility.compare(element, node.data)<0) {
            node.path += ". Double Right/Left Rotation";
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    private int getBalanceFactor(BTreeNode node) {
        if(node == null)return 0;
        else return height(node.left) - height(node.right);
    }
    private BTreeNode leftRotate(BTreeNode node) {
        BTreeNode node1 = node.right;
        BTreeNode node2 = node1.left;
        //se realiza la rotacion (perform rotation)
        node1.left = node;
        node.right = node2;
        return node1;
    }
    private BTreeNode rightRotate(BTreeNode node) {
        BTreeNode node1 = node.left;
        BTreeNode node2 = node1.right;
        //se realiza la rotacion (perform rotation)
        node1.right = node;
        node.left = node2;
        return node1;
    }

    @Override
    public void remove(Object element) throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Search Tree is empty");

        root = remove(root,element);
    }

    private BTreeNode remove(BTreeNode node, Object element){
        if(node!=null) {
            if (Utility.compare(element,node.data)<0)
                node.left = remove(node.left,element);
            else if (Utility.compare(element,node.data)>0)
                node.right = remove(node.right, element);
            else if (Utility.compare(node.data, element) == 0) {


                //caso 1 el nodo es una hoja
                if (node.left == null && node.right == null) {
                    return null;
                }
                //caso 2 el nodo solo tiene un hijo
                else if (node.left == null && node.right != null) {
                    return node.right;
                }else if(node.left!= null && node .right==null){
                    return node.left;
                }else
                    //Caso 3 el nodo tiene dos hijos
                    if (node.left!= null && node.right!= null){
                        Object value = min(node.right);
                        node.data=value;
                        node.right= remove(node.right, value);
                    }
            }
            //luego de suprimir se determina si requiere o no
            //
            node = reBalance(node,element);
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
            //si no fucnina se pone un -1 al return que esta en la linea de arriba
        else if(Utility.compare(node.data, element)==0) return counter;
        else if (Utility.compare(element,node.data)<0)
            return height(node.left,element,++counter);
        else return height(node.right,element,++counter);

        //devuelve altura de el nodo correspondiente
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
        String result = "AVL BINARY SEARCH TREE TOUR...\n";
        result+="PreOrder: "+preOrder(root)+"\n";
        result+="InOrder: "+inOrder(root)+"\n";
        result+="PostOrder: "+postOrder(root)+"\n";
        return result;
    }



}