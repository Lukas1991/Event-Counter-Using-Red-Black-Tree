//package cc150;

import java.util.*;
import java.io.*;
import java.lang.*;

public class bbst {

	private Node root;   //this is the root of the BST.
	
	private static final boolean RED = false;
	private static final boolean BLACK = true;
	
	public class Node{   //class Node of red-black tree
		
		boolean color;
		private int key;
		private int val;
		private Node left;
		private Node right;
		private Node parent;
		private int N;
		
		public Node(int key, int val,boolean color,int N,Node parent,Node left,Node right)
		{
			this.key=key;
			this.val=val;
			this.color=color;
			this.N =N;
			this.parent=parent;
			this.left=left;
			this.right=right;
		}
	}
	
	private Node getparent(Node t)   //Get the parent of the Node t; 
	{
		return t!=null?t.parent:null;
	}
	
	private boolean isRed(Node t)    //Whether the Node  color is Red.
	{
		if((t!=null)&&(t.color==RED))
			return true;
		return false;
	}
	
	private boolean isBlack(Node t)   // Whether the Node color is Black.
	{
		if((t!=null)&&(t.color==BLACK))
			return true;
		return false;
	}
	
	private void setBlack(Node t)    //Set the color of the Node to black
	{
		if(t!=null)
			 t.color=BLACK;
	}
	
	private void setRed(Node t)    //Set the color of the Node to red.
	{
	    if(t!=null)
	    	t.color=RED;
		
	}
	
	private boolean getcolor(Node t)  //Get the color of Node
	{
		if(t!=null)
			 return t.color;
		return BLACK;
	}
	
	private void setparent(Node t,Node parent)  //Set the parent of Node t to parent.
	{
		if(t!=null)
			t.parent=parent;
	}
	
	private void setcolor(Node t,boolean color)  //Set the Node color
	{
		if(t!=null)
			t.color=color;
	}
	
	private Node search(Node t,int key) //Given key, search the Node which has the key in the tree, if it is not in the tree,return null.
	{
	     while(t!=null)
	     {
	    	 if(key<t.key)
	    		 t=t.left;
	    	 else if(key>t.key)
	    		 t=t.right;
	    	 else
	    		 return t;
	     }
	     return null;
	}
	
	private int getmaxdepth(Node t)  //Get the depth of the tree;
	{
		if(t==null)
			return 0;
		else
		{
			int ldepth=getmaxdepth(t.left);
			int rdepth=getmaxdepth(t.right);
			if(ldepth>rdepth)
				return ldepth+1;
			else
				return rdepth+1;
		}
	}
	
	
	
	
	private void  Rotateleft(Node x)   //helper function, left Rotate of the Tree
	{
		//the right child of node x is y.
		Node y= x.right;
		
		
		//we can set the "left child " of y to the "right child " of x;
		//if the left child of y is not null, we can set x as the parent of the left child of y. 
		x.right=y.left;
		if(y.left!=null)
			y.left.parent=x;
		
		//we can set the parent of x as the parent of y.
		y.parent=x.parent;
		
		if(x.parent==null)
			this.root=y;              //if the parent of x is null, we can set the y as the root
		else if(x.parent.left==x)
			x.parent.left=y;         //if x is its parents' left child,we set y as the left child of its parent
		else
			x.parent.right=y;        //if x is its parents' right child,we set y as the right child of its parent
		
		y.left=x;        //we set x as the left child of y
		x.parent=y;      //we set x's parent as y;
	}
		
	
	
	
	
	private void Rotateright(Node y)   //helper function, right Rotate of the tree
	{
		//the left child of y is x
		Node x=y.left;
		
		//we can set the "left child " of y to the "right child " of x;
		//if the right child of x is not null, we can set y as the parent of the right child of x. 
		y.left =x.right;
		if(x.right!=null)
			  x.right.parent=y;
		
		//we can set the parent of y as the parent of x.
		x.parent = y.parent;
		
		if(y.parent==null)
			this.root=x;               //if the parent of y is null,we can set the x as the root
		else if(y==y.parent.right)
			y.parent.right=x;          //if y is its parents' right child, we set x as the right child of its parent
		else
			y.parent.left=x;           //if y is its parents' left child, we set x as the left child of its parent
		
		//we set y as the right child of x
		x.right=y;
		// we set x as the parent of y
		y.parent=x;
	}
		
	
	 private void insert(Node t)  //insert Node t to the tree.
	 {
		 Node y=null;
		 Node x=this.root;
		 //we should find a place to insert node t
		 while(x!=null)
		 {
			 y=x;
			 if(t.key<x.key)
			     x=x.left;
			 else
				 x=x.right;
		 }
		 
		 t.parent=y;
		 //we should decide whether t is the right or left child of its parent.
		 if(y==null)
		     this.root = t;
		 else
		 {
			 if(t.key<y.key)
				 y.left=t;
			 else
				 y.right=t;
		 }
         
		 t.color=RED;
		 
		 FixInsert(t);
	 }
	
