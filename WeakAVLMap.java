package col106.assignment4.WeakAVLMap;

import java.util.Vector;
import java.util.Queue;
import java.util.LinkedList;
@SuppressWarnings("unchecked")

public class WeakAVLMap<K extends Comparable,V> implements WeakAVLMapInterface<K,V>{

	class Node{
        public K key;
		public V data;
        public int rank;
        public int rankdiff;
		public Node left;
        public Node right;
        public Node parent;

        public Node(){
            key = null;
            data = null;
            rank = 0;
            rankdiff = -5;
            left = null;
            right = null;
            parent = null;
        }
    }

    public Node root;
    public int rtct;

	public WeakAVLMap(){
		root = null;
        rtct = 0;
	}
	
	////////////////////////////////////////////////////////////////////////////////

    public Node recurins(K key, V value, Node node, boolean present, V extra){
        
        if (node.key == null){
            present = false;
            node.key = key;
            node.data = value;
            node.rank = 1;
            node.rankdiff = node.parent.rank - 1;
            //System.out.println(node.rankdiff);
            node.left = new Node();
            node.left.parent = node;
            node.left.rankdiff = 1;
            node.right =  new Node();
            node.right.parent = node;
            node.right.rankdiff = 1;
        }

        else if(key.compareTo(node.key)==0){
            extra = node.data;
            node.data = value;
            present = true;
        }
        
		else if(key.compareTo(node.key)<0){
			node.left = recurins(key, value, node.left, present, extra);
		}
		else{
			node.right = recurins(key, value, node.right, present, extra);
		}

		return node;
	}

    public Node findnode(Node root, K key){
        //System.out.println(key);
        if(root.key == null){
            return null;
        }
		else if(root.key.compareTo(key) == 0){
			//System.out.println("a");
			//System.out.println(root.data);
			return root;	
		}
		else if(key.compareTo(root.key)<0){
			//System.out.println("b");
			return findnode(root.left, key);	
		}
		else{
			//System.out.println("c");
			return findnode(root.right, key);	
		}
    }
    


