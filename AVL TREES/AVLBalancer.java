package impl;

public class AVLBalancer<K extends Comparable<K>,V> implements Balancer<K,V,AVLInfo<K,V>> {

    public BSTMap<K, V, AVLInfo<K, V>>.Node putFixup(BSTMap<K, V, AVLInfo<K, V>>.Node fix) {
        //System.out.println("before: " + fix);
    	fix.getInfo().recompute();

         // add code here
        int balance = fix.getInfo().getBalance();
        
        //Left-Left case or Left-Right case.
        if (balance > 1) {
        	//if left-right — convert to left-left
        	if(fix.getLeft().getInfo().getBalance() < 0) {
        		fix.leftRotateLeft();
        	}
        	//fix left-left
        	fix = fix.rotateRight();
        }
        
        //Right-Right case or Right-Left case
        if (balance < -1) {
        	//if right-left — convert to right-right
        	if(fix.getRight().getInfo().getBalance() > 0) {
        		fix.rightRotateRight();
        	}
        	//fix right-right
        	fix = fix.rotateLeft();
        }
        //System.out.println("after: " + fix);
        return fix;
    }

    public BSTMap<K, V, AVLInfo<K, V>>.Node removeFixup(BSTMap<K, V, AVLInfo<K, V>>.Node fix) {
        return putFixup(fix);
    }

    public AVLInfo<K, V> newInfo(BSTMap<K, V, AVLInfo<K, V>>.Node node) {
        return new AVLInfo<K,V>(0, 0, node);
    }
    public AVLInfo<K, V> nullInfo(BSTMap<K, V, AVLInfo<K, V>>.Node node) {
        return newInfo(node);
    }

    public void rootFixup(BSTMap<K, V, AVLInfo<K, V>>.Node fix) { }

}