	 public void insert(int key,int val)  //insert event counter with id key and count val to the tree
	 {
		 Node t= new Node(key,val,BLACK,0,null,null,null);  //create  a new node with key and val
		 if(t!=null)
			 insert(t);
	 }
	
	 
	 //helper function, after insert operation, 
	 //we first treat res-black tree as a binary search tree,
	 //then we insert the new node with color red,which is said in the lecture
	 //then the request of the red-black tree "the child node of a red node must be black node" may be violated
	 //so we should fix up the problem through a list of operations below
	 
	 private void FixInsert(Node t)    
	 {
		 Node parent;
		 Node grandparent;
		 
		 while((parent=getparent(t))!=null&&isRed(parent)) //if parent node of t exits, and parent node's color is red.
		 {
			 grandparent = getparent(parent);
			 
			 if(grandparent.left==parent)     //if parent node is the left child of the grandparent
			 {
				 Node uncle = grandparent.right;
				 if((uncle!=null)&&isRed(uncle)) //uncle node's color is red.
				 {
					 setcolor(uncle,BLACK);
					 setcolor(parent,BLACK);
					 setcolor(grandparent,RED);
					 t=grandparent;
					 continue;
				 }
				 if(parent.right==t)       //uncle node's color is black and t is the right node of its parent
				 {
					 Node temp;
					 Rotateleft(parent);
					 temp=parent;
					 parent=t;
					 t=temp;
				 }
				 //uncle node's color is black and t is the left node of its parent
					setcolor(parent,BLACK); 
					setcolor(grandparent,RED);
					Rotateright(grandparent);
			 }
			 else
			 {    //if parent node is the right child of the grandparent
				 Node uncle = grandparent.left;
				 if((uncle!=null)&&(isRed(uncle)))   //uncle node's color is red.
				 {
					 setcolor(uncle,BLACK);
					 setcolor(parent,BLACK);
					 setRed(grandparent); 
					 t=grandparent;
					 continue;
				 }
				 if(parent.left==t&&(uncle!=null)&&isBlack(parent)) //uncle node's color is black and t is the left node of its parent
				 {
					 Node temp;
					 Rotateright(parent);
					 temp=parent;
					 parent=t;
					 t=temp;
				 }                                       //uncle node's color is black and t is the right node of its parent
					 setcolor(parent,BLACK);   
					 setcolor(grandparent,RED);
					 Rotateleft(grandparent);
			 }
		 }
		
			 setcolor(this.root,BLACK);   //set the color of the root Node to BLACK
		 }
		 