    public void percolate(Node node){
        //System.out.println("help");
        if(node == root || node.rankdiff == 1){
            //System.out.println("endrec");
            return;
        }

        else{
            Node sibling = node.parent.left;
            
            if(node == sibling){
                sibling = node.parent.right;
            }

            if(sibling.rankdiff ==  1){
                //System.out.println("what even");

                node.parent.rank+=1;
                node.parent.rankdiff-=1;
                node.rankdiff +=1;
                sibling.rankdiff +=1;
                percolate(node.parent);
            }

            else{
                
                boolean dirup = false;///////////true for left
                boolean dirdown = false;//////////   "
                if(node == node.parent.left){
                    dirup = true;
                }

                if(node.left.rankdiff == 1){
                    dirdown = true;
                }

                ///start mehnat
                if(dirup && dirdown){
                    Node temp1 = node.parent.parent;

                    boolean isleft = false;
                    if(temp1!=null && temp1.left == node.parent){
                        isleft = true;
                    }

                    node.right.rankdiff -= 1;
                    node.parent.right.rankdiff -= 1;
                    node.rankdiff = node.parent.rankdiff;
                    node.parent.rank -= 1;
                    node.parent.rankdiff = 1;
                    
                    
                    node.parent.left = node.right;
                    node.right.parent = node.parent;
                    node.right = node.parent;
                    node.parent.parent = node;
                    node.parent = temp1;
                    if(temp1!=null){
                        if(isleft){
                            temp1.left = node;
                        }
                        else{
                            temp1.right = node;
                        }
                    }
                    else{
                        root = node;
                    }
                    
                    /*node.parent.left = node.right;
                    node.parent.left.parent = node.parent;
                    
                    node.right = node.parent;
                    node.parent.parent = node;
                    
                    node.parent = temp1;
                    
                    if(temp1!=null){
                        if(isleft){
                            temp1.left = node;
                        }
                        else{
                            temp1.right = node;
                        }
                    }
                    else{
                        root = node;
                    }
                    */
                    rtct+=1;
                }

                else if(!dirup && !dirdown){
                    //System.out.println("distraction max");
                    //System.out.println(node.key);
                    Node temp1 = node.parent.parent;

                    boolean isleft = false;
                    if(temp1!=null && temp1.left == node.parent){
                        isleft = true;
                    }

                    node.left.rankdiff -= 1;
                    node.parent.left.rankdiff -= 1;
                    node.rankdiff = node.parent.rankdiff;
                    node.parent.rank -= 1;
                    node.parent.rankdiff = 1;
                    

                    node.parent.right = node.left;
                    node.left.parent = node.parent;
                    node.left = node.parent;
                    node.parent.parent = node;
                    node.parent = temp1;
                    if(temp1!=null){
                        if(isleft){
                            temp1.left = node;
                        }
                        else{
                            temp1.right = node;
                        }
                    }
                    else{
                        root = node;
                    }

                    /*
                    node.parent.right = node.right;
                    node.parent.right.parent = node.parent;                    
                    node.left = node.parent;
                    node.parent.parent = node;
                    
                    node.parent = temp1;

                    if(temp1!=null){
                        if(isleft){
                            temp1.left = node;
                        }
                        else{
                            temp1.right = node;
                        }
                    }
                    else{
                        root = node;
                    }
                    */

                    rtct+=1;
                }

                else if(dirup && !dirdown){
                    
                    Node temp1 = node.parent.parent;

                    boolean isleft = false;
                    if(temp1!=null && temp1.left == node.parent){
                        isleft = true;
                    }

                    
                    node.rank-=1;
                    node.parent.rank-=1;
                    node.rankdiff = 1;
                    node.right.rank+=1;
                    node.right.rankdiff = node.parent.rankdiff;
                    node.parent.rankdiff = 1;
                    node.left.rankdiff -=1;
                    node.parent.right.rankdiff -=1;
                    
                    /*
                    node.right.rank+=1;
                    node.right.rankdiff = node.parent.rankdiff;

                    node.rank-=1;
                    node.rankdiff = 1;
                    node.parent.rank-=1;
                    node.parent.rankdiff = 1;

                    node.left.rankdiff-=1;
                    node.parent.right.rankdiff-=1;
                    */

                    node.parent.parent = node.right;
                    
                    node.right.parent = temp1;
                    if(temp1!=null){
                        if(isleft){
                            temp1.left = node.right;
                        }
                        else{
                            temp1.right = node.right;
                        }
                    }


                    node.parent.left = node.right.right;
                    node.right.right.parent = node.parent;
                    node.right.left.parent = node;
                    node.right.right = node.parent;
                    
                    node.parent = node.right;

                    node.right = node.right.left;
                    node.parent.left = node;
                    
                    if(temp1==null){
                        root = node.parent;
                        root.rankdiff = -1;
                    }

                    rtct+=2;
                }

                else{

                    Node temp1 = node.parent.parent;

                    boolean isleft = false;
                    if(temp1!=null && temp1.left == node.parent){
                        isleft = true;
                    }

                    node.rank-=1;
                    node.parent.rank-=1;
                    node.rankdiff = 1;
                    node.left.rank+=1;
                    node.left.rankdiff = node.parent.rankdiff;
                    node.parent.rankdiff = 1;
                    node.right.rankdiff -=1;
                    node.parent.left.rankdiff -=1;

                    

                    /*
                    node.left.rank+=1;
                    node.left.rankdiff = node.parent.rankdiff;

                    node.rank-=1;
                    node.rankdiff = 1;
                    node.parent.rank-=1;
                    node.parent.rankdiff = 1;

                    node.left.rankdiff-=1;
                    node.parent.left.rankdiff-=1;
                    */

                    node.parent.parent = node.left;
                    
                    node.left.parent = temp1;
                    if(temp1!=null){
                        if(isleft){
                            temp1.left = node.left;
                        }
                        else{
                            temp1.right = node.left;
                        }
                    }

                    node.parent.right = node.left.left;
                    node.left.left.parent = node.parent;
                    node.left.right.parent = node;
                    node.left.left = node.parent;
                    
                    node.parent = node.left;

                    node.left = node.left.right;
                    node.parent.right = node;
                    
                    if(temp1==null){
                        root = node.parent;
                        root.rankdiff = -1;
                    }

                    rtct+=2;
                }



            }
        }
    }

