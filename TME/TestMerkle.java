package TME;

import java.util.ArrayList;

public class TestMerkle {

	public static void main(String[] args) {
		
		byte[] a = "1".getBytes();
		byte[] b = "2".getBytes();
		byte[] c = "3".getBytes();
		byte[] d = "4".getBytes();
		byte[] e = "5".getBytes();
		byte[] f = "6".getBytes();
		byte[] g = "7".getBytes();
		byte[] h = "8".getBytes();
		
		ArrayList<byte[]> l = new ArrayList<byte[]>();
		l.add(a);
		l.add(b);
		l.add(c);
		l.add(d);
		l.add(e);
		l.add(f);
		l.add(g);
		l.add(h);
		
		/*
		MerkleTree res = TME2.create_merkle_tree(l);
		StringBuilder sb = new StringBuilder();
        for (byte by : res.getValue()) {
            sb.append(String.format("%02x", by));
        }
		System.out.println(sb.toString());
		*/
		MerkleTree tree = TME2.create_merkle_tree(l);
		MerkleTree leaf = tree.getDroite().getGauche().getGauche();// feuille 5
		MerkleTree res = TME2.witness(tree, leaf);
		
		System.out.println(res.toString());
		
	}

}
