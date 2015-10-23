public class LinkedBinaryTreeNode<E>
{
	protected E data;
	protected LinkedBinaryTreeNode<E> parent;
	protected LinkedBinaryTreeNode<E> left;
	protected LinkedBinaryTreeNode<E> right;
    protected int balance;
	protected int key;
	public LinkedBinaryTreeNode(int key)
	{
		this.key=key;
		this.data = null;
		this.parent = null;
		this.left = null;
		this.right = null;
	}
	public LinkedBinaryTreeNode(int key, E data)
	{
		this.key=key;
		this.data = data;
		this.parent = null;
		this.left = null;
		this.right = null;
	}
	public LinkedBinaryTreeNode(int key,
								E data,
								LinkedBinaryTreeNode<E> parent,
								LinkedBinaryTreeNode<E> leftChild,
								LinkedBinaryTreeNode<E> rightChild)
	{
		this.key=key;
		this.data=data;
		this.parent = parent;
		this.left = leftChild;
		this.right = rightChild;
	}
	public E getData()
	{
		return data;
	}
	public void setData(E data)
	{
		this.data = data;
	}
	public LinkedBinaryTreeNode<E> getParent()
	{
		return parent;
	}
	public void setParent(LinkedBinaryTreeNode<E> parent)
	{
		this.parent=parent;
	}
	public LinkedBinaryTreeNode<E> getLeft()
	{
		return left;
	}
	public void setLeft(LinkedBinaryTreeNode<E> childNode)
	{
		for (LinkedBinaryTreeNode<E> n =this; n!=null;n=n.parent)
		{
			if(n==childNode)
			{
				throw new IllegalArgumentException();
			}
		}
		if(this.left!=null)
		{
			left.parent=null;
		}
		if (childNode!=null)
		{
			childNode.parent = this;
		}
		this.left = childNode;
	}
	public LinkedBinaryTreeNode<E> getRight()
	{
		return right;
	}
	public void setRight(LinkedBinaryTreeNode<E> childNode)
	{
		for(LinkedBinaryTreeNode<E> n = this; n!=null;n=n.parent)
		{
			if (n==childNode)
			{
				throw new IllegalArgumentException();
			}
		}
		if(right!=null)
		{
			right.parent=null;
		}
		if(childNode!=null)
		{
			childNode.parent=this;
		}
		this.right=childNode;
	}
	public void removeFromParent()
	{
		if(parent!=null)
		{
			if(parent.left==this)
			{
				parent.left=null;
			}
			else if (parent.right==this)
			{
				parent.right=null;
			}
			this.parent=null;
		}
	}
	public int numChildren()
	{
		int count=0;
		if(this.getLeft()!=null)
			count++;
		if(this.getRight()!=null)
			count++;
		return count;
	}
}