	public V put(K key, V value){
        
        if(root == null){

            root = new Node();
            root.parent = null;
            root.key = key;
            root.data =  value;
            root.rank = 1;
            root.rankdiff = -1;
            root.left = new Node();
            root.left.parent = root;
            root.left.rankdiff = 1;
            root.right =  new Node();
            root.right.parent = root;
            root.right.rankdiff = 1;

            return null;
        }
        
        else{
            //System.out.println("umm");
            V ret = null;
            boolean found = false;
            Node n = findnode(root, key);
            if(n != null){
                ret = n.data;
                n.data = value;
            }
            else{
                root = recurins(key, value, root, found, ret);

                n = findnode(root, key);

                if(n.rankdiff == 0){
                    //System.out.println("hi");
                    percolate(n);
                }
            }
            return ret; 
        }

	}


    public Node findleft(Node node){
        
        if(node.left.key == null){
            return node;
        }
        else{
            return findleft(node.left);
        }
    }

    public Node inosuc(Node node){
        if(node.right.key == null){
            return null;
        }
        else{
            return findleft(node.right);
        }
    }


    public void fixupper(Node node){
        if (node == root){
            return;
        }

        Node par = node.parent;
        Node sibling = par.left;
        if(sibling == node){
            sibling = par.right;
        }

        if((node!=root && node.rankdiff == 3) || (node.rank == 0 && node.rankdiff == 2 && sibling.rank == 0 && sibling.rankdiff == 2)){

            if(node.rank == 0 && node.rankdiff == 2 && sibling.rank == 0 && sibling.rankdiff == 2){
                node.rankdiff-=1;
                sibling.rankdiff-=1;
                par.rank-=1;
                par.rankdiff+=1;
                fixupper(par);
            }

            else if(sibling.rankdiff == 2){
                par.rank-=1;
                node.rankdiff-=1;
                sibling.rankdiff-=1;
                par.rankdiff+=1;
                fixupper(par);
            }
            else if(sibling.left.rankdiff == 2 && sibling.right.rankdiff == 2){
                par.rank-=1;
                par.rankdiff+=1;
                node.rankdiff-=1;
                sibling.rank-=1;
                sibling.left.rankdiff-=1;
                sibling.right.rankdiff-=1;
                fixupper(par);
            }
            else{
                boolean dirup = false;///////////true for left
                boolean dirdown = false;//////////   "
                if(sibling == sibling.parent.left){
                    dirup = true;
                }

                if(sibling.left.rankdiff == 1){
                    dirdown = true;
                }

                ///////////////////mehnat pt2
                if(dirup && dirdown){
                    Node temp1 = sibling.parent.parent;

                    boolean isleft = false;
                    if(temp1!=null && temp1.left == sibling.parent){
                        isleft = true;
                    }

                    sibling.left.rankdiff = 2;
                    sibling.rank+=1;
                    sibling.rankdiff = sibling.parent.rankdiff;
                    node.rankdiff-=1;
                    sibling.parent.rank-=1;
                    sibling.parent.rankdiff = 1;


                    sibling.right.parent = sibling.parent;
                    sibling.parent.left = sibling.right;
                    sibling.right = sibling.parent;
                    sibling.parent.parent = sibling;
                    sibling.parent = temp1;
                    if(temp1!=null){
                        if(isleft){
                            temp1.left = sibling;
                        }
                        else{
                            temp1.right = sibling;
                        }
                    }
                    else{
                        root = sibling;
                    }

                    fixupper(node);


                    /*sibling.parent.left = sibling.right;
                    sibling.right.parent = sibling.parent;
                    sibling.right = sibling.parent;
                    sibling.parent.parent = sibling;

                    sibling.parent = temp1;
                    
                    if(temp1!=null){
                        if(isleft){
                            temp1.left = sibling;
                        }
                        else{
                            temp1.right = sibling;
                        }
                    }
                    else{
                        root = sibling;
                    }*/

                    rtct+=1;
                }
                else if(!dirup && !dirdown){
                    Node temp1 = sibling.parent.parent;

                    boolean isleft = false;
                    if(temp1!=null && temp1.left == sibling.parent){
                        isleft = true;
                    }

                    sibling.right.rankdiff = 2;
                    sibling.rank+=1;
                    sibling.rankdiff = sibling.parent.rankdiff;
                    node.rankdiff-=1;
                    sibling.parent.rank-=1;
                    sibling.parent.rankdiff = 1;


                    sibling.left.parent = sibling.parent;
                    sibling.parent.right = sibling.left;
                    sibling.left = sibling.parent;
                    sibling.parent.parent = sibling;
                    sibling.parent = temp1;
                    if(temp1!=null){
                        if(isleft){
                            temp1.left = sibling;
                        }
                        else{
                            temp1.right = sibling;
                        }
                    }
                    else{
                        root = sibling;
                    }
                    fixupper(node);

                    /*
                    sibling.parent.right = sibling.left;
                    sibling.left.parent = sibling.parent;
                    sibling.left = sibling.parent;
                    sibling.parent.parent = sibling;

                    sibling.parent = temp1;
                    if(temp1!=null){
                        if(isleft){
                            temp1.left = sibling;
                        }
                        else{
                            temp1.right = sibling;
                        }
                    }
                    else{
                        root = sibling;
                    }*/


                    rtct+=1;
                }
                else if(dirup && !dirdown){
                    
                    Node temp1 = sibling.parent.parent;

                    boolean isleft = false;
                    if(temp1!=null && temp1.left == sibling.parent){
                        isleft = true;
                    }

                    sibling.right.rank +=2;
                    sibling.right.rankdiff = sibling.parent.rankdiff;
                    sibling.left.rankdiff-=1;
                    sibling.rank-=1;
                    sibling.parent.rank-=2;
                    sibling.rankdiff = 2;
                    sibling.parent.rankdiff = 2;
                    sibling.parent.right.rankdiff = 1;
                    
                    sibling.right.right.parent = sibling.parent;
                    sibling.parent.left = sibling.right.right;
                    
                    sibling.right.parent = temp1;
                    sibling.right.right = sibling.parent;
                    sibling.parent.parent = sibling.right;
                    sibling.right.left.parent = sibling;
                    sibling.right = sibling.right.left;
                    sibling.parent = sibling.parent.parent;
                    sibling.parent.left = sibling;

                    if(temp1!=null){
                        if(isleft){
                            temp1.left = sibling.parent;;
                        }
                        else{
                            temp1.right = sibling.parent;
                        }
                    }
                    else{
                        root = sibling.parent;
                        sibling.parent.rankdiff = -1;
                    }

                    fixupper(node);
                    
                    rtct+=2;
                }
                else{
                    Node temp1 = sibling.parent.parent;

                    boolean isleft = false;
                    if(temp1!=null && temp1.left == sibling.parent){
                        isleft = true;
                    }

                    sibling.left.rank +=2;
                    sibling.left.rankdiff = sibling.parent.rankdiff;
                    sibling.right.rankdiff-=1;
                    sibling.rank-=1;
                    sibling.parent.rank-=2;
                    sibling.rankdiff = 2;
                    sibling.parent.rankdiff = 2;
                    sibling.parent.left.rankdiff = 1;
                    
                    sibling.left.left.parent = sibling.parent;
                    sibling.parent.right = sibling.left.left;
                    
                    sibling.left.parent = temp1;
                    sibling.left.left = sibling.parent;
                    sibling.parent.parent = sibling.left;
                    sibling.left.right.parent = sibling;
                    sibling.left = sibling.left.right;
                    sibling.parent = sibling.parent.parent;
                    sibling.parent.right = sibling;

                    if(temp1!=null){
                        if(isleft){
                            temp1.left = sibling.parent;
                        }
                        else{
                            temp1.right = sibling.parent;
                        }
                    }
                    else{
                        root = sibling.parent;
                        sibling.parent.rankdiff = -1;
                    }  
                    
                    fixupper(node);
                    rtct+=2;
                }



            }
        }
    }