	//delete a node from red-black tree
	//first we should delete it as a binary search tree,then through a list of operations and get a new red-black tree
	//There are three cases when we delete a node in the binary-search tree
	// 1. if the node is a leaf node, we delete it 
	// 2. if the node has only one child, we delete the node and use its child to replace it.
	// 3. if the node has two child,we should first find the next node of it(inorder), and copy the next node to this node and then delete this node.
	//   if the next node is the leaf node, we use case 1 and if the next node has only one child,we use the case 2.
	 
	  
	  private void remove(Node t)
	  {
		  Node child;
		  Node parent;
		  boolean color;
		  
 		  if((t.left!=null)&&(t.right!=null))  //if the node has two child,case 3
		  {
			  Node replace =t;   
			  
			  replace=replace.right;   //we should find a next node of it (inorder sequence)
			  while(replace.left!=null)
				   replace=replace.left;
			  
			  if((getparent(t)!=null)&&getparent(t).left==t)  //if node t is not the root node and is the left child of its parent
			  {
	               getparent(t).left=replace;
			  }
			  else if((getparent(t)!=null)&&(getparent(t).right==t)) //if node t is not the root node and is the right child of its parent
			  {
				  getparent(t).right=replace;
			  }
			  else
			  {                             //if node t is the root node.
				  this.root=replace;
			  }
			  
			  
			  child = replace.right;         //the next node's child,it cannot have a left child, because it is the inorder next Node of t
			  parent=getparent(replace);     //the next node's parent;
			  color = getcolor(replace);     //store the color of the next node of t
			  
			  if(parent==t)     //if the next node of t's parent is t
			  {
				  parent=replace;
			  }
			  else             //if the next node of t's parent is not t
			  {
				  if(child!=null)
					  setparent(child,parent);  //set the parent of the child node
				  
				  parent.left=child;            //set the parent's left child
				  replace.right=t.right;        //set the next node's right child
				  setparent(t.right,replace);
			  }
			  
			  replace.parent=t.parent;   //set the next node's parent,color and left child
			  replace.color=t.color;
			  replace.left=t.left;
			  t.left.parent=replace;
			  
			  if(color==BLACK)   //if the color is black,we should fix the red-black tree
				  fixremove(child,parent); 
			  
			  t=null;
			  return;
		  }
		  
		  if(t.left!=null)      //if node t has only one child,case 2;
			  child = t.left;
		  else
			  child=t.right;
		  
		  parent=t.parent;    //store t's parent and color
		  color=t.color;
		  
		  if(child!=null)         
			  child.parent=parent;
		  
		  if((parent!=null)&&(parent.left==t))  //if node t is not the root node and it is the left child of its parent 
		  {
			  parent.left=child;
		  }
		  else if((parent!=null)&&(parent.right==t))  //if node t is not the root node and it is the right child of its parent 
		  {
			  parent.right=child;
		  }
		  else
			  this.root=child;   //id node is the root,change root
		  
		  if(color==BLACK)       //if the color is black,we should fix the red-black tree
			  fixremove(child,parent);
		  
		  t=null;  
	  }
		 
		 
	  public void remove(int key,int val)  //Remove the Node with key and val from the tree.
	  {
			 Node t;
			 if((t=search(root,key))!=null)
				 remove(t);
	  }
		 
	     //helper function, after remove operation, 
		 //we first treat res-black tree as a binary search tree,
		 //if we remove the new node with color black
		 //then a subtree becomes one black pointer (node) deficient
		 //so we should fix up the problem through a list of operations below
	  
	  
	  private void fixremove(Node t, Node parent) 
	  {
		  Node brother;
		  
		  while((t==null||isBlack(t))&&(t!=this.root))   //if this node is blacka ans it is not the rot node
		  {
			  if(parent.left==t)  //if it is the left child of its parent
			  {
				  brother=parent.right;
				  if(isRed(brother))  //if its brother is red
				  {
					  setcolor(brother,BLACK);
					  setcolor(parent,RED);
					  Rotateleft(parent);
					  brother=parent.right;
				  }
				  if ((brother.left==null || isBlack(brother.left)) &&
			                (brother.right==null || isBlack(brother.right))) 
				  {//if its brother is black and its brother's child are all black
				      setcolor(brother,RED);
				      t=parent;
				      parent=getparent(t);
				  }
				  else
				  {   //if its brother is black,and its brother's left child is red and right child is black
					  if (brother.right==null || isBlack(brother.right)) {  
		                    setcolor(brother.left,BLACK);
		                    setcolor(brother,RED);
		                    Rotateright(brother);
		                    brother = parent.right;
		                }
					  //if its brother is black,and its brother's right child is red
		                setcolor(brother, getcolor(parent));
		                setcolor(parent,BLACK);
		                setcolor(brother.right,BLACK);
		                Rotateleft(parent);
		                t = this.root;
		                break;
		            }
				  }
			  else
			  {   //if it is the right child of its parent
				  brother = parent.left;
				  if(isRed(brother))   //if the brother is red
				  {
					 setcolor(brother,BLACK);
					 setcolor(parent,RED);
					 Rotateright(parent);
					 brother=parent.left;
				  }
				  if ((brother.left==null || isBlack(brother.left)) &&
			                (brother.right==null || isBlack(brother.right))) 
				  {//if its brother is black and its brother's child are all black
			                setcolor(brother,RED);
			                t = parent;
			                parent = getparent(t);
			       } 
				  else {
					  if (brother.left==null || isBlack(brother.left)) 
					  { //if its brother is black,and its brother's left child is red and right child is black
		                    setcolor(brother.right,BLACK);
		                    setcolor(brother,RED);
		                    Rotateleft(brother);
		                    brother = parent.left;
		              }
					//if its brother is black,and its brother's right child is red
		                setcolor(brother, getcolor(parent));
		                setcolor(parent,BLACK);
		                setcolor(brother.left,BLACK);
		                Rotateright(parent);
		                t = this.root;
		                break;
		            } 
			     }  
			  }
			  
			  if(t!=null)
				  setBlack(t);  //set the Node t to black
		  }
		  

	  
	   public void increase(int key,int val)  // find the Node of key,if not exist, insert it,if exist increase its value by val
	   {
		   Node temp= search(root,key);  //search the tree,find the Node with key
		   if(temp==null)   //if not exist ,insert it
		   {
			   insert(key,val);
			   System.out.println(val);
		   }
		   else
		   {
			   int newval=temp.val+val;
			   temp.val=newval;
			   System.out.println(temp.val);
		   }
	   }
	  
