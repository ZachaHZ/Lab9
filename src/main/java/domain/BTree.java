package domain;

public class BTree implements Tree {
    public BTreeNode root; //unica entrada al arbol

    public BTree(){
        this.root = null;
    }

    @Override
    public int size() throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Tree is empty");
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
            throw new TreeException("Binary Tree is empty");
        }
        return binarySearch(root, element);
    }

    //método interno
    private boolean binarySearch(BTreeNode node, Object element){
        if(node==null)
            return false;
        else
        if(util.Utility.compare(node.data, element)==0)
            return true; //ya lo encontro
        else
            return binarySearch(node.left, element)
                    || binarySearch(node.right, element);
    }

    @Override
    public void add(Object element) {
        //this.root = add(root, element);
        this.root = add(root, element, "root");
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
            int value = util.Utility.getRandom(10);
            if(value%2==0) //si es par inserte por la izq
                node.left = add(node.left, element);
            else //si es impart inserte por la der
                node.right = add(node.right, element);
        }
        return node;
    }

    private BTreeNode add(BTreeNode node, Object element, String path){
        if(node==null){ //si el arbol esta vacio
            node = new BTreeNode(element, path);
        }else{
            //debemos establecer algun criterio de insercion
            int value = util.Utility.getRandom(10);
            if(value%2==0) //si es par inserte por la izq
                node.left = add(node.left, element, path+"/left");
            else //si es impart inserte por la der
                node.right = add(node.right, element, path+"/right");
        }
        return node;
    }

    @Override
    public void remove(Object element) throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Tree is empty");

        root = remove(root,element);
    }

    private BTreeNode remove(BTreeNode node, Object element){
        if(node!=null) {
            if (util.Utility.compare(node.data, element) == 0) {


                //caso 1 el nodo es una hoja
                if (node.left == null && node.right == null) {
                    return null;

                }
                //caso 2 el nodo solo tiene un hijo
                else if (node.left == null && node.right != null) {
                    node.right = newPath(node.right, node.path);
                    return node.right;

                }else if(node.left!= null && node .right==null){
                    //node.left = new Path(node.left, node.path);
                    return node.left;
                }
                //Caso 3 el nodo tiene dos hijos
                if (node.left!= null && node.right!= null){
                    Object value = getLeaf(node.right);
                    node.data=value;
                    node.right= removeLeaf(node.right, value);
                }
            }
            node.left = remove(node.left, element);
            node.right = remove(node.right, element);
        }
        return node;
    }

    private BTreeNode newPath(BTreeNode node, String parentPath) {
        if (node != null) {
            //  path del nodo
            node.path = parentPath + (parentPath.equals("root") ? "/right" : "/left");
            //  de los hijos
            cargarPaths(node, node.path);
        }
        return node;
    }


    private void cargarPaths(BTreeNode node, String path) {
        if (node != null) {
            node.path = path;
            cargarPaths(node.left, path + "/left");
            cargarPaths(node.right, path + "/right");
        }
    }


    private Object getLeaf(BTreeNode node) {
        Object aux;
        if(node==null){
            return null;
        }else //preguntamos si es una hoja
            if ( node.left == null && node.right==null)
                return  node.data;
            else {
                aux = getLeaf(node.left);
                if (aux==null){
                    aux= getLeaf(node.right);
                }
                return aux;
            }

    }
    private BTreeNode removeLeaf(BTreeNode node, Object value) {
        if ( node ==null) return null;
        else if(node.left==null && node.right==null && util.Utility.compare(node.data, value)==0)
            return null;
        else{
            node.left=removeLeaf(node.left, value);
            node.right=removeLeaf(node.right, value);

        }
        return node;
    }

    @Override
    public int height(Object element) throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Tree is empty");
        }

        return height(root, element, 0);
    }

    private int height(BTreeNode node, Object element, int counter) {

        if(node == null) return 0; //significa q el elemento no existe
        else if(util.Utility.compare(node.data, element)==0) return counter;
        else return Math.max(height(node.left, element, ++counter), height(node.right, element,counter));
        //devuelve altura de   en el nodo correspondiente
    }

    @Override
    public int height() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Tree is empty");
        return height(root)-1;

    }

    private int height(BTreeNode node){
        if (node ==null) return 0;
        else return Math.max(height(node.left), height(node.right))+1; //suma 1 porque baja un nivel
    }

    @Override
    public Object min() throws TreeException {
        return null;
    }

    @Override
    public Object max() throws TreeException {
        return null;
    }

    @Override
    public String preOrder() throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Tree is empty");
        }
        return preOrder(root)+"\n";
    }

    //node-left-right
    private String preOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result =  node.data+", ";//+"("+node.path+") ";
            result += preOrder(node.left);
            result += preOrder(node.right);
        }
        return result;
    }

    @Override
    public String inOrder() throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Tree is empty");
        }
        return inOrder(root)+"\n";
    }

    //left-node-right
    private String inOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result  = inOrder(node.left);
            result += node.data+", ";
            result += inOrder(node.right);
        }
        return result;
    }

    @Override
    public String postOrder() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Tree is empty");
        }
        return postOrder(root) + "\n";
    }

    //left-right-node
    private String postOrder(BTreeNode node){
        String result="";
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
            return "Binary Tree is empty";
        String result = "BINARY TREE TOUR...\n";
        result+="PreOrder: "+preOrder(root)+"\n";
        result+="InOrder: "+inOrder(root)+"\n";
        result+="PostOrder: "+postOrder(root)+"\n";
        return result;
    }


    public String toString2() throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Tree is empty");
        }
        return toString2(root)+"\n";
    }

    //left-node-right
    private String toString2(BTreeNode node){
        String result="";
        if(node!=null){
            result  = inOrder(node.left);
            result += node.data+" "+node.path+", ";
            result += inOrder(node.right);
        }
        return result;
    }

    public void printHeightOfEachElement() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Tree is empty");
        }
        printHeightOfEachElement(root);
    }

    private void printHeightOfEachElement(BTreeNode node) throws TreeException {
        if (node != null) {
            System.out.println("Element: " + node.data + " Height: " + height(node.data));
            printHeightOfEachElement(node.left);
            printHeightOfEachElement(node.right);
        }
    }
}