    public void remlow(Node node){
        if(node == root){
            root = root.right;
            if(root.key == null){
                root = null;
            }
            else{
                root.rankdiff = -1;
            }
        }
        else{
            boolean isleftofpar = false;

            Node child = node.left;

            if(child.rankdiff == 2){
                child = node.right;
            }
        
            if(node == node.parent.left){
                isleftofpar = true;
            }
            
            if(isleftofpar){///////////??????????????????????????????
                node.parent.left = child;
            }
            else{
                node.parent.right = child;
            }
            
            child.parent = node.parent;
            node = null;
            //child.parent.rank = 1;
            //child.parent.left.rankdiff = 1;
            child.rankdiff = child.parent.rank - child.rank;

            Node sibling = child.parent.left;
            if(isleftofpar){
                sibling = child.parent.right;
            }
            
            if(child.key == null && sibling.key == null){
                sibling.rankdiff-=1;
                child.rankdiff-=1;
                child.parent.rank = 1;
                child.parent.rankdiff +=1;
                fixupper(child.parent);
                
            }
            else{
                fixupper(child);
            }
        }
       
    }

	public V remove(K key){
		V ret = null;
		if(root!=null){

		
			Node node = findnode(root, key);
			
			if(node!=null){
				ret = node.data;
				Node succ = inosuc(node);
				
				if(succ == null){
					remlow(node);
				}
				else{
					node.key = succ.key;
					node.data = succ.data;
					remlow(succ);
				}
				
			}
		}

		return ret;
	}