	   public void reduce(int key,int val)   // find the Node of key,if not exist, print 0,if exist decrease its value by val,if below or equal 0,remove it from the tree
	   {
		   Node temp=search(root,key);   //search the tree,find the Node with key
		   if(temp==null)               //if not exist
			   System.out.println(0);
		   else if(temp.val<=val)   // after reduce,if below or equals 0
			   {
			   remove(temp);
			   System.out.println(0);
			   }
		   else
		   {
			  int newval = temp.val - val;
			  temp.val=newval;
			  System.out.println(temp.val);
		   }
	   }
	  
	   
	   public void Count(int key)      //get the val of the key.
	   {
		   Node temp=search(root,key);  //search the tree,get the Node of key
		   if(temp==null)                   //if not exist
			   System.out.println(0);
		   else
			   System.out.println(temp.val);
	   }
	  
	   private Node Max(Node x)   //get the Node with maximum key 
	   {
		   if(x.right==null) return x;
		   else     return Max(x.right);
	   }
	   
	   private Node Min(Node x)   //get the Node with minimum key 
	   {
		   if(x.left==null) return x;
		   else   return Min(x.left);
	   }
	   
	   
	   
	    public void Next(Node x,int key)    //Helper function, helpers to get the Node with the lowest key which is greater than key
	    {
	    	 Node temp=null;
	         while(x!=null)
	         {
	        	 if(x.key<=key)
	        		 x=x.right;
	        	 else
	        	 {
	        		 temp=x;
	        		 x=x.left;
	        	 }
	         }
	         
	         if(temp!=null)
	        	 System.out.println(temp.key+" "+temp.val);
	         else
	        	 System.out.println(0+" "+0);
	    }
	  
	    public void Next(int key)   //Print the key and the val of the Node with the lowest key that is greater than the key.
	    {
	    	Next(root,key);
	    }
	  
	    public void Previous(Node x,int key)   //Helper function, helpers to get the Node with the greatest key which is less than key
	    {
	    	Node temp=null;
	    	
	    	while(x!=null)
	    	{
	           if(key<=x.key)
	        	   x=x.left;
	           else
	           {
	        	   temp=x;
	        	   x=x.right;
	           }
	    	}
	    	
	    	if(temp!=null)
	    		System.out.println(temp.key+" "+temp.val);
	    	else
	    		System.out.println(0+" "+0);
	    }
	    
	    public void Previous(int key)   //Print the key and the val of the Node with the greatest key which is less than key
	    {
	    	Previous(root,key);
	    }
	    
	  
	    public void InRange(int key1,int key2)    //Print the total count of keys between key1 and key2(inclusively)
	    {
	    	int sum=0;
	    	Queue<Integer> queue=new LinkedList<Integer>();
	    	InRange(root,queue,key1,key2);
	    	int len=queue.size();
	    	for(int i=0;i<len;i++)
	    	{
	    		int tmp=queue.poll();
	    		sum +=tmp;
	    	}
	    	System.out.println(sum);	
	    }
	  
	    private void InRange(Node x,Queue<Integer> queue, int key1, int key2)   //Helper function, helpers to get all nodes which key is between key1 and key2
	    {
	    	if(x==null)  return;
	    	if(x.key>key1) InRange(x.left,queue,key1,key2);
	    	if((x.key>=key1)&&(x.key<=key2)) queue.add(x.val);
	    	if(x.key<key2) InRange(x.right,queue,key1,key2);
	    }
	    
	    
	    public void buildtree(int[] key,int[] val)   //Build a Red-black tree from the input(sorted list), first it build a binary search tree, then assign color to each node;
	    {
	    	int len=key.length;
	    	root=buildtree(key,val,0,len-1);   //build a balanced binary search tree.
	    	int maxdepth = getmaxdepth(root);
	    	buildcolor(root,maxdepth,1);     //assign color to it.
	    }
	    
	    public Node buildtree(int[] key,int[] val,int l,int r)   //Build a balanced binary search tree from a sorted list.
	    {
	    	if(l>r)
	    		return null;
	    	
	    	int mid = l +(r-l)/2;
	    	Node root = new Node(key[mid],val[mid],BLACK,0,null,null,null);
	    	Node ll = buildtree(key,val,l,mid-1);
	    	Node rr = buildtree(key,val,mid+1,r);
	    	root.left=ll;
	    	root.right=rr;
	    	if(ll!=null)
	    	{
	    		ll.parent=root;
	    	}
	    	if(rr!=null)
	    	{
	    	rr.parent=root;
	    	}
	    	return root;
	    }
	    
	    public void buildcolor(Node t,int maxdepth,int level)   // assign color to the balanced search tree.
	    {
	    	if(t==null)
	    		return;
	    	if(level==maxdepth)
	    		t.color=RED;
	    	buildcolor(t.left,maxdepth,level+1);
	    	buildcolor(t.right,maxdepth,level+1);
	    }
	    

	  
	    public static void main(String[] args) throws Exception
	    {
	    	bbst eventcounter = new bbst();
	    	Scanner reader = new Scanner(new FileInputStream(args[0]));
	    	int len=reader.nextInt();
	    	int[] newkey =new int[len];
	    	int[] newval =new int[len];
	    	
	    	int key3;
	    	int val3;
	    	
	       // long startTime = System.currentTimeMillis();

	    	for(int i=0;i<len;i++)                       //read from file
	    	{
	    		newkey[i]=reader.nextInt();
	    		newval[i]=reader.nextInt();
	    		//key3 = reader.nextInt();
	    		//val3 = reader.nextInt();
	    		//eventcounter.insert(key3,val3);
	    	}
	    	

	    	
            eventcounter.buildtree(newkey,newval);
	    	
            
	    	
	    	//long endTime   = System.currentTimeMillis();
	    	//long totalTime = endTime - startTime;
	    	//System.out.println("Initialize Time "+totalTime);
	    	
	    	//FileInputStream fis = new FileInputStream("commands.txt");     
	    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));    //read from command file
	    	
	    	//startTime = System.currentTimeMillis();
	    	
	    	String line = null;
	    	int key,val;
	    	int key1,key2;
	    	while (!(line = br.readLine()).equals("quit")) {                   //execute command 
	    		String tepStr[] = line.split(" ");
	    		String command=tepStr[0];
	    		switch(command){
	    			case "increase":
	    			{
	    				key = Integer.parseInt(tepStr[1]);
	    				val = Integer.parseInt(tepStr[2]);
	    				eventcounter.increase(key,val);
	    				break;
	    			}
	    			case "reduce":
	    			{
	    				key = Integer.parseInt(tepStr[1]);
	    				val = Integer.parseInt(tepStr[2]);
	    				eventcounter.reduce(key, val);
	    				break;
	    			}
	    			case "inrange":
	    			{
	    				key1 = Integer.parseInt(tepStr[1]);
	    				key2 = Integer.parseInt(tepStr[2]);
	    				eventcounter.InRange(key1,key2);
	    				break;
	    			}
	    			case "next":
	    			{
	    				key = Integer.parseInt(tepStr[1]);
	    				eventcounter.Next(key);
	    				break;
	    			}
	    			case "previous":
	    			{
	    				key = Integer.parseInt(tepStr[1]);
	    				eventcounter.Previous(key);
	    				break;
	    			}
	    			case "count":
	    			{
	    				key = Integer.parseInt(tepStr[1]);
	    				eventcounter.Count(key);
	    				break;
	    			}
	    			default:
	    				System.out.println("Invalid command");
	    			
	    		}
	    			
	    	}
	     
	    	//endTime   = System.currentTimeMillis();
	    	//totalTime = endTime - startTime;
	    	//System.out.println("Commands Time "+totalTime);
	    	
	    	br.close();
	    	
	    }
	  
	  

	  }
		 
		 
		 
		 
		 
	 
	
	
	
	
	
	
	
	
	