	public V get(K key){
        if(root!=null){
            
            Node n = findnode(root,key);
            if(n == null){
                return null;
            }
            else{
                return n.data;
            }
        }
        else{
            return null;
        }
	}


    public void fill(Node node, K key1, K key2 , Vector<V> vecv, Vector<K> veck){
        if(node.key == null){
            return;
        }
        else if(node.key.compareTo(key1)<0){
            fill(node.right,key1,key2,vecv,veck);
        }
        else if(node.key.compareTo(key2)>0){
            fill(node.left,key1,key2,vecv,veck);
        }
        else{
            vecv.add(node.data);
            veck.add(node.key);
            fill(node.left,key1,key2,vecv,veck);
            fill(node.right,key1,key2,vecv,veck);
        }
    }


    int part(K[] veck, V[] vecv, int low, int high){
        K pivot = veck[high];  
        int i = (low-1);
        for (int j=low; j<high; j++) 
        {
            if (veck[j].compareTo(pivot) < 0) 
            { 
                i+=1;
                K tk = veck[i];
                V tv = vecv[i];
                veck[i] = veck[j]; 
                vecv[i] = vecv[j];
                veck[j] = tk;
                vecv[j] = tv;
            } 
        } 
        i+=1;
        K tk = veck[i];
        V tv = vecv[i];
        veck[i] = veck[high];
        vecv[i] = vecv[high];
        veck[high] = tk;
        vecv[high] = tv; 
  
        return i; 
    } 
  
  
    public void qsort(K[] veck, V[] vecv, int low, int high){ 
        if (low < high){
            int pi = part(veck, vecv, low, high);
            qsort(veck, vecv, low, pi-1); 
            qsort(veck, vecv, pi+1, high); 
        } 
    }


	public Vector<V> searchRange(K key1, K key2){
        Vector<V> vecv = new Vector();
        if(root!=null){    
            Vector<K> veck = new Vector();
            fill(root,key1,key2,vecv,veck);
            int n = vecv.size();
            K[] arrk = (K[])new Comparable[n];
            V[] arrv = (V[])new Object[n];
            for(int i = 0; i<n; i++){
                arrk[i] = veck.get(i);
                arrv[i] = vecv.get(i);
            }
            qsort(arrk, arrv, 0, n-1);
            Vector<V> ret = new Vector();
            for(int i = 0; i<n; i++){
                ret.add(arrv[i]);
            }
            //ret.addAll(arrv);
            return ret;
        }
        else{
            return vecv;
        }
	}

	public int rotateCount(){
		return rtct;
	}
    public int recht(Node node){
        if(node.key == null){
            return 0;
        }
        else{
        
            //if(node.right.rankdiff == node.left.rankdiff){
            int lefth = recht(node.left);
            int righth = recht(node.right);

            if (lefth > righth) {
                return lefth + 1;
            } 
            else {
                return righth + 1;
            }
            //}
            /*
            else{
                int lrd = node.left.rankdiff;
                int rrd = node.right.rankdiff;
                if(lrd<rrd){
                    return 1 + recht(node.left);
                }
                else{
                    return 1 + recht(node.right);
                }
            }
            */
        }
    }

	public int getHeight(){
        if(root == null){
            return 0;
        }
        else{
            return recht(root);
        }
	}

	public Vector<K> BFS(){
        Vector<K> vec = new Vector();
        Queue<Node> q = new LinkedList<Node>();
        if(root!=null){        
            q.add(root);
        }

        while (q.isEmpty() == false){
            
            Node node = q.poll();
            //System.out.println(node.key + ", " + node.data);
            vec.add(node.key);
            if (node.left.key != null) { 
                q.add(node.left); 
            } 

            if (node.right.key != null) { 
                q.add(node.right); 
            } 
        } 
        return vec;
    }

}
